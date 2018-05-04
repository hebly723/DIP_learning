package com.graduate.entity;

public class TestImageWithBLOBs extends TestImage {
    private byte[] cva;

    private byte[] pha;

    public byte[] getCva() {
        return cva;
    }

    public void setCva(byte[] cva) {
        this.cva = cva;
    }

    public byte[] getPha() {
        return pha;
    }

    public void setPha(byte[] pha) {
        this.pha = pha;
    }
}