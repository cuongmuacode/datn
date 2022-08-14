package com.datn.quanlybanhang.fragment.nhanvien;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.adapter.KhachHangAdapterRecycler;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.NhanVien;
import com.datn.quanlybanhang.myinterface.IAddEditModel;

import java.util.List;

public class FragmentTaiKhoan extends Fragment implements IAddEditModel<NhanVien> {
    KhachHangAdapterRecycler nhanvienAdapter;
    RecyclerView recyclerView;
    List<NhanVien> nhanVienList;
    NhanVien selectNhanVien;
    MySQLiteHelper database;
    public FragmentTaiKhoan() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tai_khoan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getContext()!=null) {
            database = new MySQLiteHelper(getContext());
            nhanVienList = database.getListNhanVien();
            recyclerView = view.findViewById(R.id.nhanVienRecycler);
            registerForContextMenu(recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
            recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
            nhanvienAdapter = new KhachHangAdapterRecycler(nhanVienList, FragmentTaiKhoan.this);
            recyclerView.setAdapter(nhanvienAdapter);
        }
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull  View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(getActivity()==null)return;
        getActivity().getMenuInflater().inflate(R.menu.menu_model,menu);
        MenuItem menuItemSua = menu.findItem(R.id.menu_model_sua);
        MenuItem menuItemXoa = menu.findItem(R.id.menu_model_xoa);
        menuItemSua.setVisible(false);

        menuItemXoa.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.nav_model_xoa);
                builder.setMessage("Bạn có chắc không ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!selectNhanVien.getMaNV().equals("MaNV01")) {
                            nhanVienList.remove(selectNhanVien);
                            database.deleteNhanVien(selectNhanVien);
                        }
                        nhanvienAdapter.notifyDataSetChanged();
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
                return false;
            }
        });

    }

    @Override
    public boolean processModel(NhanVien nhanVien, int i) {
        selectNhanVien = nhanVien;
        return true;
    }
}