package com.datn.quanlybanhang.fragment.nhanvien;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.activityy.ActivityLoginn;
import com.datn.quanlybanhang.activityy.MainActivity;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.fragment.sanpham.Fragment_Add_SanPham;
import com.datn.quanlybanhang.model.NhanVien;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_DoiMatKhau extends Fragment {

    EditText editTextPassCu;
    EditText editTextPassMoi;
    EditText editTextConfirmPass;
    Button buttonDoiMatKhau;
    CircleImageView circleImageView;
    Toast toast;

    MySQLiteHelper database;

    public Fragment_DoiMatKhau() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_doimatkhau, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NhanVien nhanVien = MainActivity.nhanVien;
        buttonDoiMatKhau = view.findViewById(R.id.button_doimatkhau);
        editTextPassCu = view.findViewById(R.id.password_cu_doimatkhau);
        editTextPassMoi = view.findViewById(R.id.password_moi_doimatkhau);
        editTextConfirmPass = view.findViewById(R.id.confirm_password_doimatkhau);
        circleImageView = view.findViewById(R.id.imgDoiMatKhau);

        database =  new MySQLiteHelper(getContext());

        byte[] bytes = nhanVien.getByteImgs();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        circleImageView.setImageBitmap(bitmap);

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode()== Activity.RESULT_OK &&result.getData()!=null){
                Uri uri = result.getData().getData();
                try {
                    if(getActivity()==null) return;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(inputStream,null, options);
                    options.inSampleSize = Fragment_Add_SanPham.calculateInSampleSize(options,200,200);
                    options.inJustDecodeBounds = false;
                    inputStream = getActivity().getContentResolver().openInputStream(uri);
                    Bitmap smallBitmap = BitmapFactory.decodeStream(inputStream,null, options);
                    circleImageView.setImageBitmap(smallBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        circleImageView.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            launcher.launch(intent);
        });

        buttonDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            int Clickprosess = 0;
            @Override
            public void onClick(View view) {
                if(getActivity()==null)return;
                if(editTextPassCu.getText().toString().isEmpty()&&
                        editTextPassMoi.getText().toString().isEmpty()&&
                        editTextConfirmPass.getText().toString().isEmpty()){
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) circleImageView.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                    byte [] bytes = outputStream.toByteArray();
                    nhanVien.setByteImgs(bytes);
                    if(database.updateNhanVien(nhanVien)) {
                        try {
                            File file = new File(getActivity().getCacheDir(), "User.txt");
                            if (file.delete()){
                                getActivity().setResult(10);
                                Intent intent = new Intent(getContext(), ActivityLoginn.class);
                                setDataCache(nhanVien);
                                startActivity(intent);
                                getActivity().finish();
                                return;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                else if(editTextPassCu.getText().toString().isEmpty()||
                        editTextPassMoi.getText().toString().isEmpty()||
                        editTextConfirmPass.getText().toString().isEmpty()){
                    if (Clickprosess < 5) {
                        Clickprosess++;
                        Toast.makeText(getContext(), "Không được để trống !!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Clickprosess = 0;
                    return;
                }

                if(editTextPassCu.getText().toString().trim().equals(nhanVien.getPassNV())){
                    if(editTextPassMoi.getText().toString().trim().
                            equals(editTextConfirmPass.getText().toString().trim())){

                        BitmapDrawable bitmapDrawable = (BitmapDrawable) circleImageView.getDrawable();
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);

                        byte [] bytes = outputStream.toByteArray();

                        nhanVien.setByteImgs(bytes);
                        nhanVien.setPassNV(editTextPassMoi.getText().toString());
                        if(database.updateNhanVien(nhanVien)){
                            try {
                                File file = new File(getActivity().getCacheDir(), "User.txt");
                                if (file.delete()) {
                                    getActivity().setResult(10);
                                    Intent intent = new Intent(getContext(), ActivityLoginn.class);
                                    setDataCache(nhanVien);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                                getActivity().finish();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                    }
                    else {
                        displayToast("Mật khẩu mới không khớp !!!");
                    }
                }
                else {
                 displayToast("Mật khẩu cũ sai  !!!");
                }

            }
        });

    }
    public void setDataCache(NhanVien nhanVien){
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        File file;
        try{
            if(getActivity()==null) return;
            file = new File(getActivity().getCacheDir(),"User.txt");
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(nhanVien);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e){
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