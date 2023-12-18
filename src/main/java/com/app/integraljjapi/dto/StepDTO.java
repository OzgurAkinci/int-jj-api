package com.app.integraljjapi.dto;

public class StepDTO {
    private int[][] matrix;
    private int pivotRow;

    public StepDTO() {}

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix.clone();
    }

    public int getPivotRow() {
        return pivotRow;
    }

    public void setPivotRow(int pivotRow) {
        this.pivotRow = pivotRow;
    }

    @Override
    public String toString() {
        return "StepDto{" +
                "pivotRow=" + pivotRow +
                '}';
    }
}
