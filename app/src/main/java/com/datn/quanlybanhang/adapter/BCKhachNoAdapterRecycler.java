package com.datn.quanlybanhang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.model.KhoHang;

import java.util.List;

public class BCKhachNoAdapterRecycler extends RecyclerView.Adapter<BCKhachNoAdapterRecycler.BCKhachNoHoler>{
    List<KhoHang> khachHangList;

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
        String  str = "Tiền nợ : "+khoHang.getGia()+" VND";
        holder.textKhachhangTienNo.setText(str);
        str = "Họ tên : "+khoHang.getMaKho();
        holder.textTenKhachHangNo.setText(str);
        str = "Số điện thoại : "+khoHang.getMaSP();
        holder.textTenKhachHangSDT.setText(str);
    }

    @Override
    public int getItemCount() {
        if(khachHangList==null)
            return 0;
        return khachHangList.size();
    }

    public static class BCKhachNoHoler extends RecyclerView.ViewHolder{
        TextView textTenKhachHangNo;
        TextView textKhachhangTienNo;
        TextView textTenKhachHangSDT;

        public BCKhachNoHoler(@NonNull View itemView) {
            super(itemView);
            textTenKhachHangSDT = itemView.findViewById(R.id.baocao_khachhangsdt);
            textTenKhachHangNo = itemView.findViewById(R.id.baocao_khachhangno);
             textKhachhangTienNo = itemView.findViewById(R.id.baocao_khtienno);
        }
    }
}
