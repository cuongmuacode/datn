package com.datn.quanlybanhang.fragment.nhanvien;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.activityy.MainActivity;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.fragment.widget.LoadingDialog;
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
    Toast toast;
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
        buttonSignin.setOnClickListener(view13 -> {
            if (editTextUser.getText().toString().isEmpty() ||
                    editTextPass.getText().toString().isEmpty() ){
                displayToast("Không được để trống !!!");
                return;
            }

                NhanVien nhanVien = database.getUser(editTextUser.getText().toString().trim());
                if(nhanVien == null) {
                    Toast.makeText(getContext(), "Tài khoản không tồn tại !!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!nhanVien.getPassNV().trim().equals(editTextPass.getText().toString().trim())) {
                    displayToast("Sai mật khẩu rồi !!!");
                    return;
                }
            if(getActivity()==null) return;
            LoadingDialog loadingDialog = new LoadingDialog(getActivity());
            loadingDialog.startLoadingDialog();
            Intent intent = new Intent(getContext(), MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("User",nhanVien);
                    intent.putExtra("DataUser",bundle);
                    setDataCache(nhanVien);
                    startActivity(intent);
                    getActivity().finish();

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
    public void displayToast(String message) {
        if(toast != null)
            toast.cancel();
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

}