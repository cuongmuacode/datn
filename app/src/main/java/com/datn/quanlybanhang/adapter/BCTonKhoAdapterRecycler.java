package com.datn.quanlybanhang.adapter;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.KhoHang;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class BCTonKhoAdapterRecycler extends RecyclerView.Adapter<BCTonKhoAdapterRecycler.BCTonKhoHoler>{
    List<KhoHang> listTonKho;
    MySQLiteHelper database;
    public BCTonKhoAdapterRecycler(List<KhoHang> listTonKho, MySQLiteHelper database) {
        this.listTonKho = listTonKho;
        this.database = database;
    }

    @NonNull
    @Override
    public BCTonKhoHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_bcsanphamtonkho,parent,false);
        return new BCTonKhoAdapterRecycler.BCTonKhoHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BCTonKhoHoler holder, int position) {
        KhoHang khoHang = listTonKho.get(position);
        String str = "Tên sản phẩm : "+khoHang.getMaSP();
        holder.textTenSanPham.setText(str);
        str = "Số Lượng tồn : "+khoHang.getSoLuong();
        holder.textSoLuongSanPhamTon.setText(str);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM", new Locale("vi", "VN"));
        str = "Select sum(CTHD_SL)" +
                " From CTHD,HOAD`ON " +
                " Where strftime('%Y%m',HOADON_NGAYHD) = '"+ simpleDateFormat.format(new Timestamp(System.currentTimeMillis()))+"' and CTHD_SOHD = HOADON_SOHD and CTHD_MASP = '" +khoHang.getMaKho() +"'";

        Cursor cursor =  database.execSQLSelect(str,database.getReadableDatabase());
        if(cursor.moveToFirst())
            str = "Đã bán: "+cursor.getInt(0);
        else
            str = "Đã bán: 0";
        holder.textDaBan.setText(str);
        database.close();

    }
    @Override
    public int getItemCount() {
        if(listTonKho == null)
        return 0;
        return listTonKho.size();
    }

    public static class BCTonKhoHoler extends RecyclerView.ViewHolder {
        TextView textTenSanPham;
        TextView textSoLuongSanPhamTon;
        TextView textDaBan;

        public BCTonKhoHoler(@NonNull View itemView) {
            super(itemView);

            textDaBan = itemView.findViewById(R.id.baocao_sanphamtonkhoaDaBan);
            textTenSanPham = itemView.findViewById(R.id.baocao_tensanphamtonkho);
            textSoLuongSanPhamTon = itemView.findViewById(R.id.baocao_sanphamtonkhoa);
        }
    }
}
