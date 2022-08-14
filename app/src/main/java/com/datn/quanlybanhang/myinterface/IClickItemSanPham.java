package com.datn.quanlybanhang.myinterface;

import com.datn.quanlybanhang.model.KhachHang;
import com.datn.quanlybanhang.model.SanPham;

public interface IClickItemSanPham {
    void onClickSanPham(SanPham sanPham);
    void onClickKhachHang(KhachHang khachHang);
}
