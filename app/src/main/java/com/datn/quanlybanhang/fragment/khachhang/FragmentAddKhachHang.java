package com.datn.quanlybanhang.fragment.khachhang;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.model.KhachHang;
import com.datn.quanlybanhang.myinterface.IAddEditModel;

public class FragmentAddKhachHang extends Fragment {
    AppCompatButton buttonAdd;
    EditText editTextHoTen;
    EditText editTextSODT;
    EditText editTextEmail;
    EditText editTextDiaChi;
    Toast toast;
    EditText editTextGhiChu;
    IAddEditModel<KhachHang> iAddEditModel;
    KhachHang khachHang;
    int addOREdit;


    public FragmentAddKhachHang(IAddEditModel<KhachHang> iAddEditModel) {
        this.iAddEditModel = iAddEditModel;
        this.addOREdit = FragmentKhachHang.ADD_KHACH_HANG;
    }
    public FragmentAddKhachHang(IAddEditModel<KhachHang> iAddEditModel,KhachHang khachHang) {
        this.iAddEditModel = iAddEditModel;
        this.khachHang = khachHang;
        this.addOREdit = FragmentKhachHang.SUA_KHACH_HANG;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_add_khach_hang, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonAdd = view.findViewById(R.id.buttonaddKhachHang);
        editTextHoTen = view.findViewById(R.id.inputedittext_tenKhachHang);
        editTextSODT = view.findViewById(R.id.inputedittext_SoDienThoai);
        editTextDiaChi = view.findViewById(R.id.inputedittext_DiaChi);
        editTextEmail = view.findViewById(R.id.inputedittext_Email);
        editTextGhiChu = view.findViewById(R.id.inputedittext_GhiChu);
        if(khachHang!=null){
            editTextHoTen.setText(khachHang.getTenKH());
            editTextSODT.setText(khachHang.getSoDT());
            editTextDiaChi.setText(khachHang.getDiaChi());
            editTextEmail.setText(khachHang.getEmail());
            editTextGhiChu.setText(khachHang.getGhiChu());
        }
        if(addOREdit == FragmentKhachHang.ADD_KHACH_HANG) buttonAdd.setText("Thêm");
        else if(addOREdit == FragmentKhachHang.SUA_KHACH_HANG) buttonAdd.setText("Sửa");
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextDiaChi.getText().toString().trim().isEmpty()||editTextEmail.getText().toString().trim().isEmpty()
                        ||editTextHoTen.getText().toString().trim().isEmpty()||
                        editTextSODT.getText().toString().trim().isEmpty()) {
                    displayToast("Không được để trống !!!");
                }
                else {
                    String str;
                    if(editTextGhiChu.getText()==null)
                        str = "";
                    else
                        str = editTextGhiChu.getText().toString();
                    KhachHang khachHang = new KhachHang(
                            "MAKH",
                            editTextHoTen.getText().toString(),
                            editTextDiaChi.getText().toString(),
                            editTextEmail.getText().toString(),
                            editTextSODT.getText().toString(),
                            str
                    );
                    if(iAddEditModel.processModel(khachHang, addOREdit)&&getActivity()!=null) getActivity().onBackPressed();
                    else
                        displayToast("Không gửi được thử lại xem ?");
                }
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