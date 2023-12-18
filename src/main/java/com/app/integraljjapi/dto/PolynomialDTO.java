package com.app.integraljjapi.dto;

public class PolynomialDTO {
    private String poly;
    private String polyInt;

    public PolynomialDTO() {}

    public PolynomialDTO(String poly, String polyInt) {
        this.poly = poly;
        this.polyInt = polyInt;
    }

    public String getPoly() {
        return poly;
    }

    public void setPoly(String poly) {
        this.poly = poly;
    }

    public String getPolyInt() {
        return polyInt;
    }
    public void setPolyInt(String polyInt) {
        this.polyInt = polyInt;
    }
}
