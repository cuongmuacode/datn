package com.datn.quanlybanhang.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.activityy.ActivityThongTin;
import com.datn.quanlybanhang.activityy.MainActivity;

public class FragmentXemThem extends Fragment implements View.OnClickListener{
    TextView textViewKhachHang;
    TextView textViewTaiKhoan;
    TextView textViewDonViTinh;
    TextView textViewMayIn;
    TextView textViewDanhMuc;
    TextView textViewBaoCaoTongHop;
    TextView textViewNhapHang;
    TextView textViewCaiDat;
    TextView textViewMatHang;
    Toast toast;
    public static final int ACT_KHACHHANG = 1;
    public static final int ACT_TAIKHOAN = 2;
    public static final int ACT_DONVITINH = 3;
    public static final int ACT_MAYIN = 4;
    public static final int ACT_DANHMUC = 5;
    public static final int ACT_BAOCAOTONGHOP = 6;
    public static final int ACT_NHAPHANG = 7;
    public static final int ACT_CAIDAT = 8;
    public static final int ACT_MATHANG = 9;
    public static final int ACT_SHOP = 10;
    public static final int ACT_CHITIETHOADON = 11;
    public static final int ACT_GIOITHIEU = 12;
    public static final int ACT_HUONGDAN = 13;
    public static final int ACT_DOIMATKHAU = 14;




    public FragmentXemThem() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_xem_them, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getWidget(view);
    }
    @Override
    public void onClick(View view) {
        int i = view.getId();
        Intent intent;
        switch (i){
            case R.id.xemthem_nhaphang:
                if(MainActivity.nhanVien.getQuyen()==1) {
                    intent = new Intent(this.getContext(), ActivityThongTin.class);
                    intent.putExtra("Data", ACT_NHAPHANG);
                    startActivity(intent);
                }
                else
                    displayToast("Bạn không phải là admin !!");
                break;
            case R.id.xem_mayin:
                intent = new Intent(this.getContext(), ActivityThongTin.class);
                intent.putExtra("Data",ACT_MAYIN);
                startActivity(intent);
                break;
            case R.id.xemthem_caidat:
                intent = new Intent(this.getContext(), ActivityThongTin.class);
                intent.putExtra("Data",ACT_CAIDAT);
                startActivity(intent);
                break;
            case R.id.xemthem_baocaotonghop:
                intent = new Intent(this.getContext(), ActivityThongTin.class);
                intent.putExtra("Data",ACT_BAOCAOTONGHOP);
                startActivity(intent);
                break;
            case R.id.xemthem_danhmuc:
                intent = new Intent(this.getContext(), ActivityThongTin.class);
                intent.putExtra("Data",ACT_DANHMUC);
                startActivity(intent);
                break;
            case R.id.xemthem_donvitinh:
                intent = new Intent(this.getContext(), ActivityThongTin.class);
                intent.putExtra("Data",ACT_DONVITINH);
                startActivity(intent);
                break;
            case R.id.xemthem_taikhoan:
                if(MainActivity.nhanVien.getQuyen()==1) {
                    intent = new Intent(this.getContext(), ActivityThongTin.class);
                    intent.putExtra("Data", ACT_TAIKHOAN);
                    startActivity(intent);
                }
                else
                    displayToast("Bạn không phải là admin !!");
                break;
            case R.id.xemthem_khachang:
                intent = new Intent(this.getContext(), ActivityThongTin.class);
                intent.putExtra("Data",ACT_KHACHHANG);
                startActivity(intent);
                break;
            case R.id.xemthem_mathang:
                intent = new Intent(this.getContext(), ActivityThongTin.class);
                intent.putExtra("Data",ACT_MATHANG);
                startActivity(intent);
                break;
        }
    }
    private void getWidget(View view) {
        textViewKhachHang = view.findViewById(R.id.xemthem_khachang);
        textViewTaiKhoan = view.findViewById(R.id.xemthem_taikhoan);
        textViewDonViTinh = view.findViewById(R.id.xemthem_donvitinh);
        textViewMayIn = view.findViewById(R.id.xem_mayin);
        textViewDanhMuc = view.findViewById(R.id.xemthem_danhmuc);
        textViewBaoCaoTongHop = view.findViewById(R.id.xemthem_baocaotonghop);
        textViewNhapHang = view.findViewById(R.id.xemthem_nhaphang);
        textViewCaiDat = view.findViewById(R.id.xemthem_caidat);
        textViewMatHang = view.findViewById(R.id.xemthem_mathang);


        textViewKhachHang.setOnClickListener(this);
        textViewTaiKhoan.setOnClickListener(this);
        textViewDonViTinh.setOnClickListener(this);
        textViewMayIn.setOnClickListener(this);
        textViewDanhMuc.setOnClickListener(this);
        textViewBaoCaoTongHop.setOnClickListener(this);
        textViewNhapHang.setOnClickListener(this);
        textViewCaiDat.setOnClickListener(this);
        textViewMatHang.setOnClickListener(this);

    }
    public void displayToast(String message) {
        if(toast != null)
            toast.cancel();
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}