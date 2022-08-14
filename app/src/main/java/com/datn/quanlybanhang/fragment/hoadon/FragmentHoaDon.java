package com.datn.quanlybanhang.fragment.hoadon;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.activityy.ActivityThongTin;
import com.datn.quanlybanhang.adapter.HoaDonAdapterRecycler;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.fragment.FragmentXemThem;
import com.datn.quanlybanhang.model.HoaDon;
import com.datn.quanlybanhang.myinterface.IClickItemListenerRecycer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentHoaDon extends Fragment implements IClickItemListenerRecycer<HoaDon> {

    RecyclerView recyclerViewHoaDon;
    List<HoaDon> hoaDonList;
    List<HoaDon> hoaDonListQuery = new ArrayList<>();
    HoaDonAdapterRecycler hoaDonAdapterRecycler;
    MySQLiteHelper database;
    HoaDon selectHoaDon;
    TextView textViewHoaDonFilter;
    ImageView imageViewSort;
    SearchView searchView;
    Spinner spinner;
    Fragment_ChiTietHoaDon fragment_chiTietHoaDon;
    int i = 3;
    String textItemSprinner;

    public FragmentHoaDon() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hoa_don, container, false);
    }
    
    
    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = view.findViewById(R.id.search_viewHoaDOn);
        textViewHoaDonFilter = view.findViewById(R.id.textHoaDonFilter);
        imageViewSort = view.findViewById(R.id.sort_img_hoadon);
        recyclerViewHoaDon = view.findViewById(R.id.recyclerHoaDon);
        if(getContext()==null) return;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewHoaDon.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL);
        recyclerViewHoaDon.addItemDecoration(dividerItemDecoration);
        database = new MySQLiteHelper(getContext());
        hoaDonList = database.getListHoaDon();
        hoaDonAdapterRecycler = new HoaDonAdapterRecycler(this.getContext(),hoaDonList,this);
        recyclerViewHoaDon.setAdapter(hoaDonAdapterRecycler);
        registerForContextMenu(recyclerViewHoaDon);
        spinner = view.findViewById(R.id.spinnerHoaDonFilter);
        xuLySearch();
        xuLySort();
    }


    private void xuLySort() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.arrstr_day, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textItemSprinner = adapterView.getItemAtPosition(i).toString();
                SimpleDateFormat simpleDateFormat =new SimpleDateFormat("EEEE", new Locale("en", "UK"));
                if(textItemSprinner.equals("Chủ nhật")){
                    hoaDonListQuery.clear();
                    for(HoaDon hoaDon : hoaDonList)
                        if(simpleDateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD()))).equals("Sunday"))
                            hoaDonListQuery.add(hoaDon);
                    hoaDonAdapterRecycler = new HoaDonAdapterRecycler(getContext(),hoaDonListQuery,FragmentHoaDon.this);
                    recyclerViewHoaDon.setAdapter(hoaDonAdapterRecycler);
                }
                else if(textItemSprinner.equals("Nợ")){
                    hoaDonListQuery.clear();
                    for(HoaDon hoaDon : hoaDonList)
                        if(hoaDon.getHoaDonNo() == 0)
                            hoaDonListQuery.add(hoaDon);
                    hoaDonAdapterRecycler = new HoaDonAdapterRecycler(getContext(),hoaDonListQuery,FragmentHoaDon.this);
                    recyclerViewHoaDon.setAdapter(hoaDonAdapterRecycler);
                }
                else if(textItemSprinner.equals("Thứ hai")){
                    hoaDonListQuery.clear();
                    for(HoaDon hoaDon : hoaDonList)
                        if(simpleDateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD()))).equals("Monday"))
                            hoaDonListQuery.add(hoaDon);
                    hoaDonAdapterRecycler = new HoaDonAdapterRecycler(getContext(),hoaDonListQuery,FragmentHoaDon.this);
                    recyclerViewHoaDon.setAdapter(hoaDonAdapterRecycler);
                }
                else if(textItemSprinner.equals("Thứ ba")){
                    hoaDonListQuery.clear();
                    for(HoaDon hoaDon : hoaDonList)
                        if(simpleDateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD()))).equals("Tuesday"))
                            hoaDonListQuery.add(hoaDon);
                    hoaDonAdapterRecycler = new HoaDonAdapterRecycler(getContext(),hoaDonListQuery,FragmentHoaDon.this);
                    recyclerViewHoaDon.setAdapter(hoaDonAdapterRecycler);
                }
                else if(textItemSprinner.equals("Thứ tư")){
                    hoaDonListQuery.clear();
                    for(HoaDon hoaDon : hoaDonList)
                        if(simpleDateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD()))).equals("Wednesday"))
                            hoaDonListQuery.add(hoaDon);
                    hoaDonAdapterRecycler = new HoaDonAdapterRecycler(getContext(),hoaDonListQuery,FragmentHoaDon.this);
                    recyclerViewHoaDon.setAdapter(hoaDonAdapterRecycler);
                }

                else if(textItemSprinner.equals("Thứ năm")){
                    hoaDonListQuery.clear();
                    for(HoaDon hoaDon : hoaDonList)
                        if(simpleDateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD()))).equals("Thursday"))
                            hoaDonListQuery.add(hoaDon);
                    hoaDonAdapterRecycler = new HoaDonAdapterRecycler(getContext(),hoaDonListQuery,FragmentHoaDon.this);
                    recyclerViewHoaDon.setAdapter(hoaDonAdapterRecycler);
                }
                else if(textItemSprinner.equals("Thứ sáu")){
                    hoaDonListQuery.clear();
                    for(HoaDon hoaDon : hoaDonList)
                        if(simpleDateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD()))).equals("Friday"))
                            hoaDonListQuery.add(hoaDon);
                    hoaDonAdapterRecycler = new HoaDonAdapterRecycler(getContext(),hoaDonListQuery,FragmentHoaDon.this);
                    recyclerViewHoaDon.setAdapter(hoaDonAdapterRecycler);
                }
                else if(textItemSprinner.equals("Thứ bảy")){
                    hoaDonListQuery.clear();
                    for(HoaDon hoaDon : hoaDonList)
                        if(simpleDateFormat.format(new Date(Long.parseLong(hoaDon.getNgayHD()))).equals("Saturday"))
                            hoaDonListQuery.add(hoaDon);
                    hoaDonAdapterRecycler = new HoaDonAdapterRecycler(getContext(),hoaDonListQuery,FragmentHoaDon.this);
                    recyclerViewHoaDon.setAdapter(hoaDonAdapterRecycler);
                }
                else if(textItemSprinner.equals("Tất cả")){
                    hoaDonAdapterRecycler = new HoaDonAdapterRecycler(getContext(),hoaDonList,FragmentHoaDon.this);
                    recyclerViewHoaDon.setAdapter(hoaDonAdapterRecycler);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        imageViewSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i==1){
                    imageViewSort.setImageResource(R.drawable.ic_baseline_arrow_downward_24);
                    Collections.sort(hoaDonList, new Comparator<HoaDon>() {
                        @Override
                        public int compare(HoaDon hoaDon, HoaDon hoaDon1) {
                                if(hoaDon.getTriGia() - hoaDon1.getTriGia()==0)
                                    return 0;
                                else if(hoaDon.getTriGia() - hoaDon1.getTriGia()>0)
                                    return 1;
                                else
                                    return -1;
                        }
                    });
                    i=2;
                }
                else if(i==2){
                    imageViewSort.setImageResource(R.drawable.ic_baseline_arrow_upward_24);
                    Collections.sort(hoaDonList, new Comparator<HoaDon>() {
                        @Override
                        public int compare(HoaDon hoaDon, HoaDon hoaDon1) {
                            if(hoaDon1.getTriGia() - hoaDon.getTriGia()==0)
                                return 0;
                            else if(hoaDon1.getTriGia() - hoaDon.getTriGia()>0)
                                return 1;
                            else
                                return -1;

                        }
                    });
                    i=3;
                }
                else if(i==3){
                    imageViewSort.setImageResource(R.drawable.ic_baseline_sort_24);
                    hoaDonList.clear();
                    hoaDonList.addAll(database.getListHoaDon());
                    i=1;
                }
                hoaDonAdapterRecycler.notifyDataSetChanged();
            }
        });
    }

    private void xuLySearch() {
        if(getActivity()==null) return;
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(getContext()==null)return;
                if (view == searchView) {
                    if (b) {
                        // Open keyboard
                        ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                                showSoftInput(searchView, InputMethodManager.SHOW_FORCED);
                    } else {
                        // Close keyboard
                        ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                                hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                    }
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hoaDonAdapterRecycler.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                hoaDonAdapterRecycler.getFilter().filter(newText);
                return false;
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        i=3;
        imageViewSort.setImageResource(R.drawable.ic_baseline_sort_24);
        searchView.setIconified(true);
        hoaDonList.clear();
        hoaDonList.addAll(database.getListHoaDon());
        hoaDonAdapterRecycler = new HoaDonAdapterRecycler(getContext(),hoaDonList,this);
        recyclerViewHoaDon.setAdapter(hoaDonAdapterRecycler);
        spinner.setSelection(0);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(getActivity()==null)return;
        getActivity().getMenuInflater().inflate(R.menu.menu_model,menu);
        MenuItem menuItemTT = menu.findItem(R.id.menu_model_dathanhtoan);
        MenuItem menuItemXoa = menu.findItem(R.id.menu_model_xoa);
        MenuItem menuItemSua = menu.findItem(R.id.menu_model_sua);
        menuItemTT.setVisible(true);
        menuItemTT.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.nav_model_thanhtoan);
                builder.setMessage("Bạn muốn thanh toán ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectHoaDon.setHoaDonNo(1);
                        if(database.updateHoaDon(selectHoaDon)>0)
                            Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                        hoaDonAdapterRecycler.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });
        menuItemXoa.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.nav_model_xoa);
                builder.setMessage("Bạn có chắc không ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.deleteHoaDon(selectHoaDon);
                        hoaDonList.remove(selectHoaDon);
                        hoaDonAdapterRecycler.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });
        menuItemSua.setVisible(false);
    }

    @Override
    public void onClickItemModel(HoaDon hoaDon) {
        selectHoaDon = hoaDon;
    }

    @Override
    public void onClickChiTietModel(HoaDon hoaDon) {
        Intent intent = new Intent(getContext(), ActivityThongTin.class);
        intent.putExtra("Data", FragmentXemThem.ACT_CHITIETHOADON);
        fragment_chiTietHoaDon = new Fragment_ChiTietHoaDon(hoaDon);
        Bundle bundle = new Bundle();
        bundle.putSerializable("BundleHoaDon",hoaDon);
        intent.putExtra("Fragment",bundle);
        if(getActivity()!=null)
        getActivity().startActivity(intent);
    }

}