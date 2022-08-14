package com.datn.quanlybanhang.model;

public class DonViTinh {
    String maDVT;
    String tenDVT;

    public DonViTinh() {
    }

    public DonViTinh(String maDVT, String tenDVT) {
        this.maDVT = maDVT;
        this.tenDVT = tenDVT;
    }

    public String getMaDVT() {
        return maDVT;
    }

    public void setMaDVT(String maDVT) {
        this.maDVT = maDVT;
    }

    public String getTenDVT() {
        return tenDVT;
    }

    public void setTenDVT(String tenDVT) {
        this.tenDVT = tenDVT;
    }
}
