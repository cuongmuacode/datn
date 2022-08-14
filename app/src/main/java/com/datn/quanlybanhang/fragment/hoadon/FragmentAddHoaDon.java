package com.datn.quanlybanhang.fragment.hoadon;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.activityy.MainActivity;
import com.datn.quanlybanhang.adapter.MatHangAdapterRecycler;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.fragment.FragmentBanHang;
import com.datn.quanlybanhang.fragment.khachhang.FragmentKhachHang;
import com.datn.quanlybanhang.model.HoaDon;
import com.datn.quanlybanhang.model.KhachHang;

import com.datn.quanlybanhang.model.SanPham;
import com.datn.quanlybanhang.myinterface.IClickItemListenerRecycer;
import com.datn.quanlybanhang.myinterface.IClickItemSanPham;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;


public class FragmentAddHoaDon extends Fragment implements IClickItemSanPham,Serializable,IClickItemListenerRecycer<SanPham> {
    private List<SanPham> sanPhamList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Button buttonAdd;
    private Button buttonNo;
    private MatHangAdapterRecycler matHangAdapterRecycler;
    public SanPham selectSanPham;
    public KhachHang selectKhachhang;
    public HoaDon hoaDon;
    private MySQLiteHelper database;
    private TextView textSoHoaDon;
    private TextView textTenKhachhang;
    private TextView textTenNhanVien;
    private TextView textTongTien;
    private Random random = new Random(System.currentTimeMillis());
    private Long soHD;
    long triGia = 0;


    public FragmentAddHoaDon() {
        soHD =  Math.abs(random.nextLong());
    }


