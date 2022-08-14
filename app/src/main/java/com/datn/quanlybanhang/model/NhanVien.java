package com.datn.quanlybanhang.model;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NhanVien implements Serializable {
    private String maNV;
    private String hoTenNV;
    private String SoDT;
    private String emailNhanVien;
    private String passNV;
    private int quyen;
    private byte[] byteImgs;

    public NhanVien(String maNV, String hoTenNV, String soDT, String emailNhanVien,int quyen, String passNV,byte[] byteImgs) {
        this.maNV = maNV;
        this.hoTenNV = hoTenNV;
        this.SoDT = soDT;
        this.emailNhanVien = emailNhanVien;
        this.quyen = quyen;
        this.passNV = passNV;
        this.byteImgs = byteImgs;
    }

    public NhanVien() {
    }

    public byte[] getByteImgs() {
        return byteImgs;
    }

    public void setByteImgs(byte[] byteImgs) {
        this.byteImgs = byteImgs;
    }

    public int getQuyen() {
        return quyen;
    }

    public void setQuyen(int quyen) {
        this.quyen = quyen;
    }

    public String getEmailNhanVien() {
        return emailNhanVien;
    }

    public void setEmailNhanVien(String emailNhanVien) {
        this.emailNhanVien = emailNhanVien;
    }
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTenNV() {
        return hoTenNV;
    }

    public void setHoTenNV(String hoTenNV) {
        this.hoTenNV = hoTenNV;
    }

    public String getSoDT() {
        return SoDT;
    }

    public void setSoDT(String soDT) {
        SoDT = soDT;
    }

    public String getPassNV() {
        return passNV;
    }

    public void setPassNV(String passNV) {
        this.passNV = passNV;
    }





}
