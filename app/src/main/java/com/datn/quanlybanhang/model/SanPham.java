package com.datn.quanlybanhang.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class SanPham implements Serializable{
    private String maSP,tenSP,donViTinh,nuocSX,chiTietSP,loaiSP;
    private long giaSP;
    private int soLuongSP;
    private byte[] imgSP;

    public SanPham(String maSP, String tenSP, String donViTinh, String nuocSX,
                   String chiTietSP, long giaSP, int soLuongSP,byte[] imgSP,String loaiSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donViTinh = donViTinh;
        this.nuocSX = nuocSX;
        this.chiTietSP = chiTietSP;
        this.giaSP = giaSP;
        this.soLuongSP = soLuongSP;
        this.imgSP = imgSP;
        this.loaiSP = loaiSP;
    }

    public SanPham() {
    }

    public int getSoLuongSP() {
        return soLuongSP;
    }

    public void setSoLuongSP(int soLuongSP) {
        this.soLuongSP = soLuongSP;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getNuocSX() {
        return nuocSX;
    }

    public void setNuocSX(String nuocSX) {
        this.nuocSX = nuocSX;
    }

    public String getChiTietSP() {
        return chiTietSP;
    }

    public void setChiTietSP(String chiTietSP) {
        this.chiTietSP = chiTietSP;
    }

    public long getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(long giaSP) {
        this.giaSP = giaSP;
    }

    public String getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(String loaiSP) {
        this.loaiSP = loaiSP;
    }

    public byte[] getImgSP() {
        return imgSP;
    }

    public void setImgSP(byte[] imgSP) {
        this.imgSP = imgSP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SanPham)) return false;
        SanPham sanPham = (SanPham) o;
        return getGiaSP() == sanPham.getGiaSP() &&
                getSoLuongSP() == sanPham.getSoLuongSP() &&
                Objects.equals(getMaSP(), sanPham.getMaSP()) &&
                Objects.equals(getTenSP(), sanPham.getTenSP()) &&
                Objects.equals(getDonViTinh(), sanPham.getDonViTinh()) &&
                Objects.equals(getNuocSX(), sanPham.getNuocSX()) &&
                Objects.equals(getChiTietSP(), sanPham.getChiTietSP()) &&
                Objects.equals(getLoaiSP(), sanPham.getLoaiSP()) &&
                Arrays.equals(getImgSP(), sanPham.getImgSP());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getMaSP(), getTenSP(), getDonViTinh(), getNuocSX(), getChiTietSP(), getLoaiSP(), getGiaSP(), getSoLuongSP());
        result = 31 * result + Arrays.hashCode(getImgSP());
        return result;
    }
}
