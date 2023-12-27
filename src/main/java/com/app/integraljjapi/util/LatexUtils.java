package com.app.integraljjapi.util;

import com.app.integraljjapi.dto.ResponseDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class LatexUtils {

    public LatexUtils() {}

    public static String createLatexFromData(ResponseDTO response) throws IOException {
        var latex = getResourceFileAsString();
        return latex;
    }

    static String getResourceFileAsString() throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream("init.tex")) {
            if (is == null) return null;
            try (InputStreamReader isr = new InputStreamReader(is); BufferedReader reader = new BufferedReader(isr)) {
                    return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

}
