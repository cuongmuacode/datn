
package com.datn.quanlybanhang.fragment.baocao;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.HoaDon;
import com.datn.quanlybanhang.model.HoaDonNhap;
import com.datn.quanlybanhang.model.KhoHang;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FragmentBaoCao extends Fragment {
    TextView textViewDoanhThu;
    TextView textViewLoiNhuan;
    TextView textViewSoHoaDon;
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
    List<HoaDonNhap> hoaDonNhapList;

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
        xuLyFilter();
        super.onStart();
    }
    public void xuLyFilter(){
        listString = getMonth();
        ArrayAdapter<String> adapter = new  ArrayAdapter<>(getContext()
                , android.R.layout.simple_spinner_item,listString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        imageView.setOnClickListener(view -> spinner.performClick());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM", new Locale("vi", "VN"));
        String currentMonth =  simpleDateFormat.format(new Date(System.currentTimeMillis()));
        int index = 0;
        for(String month: listString){
            if(month.toLowerCase().equals(currentMonth)){
                spinner.setSelection(index);
            }
            index++;
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM-yyyy", new Locale("vi", "VN"));
            final SimpleDateFormat simpleDateFormatYear = new SimpleDateFormat("yyyy", new Locale("vi", "VN"));

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textItemSprinner = listString.get(i).toLowerCase()+"-"+simpleDateFormatYear.format(new Date(System.currentTimeMillis()));

                int soHoaDon = 0;
                long tienVon = 0, triGia = 0, loiNhuan,tienNo = 0;

                hoaDonList = database.getListHoaDon();
                hoaDonNhapList = database.getListHoaDonNhap();


                for(HoaDon hoaDon : hoaDonList){
                    List<KhoHang> khoHangList;
                    long sumGia = 0,sumGiaNhap = 0,sumNo = 0;
                    if (simpleDateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD()))).
                            equals(textItemSprinner)) {
                        soHoaDon++;
                        if(hoaDon.getHoaDonNo()==1) {
                            khoHangList = hoaDon.getKhoList();
                            for (KhoHang khoHang : khoHangList) {
                                sumGia += khoHang.getSoLuong() * khoHang.getGia();
                                sumGiaNhap += khoHang.getSoLuong() * khoHang.getGiaNhap();
                            }
                            triGia += sumGia;
                            tienVon += sumGiaNhap;
                        }
                        else if(hoaDon.getHoaDonNo()==0){
                            khoHangList = hoaDon.getKhoList();
                            for (KhoHang khoHang : khoHangList) {
                                sumNo = khoHang.getGia()*khoHang.getSoLuong();
                            }
                            tienNo += sumNo;
                        }
                    }
                }

                loiNhuan = triGia-tienVon;
                String str = triGia+" VND";
                textViewDoanhThu.setText(str);
                textViewTienBan.setText(str);
                str = loiNhuan+" VND";
                textViewLoiNhuan.setText(str);
                str = tienVon+" VND";
                textViewTienVon.setText(str);
                str = soHoaDon+" hóa đơn";
                textViewSoHoaDon.setText(str);
                str = tienNo+" VND";
                textViewTienNo.setText(str);
                str = "0 VND";
                textViewGiamGia.setText(str);


                int soHoaDonNhap = 0;
                long tienVonNhap = 0, tienNoNhap = 0;

                for (HoaDonNhap hoaDonNhap : hoaDonNhapList){
                    if (simpleDateFormat.format(new Date(Long.parseLong(hoaDonNhap.getNgayNhap()))).
                            equals(textItemSprinner)){
                        soHoaDonNhap++;
                        if(hoaDonNhap.getHoaDonNhapNo()==1){
                            tienVonNhap +=hoaDonNhap.getGiaNhap();
                        }
                        else if (hoaDonNhap.getHoaDonNhapNo()==0){
                            tienNoNhap +=hoaDonNhap.getGiaNhap();
                        }
                    }
                }
                str = soHoaDonNhap+" hóa đơn";
                textViewSoHoaDonNhap.setText(str);
                str = tienNoNhap+" VND";
                textViewTienNoNhap.setText(str);
                str = tienVonNhap+" VND";
                textViewVonNhap.setText(str);
                str = "0 VND";
                textViewGiamGiaNhap.setText(str);
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