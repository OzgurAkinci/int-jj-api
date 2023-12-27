package com.app.integraljjapi.util;

import ch.qos.logback.core.joran.sanity.Pair;
import com.app.integraljjapi.dto.*;

import java.util.*;

public final class AppUtils {

    private AppUtils() {
        // No need to instantiate the class, we can hide its constructor
    }

    private static Map<Integer, List<Map.Entry<String, String>>> numericOperation = new HashMap<>();

    //Functions
    public static PointerDTO[] calcPointers(int n) {
        int mod = n % 2; // If n is even or odd

        int leftCounter = ((mod == 0) ? (((n/2)-1) * -1) : ((n/2) * -1));
        int rightCounter = (n / 2); // always be n/2

        PointerDTO[] arr = new PointerDTO[n];

        int i=0;
        while (leftCounter < 0) {
            arr[i] = new PointerDTO(leftCounter, leftCounter == -1 ? "-h" : (leftCounter + "h"), "y_{" + leftCounter + "}");
            leftCounter++; i++;
        }

        i = n-1;
        while (rightCounter >= 0) {
            arr[i] = new PointerDTO(rightCounter, rightCounter == 1 ? "h" : (rightCounter == 0 ? "0" : (rightCounter + "h")), "y_{" + rightCounter + "}");
            rightCounter--; i--;
        }
        return arr;
    }

    public static PolynomialDTO getPolynomialDto(int n) {
        StringBuilder poly = new StringBuilder();
        var coefficients = new ArrayList<FractionUtils>();

        StringBuilder polyInt = new StringBuilder();

        StringBuilder polyIntLatex = new StringBuilder();

        for (int i = n ; i >= 0; i--) {
            // p(x) = cnx^n + ... + c3x^3 + c2x^2 + c1x + c0
            poly.append("c_").append("{").append(i).append("}");
            if(i != 0) {
                if(i==1) {
                    poly.append("x");
                }else {
                    poly.append("x^").append(i);
                }
                poly.append(" + ");
            }
            // P(x) = (cnx^(n+1) / (n+1)) + .... + ((c3x^4) / 4) + ((c2x^3) / 3) + ((c1x^2) / 2) + (c0x)
            polyInt.append("(").append("(").append("c_").append("{").append(i).append("}").append(")");
            if( i == 0) {
                polyInt.append("x");
            }else {
                polyInt.append("x^").append(i+1);
            }
            polyInt.append(")");
            if(i != 0) {
                polyInt.append("/").append(i+1);
                polyInt.append(" + ");
            }

            // polyIntLatex
            if( i == 0) {
                polyIntLatex.append("c_").append("{").append(i).append("}");
                polyIntLatex.append("x");
            }else {
                polyIntLatex.append("\\frac{").append("c_").append("{").append(i).append("}");
                polyIntLatex.append("x^").append(i+1);
                polyIntLatex.append("}");
            }
            if(i != 0) {
                polyIntLatex.append("{").append(i+1).append("}");
                polyIntLatex.append(" + ");
            }

            FractionUtils f = i == 0 ? FractionUtils.ZERO : FractionUtils.getInstance(1, i);
            coefficients.add(f);
        }

        return new PolynomialDTO(poly.toString(), polyInt.toString(), polyIntLatex.toString());
    }


    public static String[][] initSymbolicMatrix (PointerDTO[] pointers, int n) {
        String[][] matrix = new String[n+1][n+2];

        for(int i=0; i<pointers.length; i++) {
            int tempN = n;
            for(int j=0; j<=n; j++) {
                PointerDTO pointer = pointers[i];
                int coefficient = pointer.getCoefficient();
                String element = h(coefficient, tempN);
                matrix[i][j] = element;
                tempN--;
            }
            matrix[i][n+1] = "y" + "{" + pointers[i].getCoefficient() + "}";
        }
        return matrix;
    }

    public static String h(int coefficientInt, int pow) {
        boolean powIsEvenNumber = (pow % 2) == 0;
        coefficientInt = (powIsEvenNumber && coefficientInt < 0) ? (coefficientInt * -1): coefficientInt;

        if(coefficientInt == 0 && pow == 0) {
            return "1";
        }
        else if(coefficientInt == 0) {
            return "0";
        }else if(pow == 0) {
            return "1";
        }else if(pow == 1 || pow == -1) {
            return symMultiply(coefficientInt, pow);
        } else {
            return symMultiply((int)Math.pow(coefficientInt, pow), pow) + "^" + pow;
        }
    }

    public static int[][] initMatrix (PointerDTO[] pointers, int n) {
        int[][] arr  = new int[n+1][n+1];
        for(int i=0; i<pointers.length; i++) {
            PointerDTO pointer = pointers[i];
            int tempN = n;
            for(int j=0; j<=n; j++) {
                int coefficient = pointer.getCoefficient();
                int v = (int) Math.pow(coefficient, tempN);
                arr[i][j] = v;
                tempN--;
            }
        }
        return arr;
    }

    public static String symMultiply(int num, int pow) {
        if(num == 0) {
            return "0";
        }else if(num == -1) {
            return "-h";
        }else if(num == 1) {
            return "h";
        }else {
            return num + "h";
        }
    }

