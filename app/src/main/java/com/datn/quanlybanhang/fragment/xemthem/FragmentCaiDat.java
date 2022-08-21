package com.datn.quanlybanhang.fragment.xemthem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.HoaDon;
import com.datn.quanlybanhang.model.KhachHang;
import com.datn.quanlybanhang.model.KhoHang;
import com.datn.quanlybanhang.model.SanPham;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FragmentCaiDat extends Fragment {
    Random random = new Random(System.currentTimeMillis());
    MySQLiteHelper database;

    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cai_dat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getContext()==null) return;
        database = new MySQLiteHelper(getContext());
        textView = view.findViewById(R.id.textCatDatFake);

        textView.setOnClickListener(view1 -> {
            if(database.getCountHoaDon()>20)
                return;
            List<SanPham> sanPhams =  database.getListSanPham();
            List<KhoHang> khoHangs = database.getListKhoHang();
            List<KhachHang> khachHangs = database.getListKhachHang();

            List<KhoHang> khoHangHoaDon = new ArrayList<>();

            List<SanPham> sanPhamHoaDon = sanPhams.subList(10,14);

            for(SanPham sanPham: sanPhamHoaDon){
                for(KhoHang khoHang : khoHangs){
                    if(khoHang.getMaSP().equals(sanPham.getMaSP())){
                        khoHangHoaDon.add(khoHang);
                    }
                }
            }

            long triGia = 0;
            for(KhoHang khoHang:khoHangHoaDon){
                triGia +=Math.abs(random.nextInt(khoHang.getSoLuong()-1))*khoHang.getGia();
            }


            KhachHang selectKhachHang;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",new Locale("vi","VN"));
            random =  new Random(System.currentTimeMillis());
            for(int i = 3;i<=6;i++){
                for(int j = 0;j<=9;j++){
                    selectKhachHang = khachHangs.get(Math.abs(random.nextInt(khachHangs.size()-1)));
                    String str = "16"+i+""+j+""+"006111111";
                    Date time = new Date(Long.parseLong(str));
                    System.out.println(sdf.format(time));
                    HoaDon hoaDon = new HoaDon(
                            "HD"+Math.abs(random.nextLong()),
                            str,
                            selectKhachHang.getMaKH(),
                            "MaNV01",
                            triGia,
                            sanPhamHoaDon,
                            khoHangHoaDon,
                            (true)?1:0
                    );
                    database.addHoaDon(hoaDon);
                }
            }
        });
    }
}