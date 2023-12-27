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
        latex = latex.replace("paramN", String.valueOf(response.getN()));
        latex = latex.replace("paramFx", response.getPolynomialFunctionText());
        //latex = latex.replace("paramFX", "(\\frac{c_{2}x^3}{3}) + (\\frac{c_{1}x^2}{2}) + ((c_{0})x)");
        latex = latex.replace("paramFX", response.getPolynomialDTO().getPolyIntLatex());
        latex = latex.replace("paramH", "-h,0,h");
        latex = latex.replace("paramY", "y_{-1},y_{0},y_{1}");
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
