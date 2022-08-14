 package com.datn.quanlybanhang.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.activityy.ActivityThongTin;

import com.datn.quanlybanhang.adapter.MatHangAdapterRecycler;

import com.datn.quanlybanhang.database.MySQLiteHelper;

import com.datn.quanlybanhang.fragment.hoadon.FragmentAddHoaDon;
import com.datn.quanlybanhang.model.SanPham;
import com.datn.quanlybanhang.myinterface.IClickItemListenerRecycer;
import com.datn.quanlybanhang.myinterface.IClickItemSanPham;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;


 public class FragmentBanHang extends Fragment implements IClickItemListenerRecycer<SanPham>{
    private RecyclerView recyclerViewBanHang;
    private MatHangAdapterRecycler matHangAdapterRecycler;
    private EditText editText;
    private ImageView imageView;
    private Spinner spinner;
    List<SanPham> filterListSanPham = new ArrayList<>();
    List<SanPham> sanPhamSpinners = new ArrayList<>();
    MySQLiteHelper database;
    List<SanPham> listSanPham = new ArrayList<>();
    String textItemSprinner;
    boolean check = true;
    private FrameLayout redCircle;
    private TextView countTextView;
    public static boolean checkState = true;
    public static int countSanPham = 1;
    public static IClickItemSanPham iClickItemSanPham = new FragmentAddHoaDon();

    public FragmentBanHang() {
        // Required empty public constructor
    }
     public FragmentBanHang(boolean b) {
        check = b;
    }


     ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()== Activity.RESULT_OK) {
                iClickItemSanPham = new FragmentAddHoaDon();
                countSanPham = 0;
                countSanPhamm();
                countSanPham = 1;
                checkState = true;

            }
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ban_hang,container,false);
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.edit_sanpham_search_banhang);
        spinner = view.findViewById(R.id.spinnerBanHang);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view == editText) {
                    if(getContext()==null) return;
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
        imageView = view.findViewById(R.id.imageview_sanpham_sort_banhang);
        recyclerViewBanHang = view.findViewById(R.id.recyclerBanHang);
        if(this.getContext()!=null) {
            recyclerViewBanHang.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
            recyclerViewBanHang.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
            database = new MySQLiteHelper(getContext());
        }
        listSanPham = database.getListSanPham();
        matHangAdapterRecycler = new MatHangAdapterRecycler(this,listSanPham);
        recyclerViewBanHang.setAdapter(matHangAdapterRecycler);
        xulyEditText();
        xuLySort();
    }




    @Override
    public void onStart() {
        super.onStart();
        listSanPham.clear();
        listSanPham.addAll(database.getListSanPham());
        matHangAdapterRecycler.notifyDataSetChanged();
        spinner.setSelection(0);
        if(countSanPham<=0){
            countSanPham = 0;
            countSanPhamm();
            countSanPham = 1;
            checkState = true;
        }
        if(!checkState)
           countSanPhamm();
    }


     @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
         inflater.inflate(R.menu.custom_menu_shop,menu);
    }

     @Override
     public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable  ContextMenu.ContextMenuInfo menuInfo) {
         super.onCreateContextMenu(menu, v, menuInfo);
     }

     @Override
     public void onPrepareOptionsMenu(@NonNull Menu menu) {
         super.onPrepareOptionsMenu(menu);
         MenuItem item = menu.findItem(R.id.shopAdd);
         MenuItem item1 = menu.findItem(R.id.xoaDonHang);

         if(!check) {
             item.setVisible(false);
             item1.setVisible(false);
         }
         FrameLayout frameLayoutRoot = (FrameLayout) item.getActionView();
         redCircle =  frameLayoutRoot.findViewById(R.id.frame_view_cricler);
         countTextView = frameLayoutRoot.findViewById(R.id.textViewLayoutAdd);
         frameLayoutRoot.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getContext(), ActivityThongTin.class);
                 intent.putExtra("Data", FragmentXemThem.ACT_SHOP);
                 Bundle bundle = new Bundle();
                 bundle.putSerializable("BundleFragment",(FragmentAddHoaDon) iClickItemSanPham);
                 intent.putExtra("Fragment",bundle);
                 intentActivityResultLauncher.launch(intent);

             }
         });

         item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
             @Override
             public boolean onMenuItemClick(MenuItem item) {
                 iClickItemSanPham = new FragmentAddHoaDon();
                 countSanPham = 0;
                 countSanPhamm();
                 checkState = true;
                 countSanPham = 1;
                 return true;
             }
         });
         if (countSanPham==1&&checkState) {
             countSanPham = 0;
             countSanPhamm();
             countSanPham = 1;
         }
         else
             countSanPhamm();
 
    }

     public void countSanPhamm() {
        if(countTextView==null) return;
         String str;
        if(countSanPham<100&&countSanPham>0) {
            str = countSanPham + "";
            countTextView.setText(str);
            redCircle.setVisibility(View.VISIBLE);
        }
         else if(countSanPham==0)
             redCircle.setVisibility(View.GONE);
         else {
            countTextView.setText(":)");
            redCircle.setVisibility(View.VISIBLE);
        }
    }

     @Override
    public void onClickItemModel(SanPham sanPham) {
        if(sanPham.getSoLuongSP()<=0) Toast.makeText(getContext(),getResources().getString(R.string.toast_hethang),Toast.LENGTH_SHORT).show();
        else {
            iClickItemSanPham.onClickSanPham(sanPham);
            countSanPhamm();
            checkState = false;
        }
    }

    @Override
    public void onClickChiTietModel(SanPham sanPham) {

    }



    private void xulyEditText() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.arrstr_danhmuc, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textItemSprinner = adapterView.getItemAtPosition(i).toString();
                listSanPham.clear();
                listSanPham = database.getListSanPham();

               if(textItemSprinner.equals("Trái cây")){
                    sanPhamSpinners.clear();
                    for(SanPham sanPham : listSanPham)
                        if(sanPham.getLoaiSP().equals("Trái cây"))
                            sanPhamSpinners.add(sanPham);
                        listSanPham.clear();
                        listSanPham.addAll(sanPhamSpinners);
                    matHangAdapterRecycler = new MatHangAdapterRecycler(FragmentBanHang.this,listSanPham);
                    recyclerViewBanHang.setAdapter(matHangAdapterRecycler);
                }
                else if(textItemSprinner.equals("Đồ ăn vặt")) {
                    sanPhamSpinners.clear();
                    for (SanPham sanPham : listSanPham)
                        if (sanPham.getLoaiSP().equals("Đồ ăn vặt"))
                            sanPhamSpinners.add(sanPham);
                   listSanPham.clear();
                   listSanPham.addAll(sanPhamSpinners);
                   matHangAdapterRecycler = new MatHangAdapterRecycler(FragmentBanHang.this,listSanPham);
                   recyclerViewBanHang.setAdapter(matHangAdapterRecycler);
               }
                else if(textItemSprinner.equals("Rau củ")) {
                    sanPhamSpinners.clear();
                    for (SanPham sanPham : listSanPham)
                        if (sanPham.getLoaiSP().equals("Rau củ"))
                            sanPhamSpinners.add(sanPham);
                   listSanPham.clear();
                   listSanPham.addAll(sanPhamSpinners);
                   matHangAdapterRecycler = new MatHangAdapterRecycler(FragmentBanHang.this,listSanPham);
                   recyclerViewBanHang.setAdapter(matHangAdapterRecycler);
               }
                else if(textItemSprinner.equals("Thịt")) {
                    sanPhamSpinners.clear();
                    for (SanPham sanPham : listSanPham)
                        if (sanPham.getLoaiSP().equals("Thịt"))
                            sanPhamSpinners.add(sanPham);
                   listSanPham.clear();
                   listSanPham.addAll(sanPhamSpinners);
                   matHangAdapterRecycler = new MatHangAdapterRecycler(FragmentBanHang.this,listSanPham);
                   recyclerViewBanHang.setAdapter(matHangAdapterRecycler);
               }
                else if(textItemSprinner.equals("Cá")) {
                    sanPhamSpinners.clear();
                    for (SanPham sanPham : listSanPham)
                        if (sanPham.getLoaiSP().equals("Cá"))
                            sanPhamSpinners.add(sanPham);
                   listSanPham.clear();
                   listSanPham.addAll(sanPhamSpinners);
                   matHangAdapterRecycler = new MatHangAdapterRecycler(FragmentBanHang.this,listSanPham);
                   recyclerViewBanHang.setAdapter(matHangAdapterRecycler);
               }
                else if(textItemSprinner.equals("Gia vị")) {
                    sanPhamSpinners.clear();
                    for (SanPham sanPham : listSanPham)
                        if (sanPham.getLoaiSP().equals("Gia vị"))
                            sanPhamSpinners.add(sanPham);
                   listSanPham.clear();
                   listSanPham.addAll(sanPhamSpinners);
                   matHangAdapterRecycler = new MatHangAdapterRecycler(FragmentBanHang.this,listSanPham);
                   recyclerViewBanHang.setAdapter(matHangAdapterRecycler);
               }
                else if(textItemSprinner.equals("Đồ uống")) {
                    sanPhamSpinners.clear();
                    for (SanPham sanPham : listSanPham)
                        if (sanPham.getLoaiSP().equals("Đồ uống"))
                            sanPhamSpinners.add(sanPham);
                   listSanPham.clear();
                   listSanPham.addAll(sanPhamSpinners);
                   matHangAdapterRecycler = new MatHangAdapterRecycler(FragmentBanHang.this,listSanPham);
                   recyclerViewBanHang.setAdapter(matHangAdapterRecycler);
               }
                else if(textItemSprinner.equals("Tất cả mặt hàng")){
                    matHangAdapterRecycler = new MatHangAdapterRecycler(FragmentBanHang.this,listSanPham);
                    recyclerViewBanHang.setAdapter(matHangAdapterRecycler);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                    matHangAdapterRecycler = new MatHangAdapterRecycler(FragmentBanHang.this,listSanPham);
                    recyclerViewBanHang.setAdapter(matHangAdapterRecycler);

                }
                else{
                    filterListSanPham.clear();
                    for(SanPham sanPham : listSanPham){
                        if(sanPham.getTenSP().contains(search)||
                                (sanPham.getGiaSP()+"").contains(search))
                            filterListSanPham.add(sanPham);
                    }
                    recyclerViewBanHang.setAdapter(new MatHangAdapterRecycler(FragmentBanHang.this,filterListSanPham));
                }
                matHangAdapterRecycler.notifyDataSetChanged();
            }
        });
    }

    private void xuLySort() {
        imageView.setOnClickListener(new View.OnClickListener() {
            int i = 1;
            @Override
            public void onClick(View view) {
                if(i==1){
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
                else if(i==2) {
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
                else if(i == 3){
                    imageView.setImageResource(R.drawable.baseline_sort_by_alpha_24);
                    listSanPham.clear();
                    listSanPham.addAll(database.getListSanPham());
                    spinner.setSelection(0);
                    i=1;
                }
                matHangAdapterRecycler.notifyDataSetChanged();
            }
        });
    }

}