package com.datn.quanlybanhang.fragment.xemthem.danhmuc;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.DanhMuc;

import java.util.Random;


public class FragmentAddDanhMuc extends Fragment {

    DanhMuc danhMuc;
    private EditText editTextTenDanhMuc;
    private Toast toast;
    private final Random random = new Random(System.currentTimeMillis());
    MySQLiteHelper database;
    public FragmentAddDanhMuc() {
    }
    public FragmentAddDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_danh_muc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button buttonSua = view.findViewById(R.id.button_them_danhmuc);
        Button buttonXoa = view.findViewById(R.id.button_xoa_danhmuc);
        editTextTenDanhMuc = view.findViewById(R.id.tenDanhMuc);
        if(danhMuc!=null) {
            buttonSua.setText("Sửa");
            editTextTenDanhMuc.setText(danhMuc.getTenDanhMuc());
            buttonXoa.setVisibility(View.VISIBLE);
        }
        if(getContext()!=null)
            database = new MySQLiteHelper(getContext());
        buttonSua.setOnClickListener(view1 -> {
            if(editTextTenDanhMuc.getText().toString().trim().equals("")){
                displayToast("Không được để trống !!!");
                return;
            }
            if(danhMuc==null){
             boolean a = database.addDanhMuc("MaDM"+random,editTextTenDanhMuc.getText().toString());
                if(a) {
                    displayToast("Thành công !!!");
                    if(getActivity()!=null)
                        getActivity().onBackPressed();
                }
                else
                    displayToast("Thử lại xem");
            }
            else{
                int a = database.updateDanhMuc(danhMuc.getMaDanhMuc(),editTextTenDanhMuc.getText().toString());
                if(a>0) {
                    displayToast("Thành công !!!");
                    if(getActivity()!=null)
                        getActivity().onBackPressed();
                }
                else
                    displayToast("Thử lại xem !!!!");
            }
        });
        buttonXoa.setOnClickListener(view12 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.nav_model_xoa);
            builder.setMessage("Bạn có chắc không ?");
            builder.setCancelable(true);
            builder.setPositiveButton("Có", (dialogInterface, i) -> {
                database.deleteDanhMuc(danhMuc.getMaDanhMuc());
                displayToast("Thành công !!!");
                if(getActivity()!=null)
                    getActivity().onBackPressed();

            });
            builder.setNegativeButton("Không", (dialogInterface, i) -> dialogInterface.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });


    }
    public void displayToast(String message) {
        if(toast != null)
            toast.cancel();
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}