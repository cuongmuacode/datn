package com.datn.quanlybanhang.fragment.khachhang;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.adapter.KhachHangAdapterRecycler;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.KhachHang;
import com.datn.quanlybanhang.myinterface.IAddEditModel;
import com.datn.quanlybanhang.myinterface.IClickItemListenerRecycer;
import com.datn.quanlybanhang.myinterface.IClickItemSanPham;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;


public class FragmentKhachHang extends Fragment implements IClickItemListenerRecycer<KhachHang>,IAddEditModel<KhachHang> {
    private EditText editText;
    private RecyclerView recyclerView;
    private ImageView imageView;
    KhachHang selectKhachHang;
    MySQLiteHelper database;
    KhachHangAdapterRecycler khachHangAdapterRecycler;
    List<KhachHang> listKhachHang;
    Toast toast;
    public static final int SUA_KHACH_HANG = 0;
    public static final int ADD_KHACH_HANG = 1;
    static Random random = new Random(System.currentTimeMillis());
    static long soMaKH = Math.abs(random.nextLong());

    List<KhachHang> filterListKhachHang = new ArrayList<>();


    IClickItemSanPham iClickItemSanPham;
    public FragmentKhachHang() {
        // Required empty public constructor
    }
    public FragmentKhachHang(IClickItemSanPham iClickItemSanPham) {
        // Required empty public constructor
        this.iClickItemSanPham = iClickItemSanPham;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_khac_hang, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.edit_khachhang_search);
        recyclerView = view.findViewById(R.id.recycler_KhachHang);
        imageView = view.findViewById(R.id.imageview_khachhang_sort);
        if(getContext()==null) return;
        database = new MySQLiteHelper(getContext());
        //registerForContextMenu(recyclerView);
        xuLyRecyclerView();
        xulyEditText();
        xuLySort();
    }

    @Override
    public void onStart() {
        xuLyRecyclerView();
        super.onStart();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull  Menu menu, @NonNull  MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.add(1,R.id.menu_right_add,1,R.string.nav_add).setOnMenuItemClickListener(menuItem1 -> {
            replaceFragment(new FragmentAddKhachHang(FragmentKhachHang.this));
            return true;
        });
        menuItem.setIcon(R.drawable.ic_baseline_add_24);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull  View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(getActivity()!=null)
            getActivity().getMenuInflater().inflate(R.menu.menu_model,menu);
    }

    @Override
    public void onClickItemModel(KhachHang khachHang ) {
         selectKhachHang = khachHang;
    }

    @Override
    public void onClickChiTietModel(KhachHang khachHang) {
            if(getActivity()==null) return;
            if(iClickItemSanPham!=null) {
                iClickItemSanPham.onClickKhachHang(khachHang);
                getActivity().onBackPressed();
                return;
            }
            replaceFragment(new Fragment_ChiTietKhachHang(khachHang));
    }

    @Override
    public boolean processModel(KhachHang khachHang,int i) {
        if(i == ADD_KHACH_HANG){
            String stringMaKH = "MaKH"+soMaKH;
            khachHang.setMaKH(stringMaKH);
            if(database.addKhachHang(khachHang)) soMaKH = Math.abs(random.nextLong());
            else {
                soMaKH = Math.abs(random.nextLong());
                return false;
            }
            listKhachHang.addAll(database.getListKhachHang());
            khachHangAdapterRecycler.notifyDataSetChanged();
            return true;
        }
        return false;
    }
    private void xulyEditText() {
        editText.setOnFocusChangeListener((view, b) -> {
            if(getContext()==null) return;
            if (view == editText) {
                if (b) {
                    // Open keyboard
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                            showSoftInput(editText, InputMethodManager.SHOW_FORCED);
                } else {
                    // Close keyboard
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                            hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }
            }
        });
        editText.setOnFocusChangeListener((view, b) -> {
            if(getContext()==null) return;
            if (view == editText) {
                if (b) {
                    // Open keyboard
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                            showSoftInput(editText, InputMethodManager.SHOW_FORCED);
                } else {
                    // Close keyboard
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                            hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().isEmpty()) {
                    khachHangAdapterRecycler = new KhachHangAdapterRecycler(listKhachHang,FragmentKhachHang.this);
                    recyclerView.setAdapter(khachHangAdapterRecycler);
                    khachHangAdapterRecycler.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String search = editable.toString();
                filterListKhachHang.clear();
                if(search.isEmpty()) {
                    khachHangAdapterRecycler = new KhachHangAdapterRecycler(listKhachHang,FragmentKhachHang.this);
                    recyclerView.setAdapter(khachHangAdapterRecycler);
                }
                else{
                    for(KhachHang khachHang : listKhachHang){
                        if(removeAccent(khachHang.getTenKH().toLowerCase()).contains(search)||
                                removeAccent(khachHang.getTenKH()).contains(search)||
                                khachHang.getTenKH().contains(search)||
                                khachHang.getTenKH().toLowerCase().contains(search)||
                                (khachHang.getSoDT().toLowerCase()).contains(search))
                            filterListKhachHang.add(khachHang);
                    }
                    recyclerView.setAdapter(new KhachHangAdapterRecycler(filterListKhachHang,FragmentKhachHang.this));
                }
            }
        });
    }

    private void xuLySort() {
        imageView.setOnClickListener(new View.OnClickListener() {
            int i = 1;
            @Override
            public void onClick(View view) {
                if(i==1){
                    displayToast("Sắp xếp A - Z");
                    imageView.setImageResource(R.drawable.ic_baseline_arrow_downward_24);
                    Collections.sort(listKhachHang, (khachHang, khachHang1) -> {
                        int a = khachHang.getTenKH().toLowerCase().compareTo(khachHang1.getTenKH().toLowerCase());
                        if(a!=0) return a;
                        else return   khachHang.getEmail().compareTo(khachHang1.getEmail());
                    });
                    i=2;
                }
                else if((i==2)){
                    displayToast("Sắp xếp Z - A");
                    imageView.setImageResource(R.drawable.ic_baseline_arrow_upward_24);
                    Collections.sort(listKhachHang, (khachHang, khachHang1) -> {
                        int a = khachHang1.getTenKH().toLowerCase().compareTo(khachHang.getTenKH().toLowerCase());
                        if(a!=0) return a;
                        else return  khachHang1.getEmail().compareTo(khachHang.getEmail());
                    });
                    i=3;
                }
                else if(i==3){
                    displayToast("Sắp xếp mặc định");
                    imageView.setImageResource(R.drawable.ic_baseline_sort_24);
                    listKhachHang.clear();
                    listKhachHang.addAll(database.getListKhachHang());
                    i=1;
                }
                khachHangAdapterRecycler.notifyDataSetChanged();
            }
        });
    }
    public void xuLyRecyclerView(){
        if(getContext()==null) return;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        listKhachHang = database.getListKhachHang();
        khachHangAdapterRecycler = new KhachHangAdapterRecycler(listKhachHang, this);
        recyclerView.setAdapter(khachHangAdapterRecycler);
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
    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}