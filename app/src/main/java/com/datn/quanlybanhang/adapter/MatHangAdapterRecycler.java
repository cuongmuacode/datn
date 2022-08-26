package com.datn.quanlybanhang.adapter;

import android.content.Context;
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
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.KhoHang;
import com.datn.quanlybanhang.model.SanPham;
import com.datn.quanlybanhang.myinterface.IClickItemListenerRecycer;
import com.datn.quanlybanhang.myinterface.iClickitemHoaDon;

import java.io.Serializable;
import java.util.List;

public class MatHangAdapterRecycler extends RecyclerView.Adapter<MatHangAdapterRecycler.ProductViewHoler> implements Serializable {

    iClickitemHoaDon<SanPham> iClickitemHoaDon;
    IClickItemListenerRecycer<SanPham>  iClickItemListenerRecycer;
    List<SanPham> sanPhamList;
    List<KhoHang> khoHangList;
    Context context;
    MySQLiteHelper database;

    public MatHangAdapterRecycler(iClickitemHoaDon<SanPham> iClickitemHoaDon, List<SanPham> sanPhamList,List<KhoHang> khoHangList,Context context ) {
        this.iClickitemHoaDon = iClickitemHoaDon;
        this.sanPhamList = sanPhamList;
        this.khoHangList = khoHangList;
        this.context = context;
        database = new MySQLiteHelper(context);
    }
    public MatHangAdapterRecycler(IClickItemListenerRecycer<SanPham> iClickItemListenerRecycer, List<SanPham> sanPhamList,Context context ) {
        this.iClickItemListenerRecycer = iClickItemListenerRecycer;
        this.sanPhamList = sanPhamList;
        this.context = context;
        database = new MySQLiteHelper(context);
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
        KhoHang khoHang = database.getKhoHang(sanPham.getMaSP());
        String str="Số  lượng : "+khoHang.getSoLuong();
        holder.textNameProduct.setText(sanPham.getTenSP());
        holder.textSoLuong.setText(str);
        str = "Giá : "+khoHang.getGia()+" VND";
        holder.textGiaProduct.setText(str);
        byte[] bytes = sanPham.getImgSP();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.imgProduct.setImageBitmap(bitmap);
        if(iClickitemHoaDon != null) {
            khoHang = khoHangList.get(position);
             str="Số  lượng : "+khoHang.getSoLuong();
            holder.textSoLuong.setText(str);
            str = "Giá : "+khoHang.getGia()+" VND";
            holder.textGiaProduct.setText(str);
            holder.textRemove.setBackgroundResource(R.drawable.ic_baseline_close_24);
            holder.textRemove.setOnClickListener(view -> iClickitemHoaDon.onClickItemModel(sanPham,1));
            holder.itemView.setOnClickListener(view -> iClickitemHoaDon.onClickItemModel(sanPham,2));
        }

        if(iClickItemListenerRecycer!=null) {
            holder.itemView.setOnClickListener(view -> iClickItemListenerRecycer.onClickItemModel(sanPham));
            holder.textRemove.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        if(sanPhamList != null)
            return sanPhamList.size();
        return 0;
    }


    public static class ProductViewHoler extends RecyclerView.ViewHolder{
        private final ImageView imgProduct;
        private final TextView textNameProduct;
        private final TextView textSoLuong;
        private final TextView textGiaProduct;
        private final TextView textRemove;



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
