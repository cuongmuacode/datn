package com.datn.quanlybanhang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.model.KhoHang;

import java.util.ArrayList;
import java.util.List;

public class BCKhachNoAdapterRecycler extends RecyclerView.Adapter<BCKhachNoAdapterRecycler.BCKhachNoHoler>{
    List<KhoHang> khachHangList = new ArrayList<>();

    public BCKhachNoAdapterRecycler(List<KhoHang> khachHangList) {
        this.khachHangList = khachHangList;
    }

    @NonNull
    @Override
    public BCKhachNoHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_bckhachhang,parent,false);
        return new BCKhachNoAdapterRecycler.BCKhachNoHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BCKhachNoHoler holder, int position) {
        KhoHang khoHang = khachHangList.get(position);
        String  str = khoHang.getGia()+"";
        holder.textKhachhangTienNo.setText(str);
        holder.textTenKhachHangNo.setText(khoHang.getMaKho());
    }

    @Override
    public int getItemCount() {
        return khachHangList.size();
    }

    public static class BCKhachNoHoler extends RecyclerView.ViewHolder{
        TextView textTenKhachHangNo;
        TextView textKhachhangTienNo;
        public BCKhachNoHoler(@NonNull View itemView) {
            super(itemView);
            textTenKhachHangNo = itemView.findViewById(R.id.baocao_khachhangno);
             textKhachhangTienNo = itemView.findViewById(R.id.baocao_khtienno);
        }
    }
}
