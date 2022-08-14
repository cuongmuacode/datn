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
import com.datn.quanlybanhang.fragment.hoadon.FragmentAddHoaDon;
import com.datn.quanlybanhang.fragment.FragmentBanHang;
import com.datn.quanlybanhang.model.SanPham;
import com.datn.quanlybanhang.myinterface.IClickItemListenerRecycer;

import java.util.List;

public class MatHangAdapterRecycler extends RecyclerView.Adapter<MatHangAdapterRecycler.ProductViewHoler>{

    IClickItemListenerRecycer<SanPham> iClickItemListenerRecycer;
    List<SanPham> sanPhamList;

    public MatHangAdapterRecycler(IClickItemListenerRecycer<SanPham> iClickItemListenerRecycer, List<SanPham> sanPhamList ) {
        this.iClickItemListenerRecycer = iClickItemListenerRecycer;
        this.sanPhamList = sanPhamList;
    }

    @NonNull
    @Override
    public ProductViewHoler onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyc_view_product,parent,false);
        return new ProductViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ProductViewHoler holder, int position) {
        SanPham sanPham = sanPhamList.get(position);
        if(sanPham == null) return;
        String str="Số  lượng : "+sanPham.getSoLuongSP();
        holder.textNameProduct.setText(sanPham.getTenSP());
        holder.textSoLuong.setText(str);
        str = "Giá : "+sanPham.getGiaSP()+" VND";
        holder.textGiaProduct.setText(str);
        byte[] bytes = sanPham.getImgSP();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.imgProduct.setImageBitmap(bitmap);
        if(iClickItemListenerRecycer.getClass().getName().equals(FragmentAddHoaDon.class.getName())) {
            holder.textRemove.setBackgroundResource(R.drawable.ic_baseline_close_24);
            holder.textRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickItemListenerRecycer.onClickItemModel(sanPham);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    iClickItemListenerRecycer.onClickChiTietModel(sanPham);
                    return false;
                }
            });
        }

        if(iClickItemListenerRecycer.getClass().getName().equals(FragmentBanHang.class.getName())) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickItemListenerRecycer.onClickItemModel(sanPham);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(sanPhamList != null)
            return sanPhamList.size();
        return 0;
    }


    public static class ProductViewHoler extends RecyclerView.ViewHolder{
        private ImageView imgProduct;
        private TextView textNameProduct;
        private TextView textSoLuong;
        private TextView textGiaProduct;
        private TextView textRemove;

        public ProductViewHoler(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            textNameProduct = itemView.findViewById(R.id.productName);
            textGiaProduct = itemView.findViewById(R.id.productGia);
            textSoLuong = itemView.findViewById(R.id.productSoLuong);
            textRemove = itemView.findViewById(R.id.textItemRecycProductRemove);
        }
    }
}
