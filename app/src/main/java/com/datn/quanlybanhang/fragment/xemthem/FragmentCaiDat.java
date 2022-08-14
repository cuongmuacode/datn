package com.datn.quanlybanhang.fragment.xemthem;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datn.quanlybanhang.R;

public class FragmentCaiDat extends Fragment {
    public FragmentCaiDat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cai_dat, container, false);
    }
}