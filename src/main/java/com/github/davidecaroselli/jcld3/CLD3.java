package com.github.davidecaroselli.jcld3;

import com.github.davidecaroselli.jcld3.utils.NativeUtils;

import java.io.IOException;

public class CLD3 {

    static {
        try {
            NativeUtils.loadLibraryFromJar("jcld3");
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    private static final int MAX_NUM_BYTES = 1000;

    private static byte[] toBytes(String text) {
        byte[] bytes = text.getBytes(UTF8Charset.get());

        if (bytes.length > MAX_NUM_BYTES) {
            byte[] result = new byte[MAX_NUM_BYTES];
            System.arraycopy(bytes, 0, result, 0, result.length);
            bytes = result;
        }

        return bytes;
    }

    private final long nativeHandle;

    public CLD3() {
        this.nativeHandle = init();
    }

    private native long init();

    public synchronized LanguageResult findLanguage(String text) {
        if (text == null)
            throw new NullPointerException("text");

        XLanguageResult result = new XLanguageResult();
        findLanguage(nativeHandle, toBytes(text), result);
        return result.toLanguageResult();
    }

    public synchronized LanguageResult[] findTopLanguages(String text, int limit) {
        if (text == null)
            throw new NullPointerException("text");
        if (limit <= 0)
            throw new IllegalArgumentException("limit must be greater than 0");

        XLanguageResult[] results = new XLanguageResult[limit];
        for (int i = 0; i < results.length; i++)
            results[i] = new XLanguageResult();

        findTopNMostFreqLanguage(nativeHandle, toBytes(text), results);
        return XLanguageResult.toLanguageResults(results);
    }

    private native void findLanguage(long handle, byte[] text, XLanguageResult result);

    private native void findTopNMostFreqLanguage(long handle, byte[] text, XLanguageResult[] result);

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        dispose(nativeHandle);
    }

    private native long dispose(long handle);

}
