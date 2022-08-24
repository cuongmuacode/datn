package com.datn.quanlybanhang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.KhoHang;

import java.util.List;

public class BCSanPhamNhieuAdapterRecycler extends RecyclerView.Adapter<BCSanPhamNhieuAdapterRecycler.BCSanPhamNhieuHoler>{
    List<KhoHang> khoHangList;
    MySQLiteHelper database;
    public BCSanPhamNhieuAdapterRecycler() {

    }

    public BCSanPhamNhieuAdapterRecycler(List<KhoHang> khoHangList, MySQLiteHelper database) {
        this.khoHangList = khoHangList;
        this.database = database;
    }

    @NonNull
    @Override
    public BCSanPhamNhieuHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_bcsanphambannhieu,parent,false);
        return new BCSanPhamNhieuAdapterRecycler.BCSanPhamNhieuHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BCSanPhamNhieuHoler holder, int position) {
        holder.imageSanPham.setVisibility(View.GONE);
        KhoHang khoHang = khoHangList.get(position);
        String str = " Đã bán : "+khoHang.getSoLuong();
        holder.textSoLuongSanPham.setText(str);
        str = "Sản phẩm : "+khoHang.getMaKho();
        holder.textTenSanPham.setText(str);

    }

    @Override
    public int getItemCount() {
        return khoHangList.size();
    }

    public static class BCSanPhamNhieuHoler extends RecyclerView.ViewHolder {
        TextView textTenSanPham;
        TextView textSoLuongSanPham;
        ImageView imageSanPham;


        public BCSanPhamNhieuHoler(@NonNull View itemView) {
            super(itemView);
            textTenSanPham = itemView.findViewById(R.id.tenSanPhamBaoCao);
            textSoLuongSanPham = itemView.findViewById(R.id.productSoLuong);
            imageSanPham = itemView.findViewById(R.id.imgSanPhamBaoCao);
        }
    }}
