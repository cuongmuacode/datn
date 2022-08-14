package com.datn.quanlybanhang.fragment.baocao;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.HoaDon;
import com.datn.quanlybanhang.model.HoaDonNhap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FragmentBaoCao extends Fragment {
    TextView textViewDoanhThu;
    TextView textViewLoiNhuan;
    TextView textViewSoHoaDon;
    TextView textViewGiaTri;
    TextView textViewTienThue;
    TextView textViewGiamGia;
    TextView textViewTienBan;
    TextView textViewTienVon;
    TextView textViewTienMat;
    TextView textViewNganHang;
    TextView textViewKhachHang;
    ImageView imageView;
    Spinner spinner;
    MySQLiteHelper database;
    List<HoaDon> hoaDonList;
    List<HoaDon> hoaDonListQuery =new ArrayList<>();

    List<HoaDonNhap> hoaDonNhapList;


    String textItemSprinner;

    public FragmentBaoCao() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bao_cao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewDoanhThu = view.findViewById(R.id.baocaoDoanhThu);
        textViewLoiNhuan = view.findViewById(R.id.baocaoLoiNhuan);
        textViewSoHoaDon = view.findViewById(R.id.baocaoSoHoaDon);
        textViewGiaTri = view.findViewById(R.id.baocaoGiaTri);
        textViewTienThue = view.findViewById(R.id.baocaoTienThue);
        textViewGiamGia = view.findViewById(R.id.baocaoGiamGia);
        textViewTienBan = view.findViewById(R.id.baocaoTienBan);
        textViewTienVon = view.findViewById(R.id.baocaoTienVon);
        textViewTienMat = view.findViewById(R.id.baocaoTienMat);
        textViewNganHang= view.findViewById(R.id.baocaoNganHang);
        textViewKhachHang = view.findViewById(R.id.baocaoKhachHang);
        imageView = view.findViewById(R.id.sort_img_baocao);
        spinner = view.findViewById(R.id.spinnerBaoCaoFilter);
         database = new MySQLiteHelper(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        spinner.setSelection(0);
        xulysort();
    }

    private void xulysort() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.arrstr_day, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.performClick();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textItemSprinner = adapterView.getItemAtPosition(i).toString();
                SimpleDateFormat simpleDateFormat =new SimpleDateFormat("EEEE", new Locale("en", "UK"));
                if(textItemSprinner.equals("Chủ nhật")){
                    int soHoaDon = 0, soKhachHang =0;
                    long tienVon = 0,tienBan= 0, triGia = 0, loiNhuan = 0;

                    soKhachHang = database.getCountKhachHang();
                    soHoaDon = database.getCountHoaDon();

                    hoaDonList = database.getListHoaDon();
                    hoaDonNhapList = database.getListHoaDonNhap();

                    for (HoaDonNhap hoaDonNhap : hoaDonNhapList)
                        tienVon += hoaDonNhap.getGiaNhap()*hoaDonNhap.getSoLuong();
                    hoaDonListQuery.clear();
                    for(HoaDon hoaDon : hoaDonList)
                        if(simpleDateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD()))).equals("Sunday"))
                            hoaDonListQuery.add(hoaDon);

                    for(HoaDon hoaDon : hoaDonListQuery)
                        triGia += hoaDon.getTriGia();

                    loiNhuan = triGia - tienVon;
                    if(loiNhuan<0) loiNhuan = 0;

                    String str = triGia+" VND";
                    textViewDoanhThu.setText(str);
                    str = loiNhuan+" VND";
                    textViewLoiNhuan.setText(str);
                    str = tienBan+" VND";
                    textViewTienBan.setText(str);
                    str = tienVon+" VND";
                    textViewTienVon.setText(str);
                    str = soHoaDon+"";
                    textViewSoHoaDon.setText(str);
                    str = triGia+" VND";
                    textViewGiaTri.setText(str);
                    str = triGia+" VND";
                    textViewTienMat.setText(str);
                    str = soKhachHang+"";
                    textViewKhachHang.setText(str);
                }
                else if(textItemSprinner.equals("Thứ hai")){
                    int soHoaDon = 0, soKhachHang =0;
                    long tienVon = 0,tienBan= 0, triGia = 0, loiNhuan = 0;

                    soKhachHang = database.getCountKhachHang();
                    soHoaDon = database.getCountHoaDon();

                    hoaDonList = database.getListHoaDon();
                    hoaDonNhapList = database.getListHoaDonNhap();

                    for (HoaDonNhap hoaDonNhap : hoaDonNhapList)
                        tienVon += hoaDonNhap.getGiaNhap()*hoaDonNhap.getSoLuong();
                    hoaDonListQuery.clear();
                    for(HoaDon hoaDon : hoaDonList)
                        if(simpleDateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD()))).equals("Monday"))
                            hoaDonListQuery.add(hoaDon);

                    for(HoaDon hoaDon : hoaDonListQuery)
                        triGia += hoaDon.getTriGia();

                    loiNhuan = triGia - tienVon;
                    if(loiNhuan<0) loiNhuan = 0;

                    String str = triGia+" VND";
                    textViewDoanhThu.setText(str);
                    str = loiNhuan+" VND";
                    textViewLoiNhuan.setText(str);
                    str = tienBan+" VND";
                    textViewTienBan.setText(str);
                    str = tienVon+" VND";
                    textViewTienVon.setText(str);
                    str = soHoaDon+"";
                    textViewSoHoaDon.setText(str);
                    str = triGia+" VND";
                    textViewGiaTri.setText(str);
                    str = triGia+" VND";
                    textViewTienMat.setText(str);
                    str = soKhachHang+"";
                    textViewKhachHang.setText(str);
                }
                else if(textItemSprinner.equals("Thứ ba")){
                    int soHoaDon = 0, soKhachHang =0;
                    long tienVon = 0,tienBan= 0, triGia = 0, loiNhuan = 0;

                    soKhachHang = database.getCountKhachHang();
                    soHoaDon = database.getCountHoaDon();

                    hoaDonList = database.getListHoaDon();
                    hoaDonNhapList = database.getListHoaDonNhap();

                    for (HoaDonNhap hoaDonNhap : hoaDonNhapList)
                        tienVon += hoaDonNhap.getGiaNhap()*hoaDonNhap.getSoLuong();
                    hoaDonListQuery.clear();
                    for(HoaDon hoaDon : hoaDonList)
                        if(simpleDateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD()))).equals("Tuesday"))
                            hoaDonListQuery.add(hoaDon);

                    for(HoaDon hoaDon : hoaDonListQuery)
                        triGia += hoaDon.getTriGia();

                    loiNhuan = triGia - tienVon;
                    if(loiNhuan<0) loiNhuan = 0;

                    String str = triGia+" VND";
                    textViewDoanhThu.setText(str);
                    str = loiNhuan+" VND";
                    textViewLoiNhuan.setText(str);
                    str = tienBan+" VND";
                    textViewTienBan.setText(str);
                    str = tienVon+" VND";
                    textViewTienVon.setText(str);
                    str = soHoaDon+"";
                    textViewSoHoaDon.setText(str);
                    str = triGia+" VND";
                    textViewGiaTri.setText(str);
                    str = triGia+" VND";
                    textViewTienMat.setText(str);
                    str = soKhachHang+"";
                    textViewKhachHang.setText(str);

                }
                else if(textItemSprinner.equals("Thứ tư")){
                    int soHoaDon = 0, soKhachHang =0;
                    long tienVon = 0,tienBan= 0, triGia = 0, loiNhuan = 0;

                    soKhachHang = database.getCountKhachHang();
                    soHoaDon = database.getCountHoaDon();

                    hoaDonList = database.getListHoaDon();
                    hoaDonNhapList = database.getListHoaDonNhap();

                    for (HoaDonNhap hoaDonNhap : hoaDonNhapList)
                        tienVon += hoaDonNhap.getGiaNhap()*hoaDonNhap.getSoLuong();
                    hoaDonListQuery.clear();
                    for(HoaDon hoaDon : hoaDonList)
                        if(simpleDateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD()))).equals("Wednesday"))
                            hoaDonListQuery.add(hoaDon);

                    for(HoaDon hoaDon : hoaDonListQuery)
                        triGia += hoaDon.getTriGia();

                    loiNhuan = triGia - tienVon;
                    if(loiNhuan<0) loiNhuan = 0;

                    String str = triGia+" VND";
                    textViewDoanhThu.setText(str);
                    str = loiNhuan+" VND";
                    textViewLoiNhuan.setText(str);
                    str = tienBan+" VND";
                    textViewTienBan.setText(str);
                    str = tienVon+" VND";
                    textViewTienVon.setText(str);
                    str = soHoaDon+"";
                    textViewSoHoaDon.setText(str);
                    str = triGia+" VND";
                    textViewGiaTri.setText(str);
                    str = triGia+" VND";
                    textViewTienMat.setText(str);
                    str = soKhachHang+"";
                    textViewKhachHang.setText(str);

                }

                else if(textItemSprinner.equals("Thứ năm")){
                    int soHoaDon = 0, soKhachHang =0;
                    long tienVon = 0,tienBan= 0, triGia = 0, loiNhuan = 0;

                    soKhachHang = database.getCountKhachHang();
                    soHoaDon = database.getCountHoaDon();

                    hoaDonList = database.getListHoaDon();
                    hoaDonNhapList = database.getListHoaDonNhap();

                    for (HoaDonNhap hoaDonNhap : hoaDonNhapList)
                        tienVon += hoaDonNhap.getGiaNhap()*hoaDonNhap.getSoLuong();
                    hoaDonListQuery.clear();
                    for(HoaDon hoaDon : hoaDonList)
                        if(simpleDateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD()))).equals("Thursday"))
                            hoaDonListQuery.add(hoaDon);

                    for(HoaDon hoaDon : hoaDonListQuery)
                        triGia += hoaDon.getTriGia();

                    loiNhuan = triGia - tienVon;
                    if(loiNhuan<0) loiNhuan = 0;

                    String str = triGia+" VND";
                    textViewDoanhThu.setText(str);
                    str = loiNhuan+" VND";
                    textViewLoiNhuan.setText(str);
                    str = tienBan+" VND";
                    textViewTienBan.setText(str);
                    str = tienVon+" VND";
                    textViewTienVon.setText(str);
                    str = soHoaDon+"";
                    textViewSoHoaDon.setText(str);
                    str = triGia+" VND";
                    textViewGiaTri.setText(str);
                    str = triGia+" VND";
                    textViewTienMat.setText(str);
                    str = soKhachHang+"";
                    textViewKhachHang.setText(str);

                }
                else if(textItemSprinner.equals("Thứ sáu")){
                    int soHoaDon = 0, soKhachHang =0;
                    long tienVon = 0,tienBan= 0, triGia = 0, loiNhuan = 0;

                    soKhachHang = database.getCountKhachHang();
                    soHoaDon = database.getCountHoaDon();

                    hoaDonList = database.getListHoaDon();
                    hoaDonNhapList = database.getListHoaDonNhap();

                    for (HoaDonNhap hoaDonNhap : hoaDonNhapList)
                        tienVon += hoaDonNhap.getGiaNhap()*hoaDonNhap.getSoLuong();
                    hoaDonListQuery.clear();
                    for(HoaDon hoaDon : hoaDonList)
                        if(simpleDateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD()))).equals("Friday"))
                            hoaDonListQuery.add(hoaDon);

                    for(HoaDon hoaDon : hoaDonListQuery)
                        triGia += hoaDon.getTriGia();

                    loiNhuan = triGia - tienVon;
                    if(loiNhuan<0) loiNhuan = 0;

                    String str = triGia+" VND";
                    textViewDoanhThu.setText(str);
                    str = loiNhuan+" VND";
                    textViewLoiNhuan.setText(str);
                    str = tienBan+" VND";
                    textViewTienBan.setText(str);
                    str = tienVon+" VND";
                    textViewTienVon.setText(str);
                    str = soHoaDon+"";
                    textViewSoHoaDon.setText(str);
                    str = triGia+" VND";
                    textViewGiaTri.setText(str);
                    str = triGia+" VND";
                    textViewTienMat.setText(str);
                    str = soKhachHang+"";
                    textViewKhachHang.setText(str);

                }
                else if(textItemSprinner.equals("Thứ bảy")){
                    int soHoaDon = 0, soKhachHang =0;
                    long tienVon = 0,tienBan= 0, triGia = 0, loiNhuan = 0;

                    soKhachHang = database.getCountKhachHang();
                    soHoaDon = database.getCountHoaDon();

                    hoaDonList = database.getListHoaDon();
                    hoaDonNhapList = database.getListHoaDonNhap();

                    for (HoaDonNhap hoaDonNhap : hoaDonNhapList)
                        tienVon += hoaDonNhap.getGiaNhap()*hoaDonNhap.getSoLuong();
                    hoaDonListQuery.clear();
                    for(HoaDon hoaDon : hoaDonList)
                        if (simpleDateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD()))).equals("Saturday"))
                            hoaDonListQuery.add(hoaDon);
                    for(HoaDon hoaDon : hoaDonListQuery)
                        triGia += hoaDon.getTriGia();

                    loiNhuan = triGia - tienVon;
                    if(loiNhuan<0) loiNhuan = 0;

                    String str = triGia+" VND";
                    textViewDoanhThu.setText(str);
                    str = loiNhuan+" VND";
                    textViewLoiNhuan.setText(str);
                    str = tienBan+" VND";
                    textViewTienBan.setText(str);
                    str = tienVon+" VND";
                    textViewTienVon.setText(str);
                    str = soHoaDon+"";
                    textViewSoHoaDon.setText(str);
                    str = triGia+" VND";
                    textViewGiaTri.setText(str);
                    str = triGia+" VND";
                    textViewTienMat.setText(str);
                    str = soKhachHang+"";
                    textViewKhachHang.setText(str);

                }
                else if(textItemSprinner.equals("Tất cả")){
                    int soHoaDon = 0, soKhachHang =0;
                    long tienVon = 0,tienBan= 0, triGia = 0, loiNhuan = 0;

                    soKhachHang = database.getCountKhachHang();
                    soHoaDon = database.getCountHoaDon();

                    hoaDonList = database.getListHoaDon();
                    hoaDonNhapList = database.getListHoaDonNhap();

                    for (HoaDonNhap hoaDonNhap : hoaDonNhapList)
                        tienVon += hoaDonNhap.getGiaNhap()*hoaDonNhap.getSoLuong();
                    for(HoaDon hoaDon : hoaDonList)
                        triGia += hoaDon.getTriGia();

                    loiNhuan = triGia - tienVon;
                    if(loiNhuan<0) loiNhuan = 0;

                    String str = triGia+" VND";
                    textViewDoanhThu.setText(str);
                    str = loiNhuan+" VND";
                    textViewLoiNhuan.setText(str);
                    str = tienBan+" VND";
                    textViewTienBan.setText(str);
                    str = tienVon+" VND";
                    textViewTienVon.setText(str);
                    str = soHoaDon+"";
                    textViewSoHoaDon.setText(str);
                    str = triGia+" VND";
                    textViewGiaTri.setText(str);
                    str = triGia+" VND";
                    textViewTienMat.setText(str);
                    str = soKhachHang+"";
                    textViewKhachHang.setText(str);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}