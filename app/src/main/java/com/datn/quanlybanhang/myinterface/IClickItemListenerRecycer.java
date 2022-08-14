package com.datn.quanlybanhang.myinterface;

import com.datn.quanlybanhang.model.KhachHang;

public interface IClickItemListenerRecycer<T> {
    void onClickItemModel(T t);
    void onClickChiTietModel(T t);
}
