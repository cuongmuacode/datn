package com.datn.quanlybanhang.model;

import java.io.Serializable;
import java.sql.Date;

public class KhachHang implements Serializable {
    private String maKH,tenKH,diaChi,email,soDT,ghiChu;


    public KhachHang() {
    }

    public KhachHang(String maKH, String tenKH, String diaChi, String email, String soDT, String ghiChu) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.diaChi = diaChi;
        this.email = email;
        this.soDT = soDT;
        this.ghiChu = ghiChu;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }


}
