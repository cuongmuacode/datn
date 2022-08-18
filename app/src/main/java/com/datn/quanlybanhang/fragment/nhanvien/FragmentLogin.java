package com.datn.quanlybanhang.fragment.nhanvien;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.activityy.MainActivity;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.NhanVien;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class FragmentLogin extends Fragment  {

    public FragmentLogin() {
        // Required empty public constructor
    }
    EditText editTextUser;
    EditText editTextPass;
    TextView textForgotPass;
    TextView textResgister;
    Button buttonSignin;
    MySQLiteHelper database =  new MySQLiteHelper(getContext());
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         editTextUser = view.findViewById(R.id.et_username);
         editTextPass = view.findViewById(R.id.et_password);
         textForgotPass = view.findViewById(R.id.text_forgot_password);
         textResgister = view.findViewById(R.id.textRegister);
         buttonSignin = view.findViewById(R.id.button_signin);
         
        database =  new MySQLiteHelper(getContext());
        textForgotPass.setOnClickListener(view1 -> replaceFragment(new FragmentForgotpassword()));
         textResgister.setOnClickListener(view12 -> replaceFragment(new FragmentRegister()));
        buttonSignin.setOnClickListener(new View.OnClickListener() {
            int Clickprosess =0;
            @Override
            public void onClick(View view) {
                if (editTextUser.getText().toString().isEmpty() ||
                        editTextPass.getText().toString().isEmpty() ){
                    if (Clickprosess < 5) {
                        Clickprosess++;
                        Toast.makeText(getContext(), "Không được để trống !!!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    NhanVien nhanVien = database.getUser(editTextUser.getText().toString().trim());
                    if(nhanVien == null)
                        Toast.makeText(getContext(), "Tài khoản không tồn tại !!!", Toast.LENGTH_SHORT).show();
                    else if(nhanVien.getPassNV().trim().equals(editTextPass.getText().toString().trim())) {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("User",nhanVien);
                        intent.putExtra("DataUser",bundle);
                        setDataCache(nhanVien);
                        startActivity(intent);
                        if(getActivity()!=null)
                            getActivity().finish();
                    }
                    else
                        Toast.makeText(getContext(), "Sai mật khẩu rồi !!!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }




    public void replaceFragment(Fragment fragment){
        if(getActivity()==null) return;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutLogin, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void setDataCache(NhanVien nhanVien){
        if(getActivity()==null) return;
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        File file;
        try{
            file = new File(getActivity().getCacheDir(),"User.txt");
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(nhanVien);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}