package br.java.a08_autocomplete.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static String[] REPLACES = {"a", "e", "i", "o", "u", "c"};
    public static Pattern[] PATTERS = null;

    public static void compilePatterns() {
        PATTERS[0] = Pattern.compile("[âãáàä]", Pattern.CASE_INSENSITIVE);
        PATTERS[1] = Pattern.compile("[éèêë]", Pattern.CASE_INSENSITIVE);
        PATTERS[2] = Pattern.compile("[íìîï]", Pattern.CASE_INSENSITIVE);
        PATTERS[3] = Pattern.compile("[óòôõö]", Pattern.CASE_INSENSITIVE);
        PATTERS[4] = Pattern.compile("[úùûü]", Pattern.CASE_INSENSITIVE);
        PATTERS[5] = Pattern.compile("[ç]", Pattern.CASE_INSENSITIVE);
    }

    public static String removeAcentos(String text) {

        if (PATTERS == null) {
            compilePatterns();
        }

        String result = text;

        for(int i =0; i < PATTERS.length; i++) {
            Matcher matcher = PATTERS[i].matcher(result);
            result = matcher.replaceAll(REPLACES[i]);
        }

        return result.toUpperCase();
    }
}
