
package com.datn.quanlybanhang.fragment.baocao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.datn.quanlybanhang.model.SanPham;

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
    TextView textViewTienNo;
    TextView textViewGiamGia;
    TextView textViewTienBan;
    TextView textViewTienVon;

    TextView textViewSoHoaDonNhap;
    TextView textViewVonNhap;
    TextView textViewTienNoNhap;
    TextView textViewGiamGiaNhap;




    ImageView imageView;
    Spinner spinner;
    MySQLiteHelper database;
    List<HoaDon> hoaDonList;
    List<HoaDon> hoaDonListQuery =new ArrayList<>();
    List<SanPham> sanPhamList = new ArrayList<>();

    List<HoaDonNhap> hoaDonNhapList;
    List<HoaDonNhap> hoaDonNhapListQuery = new ArrayList<>();


    String textItemSprinner;
    List<String> listString;

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
        textViewDoanhThu = view.findViewById(R.id.text_baocao_doanhthu);
        textViewLoiNhuan = view.findViewById(R.id.text_baocao_loinhuan);
        textViewSoHoaDon = view.findViewById(R.id.baocaoSoHoaDon);
        textViewGiaTri = view.findViewById(R.id.baocaoGiaTri);
        textViewTienNo = view.findViewById(R.id.baocaoTienNo);
        textViewGiamGia = view.findViewById(R.id.baocaoGiamGia);
        textViewTienBan = view.findViewById(R.id.baocaoTienBan);
        textViewTienVon = view.findViewById(R.id.baocaoTienVon);

        textViewSoHoaDonNhap = view.findViewById(R.id.baocaoSoHoaDonNhap);
        textViewVonNhap = view.findViewById(R.id.baocaoTienVonNhap);
        textViewTienNoNhap = view.findViewById(R.id.baocaoTienNoNhap);
        textViewGiamGiaNhap = view.findViewById(R.id.baocaoGiamGiaNhap);

        imageView = view.findViewById(R.id.sort_img_baocao);
        spinner = view.findViewById(R.id.spinnerBaoCaoFilter);
        database = new MySQLiteHelper(getContext());
    }


    @Override
    public void onStart() {
        super.onStart();
        spinner.setSelection(0);
        xuLyFilter();

    }
    public void xuLyFilter(){
        listString = getMonth();
        ArrayAdapter<String> adapter = new  ArrayAdapter<>(getContext()
                , android.R.layout.simple_spinner_item,listString);
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
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM-yyyy", new Locale("vi", "VN"));
                SimpleDateFormat simpleDateFormatYear = new SimpleDateFormat("yyyy", new Locale("vi", "VN"));
                textItemSprinner = listString.get(i).toLowerCase()+"-"+simpleDateFormatYear.format(new Date(System.currentTimeMillis()));


                int soHoaDon;
                long tienVon = 0, triGia = 0, loiNhuan,tienNo = 0;

                soHoaDon = database.getCountHoaDon();

                hoaDonList = database.getListHoaDon();
                hoaDonNhapList = database.getListHoaDonNhap();

                hoaDonListQuery.clear();
                for (HoaDon hoaDon : hoaDonList){
                    if (simpleDateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD()))).equals(textItemSprinner)
                            &&hoaDon.getHoaDonNo()==1)
                        hoaDonListQuery.add(hoaDon);
                }

//                for (HoaDon hoaDon : hoaDonListQuery){
//                    for(SanPham sanPham : hoaDon.getSanPhamList()){
//                        triGia += sanPham.getGiaSP()*sanPham.getSoLuongSP();
//                        tienVon += sanPham.getGiaNhapSP()*sanPham.getSoLuongSP();
//                    }
//                }
                loiNhuan = triGia - tienVon;
                hoaDonListQuery.clear();
                for (HoaDon hoaDon : hoaDonList){
                    if (simpleDateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD()))).equals(textItemSprinner)
                            &&hoaDon.getHoaDonNo()==0)
                        hoaDonListQuery.add(hoaDon);
                }
                for (HoaDon hoaDon : hoaDonListQuery){
                    tienNo += hoaDon.getTriGia();
                }

