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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.fragment.sanpham.Fragment_Add_SanPham;
import com.datn.quanlybanhang.model.NhanVien;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentRegister extends Fragment {

    EditText editTextEmail;
    EditText editTextUserName;
    EditText editTextPhone ;
    EditText editTextPass;
    EditText editTextConfirmPass;
    Button buttonSignup;
    CircleImageView circleImageView;

    MySQLiteHelper database;
    Random random = new Random(System.currentTimeMillis());
    int maNV;


    public FragmentRegister() {
        // Required empty public constructor
        maNV = random.nextInt();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextEmail = view.findViewById(R.id.et_email);
        editTextUserName = view.findViewById(R.id.et_username);
        editTextPhone = view.findViewById(R.id.et_phone);
        editTextPass = view.findViewById(R.id.et_password);
        editTextConfirmPass = view.findViewById(R.id.et_confirm_password);
        buttonSignup = view.findViewById(R.id.button_signin);
        circleImageView = view.findViewById(R.id.imgNhanVien);

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode()== Activity.RESULT_OK&&result.getData()!=null){
                Uri uri = result.getData().getData();
                try {
                    if(getActivity()==null)return;
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

        database =  new MySQLiteHelper(getContext());
        buttonSignup.setOnClickListener(new View.OnClickListener() {
             int Clickprosess = 0;
             @Override
             public void onClick(View view) {
                 if (editTextUserName.getText().toString().isEmpty() ||
                         editTextEmail.getText().toString().isEmpty() ||
                         editTextConfirmPass.getText().toString().isEmpty() ||
                         editTextPass.getText().toString().isEmpty() ||
                         editTextPhone.getText().toString().isEmpty()||circleImageView.getDrawable()==null) {
                     if (Clickprosess < 5) {
                         Clickprosess++;
                         Toast.makeText(getContext(), "Không được để trống !!!", Toast.LENGTH_SHORT).show();
                     }

                 }

                 else{
                     if(editTextPass.getText().toString().trim().equals(editTextConfirmPass.getText().toString().trim())) {
                        if(isValidEmailAddress(editTextEmail.getText().toString().trim())) {
                            if(database.getUserEmail(editTextEmail.getText().toString().trim())==null){
                                if(database.getUser(editTextUserName.getText().toString().trim())==null){

                                    BitmapDrawable bitmapDrawable = (BitmapDrawable) circleImageView.getDrawable();
                                        Bitmap bitmap = bitmapDrawable.getBitmap();
                                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);

                                    byte [] bytes = outputStream.toByteArray();
                                    NhanVien nhanVien = new NhanVien(
                                            "MaNV" + maNV,
                                            editTextUserName.getText().toString().trim(),
                                            editTextPhone.getText().toString().trim(),
                                            editTextEmail.getText().toString().trim(),
                                            0,
                                            editTextPass.getText().toString().trim(),
                                            bytes
                                    );
                                    if(database.addNhanVien(nhanVien))
                                        replaceFragment(new FragmentLogin());
                                    else
                                        maNV = random.nextInt();
                                }else if (Clickprosess < 5) {
                                    Clickprosess++;
                                    Toast.makeText(getContext(), "Tài khoản đã tồn tại !!!", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                            }
                            else if (Clickprosess < 5) {
                                Clickprosess++;
                                Toast.makeText(getContext(), "Email đã đăng ký rồi !!!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        else
                            Toast.makeText(getContext(), "Email không đúng !!!", Toast.LENGTH_SHORT).show();

                     }
                     else if (Clickprosess < 5) {
                             Clickprosess++;
                             Toast.makeText(getContext(), "Mật khẩu không khớp !!!", Toast.LENGTH_SHORT).show();
                        return;
                     }
                    Clickprosess = 0;
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
    public  boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

}