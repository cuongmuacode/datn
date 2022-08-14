package com.datn.quanlybanhang.fragment.sanpham;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.adapter.SanPhamAdapterRecycler;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.DanhMuc;
import com.datn.quanlybanhang.model.SanPham;
import com.datn.quanlybanhang.myinterface.IAddEditModel;
import com.datn.quanlybanhang.myinterface.IClickItemListenerRecycer;
import com.datn.quanlybanhang.myinterface.IClickItemSanPham;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class Fragment_San_Pham extends Fragment implements IClickItemListenerRecycer<SanPham>, IAddEditModel<SanPham> {
    private EditText editText;
    private RecyclerView recyclerView;
    private ImageView imageView;
    private Spinner spinner;

    SanPham selectSanPham;
    MySQLiteHelper database;
    SanPhamAdapterRecycler sanPhamAdapterRecycler;

    List<SanPham> listSanPham;
    List<SanPham> filterListSanPham = new ArrayList<>();
    List<SanPham> sanPhamSpinners = new ArrayList<>();

    static Random random = new Random(System.currentTimeMillis());
    static long soMaSP = Math.abs(random.nextLong());

    public static final int SUA_SAN_PHAM = 1;
    public static final int ADD_SAN_PHAM = 2;

    Toast toast;
    String textItemSprinner;

    IClickItemSanPham iClickItemSanPham;

    public Fragment_San_Pham() {
    }
    public Fragment_San_Pham(IClickItemSanPham iClickItemSanPham) {
        this.iClickItemSanPham = iClickItemSanPham;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment__san__pham, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = view.findViewById(R.id.edit_sanpham_search);
        recyclerView = view.findViewById(R.id.recycler_SanPham);
        imageView = view.findViewById(R.id.imageview_sanpham_sort);
        spinner = view.findViewById(R.id.spinnerSanPham);
        xuLyRecyclerView();
        xulyEditText();
        xuLySort();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.add(1,R.id.menu_right_add,1,R.string.nav_add).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                replaceFragment(new Fragment_Add_SanPham(Fragment_San_Pham.this));
                return true;
            }
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



    private void xulyEditText() {
        List<DanhMuc> listDanhMuc = database.getListDanhMuc();
        List<String> listStringDanhMuc = new ArrayList<String>();
        listStringDanhMuc.add("Tất cả mặt hàng");
        for(DanhMuc danhMuc : listDanhMuc){
            listStringDanhMuc.add(danhMuc.getTenDanhMuc());
        }

        ArrayAdapter<CharSequence> adapter = new  ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_item,listStringDanhMuc);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textItemSprinner = listStringDanhMuc.get(i);
                listSanPham.clear();
                listSanPham = database.getListSanPham();
                if(textItemSprinner.equals(textItemSprinner)){
                    sanPhamSpinners.clear();
                    for(SanPham sanPham : listSanPham)
                        if(sanPham.getLoaiSP().equals(textItemSprinner))
                            sanPhamSpinners.add(sanPham);
                        listSanPham.clear();
                        listSanPham.addAll(sanPhamSpinners);
                    sanPhamAdapterRecycler = new SanPhamAdapterRecycler(Fragment_San_Pham.this,listSanPham);
                    recyclerView.setAdapter(sanPhamAdapterRecycler);
                }
                else if(textItemSprinner.equals("Tất cả mặt hàng")){
                    sanPhamAdapterRecycler = new SanPhamAdapterRecycler(Fragment_San_Pham.this,listSanPham);
                    recyclerView.setAdapter(sanPhamAdapterRecycler);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(getContext()==null)return;
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
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String search = editable.toString();
                if(search.isEmpty()) {
                    sanPhamAdapterRecycler = new SanPhamAdapterRecycler(Fragment_San_Pham.this,listSanPham);
                    recyclerView.setAdapter(sanPhamAdapterRecycler);

                }
                else{
                    filterListSanPham.clear();
                    for(SanPham sanPham : listSanPham){
                        if(sanPham.getTenSP().contains(search)||
                                (sanPham.getGiaSP()+"").contains(search))
                            filterListSanPham.add(sanPham);
                    }
                    recyclerView.setAdapter(new SanPhamAdapterRecycler(Fragment_San_Pham.this,filterListSanPham));
                }
                sanPhamAdapterRecycler.notifyDataSetChanged();
            }
        });
    }

    private void xuLyRecyclerView() {
        if(getContext()!=null) {
            database = new MySQLiteHelper(getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
            recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        }
        listSanPham = database.getListSanPham();
        sanPhamAdapterRecycler = new SanPhamAdapterRecycler(this,listSanPham);
        recyclerView.setAdapter(sanPhamAdapterRecycler);
    }

    @Override
    public void onStart() {
        xuLyRecyclerView();
        super.onStart();
    }

    private void xuLySort() {
        imageView.setOnClickListener(new View.OnClickListener() {
           int i = 1;
            @Override
            public void onClick(View view) {
                if(i==1){
                    displayToast("Sắp xếp A - Z");
                    imageView.setImageResource(R.drawable.ic_baseline_arrow_downward_24);
                    Collections.sort(listSanPham, new Comparator<SanPham>() {
                        @Override
                        public int compare(SanPham sanPham, SanPham sanPham1) {
                            int a = sanPham.getTenSP().compareTo(sanPham1.getTenSP());
                            if(a!=0) return a;
                            else return  sanPham.getSoLuongSP() - sanPham1.getSoLuongSP();
                        }
                    });
                    i=2;
                }
                else if(i==2){
                    displayToast("Sắp xếp Z - A");
                    imageView.setImageResource(R.drawable.ic_baseline_arrow_upward_24);
                    Collections.sort(listSanPham, new Comparator<SanPham>() {
                        @Override
                        public int compare(SanPham sanPham, SanPham sanPham1) {
                            int a = sanPham1.getTenSP().compareTo(sanPham.getTenSP());
                            if(a!=0) return a;
                            else return  sanPham1.getSoLuongSP() - sanPham.getSoLuongSP();
                        }
                    });
                    i=3;
                }
                else if(i==3){
                    displayToast("Sắp xếp mặc định");
                    imageView.setImageResource(R.drawable.baseline_sort_by_alpha_24);
                        listSanPham.clear();
                        listSanPham.addAll(database.getListSanPham());
                        spinner.setSelection(0);
                    i=1;
                }
                sanPhamAdapterRecycler.notifyDataSetChanged();
            }
        });
    }


    public void replaceFragment(Fragment fragment){
        if(getActivity()==null)return;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayoutcontentthongtin, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public boolean processModel(SanPham sanPham, int i) {
         if(i == ADD_SAN_PHAM){
            String stringMaSP = "MaSP"+soMaSP;
            sanPham.setMaSP(stringMaSP);
            if(database.addSanPham(sanPham)) soMaSP = Math.abs(random.nextLong());
            else {
                soMaSP = Math.abs(random.nextLong());
                return false;
            }
            listSanPham.addAll(database.getListSanPham());
            sanPhamAdapterRecycler.notifyDataSetChanged();
            return true;
        }
        return false;
    }

    @Override
    public void onClickItemModel(SanPham sanPham) {
        selectSanPham = sanPham;
    }

    @Override
    public void onClickChiTietModel(SanPham sanPham) {
        if(iClickItemSanPham!=null) {
            iClickItemSanPham.onClickSanPham(sanPham);
            if(getActivity()!=null)
                getActivity().onBackPressed();
            return;
        }
        replaceFragment(new Fragment_ChiTietSanPham(sanPham));
    }
    public void displayToast(String message) {
        if(toast != null)
            toast.cancel();
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}