package com.poly.utils;

import java.text.Normalizer;
import java.nio.charset.StandardCharsets;

public class DiacriticsUtil {

    public static String removeDiacritics(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static byte[] convertToUTF8Bytes(String input) {
        String resultString = removeDiacritics(input);
        return resultString.getBytes(StandardCharsets.UTF_8);
    }
}
