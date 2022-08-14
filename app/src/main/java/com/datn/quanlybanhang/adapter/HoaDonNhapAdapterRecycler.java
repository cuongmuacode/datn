package com.datn.quanlybanhang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.HoaDonNhap;
import com.datn.quanlybanhang.model.KhachHang;
import com.datn.quanlybanhang.model.NhanVien;
import com.datn.quanlybanhang.model.SanPham;
import com.datn.quanlybanhang.myinterface.IClickItemListenerRecycer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HoaDonNhapAdapterRecycler extends RecyclerView.Adapter<HoaDonNhapAdapterRecycler.HoaDonNhapViewHolder>{
    List<HoaDonNhap> hoaDonNhaps;
    Context context;
    IClickItemListenerRecycer<HoaDonNhap> hoaDonNhapIClickItemListenerRecycer;
    public HoaDonNhapAdapterRecycler(List<HoaDonNhap> hoaDonNhaps, Context context,
                                     IClickItemListenerRecycer<HoaDonNhap> hoaDonNhapIClickItemListenerRecycer) {
        this.hoaDonNhaps = hoaDonNhaps;
        this.context = context;
        this.hoaDonNhapIClickItemListenerRecycer = hoaDonNhapIClickItemListenerRecycer;
    }

    @NonNull
    @Override
    public HoaDonNhapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyc_view_hoadon,parent,false);
        return new HoaDonNhapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonNhapViewHolder holder, int position) {
        HoaDonNhap hoaDonNhap = hoaDonNhaps.get(position);
        MySQLiteHelper database = new MySQLiteHelper(context);
        KhachHang khachHang = database.getKhachHang(hoaDonNhap.getMaKH());
        SanPham sanPham = database.getSanPham(hoaDonNhap.getMaSP());
        NhanVien nhanVien = database.getNhanVien(hoaDonNhap.getMaNV());
        holder.imageView.setVisibility(View.GONE);
        holder.maHoaDon.setText(hoaDonNhap.getSoHDNhap());
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy ' |  Giờ : 'HH:mm");
        String str;
        if(khachHang!=null&&nhanVien!=null) {
         str = "Khách hàng : "+khachHang.getTenKH()+"\nNhân viên : "+nhanVien.getHoTenNV()+"\nNgày : "+dateFormat.format(new Date(Long.parseLong(hoaDonNhap.getNgayNhap())));
            holder.textKhacHang.setText(str);
        }
        String checkNo = "";
        if(hoaDonNhap.getHoaDonNhapNo()==0) checkNo = "Nợ";
            holder.textngayLapHD.setText(checkNo);
            holder.textngayLapHD.setTextColor(Color.RED);

        if(sanPham!=null) {
            str = "Tên sản phẩm : " + sanPham.getTenSP() + "\nGiá bán : " + hoaDonNhap.getGiaBan() + " VND\nGiá nhập : " + hoaDonNhap.getGiaNhap() + " VND\nSố lượng : " + hoaDonNhap.getSoLuong();
            holder.textGiaProduct.setText(str);
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                hoaDonNhapIClickItemListenerRecycer.onClickChiTietModel(hoaDonNhap);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(hoaDonNhaps!=null){
            return hoaDonNhaps.size();
        }
        return 0;
    }

    public static class HoaDonNhapViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView maHoaDon;
        TextView textKhacHang;
        TextView textGiaProduct;
        TextView textGiaProductNo;
        TextView textngayLapHD;

        public HoaDonNhapViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =  itemView.findViewById(R.id.imgMoreHoaDon);
            maHoaDon = itemView.findViewById(R.id.idHoaDon);
            textKhacHang = itemView.findViewById(R.id.hoaDonKhachhang);
            textGiaProduct = itemView.findViewById(R.id.productGiahoadon);
            textngayLapHD =  itemView.findViewById(R.id.ngayHoadon);
            textGiaProductNo = itemView.findViewById(R.id.productGiahoadonNo);
        }
    }
}

