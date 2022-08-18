package com.datn.quanlybanhang.fragment.khachhang;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.KhachHang;
import com.datn.quanlybanhang.myinterface.IAddEditModel;

public class Fragment_ChiTietKhachHang extends Fragment implements IAddEditModel<KhachHang> {

    private  KhachHang khachHang;
    MySQLiteHelper database;
    public Fragment_ChiTietKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__chi_tiet_khach_hang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textViewName = view.findViewById(R.id.chiTietKhachHang_Name);
        TextView textViewDiaChi = view.findViewById(R.id.chiTietKhachHang_DiaChi);
        TextView textViewEmail = view.findViewById(R.id.chiTietKhachHang_Email);
        TextView textViewGhiChu = view.findViewById(R.id.chiTietKhachHang_GhiChu);
        TextView textViewSoDT = view.findViewById(R.id.chiTietKhachHang_SODT);
        Button buttonSua = view.findViewById(R.id.button_sua_chitietkh);
        Button buttonXoa = view.findViewById(R.id.button_xoa_chitietkh);
        if(getContext()==null) return;
        database = new MySQLiteHelper(getContext());

        textViewName.setText(Html.fromHtml("<b>Tên khách hàng</b> : "+khachHang.getTenKH()));
        textViewDiaChi.setText(Html.fromHtml("<b>Địa chỉ</b> : "+khachHang.getDiaChi()));
        textViewEmail.setText(Html.fromHtml("<b>Email</b> : "+khachHang.getEmail()));
        textViewSoDT.setText(Html.fromHtml("<b>Số điện thoại</b> : "+khachHang.getSoDT()));
        textViewGhiChu.setText(Html.fromHtml("<b>Ghi chú</b> : "+khachHang.getGhiChu()));

        buttonSua.setOnClickListener(view1 -> {
            if(khachHang.getMaKH().equals("MaKH01")) {
                Toast.makeText(getContext(), "Không được phép sửa!!", Toast.LENGTH_SHORT).show();
                return;
            }
            replaceFragment(new FragmentAddKhachHang(Fragment_ChiTietKhachHang.this,khachHang));
        });
        buttonXoa.setOnClickListener(view12 -> {
            if(khachHang.getMaKH().equals("MaKH01")) {
                Toast.makeText(getContext(), "Không được phép xóa!!", Toast.LENGTH_SHORT).show();
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.nav_model_xoa);
            builder.setMessage("Bạn có chắc không ?");
            builder.setCancelable(true);
            builder.setPositiveButton("Có", (dialogInterface, i) -> {
                database.deleteKhachHang(khachHang);
                if(getActivity()!=null)
                    getActivity().onBackPressed();
            });
            builder.setNegativeButton("Không", (dialogInterface, i) -> dialogInterface.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }
    public void replaceFragment(Fragment fragment){
        if(getActivity()==null) return;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayoutcontentthongtin, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean processModel(KhachHang khachHang1, int i) {
        if(i == FragmentKhachHang.SUA_KHACH_HANG) {
            khachHang1.setMaKH(this.khachHang.getMaKH());
            database.updateKhachHang(khachHang1);
            this.khachHang = khachHang1;
            return true;
        }
        else
        return false;
    }
}