    /*
    Bu fonksiyon, bir 2 boyutlu double türündeki matrisi parametre olarak alır ve
    matrisin üzerinde değişiklik yapar, bu nedenle eşelon matrisi elde etmek için,
    matrisi önce fonksiyona göndermeniz gerekir. Fonksiyon, eşelon matrisi oluşturmak için
    Gauss eleme yöntemini kullanır.
    */
    public static MatrixDTO findEchelonMatrix(int[][] A, String[] B) {
        MatrixDTO matrixDto = new MatrixDTO();
        for (int i = 0; i<A.length; i++)
            numericOperation.put(i, new ArrayList<>());

        int[][] initMatrix = Arrays.stream(A).map(int[]::clone).toArray(int[][]::new);
        matrixDto.setInitMatrix(initMatrix);

        int rowCount = A.length;
        int columnCount = A[0].length;

        var stepDtoList = new ArrayList<StepDTO>();
        for (int pivot = 0; pivot < rowCount; pivot++) {
            // Bulunan pivot sütununu ve satırını bul
            int maxRow = findPivotRow(A, pivot);

            // Gereksiz swap işlemlerinden kaçın
            if (pivot != maxRow) {
                swapRows(A, pivot, maxRow, B);
                var operation = "R" + (pivot+1) + " <-> R" + (maxRow+1);

                int[][] matrix = Arrays.stream(A).map(int[]::clone).toArray(int[][]::new);

                StepDTO swapStepDto = new StepDTO();
                swapStepDto.setPivotRow(pivot);
                swapStepDto.setMatrix(matrix);
                swapStepDto.setProcess(operation);
                swapStepDto.setSolution(B.clone());
                stepDtoList.add(swapStepDto);
            }

            // Pivot satırını normalize et
            int pivotValue = A[pivot][pivot];
            if (pivotValue != 0 && pivotValue != 1) { // Gereksiz işlemleri önle
                multiplyRow(A, pivot, 1.0 / pivotValue, B);
                var operation = "R" + (pivot+1) + " <- R" + (pivot+1) + " * " + (1.0 / pivotValue);

                int[][] matrix = Arrays.stream(A).map(int[]::clone).toArray(int[][]::new);

                StepDTO normalizationStepDto = new StepDTO();
                normalizationStepDto.setPivotRow(pivot);
                normalizationStepDto.setMatrix(matrix);
                normalizationStepDto.setProcess(operation);
                normalizationStepDto.setSolution(B.clone());
                stepDtoList.add(normalizationStepDto);
            }

            // Pivot sütununu sıfırla
            for (int row = 0; row < rowCount; row++) {
                if (row != pivot && A[row][pivot] != 0) {
                    int multiplier = -A[row][pivot];
                    addRows(A, row, pivot, multiplier, B);
                    var operation = "R" + (row+1) + " <- R" + (row+1) + " + R" + (pivot+1) + " * " + multiplier;

                    int[][] matrix = Arrays.stream(A).map(int[]::clone).toArray(int[][]::new);

                    StepDTO clearPivotStepDto = new StepDTO();
                    clearPivotStepDto.setPivotRow(pivot);
                    clearPivotStepDto.setMatrix(matrix);
                    clearPivotStepDto.setProcess(operation);
                    clearPivotStepDto.setSolution(B.clone());
                    stepDtoList.add(clearPivotStepDto);
                }
            }
        }

        matrixDto.setSteps(stepDtoList);
        matrixDto.setEchelonMatrix(A);
        matrixDto.setSolutionMatrix(B);

        return matrixDto;
    }

    private static int findPivotRow(int[][] matrix, int pivot) {
        int maxRow = pivot;
        for (int row = pivot + 1; row < matrix.length; row++) {
            if (Math.abs(matrix[row][pivot]) > Math.abs(matrix[maxRow][pivot])) {
                maxRow = row;
            }
        }
        return maxRow;
    }

    private static void swapRows(int[][] matrix, int row1, int row2, String[] B) {
        int[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;

        String temp2 = B[row1];
        B[row1] = B[row2];
        B[row2] = temp2;

        List<Map.Entry<String, String>> tmp =  numericOperation.get(row1);
        numericOperation.put(row1,numericOperation.get(row2));
        numericOperation.put(row2,tmp);

    }

    private static void multiplyRow(int[][] matrix, int row, double scalar, String[] B) {
        for (int i = 0; i < matrix[row].length; i++) {
            matrix[row][i] *= scalar;
        }
        boolean p = B[row].length()>5? true : false;
        B[row] = (p?"(":"") + B[row] + (p?")":"")+" * " + scalar;
    }

    private static void addRows(int[][] matrix, int targetRow, int sourceRow, int multiplier, String[] B) {
        for (int i = 0; i < matrix[0].length; i++) {
            matrix[targetRow][i] += multiplier * matrix[sourceRow][i];
        }

        boolean p = B[targetRow].length()>5? true : false;
        boolean p2 = B[sourceRow].length()>5? true : false;
        B[targetRow] = (p?"(":"") + B[targetRow] + (p?")":"")+(multiplier>0?" + ":(multiplier<0?" - ":"")) + (multiplier>1 || multiplier<-1 ? multiplier : "") + (p2?"(":"") + B[sourceRow] + (p2?")":"");
    }
}
