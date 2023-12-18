package com.app.integraljjapi.dto;

public class ResponseDTO {
    private int n;
    private PointerDTO[] pointers;
    private String hPointersText;
    private String yPointersText;

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
}
