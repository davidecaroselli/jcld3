package com.github.davidecaroselli.jcld3;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CLD3Test {

    @Test
    public void english() {
        CLD3 cld3 = new CLD3();
        LanguageResult result = cld3.findLanguage("This text is written in English.");

        assertEquals("en", result.language);
        assertTrue(result.probability > .9f);
        assertTrue(result.reliable);
        assertTrue(result.proportion > .9f);
    }

    @Test
    public void german() {
        CLD3 cld3 = new CLD3();
        LanguageResult result = cld3.findLanguage("Text in deutscher Sprache verfasst.");

        assertEquals("de", result.language);
        assertTrue(result.probability > .9f);
        assertTrue(result.reliable);
        assertTrue(result.proportion > .9f);
    }

    @Test
    public void mixed() {
        CLD3 cld3 = new CLD3();
        LanguageResult[] results = cld3.findTopLanguages("This piece of text is in English. Този текст е на Български.", 3);

        assertEquals(2, results.length);

        LanguageResult enResult = results[0];
        LanguageResult bgResult = results[1];

        assertEquals("en", enResult.language);
        assertTrue(enResult.probability > .9f);
        assertTrue(enResult.reliable);
        assertTrue(enResult.proportion < .6f);

        assertEquals("bg", bgResult.language);
        assertTrue(bgResult.probability > .9f);
        assertTrue(bgResult.reliable);
        assertTrue(bgResult.proportion < .6f);
    }
}
