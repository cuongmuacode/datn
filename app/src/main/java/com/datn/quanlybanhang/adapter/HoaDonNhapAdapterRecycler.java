package com.datn.quanlybanhang.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Locale;

public class HoaDonNhapAdapterRecycler extends RecyclerView.Adapter<HoaDonNhapAdapterRecycler.HoaDonNhapViewHolder>{
    List<HoaDonNhap> hoaDonNhaps;
    Toast toast;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyc_view_hoadonnhap,parent,false);
        return new HoaDonNhapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonNhapViewHolder holder, int position) {
        HoaDonNhap hoaDonNhap = hoaDonNhaps.get(position);
        MySQLiteHelper database = new MySQLiteHelper(context);
        KhachHang khachHang = database.getKhachHang(hoaDonNhap.getMaKH());
        SanPham sanPham = database.getSanPham(hoaDonNhap.getMaSP());
        NhanVien nhanVien = database.getNhanVien(hoaDonNhap.getMaNV());
        holder.itemView.setOnClickListener(view -> {
            holder.btnXoa.setVisibility(View.VISIBLE);
            if(hoaDonNhap.getHoaDonNhapNo()==0) {
                holder.btnThanhToan.setVisibility(View.VISIBLE);
            }
            else holder.btnThanhToan.setVisibility(View.GONE);

        });
        holder.btnXoa.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.nav_model_xoa);
            builder.setMessage("Bạn có chắc không ?");
            builder.setCancelable(true);
            builder.setPositiveButton("Có", (dialogInterface, i) -> {
                database.deleteHoaDonNhap(hoaDonNhap);
                hoaDonNhaps.remove(hoaDonNhap);
                notifyDataSetChanged();
                displayToast("Thành công !!");
            });
            builder.setNegativeButton("Không", (dialogInterface, i) -> dialogInterface.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        holder.btnThanhToan.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.nav_model_thanhtoan);
            builder.setMessage("Bạn muốn thanh toán ?");
            builder.setCancelable(true);
            builder.setPositiveButton("Có", (dialogInterface, i) -> {
                hoaDonNhap.setHoaDonNhapNo(1);
                if(database.updateHoaDonNhap(hoaDonNhap)>0)
                    displayToast("Thành công !!");
                String checkNo = "";
                if(hoaDonNhap.getHoaDonNhapNo()==0) checkNo = "Nợ";
                holder.textGiaProductNo.setText(checkNo);

            });
            builder.setNegativeButton("Không", (dialogInterface, i) -> dialogInterface.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        holder.textngayLapHD.setVisibility(View.GONE);
        holder.imageView.setVisibility(View.GONE);
        holder.maHoaDon.setText(hoaDonNhap.getSoHDNhap());
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy ' |  Giờ : 'HH:mm",new Locale("vi","VN"));
        String str;
        if(khachHang!=null&&nhanVien!=null) {
         str = "Khách hàng : "+khachHang.getTenKH()+"\nNhân viên : "+nhanVien.getHoTenNV()+"\nNgày : "+dateFormat.format(new Date(Long.parseLong(hoaDonNhap.getNgayNhap())));
            holder.textKhacHang.setText(str);
        }
        String checkNo = "";
        if(hoaDonNhap.getHoaDonNhapNo()==0)
            checkNo = "Nợ";

            holder.textGiaProductNo.setText(checkNo);

        if(sanPham!=null) {
            str = "Tên sản phẩm : " + sanPham.getTenSP() + "\nGiá bán : " + hoaDonNhap.getGia() + " VND\nGiá nhập : " + hoaDonNhap.getGiaNhap() + " VND\nSố lượng : " + hoaDonNhap.getSoLuongNhap();
            holder.textGiaProduct.setText(str);
        }

    }
    public void displayToast(String message) {
        if(toast != null)
            toast.cancel();
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
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
        Button btnThanhToan;
        Button btnXoa;
        public HoaDonNhapViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =  itemView.findViewById(R.id.imgMoreHoaDonNhap);
            maHoaDon = itemView.findViewById(R.id.idHoaDonNhap);
            textKhacHang = itemView.findViewById(R.id.hoaDonNhapKhachhang);
            textGiaProduct = itemView.findViewById(R.id.productGiahoadonNhap);
            textGiaProductNo = itemView.findViewById(R.id.productGiahoadonNoNhap);
            textngayLapHD =  itemView.findViewById(R.id.ngayHoadonNhap);
            btnThanhToan = itemView.findViewById(R.id.button_thanhtoan_hoadonNhap);
            btnXoa = itemView.findViewById(R.id.button_xoa_hoadonNhap);
        }
    }
}

