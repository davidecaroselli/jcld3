package it.davidecaroselli.jcld3.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.ProviderNotFoundException;

public class FileUtils {

    public static void deleteQuietly(File file) {
        try {
            forceDelete(file);
        } catch (IOException e) {
            // ignore
        }
    }

    public static void forceDelete(File file) throws IOException {
        if (!file.exists())
            return;

        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null && children.length > 0) {
                for (File child : children)
                    forceDelete(child);
            }

            if (!file.delete())
                throw new IOException("Failed to delete folder: " + file);
        } else {
            if (!file.delete())
                throw new IOException("Failed to delete file: " + file);
        }
    }

    public static boolean isPosixCompliant() {
        try {
            return FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
        } catch (FileSystemNotFoundException | ProviderNotFoundException | SecurityException e) {
            return false;
        }
    }
}
