package com.datn.quanlybanhang.fragment.baocao;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.fragment.widget.LoadingDialog;


public class FragmentBaoCaoTongHop extends Fragment {
    TextView textKhachMuaHang;
    TextView textTonKho;
    TextView textCongNo;
    TextView textDoanhThu;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bao_cao_tong_hop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textCongNo = view.findViewById(R.id.baocaotonghop_congno);
        textTonKho = view.findViewById(R.id.baocaotonghop_tonkho);
        textDoanhThu = view.findViewById(R.id.baocaotonghop_doanhthu_loinhuan);
        textKhachMuaHang = view.findViewById(R.id.baocaotonghop_khachhang);
        if(getActivity()==null) return;
        textCongNo.setOnClickListener((view1 -> {

        }));

        textTonKho.setOnClickListener((view1 -> {

        }));

        textKhachMuaHang.setOnClickListener((view1 -> {

        }));
        textDoanhThu.setOnClickListener((view1 -> {
            LoadingDialog loadingDialog= new LoadingDialog(getActivity());
            replaceFragment(new FragmentBKDTLN(loadingDialog));
        }));
    }
    public void replaceFragment(Fragment fragment){
        if(getActivity()==null)return;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayoutcontentthongtin, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}