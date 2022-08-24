package com.datn.quanlybanhang.fragment.hoadon;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.activityy.MainActivity;
import com.datn.quanlybanhang.adapter.MatHangAdapterRecycler;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.fragment.FragmentBanHang;
import com.datn.quanlybanhang.fragment.khachhang.FragmentKhachHang;
import com.datn.quanlybanhang.model.HoaDon;
import com.datn.quanlybanhang.model.KhachHang;
import com.datn.quanlybanhang.model.KhoHang;
import com.datn.quanlybanhang.model.SanPham;
import com.datn.quanlybanhang.myinterface.IClickItemSanPham;
import com.datn.quanlybanhang.myinterface.iClickitemHoaDon;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public class FragmentAddHoaDon extends Fragment implements IClickItemSanPham,Serializable, iClickitemHoaDon<SanPham> {
    private List<SanPham> sanPhamList = new ArrayList<>();
    private List<KhoHang> khoHangList = new ArrayList<>();
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
    Toast toast;
    private Random random = new Random(System.currentTimeMillis());
    private Long soHD;
    long triGia = 0;


    public FragmentAddHoaDon() {
        soHD =  Math.abs(random.nextLong());
    }


    public FragmentAddHoaDon(List<SanPham> sanPhamList,List<KhoHang> khoHangList, SanPham selectSanPham, KhachHang selectKhachhang,
                             HoaDon hoaDon, Random random, long soHD) {
        this.sanPhamList = sanPhamList;
        this.khoHangList = khoHangList;
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
        menuItem.setOnMenuItemClickListener(menuItem1 -> {
            replaceFragment(new FragmentBanHang(false));
            return false;
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onClickSanPham(SanPham sanPham,KhoHang khoHang) {
        if(sanPham !=null)
            if (sanPhamList.isEmpty()) {
                sanPhamList.add(sanPham);
                khoHangList.add(khoHang);
            } else {
                for (SanPham sp : sanPhamList) {
                    if (sp.getMaSP().equals(sanPham.getMaSP()))
                        return;
                }
                sanPhamList.add(sanPham);
                khoHangList.add(khoHang);
                FragmentBanHang.countSanPham++;
            }
    }

    @Override
    public void onResume() {
        super.onResume();
        System.gc();
        FragmentBanHang.iClickItemSanPham =  new FragmentAddHoaDon(FragmentAddHoaDon.this.sanPhamList,FragmentAddHoaDon.this.khoHangList,
                FragmentAddHoaDon.this.selectSanPham,FragmentAddHoaDon.this.selectKhachhang,FragmentAddHoaDon.this.hoaDon,
                FragmentAddHoaDon.this.random,FragmentAddHoaDon.this.soHD);
    }

    @Override
    public void onClickKhachHang(KhachHang khachHang) {
        selectKhachhang = khachHang;
    }

    @Override
    public void onClickItemModel(SanPham sanPham, int check) {
        if(check == 1){
            if(getActivity()!=null)
                getActivity().setResult(Activity.RESULT_CANCELED);
            sanPhamList.remove(sanPham);
            for (KhoHang khoHang : khoHangList){
                if(khoHang.getMaSP().equals(sanPham.getMaSP())){
                    khoHangList.remove(khoHang);
                }
            }
            if(FragmentBanHang.countSanPham>0)
                FragmentBanHang.countSanPham--;

            if(!sanPhamList.isEmpty()) {
                triGia = 0;
                for (SanPham sp : sanPhamList){
                    for(KhoHang khoHang : khoHangList) {
                        if(khoHang.getMaSP().equals(sp.getMaSP())) {
                            triGia += khoHang.getSoLuong() * khoHang.getGia();
                        }
                    }
                }
                String str =  triGia + " VND";
                textTongTien.setText(str);
            }
            else
                textTongTien.setText("0 VND");
            matHangAdapterRecycler.notifyDataSetChanged();
        }
        else if(check == 2){
            for(KhoHang khoHang : khoHangList) {
                if(khoHang.getMaSP().equals(sanPham.getMaSP())){
                    replaceFragment(new FragmentThemSoLuong(sanPham,khoHang));
                    return;
                }
            }


        }
    }

    @Override
    public void onClickChiTietModel(SanPham sanPham) {
        selectSanPham = sanPham;
    }



    private void xulyall() {
        if(getContext()==null) return;
        matHangAdapterRecycler =  new MatHangAdapterRecycler(
                FragmentAddHoaDon.this,sanPhamList,this.khoHangList,getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
            recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(matHangAdapterRecycler);

        String str = getResources().getString(R.string.addhoadon_sohoadon)+soHD;
        textSoHoaDon.setText(str);
        str = getResources().getString(R.string.addhoadon_tennhanvien)+MainActivity.nhanVien.getHoTenNV();
        textTenNhanVien.setText(str);

        textTenKhachhang.setOnClickListener(view -> replaceFragment(new FragmentKhachHang(FragmentAddHoaDon.this)));

        if(selectKhachhang==null) selectKhachhang = database.getKhachHang("MaKH01");
        str = getResources().getString(R.string.addhoadon_tenkhachhanh)+selectKhachhang.getTenKH();
        textTenKhachhang.setText(str);

        if(!sanPhamList.isEmpty()) {
            triGia = 0;
            for (SanPham sp : sanPhamList){
                for (KhoHang khoHang : khoHangList) {
                    if(sp.getMaSP().equals(khoHang.getMaSP()))
                    triGia += khoHang.getSoLuong() * khoHang.getGia();
                }
            }
            str =  triGia + " VND";
            textTongTien.setText(str);
        }
        else textTongTien.setText("0 VND");

        buttonAdd.setOnClickListener(view -> {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS",new Locale("vi","VN"));
            Timestamp timestamp = Timestamp.valueOf(simpleDateFormat.format(System.currentTimeMillis()));
            if(!sanPhamList.isEmpty()){
                hoaDon = new HoaDon(
                        "HD" + soHD,
                        simpleDateFormat.format(timestamp),
                        selectKhachhang.getMaKH(),
                        MainActivity.nhanVien.getMaNV(),
                        triGia,
                        sanPhamList,
                        khoHangList,
                        1
                );
                if(database.addHoaDon(hoaDon)) {
                    displayToast( getResources().getString(R.string.toast_thanhcong));
                    for(KhoHang khoHang:khoHangList){
                        KhoHang kho = database.getKhoHang(khoHang.getMaSP());
                        int a = kho.getSoLuong() - khoHang.getSoLuong();
                        kho.setSoLuong(a);
                        database.updateKhoaHang(kho);
                    }
                    if(getActivity()!=null) {
                        getActivity().setResult(Activity.RESULT_OK);
                        getActivity().onBackPressed();
                    }

                }
                else {
                    soHD =  Math.abs(random.nextLong());
                    displayToast(getResources().getString(R.string.toast_thulai));
                }
            }
            else displayToast(getResources().getString(R.string.toast_chuachonsanpham));
        });

        buttonNo.setOnClickListener(view -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS",new Locale("vi","VN"));
            Timestamp timestamp = Timestamp.valueOf(simpleDateFormat.format(System.currentTimeMillis()));
            if(!sanPhamList.isEmpty()){
                hoaDon = new HoaDon(
                        "HD" + soHD,
                        simpleDateFormat.format(timestamp),
                        selectKhachhang.getMaKH(),
                        MainActivity.nhanVien.getMaNV(),
                        triGia,
                        sanPhamList,
                        khoHangList,
                        0
                );
                if(selectKhachhang.getMaKH().equals("MaKH01")){
                    displayToast("Khách lẻ không được ghi nợ !!!");
                    return;
                }
                if(database.addHoaDon(hoaDon)) {
                    displayToast( getResources().getString(R.string.toast_thanhcong));
                    for(KhoHang khoHang:khoHangList){
                        KhoHang kho = database.getKhoHang(khoHang.getMaSP());
                        int a = kho.getSoLuong() - khoHang.getSoLuong();
                        kho.setSoLuong(a);
                        database.updateKhoaHang(kho);
                    }
                    if(getActivity()!=null) {
                        getActivity().setResult(Activity.RESULT_OK);
                        getActivity().onBackPressed();
                    }

                }
                else {
                    soHD =  Math.abs(random.nextLong());
                    displayToast(getResources().getString(R.string.toast_thulai));
                }
            }
            else
                displayToast(getResources().getString(R.string.toast_chuachonsanpham));

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
    public void displayToast(String message) {
        if(toast != null)
            toast.cancel();
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }




}