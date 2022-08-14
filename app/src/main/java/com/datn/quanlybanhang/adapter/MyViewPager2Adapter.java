package com.datn.quanlybanhang.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.datn.quanlybanhang.fragment.FragmentBanHang;
import com.datn.quanlybanhang.fragment.baocao.FragmentBaoCao;
import com.datn.quanlybanhang.fragment.hoadon.FragmentHoaDon;
import com.datn.quanlybanhang.fragment.FragmentXemThem;

public class MyViewPager2Adapter extends FragmentStateAdapter {


    public MyViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


     FragmentBanHang fragmentBanHang = new FragmentBanHang();
     FragmentHoaDon fragmentHoaDon = new FragmentHoaDon();
     FragmentBaoCao fragmentBaoCao = new FragmentBaoCao();
     FragmentXemThem fragmentXemThem = new FragmentXemThem();

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0: return fragmentBanHang;
            case 1: return fragmentHoaDon;
            case 2: return fragmentBaoCao;
            case 3: return fragmentXemThem;
        }
        return  fragmentBanHang;
    }



    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public boolean containsItem(long itemId) {
        return super.containsItem(itemId);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}
