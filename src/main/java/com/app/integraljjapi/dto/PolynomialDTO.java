package com.app.integraljjapi.dto;

public class PolynomialDTO {
    private String poly;
    private String polyInt;
    private String polyIntLatex;

    public PolynomialDTO() {}

    public PolynomialDTO(String poly, String polyInt, String polyIntLatex) {
        this.poly = poly;
        this.polyInt = polyInt;
        this.polyIntLatex = polyIntLatex;
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

    public String getPolyIntLatex() {
        return polyIntLatex;
    }

    public void setPolyIntLatex(String polyIntLatex) {
        this.polyIntLatex = polyIntLatex;
    }
}
