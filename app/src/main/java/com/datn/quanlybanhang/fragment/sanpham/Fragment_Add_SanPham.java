package com.datn.quanlybanhang.fragment.sanpham;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.DanhMuc;
import com.datn.quanlybanhang.model.DonViTinh;
import com.datn.quanlybanhang.model.KhoHang;
import com.datn.quanlybanhang.model.SanPham;
import com.datn.quanlybanhang.myinterface.IAddEditModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class Fragment_Add_SanPham extends Fragment {
    EditText textInputEditTextTenSP;
    Spinner spinnerDonViTinh;
    Spinner spinnerDanhMuc;
    ImageView imageViewSanPham;
    EditText textInputEditTextNuocSX;
    EditText textInputEditTextChiTietSP;
    Button appCompatButton;
    Toast toast;
    IAddEditModel<SanPham> iAddEditModel;
    int addOREdit,soLuong = 0;
    String strDonViTinh;
    String strDanhMuc;
    SanPham sanPham;
    MySQLiteHelper database;


    public Fragment_Add_SanPham(IAddEditModel<SanPham> iAddEditModel) {
        this.iAddEditModel = iAddEditModel;
        this.addOREdit = Fragment_San_Pham.ADD_SAN_PHAM;
    }
    public Fragment_Add_SanPham(IAddEditModel<SanPham> iAddEditModel, SanPham sanPham) {
        this.iAddEditModel = iAddEditModel;
        this.addOREdit = Fragment_San_Pham.SUA_SAN_PHAM;
        this.sanPham = sanPham;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__add__san_pham, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode()== Activity.RESULT_OK&&result.getData()!=null){
                Uri uri = result.getData().getData();
                try {
                    if(getActivity()==null)return;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(inputStream,null, options);
                    options.inSampleSize = calculateInSampleSize(options,200,200);
                    options.inJustDecodeBounds = false;
                    inputStream = getActivity().getContentResolver().openInputStream(uri);
                    Bitmap smallBitmap = BitmapFactory.decodeStream(inputStream,null, options);
                    imageViewSanPham.setImageBitmap(smallBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        textInputEditTextTenSP = view.findViewById(R.id.inputedittext_tenSanPham);
        textInputEditTextNuocSX = view.findViewById(R.id.inputedittext_NuocSX);
        textInputEditTextChiTietSP = view.findViewById(R.id.inputedittext_ChiTietSanPham);
        imageViewSanPham = view.findViewById(R.id.imgSanPham);
        spinnerDonViTinh = view.findViewById(R.id.sprinnerDonViTinh);
        spinnerDanhMuc =view.findViewById(R.id.sprinnerDanhMuc);
        appCompatButton = view.findViewById(R.id.buttonaddSanPham);
        if(getContext()!=null)
            database =new  MySQLiteHelper(getContext());
        if(addOREdit==Fragment_San_Pham.ADD_SAN_PHAM)
            appCompatButton.setText("Thêm");
        else if(addOREdit==Fragment_San_Pham.SUA_SAN_PHAM)
            appCompatButton.setText("Sửa");

        List<DonViTinh> listDonViTinh = database.getListDonViTinh();
        List<String> listStringDonViTinh = new ArrayList<>();

        for(DonViTinh donViTinh : listDonViTinh){
            listStringDonViTinh.add(donViTinh.getTenDVT());
        }

        ArrayAdapter<String> adapterDonViTinh = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, listStringDonViTinh);
        adapterDonViTinh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDonViTinh.setAdapter(adapterDonViTinh);

        List<DanhMuc> listDanhMuc = database.getListDanhMuc();
        List<String> listStringDanhMuc = new ArrayList<>();

        for(DanhMuc danhMuc : listDanhMuc){
            listStringDanhMuc.add(danhMuc.getTenDanhMuc());
        }

        ArrayAdapter<String> adapterDanhMuc = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, listStringDanhMuc);
        adapterDanhMuc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDanhMuc.setAdapter(adapterDanhMuc);


        spinnerDonViTinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strDonViTinh = listStringDonViTinh.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerDanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strDanhMuc = listStringDanhMuc.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(sanPham!=null){
            byte[] bytes = sanPham.getImgSP();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageViewSanPham.setImageBitmap(bitmap);
            KhoHang khoHang = database.getKhoHang(sanPham.getMaSP());
            soLuong = khoHang.getSoLuong();
            textInputEditTextTenSP.setText(sanPham.getTenSP());
            textInputEditTextNuocSX.setText(sanPham.getNuocSX());
            textInputEditTextChiTietSP.setText(sanPham.getChiTietSP());
            for(int i=0;i<spinnerDonViTinh.getAdapter().getCount();i++){
                if(spinnerDonViTinh.getAdapter().getItem(i).toString().contains(sanPham.getDonViTinh()))
                    spinnerDonViTinh.setSelection(i);
            }
            for(int i=0;i<spinnerDanhMuc.getAdapter().getCount();i++){
                if(spinnerDanhMuc.getAdapter().getItem(i).toString().contains(sanPham.getLoaiSP()))
                    spinnerDanhMuc.setSelection(i);
            }
        }

        imageViewSanPham.setOnClickListener(view12 -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            launcher.launch(intent);
        });

        appCompatButton.setOnClickListener(view1 -> {
            if (textInputEditTextChiTietSP.getText().toString().trim().isEmpty() ||
                    textInputEditTextNuocSX.getText().toString().trim().isEmpty() ||
                    textInputEditTextTenSP.getText().toString().trim().isEmpty()){
                displayToast("Không được để trống !!!");
            }
            else{
                if(addOREdit == Fragment_San_Pham.ADD_SAN_PHAM) soLuong = 0;
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageViewSanPham.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                byte [] bytes = outputStream.toByteArray();
                SanPham sanPham = new SanPham(
                    "MaSP",
                        textInputEditTextTenSP.getText().toString(),
                        strDonViTinh,
                        textInputEditTextNuocSX.getText().toString(),
                        textInputEditTextChiTietSP.getText().toString(),
                        strDanhMuc,
                        bytes
                );
                if(iAddEditModel.processModel(sanPham,addOREdit)&&getActivity()!=null) getActivity().onBackPressed();
                else
                    displayToast("Thử lại xem !!!!");
            }
        });
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;

        int stretch_width = Math.round((float)width / (float)reqWidth);
        int stretch_height = Math.round((float)height / (float)reqHeight);

        return Math.max(stretch_width, stretch_height);
    }
    public void displayToast(String message) {
        if(toast != null)
            toast.cancel();
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

}