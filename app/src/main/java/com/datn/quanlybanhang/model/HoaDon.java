package com.datn.quanlybanhang.model;

import java.io.Serializable;
import java.util.List;

public class HoaDon implements Serializable {

    private String soHD,ngayHD,maKH,maNV;
    private Long triGia;
    private List<SanPham> sanPhamList;
    private int hoaDonNo;

    public HoaDon() {
    }

    public HoaDon(String soHD, String ngayHD, String maKH, String maNV, Long triGia, List<SanPham> sanPhamList, int hoaDonNo) {
        this.soHD = soHD;
        this.ngayHD = ngayHD;
        this.maKH = maKH;
        this.maNV = maNV;
        this.triGia = triGia;
        this.sanPhamList = sanPhamList;
        this.hoaDonNo = hoaDonNo;
    }

    public int getHoaDonNo() {
        return hoaDonNo;
    }

    public void setHoaDonNo(int hoaDonNo) {
        this.hoaDonNo = hoaDonNo;
    }

    public String getSoHD() {
        return soHD;
    }

    public void setSoHD(String soHD) {
        this.soHD = soHD;
    }

    public String getNgayHD() {
        return ngayHD;
    }

    public void setNgayHD(String ngayHD) {
        this.ngayHD = ngayHD;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public Long getTriGia() {
        return triGia;
    }

    public void setTriGia(Long triGia) {
        this.triGia = triGia;
    }

    public List<SanPham> getSanPhamList() {
        return sanPhamList;
    }

    public void setSanPhamList(List<SanPham> sanPhamList) {
        this.sanPhamList = sanPhamList;
    }
}
