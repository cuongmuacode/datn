package com.datn.quanlybanhang.fragment.hoadonnhap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.adapter.HoaDonNhapAdapterRecycler;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.HoaDonNhap;
import com.datn.quanlybanhang.model.KhachHang;
import com.datn.quanlybanhang.model.NhanVien;
import com.datn.quanlybanhang.myinterface.IClickItemListenerRecycer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Fragment_HoaDonNhap extends Fragment implements IClickItemListenerRecycer<HoaDonNhap> {

    EditText editText;
    ImageView imageView;
    RecyclerView recyclerView;
    HoaDonNhapAdapterRecycler hoaDonNhapAdapterRecycler;
    List<HoaDonNhap> hoaDonNhaps;
    MySQLiteHelper database;
    List<HoaDonNhap> filterListHoaDonNhap = new ArrayList<>();
    HoaDonNhap selectHoaDonNhap;
    Toast toast;
    public Fragment_HoaDonNhap() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__hoa_don_nhap, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.edit_hoadonnhap_search);
        imageView = view.findViewById(R.id.imageview_hoadonnhap_sort);
        recyclerView = view.findViewById(R.id.recycler_hoadonnhap);
        if(getContext()==null) return;
        database = new MySQLiteHelper(getContext());
        hoaDonNhaps = database.getListHoaDonNhap();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        hoaDonNhapAdapterRecycler = new HoaDonNhapAdapterRecycler(hoaDonNhaps,getContext(),this);
        recyclerView.setAdapter(hoaDonNhapAdapterRecycler);
        registerForContextMenu(recyclerView);
        xulyEditText();
        xuLySort();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(getActivity()==null) return;
        getActivity().getMenuInflater().inflate(R.menu.menu_model,menu);
        MenuItem menuItemSua = menu.findItem(R.id.menu_model_sua);
        MenuItem menuItemXoa = menu.findItem(R.id.menu_model_xoa);
        MenuItem menuItemDaThanhToan = menu.findItem(R.id.menu_model_dathanhtoan);
        menuItemSua.setVisible(false);
        menuItemDaThanhToan.setVisible(true);
        menuItemDaThanhToan.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                return false;
            }
        });
        menuItemXoa.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                return false;
            }
        });

    }

    private void xulyEditText() {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(getContext()==null) return;
                if (view == editText) {
                    if (b) {
                        // Open keyboard
                        ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                                showSoftInput(editText, InputMethodManager.SHOW_FORCED);
                    } else {
                        // Close keyboard
                        ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                                hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    }
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String search = editable.toString();
                if(search.isEmpty()) {
                    hoaDonNhapAdapterRecycler = new HoaDonNhapAdapterRecycler(hoaDonNhaps,getContext(),
                            Fragment_HoaDonNhap.this);
                    recyclerView.setAdapter(hoaDonNhapAdapterRecycler);

                }
                else{

                    filterListHoaDonNhap.clear();
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    for(HoaDonNhap hoaDonNhap : hoaDonNhaps){
                        KhachHang khachHang = database.getKhachHang(hoaDonNhap.getMaKH());
                        NhanVien nhanVien = database.getNhanVien(hoaDonNhap.getMaNV());

                        String dateSTR = dateFormat.format(new Date(Long.parseLong(hoaDonNhap.getNgayNhap())));
                        if((hoaDonNhap.getGiaNhap()+"").contains(search)||
                                (hoaDonNhap.getGiaBan()+"").contains(search)||
                                dateSTR.contains(search)||
                                nhanVien.getHoTenNV().contains(search)||
                                khachHang.getTenKH().contains(search))
                            filterListHoaDonNhap.add(hoaDonNhap);
                    }
                    recyclerView.setAdapter(new HoaDonNhapAdapterRecycler(filterListHoaDonNhap,getContext(),
                            Fragment_HoaDonNhap.this));
                }
                hoaDonNhapAdapterRecycler.notifyDataSetChanged();
            }
        });
    }
    private void xuLySort() {
        imageView.setOnClickListener(new View.OnClickListener() {
            int i = 1;
            @Override
            public void onClick(View view) {
                if(i==1){
                    displayToast("Sắp xếp giảm dần theo giá !!!");
                    imageView.setImageResource(R.drawable.ic_baseline_arrow_downward_24);
                    Collections.sort(hoaDonNhaps, new Comparator<HoaDonNhap>() {
                        @Override
                        public int compare(HoaDonNhap hoaDonNhap, HoaDonNhap hoaDonNhap1) {
                            int a = Integer.compare(hoaDonNhap.getSoLuong(),hoaDonNhap1.getSoLuong());
                            int b = Long.compare(hoaDonNhap.getGiaBan(),hoaDonNhap1.getGiaBan());
                            int c = Long.compare(hoaDonNhap.getGiaNhap(),hoaDonNhap1.getGiaNhap());
                            if(b!=0) return b;
                            else if(a!=0) return c;
                            else return a;
                        }
                    });
                    i=2;
                }
                else if(i==2) {
                    displayToast("Sắp xếp tăng dần theo giá !!!");
                    imageView.setImageResource(R.drawable.ic_baseline_arrow_upward_24);
                    Collections.sort(hoaDonNhaps, new Comparator<HoaDonNhap>() {
                        @Override
                        public int compare(HoaDonNhap hoaDonNhap, HoaDonNhap hoaDonNhap1) {
                            int a = Integer.compare(hoaDonNhap1.getSoLuong(),hoaDonNhap.getSoLuong());
                            int b = Long.compare(hoaDonNhap1.getGiaBan(),hoaDonNhap.getGiaBan());
                            int c = Long.compare(hoaDonNhap1.getGiaNhap(),hoaDonNhap.getGiaNhap());
                            if(b!=0) return b;
                            else if(a!=0) return c;
                            else return a;
                        }
                    });
                    i=3;
                }
                else if(i == 3){
                    displayToast("Săp xếp mặc định !!!");
                    imageView.setImageResource(R.drawable.ic_baseline_sort_24);
                    hoaDonNhaps.clear();
                    hoaDonNhaps.addAll(database.getListHoaDonNhap());
                    i=1;
                }
                hoaDonNhapAdapterRecycler.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClickItemModel(HoaDonNhap hoaDonNhap) {

    }

    @Override
    public void onClickChiTietModel(HoaDonNhap hoaDonNhap) {
        selectHoaDonNhap = hoaDonNhap;
    }

    public void displayToast(String message) {
        if(toast != null)
            toast.cancel();
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}