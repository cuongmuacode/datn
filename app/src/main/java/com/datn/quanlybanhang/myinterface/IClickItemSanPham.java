package com.datn.quanlybanhang.myinterface;

import com.datn.quanlybanhang.model.KhachHang;
import com.datn.quanlybanhang.model.KhoHang;
import com.datn.quanlybanhang.model.SanPham;

import java.io.Serializable;

public interface IClickItemSanPham  extends Serializable {
    void onClickSanPham(SanPham sanPham,KhoHang khoHang);
    void onClickKhachHang(KhachHang khachHang);
}
