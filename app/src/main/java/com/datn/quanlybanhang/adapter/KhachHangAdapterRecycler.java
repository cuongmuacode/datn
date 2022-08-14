package com.datn.quanlybanhang.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.model.KhachHang;
import com.datn.quanlybanhang.model.NhanVien;
import com.datn.quanlybanhang.myinterface.IAddEditModel;
import com.datn.quanlybanhang.myinterface.IClickItemListenerRecycer;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class KhachHangAdapterRecycler extends RecyclerView.Adapter<KhachHangAdapterRecycler.KhachHangViewHolder>{

    List<KhachHang> khachHangList;
    IClickItemListenerRecycer<KhachHang> clickItemListener;
    List<NhanVien> nhanVienList;
    IAddEditModel<NhanVien> nhanVienIAddEditModel;
    public KhachHangAdapterRecycler(List<KhachHang> khachHangList, IClickItemListenerRecycer<KhachHang> clickItemListener) {
        this.khachHangList = khachHangList;
        this.clickItemListener = clickItemListener;
    }

    public KhachHangAdapterRecycler(List<NhanVien> nhanVienList, IAddEditModel<NhanVien> nhanVienIAddEditModel) {
        this.nhanVienList = nhanVienList;
        this.nhanVienIAddEditModel = nhanVienIAddEditModel;
    }

    @NonNull
    @Override
    public KhachHangViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyc_view_khachhang,parent,false);
        return new KhachHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  KhachHangViewHolder holder, int position) {
        if(this.nhanVienIAddEditModel!=null){
            NhanVien nhanVien = nhanVienList.get(position);
            holder.imageView.setVisibility(View.GONE);
            if (nhanVien == null) return;

            byte[] bytes = nhanVien.getByteImgs();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            holder.imageViewKhachHang.setImageBitmap(bitmap);
            String str = "Tài khoản : "+nhanVien.getHoTenNV();
            holder.textViewNameKH.setText(str);
            str = "SDT : "+nhanVien.getSoDT();
            holder.textViewSDTKH.setText(str);
            str = "Email : "+nhanVien.getEmailNhanVien();
            holder.textViewEmail.setText(str);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    nhanVienIAddEditModel.processModel(nhanVien,1);
                    return false;
                }
            });
        }
        if(clickItemListener!=null) {
            KhachHang khachHang = khachHangList.get(position);
            if (khachHang == null) return;
            holder.imageViewKhachHang.setImageResource(R.drawable.ic_baseline_person_outline_24);
            String str = "Họ tên : "+khachHang.getTenKH();
            holder.textViewNameKH.setText(str);
            str = "SDT : "+khachHang.getSoDT();
            holder.textViewSDTKH.setText(str);
            str = "Email : "+khachHang.getEmail();
            holder.textViewEmail.setText(str);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItemListener.onClickItemModel(khachHang);
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItemListener.onClickChiTietModel(khachHang);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(khachHangList != null)
            return khachHangList.size();
        if(nhanVienList != null)
            return nhanVienList.size();
        return 0;
    }


    public static class KhachHangViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageViewKhachHang;
        ImageView imageView;
        TextView textViewNameKH;
        TextView textViewEmail;
        TextView textViewSDTKH;
        public KhachHangViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgMoreKhachhangchitiet);
            imageViewKhachHang = itemView.findViewById(R.id.imgKhachHang);
            textViewNameKH = itemView.findViewById(R.id.recycler_khachhang_Name);
            textViewSDTKH = itemView.findViewById(R.id.recycler_khachhang_SDT);
            textViewEmail = itemView.findViewById(R.id.recycler_khachhang_Email);
        }
    }
}
