package com.app.integraljjapi.dto;

import java.util.List;

public class ResponseDTO {
    private int n;
    private PointerDTO[] pointers;
    private List<String> hPointers;
    private String hPointersText;
    private String yPointersText;
    private PolynomialDTO polynomialDTO;
    private String polynomialFunctionText;
    private String polynomialIntFunctionText;
    private String[][] symbolicMatrix;
    private MatrixDTO matrixDTO;

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public PointerDTO[] getPointers() {
        return pointers;
    }

    public void setPointers(PointerDTO[] pointers) {
        this.pointers = pointers;
    }

    public String gethPointersText() {
        return hPointersText;
    }

    public void sethPointersText(String hPointersText) {
        this.hPointersText = hPointersText;
    }

    public String getyPointersText() {
        return yPointersText;
    }

    public void setyPointersText(String yPointersText) {
        this.yPointersText = yPointersText;
    }

    public PolynomialDTO getPolynomialDTO() {
        return polynomialDTO;
    }

    public void setPolynomialDTO(PolynomialDTO polynomialDTO) {
        this.polynomialDTO = polynomialDTO;
    }

    public String getPolynomialFunctionText() {
        return polynomialFunctionText;
    }

    public void setPolynomialFunctionText(String polynomialFunctionText) {
        this.polynomialFunctionText = polynomialFunctionText;
    }

    public String getPolynomialIntFunctionText() {
        return polynomialIntFunctionText;
    }

    public void setPolynomialIntFunctionText(String polynomialIntFunctionText) {
        this.polynomialIntFunctionText = polynomialIntFunctionText;
    }

    public String[][] getSymbolicMatrix() {
        return symbolicMatrix;
    }

    public void setSymbolicMatrix(String[][] symbolicMatrix) {
        this.symbolicMatrix = symbolicMatrix;
    }

    public MatrixDTO getMatrixDTO() {
        return matrixDTO;
    }

    public void setMatrixDTO(MatrixDTO matrixDTO) {
        this.matrixDTO = matrixDTO;
    }

    public List<String> gethPointers() {
        return hPointers;
    }

    public void sethPointers(List<String> hPointers) {
        this.hPointers = hPointers;
    }
}
