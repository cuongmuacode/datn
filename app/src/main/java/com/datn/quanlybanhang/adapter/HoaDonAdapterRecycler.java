package com.datn.quanlybanhang.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.HoaDon;
import com.datn.quanlybanhang.model.KhachHang;
import com.datn.quanlybanhang.myinterface.IClickItemListenerRecycer;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HoaDonAdapterRecycler extends RecyclerView.Adapter<HoaDonAdapterRecycler.HoaDonViewHoler> {


    Context context;
    IClickItemListenerRecycer<HoaDon> hoaDonIClickItemListenerRecycer;
    List<HoaDon> hoaDonList;
    List<HoaDon> filterHoaDonList;
    MySQLiteHelper database;
    public HoaDonAdapterRecycler(Context context,List<HoaDon> hoaDonList,
                                 IClickItemListenerRecycer<HoaDon> hoaDonIClickItemListenerRecycer) {
        this.context = context;
        this.hoaDonList = hoaDonList;
        filterHoaDonList = hoaDonList;
        this.hoaDonIClickItemListenerRecycer = hoaDonIClickItemListenerRecycer;
    }
    @NonNull
    @Override
    public HoaDonViewHoler onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyc_view_hoadon,parent,false);
        return new HoaDonViewHoler(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull  HoaDonViewHoler holder, int position) {
        HoaDon hoaDon = hoaDonList.get(position);
        if(hoaDon == null) return;
        holder.maHoaDon.setText(hoaDon.getSoHD());
        database = new MySQLiteHelper(context);
        KhachHang khachHang = database.getKhachHang(hoaDon.getMaKH());
        String str;
        if(khachHang!=null) {
            str = "Khách: " + khachHang.getTenKH();
            holder.textKhacHang.setText(str);
        }else
            holder.textKhacHang.setText("Khách: ");
        str = "Tổng tiền: "+hoaDon.getTriGia().toString()+" VND";
        holder.textGiaProduct.setText(str);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", new Locale("vi","VN"));
        Timestamp timestamp = Timestamp.valueOf(hoaDon.getNgayHD());
        str = "Ngày lập: "+dateFormat.format(timestamp.getTime());
        holder.textngayLapHD.setText(str);
        if(hoaDon.getHoaDonNo()==0)
            holder.textGiaProductNo.setText("Nợ");
        else
            holder.textGiaProductNo.setText("");
        holder.itemView.setOnClickListener(view -> hoaDonIClickItemListenerRecycer.onClickChiTietModel(hoaDon));
        holder.itemView.setOnLongClickListener(view -> {
            hoaDonIClickItemListenerRecycer.onClickItemModel(hoaDon);
            return false;
        });

    }


    @Override
    public int getItemCount() {
        if(hoaDonList != null) return hoaDonList.size();
        return 0;
    }


    public static class HoaDonViewHoler extends RecyclerView.ViewHolder{
        TextView maHoaDon;
        TextView textKhacHang;
        TextView textGiaProduct;
        TextView textGiaProductNo;
        TextView textngayLapHD;


        public HoaDonViewHoler( View itemView) {
            super(itemView);
            maHoaDon = itemView.findViewById(R.id.idHoaDon);
            textKhacHang = itemView.findViewById(R.id.hoaDonKhachhang);
            textGiaProduct = itemView.findViewById(R.id.productGiahoadon);
            textGiaProductNo = itemView.findViewById(R.id.productGiahoadonNo);
            textngayLapHD =  itemView.findViewById(R.id.ngayHoadon);

        }
    }
}
