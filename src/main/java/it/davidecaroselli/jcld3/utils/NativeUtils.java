package it.davidecaroselli.jcld3.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

public class NativeUtils {

    private static String getLibraryFilename(String libname) {
        String osName = System.getProperty("os.name").toLowerCase();
        boolean isMacOS = osName.startsWith("mac os x");

        return "lib" + libname + (isMacOS ? ".dylib" : ".so");
    }

    private static InputStream getInputStream(String filename) throws UnsatisfiedLinkError {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        InputStream stream = loader.getResourceAsStream(filename);
        if (stream == null)
            throw new UnsatisfiedLinkError("Library not found in JAR: " + filename);
        return stream;
    }

    public static void loadLibraryFromJar(String libname) throws IOException, UnsatisfiedLinkError {
        String libFilename = getLibraryFilename(libname);

        File tempDirectory = Files.createTempDirectory("jar_native").toFile();
        File libFile = new File(tempDirectory, libFilename);

        try (InputStream stream = getInputStream(libFilename)) {
            Files.copy(stream, libFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (UnsatisfiedLinkError | IOException e) {
            FileUtils.deleteQuietly(tempDirectory);
            throw e;
        }

        try {
            System.load(libFile.getAbsolutePath());
        } finally {
            if (FileUtils.isPosixCompliant()) {
                FileUtils.deleteQuietly(tempDirectory);
            } else {
                libFile.deleteOnExit();
                tempDirectory.deleteOnExit();
            }
        }
    }

}
