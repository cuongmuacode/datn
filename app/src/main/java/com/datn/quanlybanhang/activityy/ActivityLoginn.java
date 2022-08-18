package com.datn.quanlybanhang.activityy;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.fragment.nhanvien.FragmentLogin;
import com.datn.quanlybanhang.fragment.sanpham.Fragment_Add_SanPham;
import com.datn.quanlybanhang.model.NhanVien;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ActivityLoginn extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       //this.deleteDatabase(MySQLiteHelper.DATABASE_NAME);

        MySQLiteHelper database = new MySQLiteHelper(this);

        if(database.getCountKhachHang()<=0) database.initKhachHang();
        if(database.getCountDanhMuc()<=0)
           database.addDanhMuc("MaDVT01","Trái cây");
         if(database.getCountDonViTinh()<=0)
           database.addDonViTinh("MaDM01","Cái");

       //database.initKhachHang();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.ssanh1, options);

        options.inSampleSize = Fragment_Add_SanPham.calculateInSampleSize(options, 200,200);
        options.inJustDecodeBounds = false;
        Bitmap smallBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ssanh1, options);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        runOnUiThread(() -> smallBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos));
        byte[] bitmapdata = bos.toByteArray();
        if(database.getCountNhanVien()<=0) database.initNhanVien(bitmapdata);

        NhanVien nhanVien = getData();
        if(nhanVien!=null){
            String username = nhanVien.getHoTenNV();
            if(database.getUser(username)!=null){
                Intent intent = new Intent(this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("User",nhanVien);
                intent.putExtra("DataUser",bundle);
                startActivity(intent);
                finish();
            }
        }
        else
            managerFragmentAll(new FragmentLogin());
    }


    private void managerFragmentAll(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayoutLogin, fragment);
        transaction.commit();
    }

    public NhanVien getData(){
        NhanVien nhanVien =  null;

        try {
            File file = new File(getCacheDir(),"User.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            nhanVien = (NhanVien) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return nhanVien;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}