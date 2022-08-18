package com.datn.quanlybanhang.fragment.hoadon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.KhoHang;
import com.datn.quanlybanhang.model.SanPham;

public class FragmentThemSoLuong extends Fragment {
    EditText textInputEditText;
    Button appCompatButton;
    Toast toast;
    SanPham selectSanPham;
    KhoHang selectKhoHang;
    public FragmentThemSoLuong(SanPham selectSanPham,KhoHang selectKhoHang) {
        // Required empty public constructor
        this.selectSanPham = selectSanPham;
        this.selectKhoHang = selectKhoHang;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_themsoluong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appCompatButton = view.findViewById(R.id.button_add_soluong);
        textInputEditText = view.findViewById(R.id.inputedittext_add_SoLuong);
        MySQLiteHelper database = new MySQLiteHelper(getContext());
        KhoHang khoHang = database.getKhoHang(selectSanPham.getMaSP());
        appCompatButton.setOnClickListener(view1 -> {
            if(textInputEditText.getText().toString().trim().isEmpty()) {
                    displayToast("Không được để trống");
                return;
            }
            if(khoHang.getSoLuong() < Integer.parseInt(textInputEditText.getText().toString()))
                displayToast("Chỉ còn "+khoHang.getSoLuong()+" sản phẩm!!");
            else {
                selectKhoHang.setSoLuong(
                        Integer.parseInt(textInputEditText.getText().toString()));
                if(getActivity()!=null)
                getActivity().onBackPressed();
            }

        });

    }
    public void displayToast(String message) {
        if(toast != null)
            toast.cancel();
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

}