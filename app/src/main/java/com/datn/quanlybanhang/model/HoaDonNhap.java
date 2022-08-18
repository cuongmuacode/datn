package com.datn.quanlybanhang.model;

public class HoaDonNhap {
    String soHDNhap,maSP,maNV,maKH,ngayNhap;
    int hoaDonNhapNo,soLuongNhap;
    long giaNhap,gia;

    public HoaDonNhap(String soHDNhap, String maSP, String maNV, String maKH, String ngayNhap, int hoaDonNhapNo, int soLuongNhap, long giaNhap, long gia) {
        this.soHDNhap = soHDNhap;
        this.maSP = maSP;
        this.maNV = maNV;
        this.maKH = maKH;
        this.ngayNhap = ngayNhap;
        this.hoaDonNhapNo = hoaDonNhapNo;
        this.soLuongNhap = soLuongNhap;
        this.giaNhap = giaNhap;
        this.gia = gia;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public long getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(long giaNhap) {
        this.giaNhap = giaNhap;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
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

}
