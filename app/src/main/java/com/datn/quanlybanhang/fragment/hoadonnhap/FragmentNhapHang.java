package com.datn.quanlybanhang.fragment.hoadonnhap;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.activityy.MainActivity;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.fragment.khachhang.FragmentKhachHang;
import com.datn.quanlybanhang.fragment.sanpham.Fragment_San_Pham;
import com.datn.quanlybanhang.model.HoaDonNhap;
import com.datn.quanlybanhang.model.KhachHang;
import com.datn.quanlybanhang.model.KhoHang;
import com.datn.quanlybanhang.model.SanPham;
import com.datn.quanlybanhang.myinterface.IClickItemSanPham;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;


public class FragmentNhapHang extends Fragment implements IClickItemSanPham {
    TextView textHoaDonNhap;
    TextView textHoaDonNhapTenNhanVien;
    TextView textHoaDonNhapTenKhachHang;
    TextView textHoaDonNhapTenSanPham;

    Toast toast;

    EditText tIETextGiaBan;
    EditText tIETextGiaNhap;
    EditText tIETextSoLuong;

    LinearLayout layoutChonKhachHang;
    LinearLayout layoutChonSanPham;

    Button appCompatButton;
    Button appCompatButtonNo;
    MySQLiteHelper database;

    HoaDonNhap hoaDonNhap;

    KhachHang selectKhachHang;
    SanPham selectSanPham;