    public FragmentAddHoaDon(List<SanPham> sanPhamList, SanPham selectSanPham, KhachHang selectKhachhang,
                             HoaDon hoaDon, Random random, long soHD) {
        this.sanPhamList = sanPhamList;
        this.selectSanPham = selectSanPham;
        this.selectKhachhang = selectKhachhang;
        this.hoaDon = hoaDon;
        this.random = random;
        this.soHD = soHD;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_add_hoa_don, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycer_hoadon_add);
        buttonAdd = view.findViewById(R.id.add_hoadon_donhang);
        buttonNo = view.findViewById(R.id.add_hoadon_donhang_no);
        textSoHoaDon = view.findViewById(R.id.addSoHoaDon);
        textTenKhachhang = view.findViewById(R.id.tv_tenkhachhang);
        textTongTien = view.findViewById(R.id.tongtienhoadon);
        textTenNhanVien = view.findViewById(R.id.tenNhanVienHoaDon);
        database = new MySQLiteHelper(getContext());
        xulyall();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull  Menu menu, @NonNull  MenuInflater inflater) {
        MenuItem menuItem = menu.add(1,1,1,getResources().getString(R.string.nav_model_xoa));
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuItem.setTitle(R.string.nav_banhang);
        menuItem.setIcon(R.drawable.ic_baseline_zoom_out_map_24);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                replaceFragment(new FragmentBanHang(false));
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(getActivity()==null) return;
        getActivity().getMenuInflater().inflate(R.menu.menu_model,menu);
        MenuItem menuItemSoLuong = menu.findItem(R.id.menu_model_soluong);
        MenuItem menuItemSua = menu.findItem(R.id.menu_model_sua);
        MenuItem menuItemXoa = menu.findItem(R.id.menu_model_xoa);
        menuItemSoLuong.setVisible(true);
        menuItemSua.setVisible(false);
        menuItemXoa.setVisible(false);
    }

    @Override
    public boolean onContextItemSelected(@NonNull  MenuItem item) {
        if(item.getItemId()==R.id.menu_model_soluong)
            replaceFragment(new FragmentThemSoLuong(selectSanPham));
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClickSanPham(SanPham sanPham) {
        if(sanPham !=null)
            if (sanPhamList.isEmpty()) {
                sanPham.setSoLuongSP(1);
                sanPhamList.add(sanPham);
            } else {
                for (SanPham sp : sanPhamList) {
                    if (sp.getMaSP().equals(sanPham.getMaSP()))
                        return;
                }
                sanPham.setSoLuongSP(1);
                sanPhamList.add(sanPham);
                FragmentBanHang.countSanPham++;
            }
    }

    @Override
    public void onResume() {
        super.onResume();
        System.gc();
        FragmentBanHang.iClickItemSanPham =  new FragmentAddHoaDon(FragmentAddHoaDon.this.sanPhamList,
                FragmentAddHoaDon.this.selectSanPham,FragmentAddHoaDon.this.selectKhachhang,FragmentAddHoaDon.this.hoaDon,
                FragmentAddHoaDon.this.random,FragmentAddHoaDon.this.soHD);
    }

    @Override
    public void onClickKhachHang(KhachHang khachHang) {
        selectKhachhang = khachHang;
    }

    @Override
    public void onClickItemModel(SanPham sanPham) {
        if(getActivity()!=null)
          getActivity().setResult(Activity.RESULT_CANCELED);
        sanPhamList.remove(sanPham);
        if(FragmentBanHang.countSanPham>0)
            FragmentBanHang.countSanPham--;
        if(!sanPhamList.isEmpty()) {
            triGia = 0;
            for (SanPham sp : sanPhamList)
                triGia += sp.getSoLuongSP() * sp.getGiaSP();
            String str =   triGia+" VND";
            textTongTien.setText(str);
        }
        else
            textTongTien.setText("0 VND");
        matHangAdapterRecycler.notifyDataSetChanged();
    }

    @Override
    public void onClickChiTietModel(SanPham sanPham) {
        selectSanPham = sanPham;
    }



    private void xulyall() {
        matHangAdapterRecycler =  new MatHangAdapterRecycler(
                FragmentAddHoaDon.this,sanPhamList);
        if(getContext()!=null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
            recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        }

        recyclerView.setAdapter(matHangAdapterRecycler);
        registerForContextMenu(recyclerView);

        String str = getResources().getString(R.string.addhoadon_sohoadon)+soHD;
        textSoHoaDon.setText(str);
        str = getResources().getString(R.string.addhoadon_tennhanvien)+MainActivity.nhanVien.getHoTenNV();
        textTenNhanVien.setText(str);

        textTenKhachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new FragmentKhachHang(FragmentAddHoaDon.this));
            }
        });

        if(selectKhachhang==null) selectKhachhang = database.getKhachHang("MaKH01");
        str = getResources().getString(R.string.addhoadon_tenkhachhanh)+selectKhachhang.getTenKH();
        textTenKhachhang.setText(str);

        if(!sanPhamList.isEmpty()) {
            triGia = 0;
            for (SanPham sp : sanPhamList)
                triGia += sp.getSoLuongSP() * sp.getGiaSP();
            str =  triGia + " VND";
            textTongTien.setText(str);
        }
        else textTongTien.setText("0 VND");

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            int processingClick = 0;

            @Override
            public void onClick(View view) {

                if(!sanPhamList.isEmpty()){
                    hoaDon = new HoaDon(
                            "HD" + soHD,
                            System.currentTimeMillis()+"",
                            selectKhachhang.getMaKH(),
                            MainActivity.nhanVien.getMaNV(),
                            triGia,
                            sanPhamList,
                            1
                    );
                    if(database.addHoaDon(hoaDon)) {
                        Toast.makeText(getContext(), getResources().getString(R.string.toast_thanhcong), Toast.LENGTH_SHORT).show();
                        for(SanPham sp:sanPhamList){
                            int a = database.getSanPham(sp.getMaSP()).getSoLuongSP()-sp.getSoLuongSP();
                            SanPham sanPham = database.getSanPham(sp.getMaSP());
                            sanPham.setSoLuongSP(a);
                            database.updateSanPham(sanPham);
                        }
                        if(getActivity()!=null) {
                            getActivity().setResult(Activity.RESULT_OK);
                            getActivity().onBackPressed();
                        }

                    }
                    else {
                        soHD =  Math.abs(random.nextLong());
                        Toast.makeText(getContext(), getResources().getString(R.string.toast_thulai), Toast.LENGTH_SHORT).show();
                    }
                }
                else if (processingClick < 5) {
                    processingClick++;
                    Toast.makeText(getContext(), getResources().getString(R.string.toast_chuachonsanpham), Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonNo.setOnClickListener(new View.OnClickListener() {
            int processingClick = 0;

            @Override
            public void onClick(View view) {

                if(!sanPhamList.isEmpty()){
                    hoaDon = new HoaDon(
                            "HD" + soHD,
                            System.currentTimeMillis()+"",
                            selectKhachhang.getMaKH(),
                            MainActivity.nhanVien.getMaNV(),
                            triGia,
                            sanPhamList,
                            0
                    );
                    if(selectKhachhang.getMaKH().equals("MaKH01")){
                        Toast.makeText(getContext(), "Khách lẻ không được ghi nợ !!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(database.addHoaDon(hoaDon)) {
                        Toast.makeText(getContext(), getResources().getString(R.string.toast_thanhcong), Toast.LENGTH_SHORT).show();
                        for(SanPham sp:sanPhamList){
                            int a = database.getSanPham(sp.getMaSP()).getSoLuongSP()-sp.getSoLuongSP();
                            SanPham sanPham = database.getSanPham(sp.getMaSP());
                            sanPham.setSoLuongSP(a);
                            database.updateSanPham(sanPham);
                        }
                        if(getActivity()!=null) {
                            getActivity().setResult(Activity.RESULT_OK);
                            getActivity().onBackPressed();
                        }

                    }
                    else {
                        soHD =  Math.abs(random.nextLong());
                        Toast.makeText(getContext(), getResources().getString(R.string.toast_thulai), Toast.LENGTH_SHORT).show();
                    }
                }
                else if (processingClick < 5) {
                    processingClick++;
                    Toast.makeText(getContext(), getResources().getString(R.string.toast_chuachonsanpham), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void replaceFragment(Fragment fragment){
        if(getActivity()==null) return;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayoutcontentthongtin, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



}