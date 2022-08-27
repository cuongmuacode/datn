package com.datn.quanlybanhang.fragment.baocao;

import android.content.Context;
import android.database.Cursor;
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
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.adapter.BCSanPhamNhieuAdapterRecycler;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.KhoHang;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class Fragment_BCSanPhamBanNhieu extends Fragment {

    List<KhoHang> khoHangList = new ArrayList<>();
    List<KhoHang> khoHangListSearch = new ArrayList<>();
    EditText editText;
    RecyclerView recyclerView;
    Spinner spinner;
    MySQLiteHelper database;
    BCSanPhamNhieuAdapterRecycler bcSanPhamNhieuAdapterRecycler;
    List<String> listMonth;
    String search;

    public Fragment_BCSanPhamBanNhieu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__b_c_san_pham_ban_nhieu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_SanPham_baocao);
        spinner = view.findViewById(R.id.spinnerSanPhamBaoCao);
        editText = view.findViewById(R.id.edit_sanpham_search_baocao);
        listMonth = getMonth();
        if (getContext() == null) return;
        ;
        database = new MySQLiteHelper(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listMonth);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        xulysanpham();
        xuLySearch();
    }

    private List<String> getMonth() {
        List<String> list = new ArrayList<>();
        list.add("Tháng 01");
        list.add("Tháng 02");
        list.add("Tháng 03");
        list.add("Tháng 04");
        list.add("Tháng 05");
        list.add("Tháng 06");
        list.add("Tháng 07");
        list.add("Tháng 08");
        list.add("Tháng 09");
        list.add("Tháng 10");
        list.add("Tháng 11");
        list.add("Tháng 12");
        return list;
    }

    void xulysanpham() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String month = "";
                String str = "";
                khoHangList.clear();
                String textDate = "";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy", new Locale("vi", "VN"));

                month = listMonth.get(i).toLowerCase().substring(listMonth.get(i).length() - 2, listMonth.get(i).length());
                textDate = simpleDateFormat.format(new Date(System.currentTimeMillis())) + month;
                str = "Select SANPHAM_TENSP,Sum(CTHD_SL) as SOLUONG " +
                        " From CTHD,SANPHAM,HOADON " +
                        "Where SANPHAM_MASP = CTHD_MASP and HOADON_SOHD = CTHD_SOHD and strftime('%Y%m',HOADON_NGAYHD) = '" + textDate + "' " +
                        " Group By SANPHAM_TENSP Order By 'SOLUONG' DESC LIMIT 10";


                Cursor cursor = database.execSQLSelect(str, database.getReadableDatabase());
                if (cursor.moveToFirst()) {
                    do {
                        KhoHang khoHang = new KhoHang(
                                cursor.getString(0), "", cursor.getInt(1), 1, 1);
                        khoHangList.add(khoHang);
                    } while (cursor.moveToNext());
                }
                database.close();
                bcSanPhamNhieuAdapterRecycler = new BCSanPhamNhieuAdapterRecycler(khoHangList, database);
                recyclerView.setAdapter(bcSanPhamNhieuAdapterRecycler);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
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
                    bcSanPhamNhieuAdapterRecycler = new BCSanPhamNhieuAdapterRecycler(khoHangList,database);
                    recyclerView.setAdapter(bcSanPhamNhieuAdapterRecycler);
                    bcSanPhamNhieuAdapterRecycler.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        editText.setOnFocusChangeListener((view, b) -> {
            if (getContext() == null) return;
            if(view == editText) {
                if(!b){
                    khoHangListSearch.clear();
                    for (KhoHang khoHang : khoHangList) {
                        if (khoHang.getMaKho().contains(search) ||
                                khoHang.getMaKho().toLowerCase().contains(search) ||
                                removeAccent(khoHang.getMaKho()).contains(search) ||
                                removeAccent(khoHang.getMaKho()).toLowerCase().contains(search)
                        )
                            khoHangListSearch.add(khoHang);
                    }
                    bcSanPhamNhieuAdapterRecycler = new BCSanPhamNhieuAdapterRecycler(khoHangListSearch, database);
                    recyclerView.setAdapter(bcSanPhamNhieuAdapterRecycler);
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                            hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    return;
                }
                editText.setText("");
                ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                        showSoftInput(editText, InputMethodManager.SHOW_FORCED);

            }
        });

    }
    public String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}