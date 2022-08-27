package com.datn.quanlybanhang.fragment.baocao;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.datn.quanlybanhang.adapter.BCKhachNoAdapterRecycler;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.KhoHang;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;


public class FragmentBaoCaoGhiNo extends Fragment {
    List<KhoHang> khoHangList = new ArrayList<>();
    List<KhoHang> khoHangListSearch = new ArrayList<>();
    EditText editText;
    RecyclerView recyclerView;
    Spinner spinner;
    MySQLiteHelper database;
    String search = "";
    BCKhachNoAdapterRecycler bcKhachNoAdapterRecycler;
    List<String> listMonth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ghi_no, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyler_bao_caokhachno);
        spinner = view.findViewById(R.id.spinnerKhachhangBaoCao);
        editText = view.findViewById(R.id.edit_khachhang_search_baocao);
        listMonth = getMonth();
        if(getContext()==null)return;
        database= new MySQLiteHelper(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,listMonth);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        xulyKhachHang();
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

    void xulyKhachHang(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String month  ;
                String str;
                khoHangList.clear();
                String textDate ;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy", new Locale("vi", "VN"));

                month = listMonth.get(i).toLowerCase().substring(listMonth.get(i).length() - 2, listMonth.get(i).length());
                textDate = simpleDateFormat.format(new Date(System.currentTimeMillis())) + month;
                str = "Select KHACHHANG_HOTEN,KHACHHANG_SODT,sum(HOADON_TRIGIA)" +
                        " From KHACHHANG,HOADON " +
                        "Where KHACHHANG_Id = HOADON_MAKH and strftime('%Y%m',HOADON_NGAYHD) = '"+textDate+ "' and HOADON_NO = 0"+
                        " Group By KHACHHANG_HOTEN,KHACHHANG_SODT  ";

                Cursor cursor =  database.execSQLSelect(str,database.getReadableDatabase());
                if(cursor.moveToFirst()) {
                    do {
                        Log.i("cuonghi",""+cursor.getString(0));
                        khoHangList.add(new KhoHang(
                                cursor.getString(0)+"",cursor.getString(1),1,1,cursor.getLong(2)));
                    } while (cursor.moveToNext());
                }
                database.close();
                bcKhachNoAdapterRecycler = new BCKhachNoAdapterRecycler(khoHangList);
                recyclerView.setAdapter(bcKhachNoAdapterRecycler);
                bcKhachNoAdapterRecycler.notifyDataSetChanged();
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
                if(search.isEmpty()){
                    bcKhachNoAdapterRecycler = new BCKhachNoAdapterRecycler(khoHangList);
                    recyclerView.setAdapter(bcKhachNoAdapterRecycler);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText.setOnFocusChangeListener((view, b) -> {
            if (getContext() == null) return;
            if (view == editText) {
                if (!b) {
                        khoHangListSearch.clear();
                        for(KhoHang khoHang: khoHangList){
                            if(khoHang.getMaKho().contains(search)||
                                    khoHang.getMaKho().toLowerCase().contains(search)||
                                    removeAccent(khoHang.getMaKho()).toLowerCase().contains(search)||
                                    removeAccent(khoHang.getMaKho()) .contains(search))
                                khoHangListSearch.add(khoHang);
                        }
                        bcKhachNoAdapterRecycler = new BCKhachNoAdapterRecycler(khoHangListSearch);
                        recyclerView.setAdapter(bcKhachNoAdapterRecycler);
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