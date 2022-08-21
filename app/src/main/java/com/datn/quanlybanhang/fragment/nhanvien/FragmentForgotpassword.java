package com.datn.quanlybanhang.fragment.nhanvien;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.NhanVien;

import java.util.Random;

public class FragmentForgotpassword extends Fragment {
    MySQLiteHelper database;
    Button buttonSend;
    EditText editTextEmail;
    Toast toast;
    TextView textGetForgotPass;
    Random random = new Random(System.currentTimeMillis());
    int pass = Math.abs(random.nextInt());

    public FragmentForgotpassword() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgotpassword, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextEmail = view.findViewById(R.id.et_email);
        database =  new MySQLiteHelper(getContext());
        textGetForgotPass = view.findViewById(R.id.text_get_forgot_password);
        buttonSend = view.findViewById(R.id.button_send);
        buttonSend.setOnClickListener(view1 -> {
            NhanVien nhanVien = database.getUserEmail(editTextEmail.getText().toString().trim());
            if(nhanVien==null) {
                displayToast("Email không tồn tại!!!");
                return;
            }
                nhanVien.setPassNV(pass+"");
                if(database.updateNhanVien(nhanVien)) {
                    textGetForgotPass.setVisibility(View.VISIBLE);
                    String str = "Mật khẩu mới :"+nhanVien.getPassNV();
                    textGetForgotPass.setText(str);
                    str = ""+nhanVien.getPassNV();
                    editTextEmail.setText(str);
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