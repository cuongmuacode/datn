package com.datn.quanlybanhang.fragment.hoadonnhap;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.activityy.MainActivity;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.fragment.khachhang.FragmentKhachHang;
import com.datn.quanlybanhang.fragment.sanpham.Fragment_San_Pham;
import com.datn.quanlybanhang.model.HoaDonNhap;
import com.datn.quanlybanhang.model.KhachHang;
import com.datn.quanlybanhang.model.SanPham;
import com.datn.quanlybanhang.myinterface.IClickItemSanPham;

import java.util.Random;


public class FragmentNhapHang extends Fragment implements IClickItemSanPham {
    TextView textHoaDonNhap;
    TextView textHoaDonNhapTenNhanVien;
    TextView textHoaDonNhapTenKhachHang;
    TextView textHoaDonNhapTenSanPham;

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

         layoutChonKhachHang.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 replaceFragment(new FragmentKhachHang(FragmentNhapHang.this));
             }
         });
        layoutChonSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new Fragment_San_Pham(FragmentNhapHang.this));
            }
        });

         tIETextGiaBan = view.findViewById(R.id.inputedittext_GiaBan);
         tIETextGiaNhap = view.findViewById(R.id.inputedittext_GiaNhap);
         tIETextSoLuong = view.findViewById(R.id.inputedittext_SoLuongHoaDonNhap);
         String str = "HDN"+soHDNhap;
         textHoaDonNhap.setText(str);
         str = "Nhân viên : "+MainActivity.nhanVien.getHoTenNV();
         textHoaDonNhapTenNhanVien.setText(str);

         if(selectSanPham!=null) {
             str = "Sản phẩm : "+selectSanPham.getTenSP();
             textHoaDonNhapTenSanPham.setText(str);
             str = selectSanPham.getGiaSP()+"";
            tIETextGiaBan.setText(str);
         }
         else
             textHoaDonNhapTenSanPham.setText("Chọn tên sản phẩm : ");
        if(selectKhachHang==null)
            selectKhachHang = database.getKhachHang("MaKH01");
        str = "Khách hàng : "+selectKhachHang.getTenKH();
        textHoaDonNhapTenKhachHang.setText(str);
        appCompatButtonNo = view.findViewById(R.id.hoadonNhap_donhangnhap_no);
        appCompatButtonNo.setOnClickListener(new View.OnClickListener() {
            int clickProcess = 0;
            @Override
            public void onClick(View view) {
                if (tIETextGiaBan.getText().toString().isEmpty() ||
                        tIETextSoLuong.getText().toString().isEmpty() ||
                        tIETextGiaNhap.getText().toString().isEmpty())
                    if (clickProcess < 5) {
                        clickProcess++;
                        Toast.makeText(getContext(), "Không được để trống !!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                clickProcess = 0;
                if (selectSanPham != null) {
                    if (!selectKhachHang.getMaKH().equals("MaKH01")) {
                        if(Integer.parseInt(tIETextSoLuong.getText().toString())+selectSanPham.getSoLuongSP()>=999999999)
                            Toast.makeText(getContext(), "Số lượng sản phẩm quá lớn!!", Toast.LENGTH_SHORT).show();
                        else {
                            hoaDonNhap = new HoaDonNhap("HDN" + soHDNhap,
                                    selectSanPham.getMaSP(),
                                    MainActivity.nhanVien.getMaNV(),
                                    selectKhachHang.getMaKH(),
                                    "" + System.currentTimeMillis(),
                                    Long.parseLong(tIETextGiaNhap.getText().toString()),
                                    Long.parseLong(tIETextGiaBan.getText().toString()),
                                    1,
                                    0
                            );
                            boolean a = database.addHoaDonNhap(hoaDonNhap);
                            if (a) {
                                selectSanPham.setSoLuongSP(hoaDonNhap.getSoLuong() + Integer.parseInt(tIETextSoLuong.getText().toString()));
                                selectSanPham.setGiaSP(hoaDonNhap.getGiaBan());
                                database.updateSanPham(selectSanPham);
                                if (getActivity() != null)
                                    getActivity().onBackPressed();
                            } else {
                                Toast.makeText(getContext(), "Thử lại xem !!!", Toast.LENGTH_SHORT).show();
                                soHDNhap = Math.abs(random.nextLong());
                            }
                        }
                    } else
                        Toast.makeText(getContext(), "Khách lẻ không được nợ !!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(), "Chọn sản phẩm !!!", Toast.LENGTH_SHORT).show();
            }
        });

         appCompatButton = view.findViewById(R.id.buttonaddHoaDonNhap);
         appCompatButton.setOnClickListener(new View.OnClickListener() {
             int clickProcess = 0;
             @Override
             public void onClick(View view) {
                 if (tIETextGiaBan.getText().toString().isEmpty() ||
                         tIETextSoLuong.getText().toString().isEmpty() ||
                         tIETextGiaNhap.getText().toString().isEmpty())
                     if (clickProcess < 5) {
                         clickProcess++;
                         Toast.makeText(getContext(), "Không được để trống !!!", Toast.LENGTH_SHORT).show();
                         return;
                     }
                 clickProcess = 0;
                 if (selectSanPham != null) {
                     if(Integer.parseInt(tIETextSoLuong.getText().toString())>=999999999)
                         Toast.makeText(getContext(), "Số lượng sản phẩm quá lớn!!", Toast.LENGTH_SHORT).show();
                     else {
                         hoaDonNhap = new HoaDonNhap("HDN" + soHDNhap,
                                 selectSanPham.getMaSP(),
                                 MainActivity.nhanVien.getMaNV(),
                                 selectKhachHang.getMaKH(),
                                 "" + System.currentTimeMillis(),
                                 Long.parseLong(tIETextGiaNhap.getText().toString()),
                                 Long.parseLong(tIETextGiaBan.getText().toString()),
                                 Integer.parseInt(tIETextSoLuong.getText().toString()),
                                 1
                         );
                         boolean a = database.addHoaDonNhap(hoaDonNhap);
                         if (a) {
                             selectSanPham.setSoLuongSP(Integer.parseInt(tIETextSoLuong.getText().toString()));
                             selectSanPham.setGiaSP(hoaDonNhap.getGiaBan());
                             database.updateSanPham(selectSanPham);
                             if (getActivity() != null)
                                 getActivity().onBackPressed();
                         } else {
                             Toast.makeText(getContext(), "Thử lại xem !!!", Toast.LENGTH_SHORT).show();
                             soHDNhap = Math.abs(random.nextLong());
                         }
                     }
                 } else
                     Toast.makeText(getContext(), "Chọn sản phẩm !!!", Toast.LENGTH_SHORT).show();
             }
         });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.add(1,2001,1,"Hóa Đơn Nhập").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                replaceFragment(new Fragment_HoaDonNhap());
                return true;
            }
        });
        menuItem.setIcon(R.drawable.ic_baseline_view_list_24);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public void onClickSanPham(SanPham sanPham) {
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
}