//                String sql = "Select CTHD_MaSP From HOADON,CTHD Where CTHD_SoHD == HOADON_Sohd";
//                String sql1 = "Select *From SanPham Where SANPHAM_MASP in " +
//                        "(Select CTHD_MaSP From HOADON,CTHD Where CTHD_SoHD == HOADON_Sohd)";
//                String sql2 = "Select *From SanPham Where SANPHAM_MASP in (Select Distinct NHAPHANG_MASP From NHAPHANG)";
//                SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();
//                Cursor cursor = database.execSQLSelect(sql2,null,sqLiteDatabase);
//                if(cursor.moveToFirst()) {
//                    do {
//                        SanPham sanPham = new SanPham(
//                                cursor.getString(0),
//                                cursor.getString(1),
//                                cursor.getString(2),
//                                cursor.getString(3),
//                                cursor.getString(4),
//                                cursor.getLong(9),
//                                cursor.getInt(5),
//                                cursor.getBlob(6),
//                                cursor.getString(7),
//                                cursor.getLong(8)
//                        );
//                        sanPhamList.add(sanPham);
//
//                    }while (cursor.moveToNext());
//                }
//                sqLiteDatabase.close();

                textViewDoanhThu.setText(triGia+" VND");
                textViewLoiNhuan.setText(loiNhuan+" VND");

                textViewTienBan.setText(triGia+ " VND");
                textViewTienVon.setText(tienVon+" VND");

                textViewSoHoaDon.setText(soHoaDon+" hóa đơn");
                textViewGiaTri.setText(triGia+" VND");

                textViewTienNo.setText(tienNo+" VND");
                textViewGiamGia.setText("0 VND");

                int soHoaDonNhap = database.getCountHoaDonNhap();
                long tienVonNhap = 0, tienNoNhap = 0;

                hoaDonNhapListQuery.clear();
                for (HoaDonNhap hoaDonNhap : hoaDonNhapList){
                    if (simpleDateFormat.format(new Date(Long.parseLong(hoaDonNhap.getNgayNhap()))).equals(textItemSprinner)
                            &&hoaDonNhap.getHoaDonNhapNo()==1)
                        hoaDonNhapListQuery.add(hoaDonNhap);
                }
                for(HoaDonNhap hoaDonNhap : hoaDonNhapListQuery){
                    tienVonNhap += hoaDonNhap.getGiaNhap();
                }

                hoaDonNhapListQuery.clear();
                for (HoaDonNhap hoaDonNhap : hoaDonNhapList){
                    if (simpleDateFormat.format(new Date(Long.parseLong(hoaDonNhap.getNgayNhap()))).equals(textItemSprinner)
                            &&hoaDonNhap.getHoaDonNhapNo()==0)
                        hoaDonNhapListQuery.add(hoaDonNhap);
                }
                for(HoaDonNhap hoaDonNhap : hoaDonNhapListQuery){
                    tienNoNhap += hoaDonNhap.getGiaNhap();
                }



                textViewSoHoaDonNhap.setText(soHoaDonNhap+" hóa đơn");
                textViewTienNoNhap.setText(tienNoNhap+" VND");
                textViewVonNhap.setText(tienVonNhap+" VND");
                textViewGiamGia.setText(" VND");



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    List<String> getMonth(){
        List<String> list = new ArrayList<>();
        list.add("Tháng 1");
        list.add("Tháng 2");
        list.add("Tháng 3");
        list.add("Tháng 4");
        list.add("Tháng 5");
        list.add("Tháng 6");
        list.add("Tháng 7");
        list.add("Tháng 8");
        list.add("Tháng 9");
        list.add("Tháng 10");
        list.add("Tháng 11");
        list.add("Tháng 12");
        return list;
    }
}