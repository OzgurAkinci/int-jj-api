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
        latex = latex.replace("paramFX", response.getPolynomialDTO().getPolyIntLatex());
        latex = latex.replace("paramH", response.gethPointersText());
        latex = latex.replace("paramY", response.getyPointersText());
        latex = latex.replace("paramXToH", getXToHLatex(response));
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

    static String getXToHLatex(ResponseDTO response) {
        var xToHText = new StringBuilder();
        for(var i=0; i<response.gethPointers().size(); i++) {
            var pointer = '('+response.gethPointers().get(i)+')';
            xToHText.append("$x \\leftrightarrow ").append(response.gethPointers().get(i))
                    .append(" \\longrightarrow ").append(response.getPolynomialFunctionText()
                            .replaceAll("x", pointer)).append("$\\\\").append("\n");
        }
        return xToHText.toString();
    }

}
