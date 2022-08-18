package com.datn.quanlybanhang.myinterface;

import com.datn.quanlybanhang.model.KhachHang;
import com.datn.quanlybanhang.model.KhoHang;
import com.datn.quanlybanhang.model.SanPham;

public interface IClickItemSanPham {
    void onClickSanPham(SanPham sanPham,KhoHang khoHang);
    void onClickKhachHang(KhachHang khachHang);
}
