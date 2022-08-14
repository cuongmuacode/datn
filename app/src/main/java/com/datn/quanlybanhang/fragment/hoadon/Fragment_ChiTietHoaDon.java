package com.datn.quanlybanhang.fragment.hoadon;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.adapter.SanPhamAdapterRecycler;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.HoaDon;
import com.datn.quanlybanhang.model.KhachHang;
import com.datn.quanlybanhang.model.NhanVien;
import com.datn.quanlybanhang.model.SanPham;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Fragment_ChiTietHoaDon extends Fragment implements Serializable {
    HoaDon hoaDon;
    List<SanPham> sanPhamList;
    TextView textSoHoaDon;
    TextView tenKhachHang;
    TextView textTenNhanVien;
    TextView textTongTien;
    TextView textNgayHD;
    RecyclerView recyclerView;
    SanPhamAdapterRecycler sanPhamAdapterRecycler;
    MySQLiteHelper database;


    public Fragment_ChiTietHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
        this.sanPhamList = hoaDon.getSanPhamList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__chi_tiet_hoa_don, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textSoHoaDon = view.findViewById(R.id.ChiTiet_SoHoaDon);
        tenKhachHang = view.findViewById(R.id.ChiTiet_tenkhachhang_hoadon);
        textTenNhanVien = view.findViewById(R.id.ChiTiet_tenNhanVienHoaDon);
        textTongTien = view.findViewById(R.id.ChiTiet_tongtienhoadon);
        textNgayHD = view.findViewById(R.id.ChiTiet_ngayHoaDon);
        recyclerView = view.findViewById(R.id.ChiTiet_recycer_hoadon);
        if(getContext()!=null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
            recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
            database = new MySQLiteHelper(getContext());
        }
        sanPhamAdapterRecycler = new SanPhamAdapterRecycler(sanPhamList);
        recyclerView.setAdapter(sanPhamAdapterRecycler);

        String str = "Số hóa đơn : "+hoaDon.getSoHD();
        textSoHoaDon.setText(str);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy '| Giờ : ' hh:mm");
        str = "Ngày : "+dateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD())));
        textNgayHD.setText(str);
        KhachHang khachHang = database.getKhachHang(hoaDon.getMaKH());
        NhanVien nhanVien = database.getNhanVien(hoaDon.getMaNV());
        str = "Tên nhân viên : ";
        if(nhanVien!=null) {
                str = str + nhanVien.getHoTenNV();
                textTenNhanVien.setText(str);
            }
        str = "Tên khách hàng : ";
            if(khachHang!=null) {
                str = str + khachHang.getTenKH();
                tenKhachHang.setText(str);
            }

        if(!sanPhamList.isEmpty()) {
            long triGia = 0;
            for (SanPham sp : sanPhamList)
                triGia += sp.getSoLuongSP() * sp.getGiaSP();
            str = triGia + " VND";
            textTongTien.setText(str);
        }
        else textTongTien.setText("0 VND");
    }
}