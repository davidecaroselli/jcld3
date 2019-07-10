package com.github.davidecaroselli.jcld3;

import java.util.Arrays;

class XLanguageResult {

    public int language = -1;
    public float probability = 0;
    public boolean reliable = false;
    public float proportion = 0;

    static LanguageResult[] toLanguageResults(XLanguageResult[] xResults) {
        int size = 0;
        for (XLanguageResult result : xResults) {
            if (LanguageRegistry.isValid(result.language))
                size++;
        }

        int i = 0;
        LanguageResult[] results = new LanguageResult[size];

        for (XLanguageResult result : xResults) {
            LanguageResult parsed = result.toLanguageResult();
            if (parsed != null)
                results[i++] = parsed;
        }

        Arrays.sort(results, (o1, o2) -> -o1.compareTo(o2));

        return results;
    }

    LanguageResult toLanguageResult() {
        if (LanguageRegistry.isValid(language))
            return new LanguageResult(LanguageRegistry.toString(language), probability, reliable, proportion);
        else
            return null;
    }
}
