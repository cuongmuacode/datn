 package com.datn.quanlybanhang.fragment.baocao;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.HoaDon;
import com.datn.quanlybanhang.model.KhoHang;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FragmentBKKhachHang extends Fragment {
    MySQLiteHelper database;
    String textDate = "";
    TextView textViewDate;
    Calendar calendar = Calendar.getInstance(new Locale("vi","VN"));
    BarChart chart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b_k_khach_hang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getActivity()==null) return;
        database = new MySQLiteHelper(getContext());

        textViewDate = view.findViewById(R.id.baocaotonghop_khach_date);

        calendar.setTimeInMillis(System.currentTimeMillis());
        textDate = ""+calendar.get(Calendar.YEAR);
        String str = "Năm : "+calendar.get(Calendar.YEAR);
        textViewDate.setText(str);

        initDatePick();
        chart = view.findViewById(R.id.barchar);
        if (getContext() != null)
            database = new MySQLiteHelper(getContext());


        BarDataSet barDataSetDoanhThu = new BarDataSet(getListBarEntry(1,true),"Doanh Thu");
        barDataSetDoanhThu.setColor(Color.GREEN);

        BarDataSet barDataSetLoiNhuan = new BarDataSet(getListBarEntry(1,false),"Lợi Nhuận");
        barDataSetLoiNhuan.setColor(Color.YELLOW);

        BarDataSet barDataSetDoanhThuNo = new BarDataSet(getListBarEntry(0,true),"Khách Nợ");
        barDataSetLoiNhuan.setColor(Color.YELLOW);

        BarData data = new BarData(barDataSetDoanhThu,barDataSetLoiNhuan,barDataSetDoanhThuNo);
        chart.setData(data);

        chart.getDescription().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getListMonth()));
        xAxis.setCenterAxisLabels(true);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisLeft().setAxisMinimum(0);
        chart.setDragEnabled(true);
        chart.setVisibleXRangeMaximum(6);

        data.setBarWidth(0.18f);
        chart.getXAxis().setAxisMinimum(0);
        chart.getXAxis().setAxisMaximum(13);
        chart.animateY(4000);

        chart.groupBars(0,1-3*0.18f,0f);
        chart.invalidate();
    }

    private void initDatePick() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {

            textDate = ""+year;
            String str = "Năm : "+year;
            textViewDate.setText(str);

            BarDataSet barDataSetDoanhThu = new BarDataSet(getListBarEntry(1,true),"Doanh Thu");
            barDataSetDoanhThu.setColor(Color.GREEN);

            BarDataSet barDataSetLoiNhuan = new BarDataSet(getListBarEntry(1,false),"Lợi Nhuận");
            barDataSetLoiNhuan.setColor(Color.YELLOW);

            BarDataSet barDataSetDoanhThuNo = new BarDataSet(getListBarEntry(0,true),"Khách Nợ");
            barDataSetLoiNhuan.setColor(Color.YELLOW);

            BarData data = new BarData(barDataSetDoanhThu,barDataSetLoiNhuan,barDataSetDoanhThuNo);
            chart.clear();
            chart.setData(data);
            chart.getDescription().setEnabled(false);

            XAxis xAxis = chart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(getListMonth()));
            xAxis.setCenterAxisLabels(true);

            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1);
            xAxis.setGranularityEnabled(true);

            chart.getAxisRight().setDrawLabels(false);
            chart.getAxisLeft().setAxisMinimum(0);
            chart.setDragEnabled(true);
            chart.setVisibleXRangeMaximum(6);

            data.setBarWidth(0.18f);
            chart.getXAxis().setAxisMinimum(0);
            chart.getXAxis().setAxisMaximum(13);
            chart.animateY(4000);

            chart.groupBars(0,1-3*0.18f,0f);
            chart.invalidate();
        };

        textViewDate.setOnClickListener((view1) -> {
            calendar.setTimeInMillis(System.currentTimeMillis());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                DatePickerDialog dialog =
                        new DatePickerDialog(getContext(),dateSetListener,year,month,day);
                dialog.show();
            }
        });
    }

    List<BarEntry> getListBarEntry(int no,boolean check){
        List<BarEntry> list = new ArrayList<>();
        list.add(getBarEntry("01",no,check));
        list.add(getBarEntry("02",no,check));
        list.add(getBarEntry("03",no,check));
        list.add(getBarEntry("04",no,check));
        list.add(getBarEntry("05",no,check));
        list.add(getBarEntry("06",no,check));
        list.add(getBarEntry("07",no,check));
        list.add(getBarEntry("08",no,check));
        list.add(getBarEntry("09",no,check));
        list.add(getBarEntry("10",no,check));
        list.add(getBarEntry("11",no,check));
        list.add(getBarEntry("12",no,check));
        return  list;
    }

    BarEntry getBarEntry(String month,int no,boolean check){
        final SimpleDateFormat simpleDateFormatMonth = new SimpleDateFormat("MM", new Locale("vi", "VN"));
        long doanhThu = 0,von = 0;
        for(HoaDon hoaDon : getListHoaDonYear(textDate,no)){
            if(simpleDateFormatMonth.format(new Date(Long.parseLong(hoaDon.getNgayHD()))).equals(month)){
                doanhThu +=hoaDon.getTriGia();
                for(KhoHang khoHang: hoaDon.getKhoList()){
                 von += khoHang.getSoLuong()* khoHang.getGiaNhap();
                }
            }
        }
        return  new BarEntry(Integer.parseInt(month),(check) ? doanhThu:von);
    }


    List<HoaDon> getListHoaDonYear(String strDate,int no){
        List<HoaDon> hoaDonListQurey = new ArrayList<>();
        final SimpleDateFormat simpleDateFormatYear = new SimpleDateFormat("yyyy", new Locale("vi", "VN"));
        for(HoaDon hoaDon : database.getListHoaDon()){
            if (simpleDateFormatYear.format(new Date(Long.parseLong(hoaDon.getNgayHD()))).
                equals(strDate)) {
                if(hoaDon.getHoaDonNo()==no) {
                   hoaDonListQurey.add(hoaDon);
                }
            }
        }
        return hoaDonListQurey;
    }

    List<String> getListMonth(){
        List<String> listMonth = new ArrayList<>();
        listMonth.add("Tháng 1");
        listMonth.add("Tháng 2");
        listMonth.add("Tháng 3");
        listMonth.add("Tháng 4");
        listMonth.add("Tháng 5");
        listMonth.add("Tháng 6");
        listMonth.add("Tháng 7");
        listMonth.add("Tháng 8");
        listMonth.add("Tháng 9");
        listMonth.add("Tháng 10");
        listMonth.add("Tháng 11");
        listMonth.add("Tháng 12");
        return  listMonth;
    }
}









