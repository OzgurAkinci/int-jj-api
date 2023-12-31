package com.app.integraljjapi.util;

import com.app.integraljjapi.dto.MatrixDTO;
import com.app.integraljjapi.dto.ResponseDTO;
import com.app.integraljjapi.dto.StepDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class LatexUtils {

    public LatexUtils() {}

    public static String createLatexFromData(ResponseDTO response) throws Exception {
        var latex = getResourceFileAsString();
        if(latex != null) {
            latex = latex.replace("paramN", String.valueOf(response.getN()));
            latex = latex.replace("paramFx", response.getPolynomialFunctionText());
            latex = latex.replace("paramFX", getPolyIntLatex(response.getPolynomialDTO().getPolyIntLatex(), response.gethPointers()));
            latex = latex.replace("paramH", response.gethPointersText());
            latex = latex.replace("paramY", response.getyPointersText());
            latex = latex.replace("paramXToH", getXToHLatex(response));
            latex = latex.replace("prmSymbolicMatrix", getSymbolicMatrixLatex(response.getSymbolicMatrix()));
            latex = latex.replace("prmInitialMatrix", getInitialMatrixLatex(response.getMatrixDTO().getInitMatrix()));
            latex = latex.replace("prmStepByStep", getStepByStepEchelonMatrixLatex(response.getMatrixDTO()));
            latex = latex.replace("paramEquationRootValues", getEquationRootValues(response.getMatrixDTO().getSolutionMatrix()));
            latex = latex.replace("paramResult", getResultLatex(response.getMatrixDTO().getSolutionMatrix(), response.getN(), response.gethPointers()));
            return latex;
        }else {
            throw new Exception("init.tex file crashed.");
        }
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

    static String getPolyIntLatex(String polyIntLatex, List<String> hPointers) {
        var response = new StringBuilder();
        response.append("$\\int_{").append(hPointers.get(0)).append("}^{").append(hPointers.get(hPointers.size() - 1)).append("}x\\,dx = ").append(polyIntLatex);
        return response.toString();
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

    static String getStepByStepEchelonMatrixLatex(MatrixDTO matrixDTO) {
        var response = new StringBuilder();
        int stepIndex = 0;
        for(StepDTO stepDTO: matrixDTO.getSteps()) {
            response.append("$PivotRow= "+matrixDTO.getSteps().get(stepIndex).getPivotRow()+", Pivot: "+matrixDTO.getSteps().get(stepIndex).getPivot()+", ("+matrixDTO.getSteps().get(stepIndex).getProcess().replace("<->", "\\leftrightarrow").replace("<-", "\\leftarrow")+") $\\\\");
            response.append("$\\begin{bmatrix}");
            var index = 0;
            for (int[] matrix : stepDTO.getMatrix()) {
                StringBuilder rowText = new StringBuilder();
                for (var j = 0; j < matrix.length; j++) {
                    rowText.append(matrix[j]);
                    rowText.append("&");
                }
                rowText.append(stepDTO.getSolution()[index]);
                response.append(rowText).append("\\\\");
                index++;
            }
            response.append(" \\end{bmatrix} \\break $").append("\\\\").append("\n");
            stepIndex++;
        }

        return response.toString();
    }

    private static String getEquationRootValues(String[] solutionMatrix) {
        var response = new StringBuilder();
        for(var i=0; i<solutionMatrix.length; i++) {
            response.append("$c{").append(i).append("}").append(" = ").append(solutionMatrix[i]).append("$\\\\").append("\n");
        }
        return response.toString();
    }

    private static String getResultLatex(String[] solutionMatrix, int n, List<String> hPointers) {
        var response = new StringBuilder();
        response.append("$\\int_{").append(hPointers.get(0)).append("}^{").append(hPointers.get(hPointers.size() - 1)).append("}x\\,dx = ");
        for(int i=n; i>=0; i--) {
            if(i==0) {
                response.append("(").append(solutionMatrix[i]).append(")").append("x");
            }else {
                response.append("\\frac{").append("(").append(solutionMatrix[i]).append(")").append("x^").append(i+1).append("}").append("{").append(i+1).append("}").append(" + ");
            }
        }
        response.append("$");

        return response.toString();
    }

}

