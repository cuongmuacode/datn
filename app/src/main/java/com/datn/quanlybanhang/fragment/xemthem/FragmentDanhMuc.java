package com.datn.quanlybanhang.fragment.xemthem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.datn.quanlybanhang.R;

public class FragmentDanhMuc extends Fragment {
    ListView listViewDanhMuc;

    public FragmentDanhMuc() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_danh_muc, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listViewDanhMuc = view.findViewById(R.id.listDanhMuc);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.arrstr_danhmuc, android.R.layout.simple_expandable_list_item_1);
        listViewDanhMuc.setAdapter(adapter);
    }
}