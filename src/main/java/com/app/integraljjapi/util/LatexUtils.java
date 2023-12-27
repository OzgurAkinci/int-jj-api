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
        latex = latex.replace("prmSymbolicMatrix", getSymbolicMatrixLatex(response.getSymbolicMatrix()));
        latex = latex.replace("prmInitialMatrix", getInitialMatrixLatex(response.getMatrixDTO().getInitMatrix()));
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

    static String getXToHLatex(ResponseDTO input) {
        var response = new StringBuilder();
        for(var i=0; i<input.gethPointers().size(); i++) {
            var pointer = '('+input.gethPointers().get(i)+')';
            response.append("$x \\leftrightarrow ").append(input.gethPointers().get(i))
                    .append(" \\longrightarrow ").append(input.getPolynomialFunctionText()
                            .replaceAll("x", pointer)).append("$\\\\").append("\n");
        }
        return response.toString();
    }

    static String getSymbolicMatrixLatex(String[][] symbolicMatrix) {
        var response = new StringBuilder();
        for (String[] matrix : symbolicMatrix) {
            StringBuilder rowText = new StringBuilder();
            for (var j = 0; j < matrix.length; j++) {
                rowText.append(matrix[j]);
                if (j < matrix.length - 1) {
                    rowText.append("&");
                }
            }
            response.append(rowText).append("\\\\");
        }
        return response.toString();
    }

    static String getInitialMatrixLatex(int[][] initialMatrix) {
        var response = new StringBuilder();
        for (int[] matrix : initialMatrix) {
            StringBuilder rowText = new StringBuilder();
            for (var j = 0; j < matrix.length; j++) {
                rowText.append(matrix[j]);
                if (j < matrix.length - 1) {
                    rowText.append("&");
                }
            }
            response.append(rowText).append("\\\\");
        }
        return response.toString();
    }

}
