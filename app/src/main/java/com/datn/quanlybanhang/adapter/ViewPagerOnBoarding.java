package com.datn.quanlybanhang.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.datn.quanlybanhang.fragment.manhinh.FragmentOnBoardingEasy;
import com.datn.quanlybanhang.fragment.manhinh.FragmentOnBoardingFree;
import com.datn.quanlybanhang.fragment.manhinh.FragmentOnBoardingSave;
import com.datn.quanlybanhang.fragment.manhinh.FragmentOnBoardingStart;


public class ViewPagerOnBoarding extends FragmentStateAdapter {

    public ViewPagerOnBoarding(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0: return new FragmentOnBoardingFree();
            case 1: return new FragmentOnBoardingEasy();
            case 2: return new FragmentOnBoardingSave();
            case 3: return new FragmentOnBoardingStart();
        }
        return  new FragmentOnBoardingFree();
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}
