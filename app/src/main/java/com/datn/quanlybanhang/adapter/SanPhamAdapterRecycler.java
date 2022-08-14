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
import com.datn.quanlybanhang.model.SanPham;
import com.datn.quanlybanhang.myinterface.IClickItemListenerRecycer;

import java.util.List;

public class SanPhamAdapterRecycler extends RecyclerView.Adapter<SanPhamAdapterRecycler.SanPhamViewHoler> {

    IClickItemListenerRecycer<SanPham> iClickItemListenerRecycer;
    List<SanPham> sanPhamList;

    public SanPhamAdapterRecycler(List<SanPham> sanPhamList) {
        this.sanPhamList = sanPhamList;
        notifyDataSetChanged();
    }

    public SanPhamAdapterRecycler(IClickItemListenerRecycer<SanPham> iClickItemListenerRecycer, List<SanPham> sanPhamList) {
        this.iClickItemListenerRecycer = iClickItemListenerRecycer;
        this.sanPhamList = sanPhamList;
    }


    @NonNull
    @Override
    public SanPhamViewHoler onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_recyc_view_product,parent,false);
        return new SanPhamViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  SanPhamViewHoler holder, int position) {
        SanPham sanPham = sanPhamList.get(position);
        if(iClickItemListenerRecycer!=null){
            if(sanPham == null) return;
            holder.textNameProduct.setText(sanPham.getTenSP());
            String str = sanPham.getGiaSP()+" VND";
            holder.textGiaProduct.setText(str);
            str = "Số lượng : "+sanPham.getSoLuongSP();
            holder.textViewSoLuong.setText(str);

            byte[] bytes = sanPham.getImgSP();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            holder.imgProduct.setImageBitmap(bitmap);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickItemListenerRecycer.onClickChiTietModel(sanPham);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    iClickItemListenerRecycer.onClickItemModel(sanPham);
                    return false;
                }
            });
        }else{
            if(sanPham == null) return;
            holder.textNameProduct.setText(sanPham.getTenSP());
            String str = sanPham.getGiaSP()+" VND x"+sanPham.getSoLuongSP();
            holder.textGiaProduct.setText(str);
            str = "Phải trả : "+sanPham.getSoLuongSP()*sanPham.getGiaSP()+" VND";
            holder.textViewSoLuong.setText(str);
            byte[] bytes = sanPham.getImgSP();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            holder.imgProduct.setImageBitmap(bitmap);
            holder.textViewRemove.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if(sanPhamList!=null) return sanPhamList.size();
        return 0;
    }

    public static class SanPhamViewHoler extends RecyclerView.ViewHolder{
        private ImageView imgProduct;
        private TextView textNameProduct;
        private TextView textGiaProduct;
        private TextView textViewSoLuong;
        private TextView textViewRemove;

        public SanPhamViewHoler(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            textNameProduct = itemView.findViewById(R.id.productName);
            textGiaProduct = itemView.findViewById(R.id.productGia);
            textViewSoLuong = itemView.findViewById(R.id.productSoLuong);
            textViewRemove = itemView.findViewById(R.id.textItemRecycProductRemove);
        }
    }
}
