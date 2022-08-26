package com.datn.quanlybanhang.activityy;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.fragment.FragmentBanHang;
import com.datn.quanlybanhang.fragment.FragmentGioiThieu;
import com.datn.quanlybanhang.fragment.FragmentHuongDan;
import com.datn.quanlybanhang.fragment.FragmentXemThem;
import com.datn.quanlybanhang.fragment.baocao.FragmentBaoCaoTongHop;
import com.datn.quanlybanhang.fragment.hoadon.FragmentAddHoaDon;
import com.datn.quanlybanhang.fragment.hoadon.Fragment_ChiTietHoaDon;
import com.datn.quanlybanhang.fragment.hoadonnhap.FragmentNhapHang;
import com.datn.quanlybanhang.fragment.khachhang.FragmentKhachHang;
import com.datn.quanlybanhang.fragment.nhanvien.FragmentTaiKhoan;
import com.datn.quanlybanhang.fragment.nhanvien.Fragment_DoiMatKhau;
import com.datn.quanlybanhang.fragment.sanpham.Fragment_San_Pham;
import com.datn.quanlybanhang.fragment.xemthem.FragmentCaiDat;
import com.datn.quanlybanhang.fragment.xemthem.FragmentMayIn;
import com.datn.quanlybanhang.fragment.xemthem.danhmuc.FragmentDanhMuc;
import com.datn.quanlybanhang.fragment.xemthem.donvitinh.FragmentDonViTinh;
import com.datn.quanlybanhang.model.HoaDon;

public class ActivityThongTin extends AppCompatActivity {
    int  i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_thong_tin);
            if(savedInstanceState!=null) finish();
            setActionToolbaActivity();
            getIntentAll();
    }

    private void setActionToolbaActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.nav_caidat);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

        }
    }
    private void managerFragmentAll(Fragment fragment){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.framelayoutcontentthongtin, fragment);
            transaction.commit();
    }

    private void getIntentAll(){
        Intent intent = getIntent();
        i = intent.getIntExtra("Data",10000);
        Bundle bundle;
        if(getSupportActionBar()==null) return;
        switch (i){
            case FragmentXemThem.ACT_CAIDAT:
                getSupportActionBar().setTitle(R.string.nav_caidat);
                managerFragmentAll(new FragmentCaiDat());
                break;
            case FragmentXemThem.ACT_BAOCAOTONGHOP:
                getSupportActionBar().setTitle(R.string.xemthem_baocaotonghop);
                managerFragmentAll(new FragmentBaoCaoTongHop());
                break;
            case FragmentXemThem.ACT_DANHMUC:
                getSupportActionBar().setTitle(R.string.xemthem_danhmuc);
                managerFragmentAll(new FragmentDanhMuc());
                break;
            case FragmentXemThem.ACT_DONVITINH:
                getSupportActionBar().setTitle(R.string.xemthem_donvitinh);
                managerFragmentAll(new FragmentDonViTinh());
                break;
            case FragmentXemThem.ACT_KHACHHANG:
                getSupportActionBar().setTitle(R.string.xemthem_khachhang);
                managerFragmentAll(new FragmentKhachHang());
                break;
            case FragmentXemThem.ACT_NHAPHANG:
                getSupportActionBar().setTitle(R.string.xemthem_nhaphang);
                managerFragmentAll(new FragmentNhapHang());
                break;
            case FragmentXemThem.ACT_TAIKHOAN:
                getSupportActionBar().setTitle(R.string.xemthem_taikhoan);
                managerFragmentAll(new FragmentTaiKhoan());
                break;
            case FragmentXemThem.ACT_MAYIN:
                getSupportActionBar().setTitle(R.string.xemthem_mayin);
                managerFragmentAll(new FragmentMayIn());
                break;
            case FragmentXemThem.ACT_MATHANG:
                getSupportActionBar().setTitle(R.string.xemthem_mathang);
                managerFragmentAll(new Fragment_San_Pham());
                break;
            case FragmentXemThem.ACT_SHOP:
                getSupportActionBar().setTitle(R.string.nav_don_hang);
                managerFragmentAll((FragmentAddHoaDon)FragmentBanHang.iClickItemSanPham);
                break;
            case FragmentXemThem.ACT_CHITIETHOADON:
                getSupportActionBar().setTitle(R.string.nav_don_hang);
                bundle= intent.getBundleExtra("Fragment");
                managerFragmentAll(new Fragment_ChiTietHoaDon((HoaDon) bundle.getSerializable("BundleHoaDon")));
                break;
            case FragmentXemThem.ACT_GIOITHIEU:
                getSupportActionBar().setTitle(R.string.nav_gioithieu);
                managerFragmentAll(new FragmentGioiThieu());
                break;
            case FragmentXemThem.ACT_HUONGDAN:
                getSupportActionBar().setTitle(R.string.nav_huongdan);
                managerFragmentAll(new FragmentHuongDan());
                break;
            case FragmentXemThem.ACT_DOIMATKHAU:
                getSupportActionBar().setTitle(R.string.nav_doimatkhau);
                managerFragmentAll(new Fragment_DoiMatKhau());
                break;

        }

    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}