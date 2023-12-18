package com.app.integraljjapi.dto;

import java.util.List;

public class MatrixDTO {
    private int[][] initMatrix;
    private int[][] echelonMatrix;
    private String[] solutionMatrix;
    private List<StepDTO> steps;

    public MatrixDTO() {}

    public MatrixDTO(int[][] initMatrix, int[][] echelonMatrix, String [] solutionMatrix) {
        this.initMatrix = initMatrix;
        this.echelonMatrix = echelonMatrix;
        this.solutionMatrix = solutionMatrix;
    }

    public int[][] getInitMatrix() {
        return initMatrix;
    }

    public void setInitMatrix(int[][] initMatrix) {
        this.initMatrix = initMatrix.clone();
    }

    public int[][] getEchelonMatrix() {
        return echelonMatrix;
    }

    public void setEchelonMatrix(int[][] echelonMatrix) {
        this.echelonMatrix = echelonMatrix;
    }

    public String[] getSolutionMatrix() {
        return solutionMatrix;
    }

    public void setSolutionMatrix(String[] solutionMatrix) {
        this.solutionMatrix = solutionMatrix;
    }

    public List<StepDTO> getSteps() {
        return steps;
    }

    public void setSteps(List<StepDTO> steps) {
        this.steps = steps;
    }
}
