package com.app.integraljjapi.dto;

public class StepDTO {
    private int[][] matrix;
    private String[] solution;
    private int pivotRow;
    private int pivot;
    private String process;

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

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public int getPivot() {
        return pivot;
    }

    public void setPivot(int pivot) {
        this.pivot = pivot;
    }

    public String[] getSolution() {
        return solution;
    }

    public void setSolution(String[] solution) {
        this.solution = solution;
    }

    @Override
    public String toString() {
        return "StepDto{" +
                "pivotRow=" + pivotRow +
                '}';
    }
}
