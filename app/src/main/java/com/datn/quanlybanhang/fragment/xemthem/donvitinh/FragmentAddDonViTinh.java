package com.datn.quanlybanhang.fragment.xemthem.donvitinh;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.datn.quanlybanhang.model.DonViTinh;

import java.util.Random;

public class FragmentAddDonViTinh extends Fragment {
    DonViTinh donViTinh;
    private EditText edittextTenDVT;
    private Button buttonSua;
    private Button buttonXoa;
    private Toast toast;
    private Random random = new Random(System.currentTimeMillis());
    MySQLiteHelper database;
    public FragmentAddDonViTinh() {
        // Required empty public constructor
    }

    public FragmentAddDonViTinh(DonViTinh donViTinh) {
        this.donViTinh = donViTinh;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_don_vi_tinh, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonSua = view.findViewById(R.id.button_them_donvitinh);
        buttonXoa = view.findViewById(R.id.button_xoa_donvitinh);
        edittextTenDVT = view.findViewById(R.id.tenDonVitinh);
        if(donViTinh!=null) {
            buttonSua.setText("Sửa");
            edittextTenDVT.setText(donViTinh.getTenDVT());
            buttonXoa.setVisibility(View.VISIBLE);
        }
        if(getContext()!=null)
            database = new MySQLiteHelper(getContext());
        buttonSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edittextTenDVT.getText().toString().trim().equals("")){
                    displayToast("Không được để trống !!!");
                    return;
                }
                if(donViTinh==null){
                    boolean a = database.addDonViTinh("MaDVT"+random,edittextTenDVT.getText().toString());
                    if(a) {
                        displayToast("Thành công !!!");
                        if(getActivity()!=null)
                            getActivity().onBackPressed();
                    }
                    else
                        displayToast("Thử lại xem");
                }
                else{
                    int a = database.updateDonViTinh(donViTinh.getMaDVT(),edittextTenDVT.getText().toString());
                    if(a>0) {
                        displayToast("Thành công !!!");
                        if(getActivity()!=null)
                            getActivity().onBackPressed();
                    }
                    else
                        displayToast("Thử lại xem !!!!");
                }
            }
        });
        buttonXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.nav_model_xoa);
                builder.setMessage("Bạn có chắc không ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        displayToast("Thành công !!!");
                        database.deleteDonViTinh(donViTinh.getMaDVT());
                        if(getActivity()!=null)
                            getActivity().onBackPressed();

                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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