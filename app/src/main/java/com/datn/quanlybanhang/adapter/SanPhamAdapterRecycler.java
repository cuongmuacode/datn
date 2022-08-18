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

import java.util.List;

public class SanPhamAdapterRecycler extends RecyclerView.Adapter<SanPhamAdapterRecycler.SanPhamViewHoler> {

    IClickItemListenerRecycer<SanPham> iClickItemListenerRecycer;
    List<SanPham> sanPhamList;
    List<KhoHang> khoHangList;
    Context context;
    public SanPhamAdapterRecycler(List<SanPham> sanPhamList, Context context,List<KhoHang> khoHangList) {
        this.sanPhamList = sanPhamList;
        this.khoHangList = khoHangList;
        notifyDataSetChanged();
        this.context = context;
    }

    public SanPhamAdapterRecycler(IClickItemListenerRecycer<SanPham> iClickItemListenerRecycer, List<SanPham> sanPhamList,Context context) {
        this.iClickItemListenerRecycer = iClickItemListenerRecycer;
        this.sanPhamList = sanPhamList;
        this.context = context;
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
        MySQLiteHelper database = new MySQLiteHelper(context);
        SanPham sanPham = sanPhamList.get(position);

        if(iClickItemListenerRecycer!=null){
            if(sanPham == null) return;
            KhoHang khoHang = database.getKhoHang(sanPham.getMaSP());
            holder.textNameProduct.setText(sanPham.getTenSP());
            String str = khoHang.getGia()+" VND";
            holder.textGiaProduct.setText(str);
            str = "Số lượng : "+khoHang.getSoLuong();
            holder.textViewSoLuong.setText(str);

            byte[] bytes = sanPham.getImgSP();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            holder.imgProduct.setImageBitmap(bitmap);

            holder.itemView.setOnClickListener(view -> iClickItemListenerRecycer.onClickChiTietModel(sanPham));
            holder.itemView.setOnLongClickListener(view -> {
                iClickItemListenerRecycer.onClickItemModel(sanPham);
                return false;
            });
        }else{
            if(sanPham == null) return;
            KhoHang khoHangk = khoHangList.get(position);
            holder.textNameProduct.setText(sanPham.getTenSP());
            String str ="Đơn giá : "+ khoHangk.getGia();
            holder.textGiaProduct.setText(str);
            str = "Số lượng : "+khoHangk.getSoLuong();
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
        private final ImageView imgProduct;
        private final TextView textNameProduct;
        private final TextView textGiaProduct;
        private final TextView textViewSoLuong;
        private final TextView textViewRemove;

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
