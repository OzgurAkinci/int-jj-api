package com.app.integraljjapi.util;

import com.app.integraljjapi.dto.*;
import com.app.integraljjapi.util.FractionUtils;

import java.util.*;

public final class AppUtils {

    private AppUtils() {
        // No need to instantiate the class, we can hide its constructor
    }

    // Variables
    public static final String PolynomialFunction = "p(x)";
    public static final String PolynomialIntFunction = "P(x)";

    //Functions
    static PointerDTO[] calcPointers (int n) {
        int mod = n % 2; // If n is even or odd

        int leftCounter = ((mod == 0) ? (((n/2)-1) * -1) : ((n/2) * -1));
        int rightCounter = (n / 2); // always be n/2

        PointerDTO[] arr = new PointerDTO[n];

        int i=0;
        while (leftCounter < 0) {
            arr[i] = new PointerDTO(leftCounter, leftCounter == -1 ? "-h" : (leftCounter + "h"), "y{" + leftCounter + "}");
            leftCounter++; i++;
        }

        i = n-1;
        while (rightCounter >= 0) {
            arr[i] = new PointerDTO(rightCounter, rightCounter == 1 ? "h" : (rightCounter == 0 ? "0" : (rightCounter + "h")), "y{" + rightCounter + "}");
            rightCounter--; i--;
        }
        return arr;
    }

    static PolynomialDTO getPolynomialDto(int n) {
        StringBuilder poly = new StringBuilder();
        List<FractionUtils> coefficients = new ArrayList<>();

        StringBuilder polyInt = new StringBuilder();
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

            FractionUtils f = i == 0 ? FractionUtils.ZERO : FractionUtils.getInstance(1, i);
            coefficients.add(f);
        }

        return new PolynomialDTO(poly.toString(), polyInt.toString());
    }


    public static String[][] initSymbolicMatrix (PointerDTO[] pointers, int n) {
        String[][] matrix = new String[n+1][n+2];

        for(int i=0; i<pointers.length; i++) {
            int tempN = n;
            for(int j=0; j<=n; j++) {
                PointerDTO pointer = pointers[i];
                int coefficient = pointer.getCoefficient();
                String element = (coefficient == 0 && j == 0) ? ("c{" + pointers[i].getCoefficient() + "}") : h(coefficient, tempN);
                matrix[i][j] = element;
                tempN--;
            }
            matrix[i][n+1] = "y" + "{" + pointers[i].getCoefficient() + "}";
        }
        return matrix;
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

    public static String h(int coefficientInt, int pow) {
        boolean powIsEvenNumber = (pow % 2) == 0;
        coefficientInt = (powIsEvenNumber && coefficientInt < 0) ? (coefficientInt * -1): coefficientInt;

        if(coefficientInt == 0) {
            return "0";
        }else if(pow == 0) {
            return "1";
        }else if(pow == 1 || pow == -1) {
            return symMultiply(coefficientInt, pow);
        } else {
            return symMultiply((int)Math.pow(coefficientInt, pow), pow) + "^" + pow;
        }
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

        int[][] initMatrix = Arrays.stream(A).map(int[]::clone).toArray(int[][]::new);
        matrixDto.setInitMatrix(initMatrix);

        int rowCount = A.length;
        int columnCount = A[0].length;

        List<StepDTO> stepDtoList = new ArrayList<>();


        int row = 0;
        for (int col = 0; col < columnCount && row < rowCount; col++) {
            StepDTO stepDto = new StepDTO();

            // Birinci adım: anahtar elemanın 0 olmadığı bir satır bulun
            int pivotRow = row;
            while (pivotRow < rowCount && A[pivotRow][col] == 0) {
                pivotRow++;
            }
            //System.out.println("pivotRow:" + pivotRow);
            stepDto.setPivotRow(pivotRow);

            //printMatrix(A);
            stepDto.setMatrix(A);

            if (pivotRow == rowCount) {
                // Bu sütundaki tüm elemanlar zaten sıfır
                continue;
            }

            // İkinci adım: anahtar elemanı olan satırı bulun ve eşleştirin
            if (pivotRow != row) {
                int[] tempRow = A[pivotRow];
                A[pivotRow] = A[row];
                A[row] = tempRow;
            }

            // Üçüncü adım: anahtar elemanı sıfırdan farklı bir sayı yapın
            int pivot = A[row][col];
            for (int j = col; j < columnCount; j++) {
                A[row][j] /= pivot;
            }

            B[row] = B[row] + "/" + pivot;

            //System.out.println("*****************************");

            // Dördüncü adım: anahtar elemanın altındaki tüm elemanlarda sıfır yapın
            for (int j = row + 1; j < rowCount; j++) {
                int factor = A[j][col];
                for (int k = col; k < columnCount; k++) {
                    A[j][k] -= factor * A[row][k];
                }
                B[j] = B[j] + "-" +  factor + "*" + B[row];
            }
            //printMatrix(A);
            stepDto.setMatrix(A);

            // Beşinci adım: bir sonraki anahtar eleman için ilerleyin
            row++;

            stepDtoList.add(stepDto);
        }

        matrixDto.setSteps(stepDtoList);
        matrixDto.setEchelonMatrix(A);
        matrixDto.setSolutionMatrix(B);
        return matrixDto;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }

    public static void printStrMatrix(String[][] matrix) {
        for (String[] ints : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }
}
