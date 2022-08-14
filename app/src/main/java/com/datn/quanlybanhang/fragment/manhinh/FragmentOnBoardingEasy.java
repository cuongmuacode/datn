package com.datn.quanlybanhang.fragment.manhinh;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datn.quanlybanhang.R;


public class FragmentOnBoardingEasy extends Fragment {


    public FragmentOnBoardingEasy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_easy, container, false);
    }
}