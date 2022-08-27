
package com.datn.quanlybanhang.fragment.hoadon;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.activityy.ActivityThongTin;
import com.datn.quanlybanhang.adapter.HoaDonAdapterRecycler;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.fragment.FragmentXemThem;
import com.datn.quanlybanhang.model.HoaDon;
import com.datn.quanlybanhang.model.KhachHang;
import com.datn.quanlybanhang.myinterface.IClickItemListenerRecycer;

import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class FragmentHoaDon extends Fragment implements IClickItemListenerRecycer<HoaDon> {
    RecyclerView recyclerViewHoaDon;
    List<HoaDon> hoaDonList;
    List<HoaDon> hoaDonListQuery = new ArrayList<>();
    HoaDonAdapterRecycler hoaDonAdapterRecycler;
    MySQLiteHelper database;
    HoaDon selectHoaDon;
    TextView textViewHoaDonFilter;
    ImageView imageViewSort;
    Spinner spinner;
    Toast toast;
    List<String> listSpiner;
    Fragment_ChiTietHoaDon fragment_chiTietHoaDon;
    int i = 1;
    String search = "";
    EditText editText;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewHoaDonFilter = view.findViewById(R.id.textHoaDonFilter);
        imageViewSort = view.findViewById(R.id.sort_img_hoadon);
        recyclerViewHoaDon = view.findViewById(R.id.recyclerHoaDon);
        if (getContext() == null) return;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewHoaDon.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        recyclerViewHoaDon.addItemDecoration(dividerItemDecoration);
        database = new MySQLiteHelper(getContext());
        editText = view.findViewById(R.id.search_hoadon);
        spinner = view.findViewById(R.id.spinnerHoaDonFilter);
        imageViewSort.setImageResource(R.drawable.ic_baseline_sort_24);
        hoaDonList = database.getListHoaDon();
        hoaDonAdapterRecycler = new HoaDonAdapterRecycler(getContext(), hoaDonList, this);
        recyclerViewHoaDon.setAdapter(hoaDonAdapterRecycler);
        xuLySearch();
        xuLySort();
    }


    private void xuLySort() {
        listSpiner = getMonth();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listSpiner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textItemSprinner = listSpiner.get(i).toLowerCase();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM", new Locale("vi", "VN"));
                hoaDonList = database.getListHoaDon();
                if (textItemSprinner.equals("tất cả")) {
                    hoaDonAdapterRecycler = new HoaDonAdapterRecycler(getContext(), hoaDonList, FragmentHoaDon.this);
                    recyclerViewHoaDon.setAdapter(hoaDonAdapterRecycler);
                    hoaDonAdapterRecycler.notifyDataSetChanged();
                } else if (textItemSprinner.equals("nợ")) {
                    hoaDonListQuery.clear();
                    for (HoaDon hoaDon : hoaDonList)
                        if (hoaDon.getHoaDonNo() == 0)
                            hoaDonListQuery.add(hoaDon);
                    hoaDonList.clear();
                    hoaDonList.addAll(hoaDonListQuery);
                    hoaDonAdapterRecycler = new HoaDonAdapterRecycler(getContext(), hoaDonList, FragmentHoaDon.this);
                    recyclerViewHoaDon.setAdapter(hoaDonAdapterRecycler);
                    hoaDonAdapterRecycler.notifyDataSetChanged();
                } else {
                    hoaDonListQuery.clear();
                    for (HoaDon hoaDon : hoaDonList) {
                        Timestamp timestamp = Timestamp.valueOf(hoaDon.getNgayHD());
                        if (simpleDateFormat.format(timestamp.getTime()).equals(textItemSprinner) && hoaDon.getHoaDonNo() == 1)
                            hoaDonListQuery.add(hoaDon);
                    }
                    hoaDonList.clear();
                    hoaDonList.addAll(hoaDonListQuery);
                    hoaDonAdapterRecycler = new HoaDonAdapterRecycler(getContext(), hoaDonList, FragmentHoaDon.this);
                    recyclerViewHoaDon.setAdapter(hoaDonAdapterRecycler);
                    hoaDonAdapterRecycler.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        imageViewSort.setOnClickListener(view -> {
            if (i == 1) {
                displayToast("Sắp xếp giảm dần");
                imageViewSort.setImageResource(R.drawable.ic_baseline_arrow_downward_24);
                Collections.sort(hoaDonList, (hoaDon, hoaDon1) -> {
                    if (hoaDon1.getTriGia() - hoaDon.getTriGia() == 0)
                        return 0;
                    else if (hoaDon1.getTriGia() - hoaDon.getTriGia() > 0)
                        return 1;
                    else
                        return -1;
                });
                i = 2;
            } else if (i == 2) {
                displayToast("Sắp xếp tăng dần");
                imageViewSort.setImageResource(R.drawable.ic_baseline_arrow_upward_24);
                Collections.sort(hoaDonList, (hoaDon, hoaDon1) -> {
                    if (hoaDon.getTriGia() - hoaDon1.getTriGia() == 0)
                        return 0;
                    else if (hoaDon.getTriGia() - hoaDon1.getTriGia() > 0)
                        return 1;
                    else
                        return -1;
                });
                i = 3;
            } else if (i == 3) {
                displayToast("Sắp xếp mặc định");
                imageViewSort.setImageResource(R.drawable.ic_baseline_sort_24);
                hoaDonList.clear();
                hoaDonList.addAll(database.getListHoaDon());
                spinner.setSelection(0);
                i = 1;
            }
            hoaDonAdapterRecycler.notifyDataSetChanged();
        });
    }

    private void xuLySearch() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                search = charSequence.toString();
                if (search.isEmpty()) {
                    hoaDonList = database.getListHoaDon();
                    hoaDonAdapterRecycler = new HoaDonAdapterRecycler(getContext(), hoaDonList, FragmentHoaDon.this);
                    recyclerViewHoaDon.setAdapter(hoaDonAdapterRecycler);
                    spinner.setSelection(0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editText.setOnFocusChangeListener((view, b) -> {
            if (getContext() == null) return;
            if(view == editText){
                if (!b) {
                    hoaDonListQuery.clear();
                    for (HoaDon hoaDon : hoaDonList) {
                        KhachHang khachHang = database.getKhachHang(hoaDon.getMaKH());
                        if (khachHang.getTenKH().contains(search) ||
                                khachHang.getTenKH().toLowerCase().contains(search) ||
                                removeAccent(khachHang.getTenKH()).contains(search) ||
                                removeAccent(khachHang.getTenKH()).toLowerCase().contains(search) ||
                                (hoaDon.getTriGia() + "").contains(search))
                            hoaDonListQuery.add(hoaDon);
                    }
                    if (!hoaDonListQuery.isEmpty())
                        hoaDonList = hoaDonListQuery;
                    else
                        hoaDonList.clear();

                    hoaDonAdapterRecycler = new HoaDonAdapterRecycler(getContext(), hoaDonList, FragmentHoaDon.this);
                    recyclerViewHoaDon.setAdapter(hoaDonAdapterRecycler);
                    hoaDonAdapterRecycler.notifyDataSetChanged();
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                            hideSoftInputFromWindow(editText.getWindowToken(), 0);
                } else {
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                            showSoftInput(editText, InputMethodManager.SHOW_FORCED);
                    editText.setText("");
                }
            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();

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
        bundle.putSerializable("BundleHoaDon", hoaDon);
        intent.putExtra("Fragment", bundle);
        if (getActivity() != null)
            getActivity().startActivity(intent);
    }

    public void displayToast(String message) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    List<String> getMonth() {
        List<String> list = new ArrayList<>();
        list.add("Tất cả");
        list.add("Nợ");
        list.add("Tháng 1");
        list.add("Tháng 2");
        list.add("Tháng 3");
        list.add("Tháng 4");
        list.add("Tháng 5");
        list.add("Tháng 6");
        list.add("Tháng 7");
        list.add("Tháng 8");
        list.add("Tháng 9");
        list.add("Tháng 10");
        list.add("Tháng 11");
        list.add("Tháng 12");
        return list;
    }

    public String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}