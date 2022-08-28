package com.datn.quanlybanhang.fragment.sanpham;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.datn.quanlybanhang.model.KhoHang;
import com.datn.quanlybanhang.model.SanPham;
import com.datn.quanlybanhang.myinterface.IAddEditModel;


public class Fragment_ChiTietSanPham extends Fragment implements IAddEditModel<SanPham> {
    private SanPham sanPham;
    MySQLiteHelper database;
    Toast toast;

    public Fragment_ChiTietSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__chi_tiet_san_pham, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageViewName = view.findViewById(R.id.chitietSanPham_Image);
        TextView textViewName = view.findViewById(R.id.chitietSanPham_TenSP);
        TextView textViewChiTietSP = view.findViewById(R.id.chitietSanPhamMota);
        TextView textViewThongTin = view.findViewById(R.id.chitietSanPhamThongTin);
        Button buttonSua = view.findViewById(R.id.btn_sua_chitietsp);
        Button buttonXoa = view.findViewById(R.id.btn_xoa_chitietsp);
        if (getContext() == null) return;
        database = new MySQLiteHelper(getContext());
        KhoHang khoHang = database.getKhoHang(sanPham.getMaSP());

        byte[] bytes = sanPham.getImgSP();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imageViewName.setImageBitmap(bitmap);

        textViewName.setText(Html.fromHtml(getString(R.string.chitietSanPham_TenSP) + " : " + sanPham.getTenSP()));

        textViewChiTietSP.setText(Html.fromHtml(getString(R.string.chitietSanPham_MoTa) + " : " + sanPham.getChiTietSP()));
        textViewThongTin.setText(Html.fromHtml(getString(R.string.chitietSanPham_NuocSX) + " : " + sanPham.getNuocSX() +
                "<p>" + getString(R.string.chitietSanPham_DonViTinh) + " : " + sanPham.getDonViTinh() +
                "</p><p>" + getString(R.string.chitietSanPham_DanhMuc) + " : " + sanPham.getLoaiSP() +
                "</p><p>" + getString(R.string.chitietSanPham_Gia) + " : " + khoHang.getGia() +
                "</p><p>" + getString(R.string.chitietSanPham_GiaNhap) + " : " + khoHang.getGiaNhap() +
                "</p><p>" + getString(R.string.chitietSanPham_SoLuong) + " : " + khoHang.getSoLuong() + "</p>"));

        buttonSua.setOnClickListener(view1 -> {
                    if (MainActivity.nhanVien.getQuyen() == 1)
                        replaceFragment(new Fragment_Add_SanPham(Fragment_ChiTietSanPham.this, sanPham));
                    else
                        displayToast("Bạn không có quyền xóa");
                }
        );
        buttonXoa.setOnClickListener(view12 -> {
            if (MainActivity.nhanVien.getQuyen() == 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.nav_model_xoa);
                builder.setMessage("Bạn có chắc không ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Có", (dialogInterface, i) -> {
                    database.deleteSanPham(sanPham);
                    database.deleteKhoHang(khoHang.getMaKho());
                    if (getActivity() != null)
                        getActivity().onBackPressed();
                });
                builder.setNegativeButton("Không", (dialogInterface, i) -> dialogInterface.cancel());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else
                displayToast("Bạn không có quyền xóa");

        });
    }

    public void replaceFragment(Fragment fragment) {
        if (getActivity() == null) return;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayoutcontentthongtin, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean processModel(SanPham sanPham, int i) {
        if (i == Fragment_San_Pham.SUA_SAN_PHAM) {
            sanPham.setMaSP(this.sanPham.getMaSP());
            database.updateSanPham(sanPham);
            this.sanPham = sanPham;
            return true;
        } else
            return false;
    }

    public void displayToast(String message) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

}