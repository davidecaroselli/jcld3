package com.github.davidecaroselli.jcld3;

class LanguageRegistry {

    private static final String[] LANGUAGES = {
            "eo", "co", "eu", "ta", "de", "mt", "ps", "te", "su", "uz", "zh-Latn", "ne",
            "nl", "sw", "sq", "hmn", "ja", "no", "mn", "so", "ko", "kk", "sl", "ig",
            "mr", "th", "zu", "ml", "hr", "bs", "lo", "sd", "cy", "hy", "uk", "pt",
            "lv", "iw", "cs", "vi", "jv", "be", "km", "mk", "tr", "fy", "am", "zh",
            "da", "sv", "fi", "ht", "af", "la", "id", "fil", "sm", "ca", "el", "ka",
            "sr", "it", "sk", "ru", "ru-Latn", "bg", "ny", "fa", "haw", "gl", "et",
            "ms", "gd", "bg-Latn", "ha", "is", "ur", "mi", "hi", "bn", "hi-Latn", "fr",
            "yi", "hu", "xh", "my", "tg", "ro", "ar", "lb", "el-Latn", "st", "ceb",
            "kn", "az", "si", "ky", "mg", "en", "gu", "es", "pl", "ja-Latn", "ga", "lt",
            "sn", "yo", "pa", "ku"
    };

    static boolean isValid(int languageId) {
        return languageId >= 0 && languageId < LANGUAGES.length;
    }

    static String toString(int languageId) {
        return isValid(languageId) ? LANGUAGES[languageId] : null;
    }

}
