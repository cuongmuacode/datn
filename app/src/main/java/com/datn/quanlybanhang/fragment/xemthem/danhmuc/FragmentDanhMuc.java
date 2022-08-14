package com.datn.quanlybanhang.fragment.xemthem.danhmuc;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.fragment.khachhang.FragmentAddKhachHang;
import com.datn.quanlybanhang.fragment.khachhang.FragmentKhachHang;
import com.datn.quanlybanhang.model.DanhMuc;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FragmentDanhMuc extends Fragment {
    ListView listViewDanhMuc;
    MySQLiteHelper database;
    Toast toast;
    List<DanhMuc> list;
    public FragmentDanhMuc() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_danh_muc, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getContext()!=null)
            database = new MySQLiteHelper(getContext());
             list = database.getListDanhMuc();
         ArrayList<String> listString = new ArrayList<>();
         for(DanhMuc danhMuc : list){
             listString.add(danhMuc.getTenDanhMuc());
         }
        listViewDanhMuc = view.findViewById(R.id.listDanhMuc);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1,listString);
        listViewDanhMuc.setAdapter(adapter);
        listViewDanhMuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                replaceFragment(new FragmentAddDanhMuc(list.get(i)));
            }
        });
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.add(1,R.id.menu_right_add,1,R.string.nav_add).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                replaceFragment(new FragmentAddDanhMuc());
                return true;
            }
        });
        menuItem.setIcon(R.drawable.ic_baseline_add_24);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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