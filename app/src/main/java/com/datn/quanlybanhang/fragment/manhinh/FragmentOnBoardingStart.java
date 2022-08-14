package com.datn.quanlybanhang.fragment.manhinh;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.activityy.ActivityLoginn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class FragmentOnBoardingStart extends Fragment {


    public FragmentOnBoardingStart() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.startIDButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityLoginn.class);
                setDataCache(60);
                startActivity(intent);
                if(getActivity()!=null)
                getActivity().finish();
            }
        });
    }
    public void setDataCache(int i){
        FileOutputStream fileOutputStream;
        File file;
        try{
            if(getActivity()==null) return;
            file = new File(getActivity().getCacheDir(),"stateOnBoarding.txt");
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(i);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}