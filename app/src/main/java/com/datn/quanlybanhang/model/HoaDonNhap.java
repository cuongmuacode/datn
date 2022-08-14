package com.datn.quanlybanhang.model;

public class HoaDonNhap {
    String soHDNhap,maSP,maNV,maKH,ngayNhap;
    long giaNhap,giaBan;
    int soLuong,hoaDonNhapNo;

    public HoaDonNhap(String soHDNhap, String maSP, String maNV, String maKH, String ngayNhap, long giaNhap, long giaBan, int soLuong, int hoaDonNhapNo) {
        this.soHDNhap = soHDNhap;
        this.maSP = maSP;
        this.maNV = maNV;
        this.maKH = maKH;
        this.ngayNhap = ngayNhap;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.hoaDonNhapNo = hoaDonNhapNo;
    }

    public int getHoaDonNhapNo() {
        return hoaDonNhapNo;
    }

    public void setHoaDonNhapNo(int hoaDonNhapNo) {
        this.hoaDonNhapNo = hoaDonNhapNo;
    }

    public HoaDonNhap() {
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getSoHDNhap() {
        return soHDNhap;
    }

    public void setSoHDNhap(String soHDNhap) {
        this.soHDNhap = soHDNhap;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public long getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(long giaNhap) {
        this.giaNhap = giaNhap;
    }

    public long getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(long giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
