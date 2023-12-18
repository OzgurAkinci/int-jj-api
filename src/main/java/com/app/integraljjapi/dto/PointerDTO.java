package com.app.integraljjapi.dto;

public class PointerDTO {
    private int coefficient;
    private String hCoefficient;
    private String yCoefficient;

    public PointerDTO() {}

    public PointerDTO(int coefficient, String hCoefficient) {
        this.coefficient = coefficient;
        this.hCoefficient = hCoefficient;
    }

    public PointerDTO(int coefficient, String hCoefficient, String yCoefficient) {
        this.coefficient = coefficient;
        this.hCoefficient = hCoefficient;
        this.yCoefficient = yCoefficient;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public String gethCoefficient() {
        return hCoefficient;
    }

    public void sethCoefficient(String hCoefficient) {
        this.hCoefficient = hCoefficient;
    }

    public String getyCoefficient() {
        return yCoefficient;
    }

    public void setyCoefficient(String yCoefficient) {
        this.yCoefficient = yCoefficient;
    }

    @Override
    public String toString() {
        return "PointerDTO{" +
                "coefficient=" + coefficient +
                ", hCoefficient='" + hCoefficient + '\'' +
                ", yCoefficient='" + yCoefficient + '\'' +
                '}';
    }
}