    Random random = new Random(System.currentTimeMillis());
    long soHDNhap;
    public FragmentNhapHang() {
        soHDNhap = Math.abs(random.nextLong());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_nhap_hang, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = new MySQLiteHelper(getContext());

         textHoaDonNhap = view.findViewById(R.id.addSoHoaDonNhap);
         textHoaDonNhapTenNhanVien = view.findViewById(R.id.tenNhanVienHoaDonNhap);
         textHoaDonNhapTenKhachHang= view.findViewById(R.id.tenkhachhang_hoadonNhap);
         textHoaDonNhapTenSanPham = view.findViewById(R.id.tenSanPham_hoadonNhap);
         layoutChonKhachHang = view.findViewById(R.id.linearchonkhachhang);
         layoutChonSanPham = view.findViewById(R.id.linearchonSanPham);

         layoutChonKhachHang.setOnClickListener(view1 -> replaceFragment(new FragmentKhachHang(FragmentNhapHang.this)));
        layoutChonSanPham.setOnClickListener(view12 -> replaceFragment(new Fragment_San_Pham(FragmentNhapHang.this)));

         tIETextGiaBan = view.findViewById(R.id.inputedittext_GiaBan);
         tIETextGiaNhap = view.findViewById(R.id.inputedittext_GiaNhap);
         tIETextSoLuong = view.findViewById(R.id.inputedittext_SoLuongHoaDonNhap);
         String str = "HDN"+soHDNhap;
         textHoaDonNhap.setText(str);
         str = "Nhân viên : "+MainActivity.nhanVien.getHoTenNV();
         textHoaDonNhapTenNhanVien.setText(str);

         if(selectSanPham!=null) {
             KhoHang khoHang = database.getKhoHang(selectSanPham.getMaSP());
             str = "Sản phẩm : "+selectSanPham.getTenSP();
             textHoaDonNhapTenSanPham.setText(str);
             str = khoHang.getGia()+"";
            tIETextGiaBan.setText(str);
         }
         else
             textHoaDonNhapTenSanPham.setText("Chọn tên sản phẩm : ");
        if(selectKhachHang==null)
            selectKhachHang = database.getKhachHang("MaKH01");
        str = "Khách hàng : "+selectKhachHang.getTenKH();
        textHoaDonNhapTenKhachHang.setText(str);
        appCompatButtonNo = view.findViewById(R.id.hoadonNhap_donhangnhap_no);
        appCompatButtonNo.setOnClickListener(view13 -> {
            if (tIETextGiaBan.getText().toString().trim().isEmpty() ||
                    tIETextSoLuong.getText().toString().trim().isEmpty() ||
                    tIETextGiaNhap.getText().toString().trim().isEmpty()){
                    displayToast("Không được để trống !!!");
                    return;
                }

            if (selectSanPham != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS",new Locale("vi","VN"));
                if (!selectKhachHang.getMaKH().equals("MaKH01")) {
                    KhoHang khoHang = database.getKhoHang(selectSanPham.getMaSP());
                    hoaDonNhap = new HoaDonNhap("HDN" + soHDNhap,
                                selectSanPham.getMaSP(),
                                MainActivity.nhanVien.getMaNV(),
                                selectKhachHang.getMaKH(),
                            simpleDateFormat.format(new Timestamp(System.currentTimeMillis())),
                                0,
                                Integer.parseInt(tIETextSoLuong.getText().toString()),
                                Long.parseLong(tIETextGiaNhap.getText().toString()),
                                Long.parseLong(tIETextGiaBan.getText().toString())
                        );
                        boolean a = database.addHoaDonNhap(hoaDonNhap);
                        if (a) {
                            khoHang.setGiaNhap(Long.parseLong(tIETextGiaNhap.getText().toString()));
                            khoHang.setGia(Long.parseLong(tIETextGiaBan.getText().toString()));
                            khoHang.setSoLuong(Integer.parseInt(tIETextSoLuong.getText().toString()));
                            database.updateKhoaHang(khoHang);
                            tIETextGiaBan.setText("");
                            tIETextGiaNhap.setText("");
                            tIETextSoLuong.setText("");
                            textHoaDonNhapTenKhachHang.setText("Khách hàng :");
                            textHoaDonNhapTenSanPham.setText("Sản phẩm :");
                            soHDNhap = Math.abs(random.nextLong());
                            selectSanPham = null;
                            selectKhachHang = null;

                        } else {
                            displayToast("Thử lại xem !!!");
                            soHDNhap = Math.abs(random.nextLong());
                        }

                } else
                displayToast("Khách lẻ không được nợ !!");
            }
            else
            displayToast("Chọn sản phẩm !!!");
        });

         appCompatButton = view.findViewById(R.id.buttonaddHoaDonNhap);
         appCompatButton.setOnClickListener(view14 -> {
             if (tIETextGiaBan.getText().toString().isEmpty() ||
                     tIETextSoLuong.getText().toString().trim().isEmpty() ||
                     tIETextGiaNhap.getText().toString().trim().isEmpty()) {
                    displayToast("Không được để trống !!!");
                 return;
             }
             if (selectSanPham != null) {
                 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS",new Locale("vi","VN"));

                 KhoHang khoHang = database.getKhoHang(selectSanPham.getMaSP());
                 hoaDonNhap = new HoaDonNhap("HDN" + soHDNhap,
                             selectSanPham.getMaSP(),
                             MainActivity.nhanVien.getMaNV(),
                             selectKhachHang.getMaKH(),
                             simpleDateFormat.format(new Timestamp(System.currentTimeMillis())),
                             1,
                             Integer.parseInt(tIETextSoLuong.getText().toString()),
                             Long.parseLong(tIETextGiaNhap.getText().toString()),
                             Long.parseLong(tIETextGiaBan.getText().toString())
                     );
                     boolean a = database.addHoaDonNhap(hoaDonNhap);
                     if (a) {
                         khoHang.setGiaNhap(Long.parseLong(tIETextGiaNhap.getText().toString()));
                         khoHang.setGia(Long.parseLong(tIETextGiaBan.getText().toString()));
                         khoHang.setSoLuong(Integer.parseInt(tIETextSoLuong.getText().toString()));
                         database.updateKhoaHang(khoHang);
                         tIETextGiaBan.setText("");
                         tIETextGiaNhap.setText("");
                         tIETextSoLuong.setText("");
                         textHoaDonNhapTenKhachHang.setText("Khách hàng :");
                         textHoaDonNhapTenSanPham.setText("Sản phẩm :");
                         soHDNhap = Math.abs(random.nextLong());
                         selectSanPham = null;
                         selectKhachHang = null;
                     } else {
                         displayToast("Thử lại xem !!!");soHDNhap = Math.abs(random.nextLong());
                     }

             } else
         displayToast("Chọn sản phẩm !!!");
         });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.add(1,2001,1,"Hóa Đơn Nhập").setOnMenuItemClickListener(menuItem1 -> {
            replaceFragment(new Fragment_HoaDonNhap());
            return true;
        });
        menuItem.setIcon(R.drawable.ic_baseline_view_list_24);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public void onClickSanPham(SanPham sanPham,KhoHang khoHang) {
        selectSanPham = sanPham;
    }

    @Override
    public void onClickKhachHang(KhachHang khachHang) {
        selectKhachHang = khachHang;
    }

    public void replaceFragment(Fragment fragment){
        if(getActivity()==null) return;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayoutcontentthongtin, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void displayToast(String message) {
        if(toast != null)
            toast.cancel();
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}