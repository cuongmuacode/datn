package com.datn.quanlybanhang.fragment.hoadon;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.adapter.SanPhamAdapterRecycler;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.HoaDon;
import com.datn.quanlybanhang.model.KhachHang;
import com.datn.quanlybanhang.model.KhoHang;
import com.datn.quanlybanhang.model.NhanVien;
import com.datn.quanlybanhang.model.SanPham;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Fragment_ChiTietHoaDon extends Fragment implements Serializable {
    HoaDon hoaDon;
    List<SanPham> sanPhamList;
    List<KhoHang> khoHangList;
    TextView textSoHoaDon;
    TextView tenKhachHang;
    TextView textTenNhanVien;
    TextView textTongTien;
    TextView textNgayHD;
    RecyclerView recyclerView;
    SanPhamAdapterRecycler sanPhamAdapterRecycler;
    MySQLiteHelper database;
    Button buttonThanhToan;
    Button buttonXoa;
    Toast toast;


    public Fragment_ChiTietHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
        this.sanPhamList = hoaDon.getSanPhamList();
        this.khoHangList = hoaDon.getKhoList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__chi_tiet_hoa_don, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textSoHoaDon = view.findViewById(R.id.ChiTiet_SoHoaDon);
        tenKhachHang = view.findViewById(R.id.ChiTiet_tenkhachhang_hoadon);
        textTenNhanVien = view.findViewById(R.id.ChiTiet_tenNhanVienHoaDon);
        textTongTien = view.findViewById(R.id.ChiTiet_tongtienhoadon);
        textNgayHD = view.findViewById(R.id.ChiTiet_ngayHoaDon);
        recyclerView = view.findViewById(R.id.ChiTiet_recycer_hoadon);
        buttonThanhToan = view.findViewById(R.id.buttonthanhtoanchitieethoadon);
        buttonXoa = view.findViewById(R.id.buttonxoachitiethoadon);
        if(getContext()==null) return;
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
            recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
            database = new MySQLiteHelper(getContext());
        sanPhamAdapterRecycler = new SanPhamAdapterRecycler(sanPhamList,getContext(),khoHangList);
        recyclerView.setAdapter(sanPhamAdapterRecycler);


            buttonXoa.setVisibility(View.VISIBLE);
            buttonXoa.setOnClickListener(view1 -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.nav_model_xoa);
                builder.setMessage("Bạn có chắc không ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Có", (dialogInterface, i) -> {
                    database.deleteHoaDon(hoaDon);
                    if(getActivity()!=null)
                        getActivity().onBackPressed();
                    displayToast("Thành công !!!");
                });
                builder.setNegativeButton("Không", (dialogInterface, i) -> dialogInterface.cancel());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            });


        if(hoaDon.getHoaDonNo()==0){
            buttonThanhToan.setVisibility(View.VISIBLE);
            buttonThanhToan.setOnClickListener(view12 -> {
                hoaDon.setHoaDonNo(1);
                if(database.updateHoaDon(hoaDon)>0){
                  if(getActivity()!=null)
                    getActivity().onBackPressed();
                    displayToast("Thành công !!!");
                }
            });
        }
        else if(hoaDon.getHoaDonNo()==1){
            buttonThanhToan.setVisibility(View.GONE);
        }


        String str = "Số hóa đơn : "+hoaDon.getSoHD();
        textSoHoaDon.setText(str);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy '| Giờ : ' HH:mm",new Locale("vi", "VN"));
        str = "Ngày : "+dateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD())));
        textNgayHD.setText(str);
        KhachHang khachHang = database.getKhachHang(hoaDon.getMaKH());
        NhanVien nhanVien = database.getNhanVien(hoaDon.getMaNV());
        str = "Tên nhân viên : ";
        if(nhanVien!=null) {
                str = str + nhanVien.getHoTenNV();
                textTenNhanVien.setText(str);
            }
        str = "Tên khách hàng : ";
            if(khachHang!=null) {
                str = str + khachHang.getTenKH();
                tenKhachHang.setText(str);
            }
            str = hoaDon.getTriGia()+" VND";
            textTongTien.setText(str);
    }
    public void displayToast(String message) {
        if(toast != null)
            toast.cancel();
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}