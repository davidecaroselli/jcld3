package it.davidecaroselli.jcld3;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

class UTF8Charset {

    private static Charset charset = null;

    static Charset get() {
        if (charset == null) {
            try {
                charset = Charset.forName("UTF-8");
            } catch (UnsupportedCharsetException e) {
                throw new Error("UTF-8 not supported");
            }
        }

        return charset;
    }

}
