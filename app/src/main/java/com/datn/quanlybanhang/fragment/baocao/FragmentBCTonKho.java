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
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.adapter.BCTonKhoAdapterRecycler;
import com.datn.quanlybanhang.database.MySQLiteHelper;
import com.datn.quanlybanhang.model.KhoHang;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;


public class FragmentBCTonKho extends Fragment {

    EditText editText;
    RecyclerView recyclerView;
    TextView textView;
    MySQLiteHelper database;
    String search = "";
    List<KhoHang> tonKhoList = new ArrayList<>();
    List<KhoHang> tonKhoListQurey = new ArrayList<>();

    public FragmentBCTonKho() {
        // Required empty public constructor
    }

    BCTonKhoAdapterRecycler bcTonKhoAdapterRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b_c_ton_kho, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.textbaocaoTonkho);
        editText = view.findViewById(R.id.edit_tonkho_search_baocao);
        recyclerView = view.findViewById(R.id.reycler_baocaoTonKho);
        database = new MySQLiteHelper(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM", new Locale("vi", "VN"));
        String str = "Báo cáo tồn kho tháng " + simpleDateFormat.format(new Date(System.currentTimeMillis()));
        textView.setText(str);

        str = "Select SANPHAM_MASP,SANPHAM_TENSP,KHO_SOLUONG" +
                " From SANPHAM,KHO " +
                " Where SANPHAM_MASP = KHO_MASP ";

        Cursor cursor = database.execSQLSelect(str, database.getReadableDatabase());
        if (cursor.moveToFirst()) {
            do {
                Log.i("cuonghi", "" + cursor.getString(0));
                tonKhoList.add(new KhoHang(cursor.getString(0), cursor.getString(1), cursor.getInt(2), 1, 1));
            } while (cursor.moveToNext());
        }
        database.close();
        bcTonKhoAdapterRecycler = new BCTonKhoAdapterRecycler(tonKhoList, database);
        recyclerView.setAdapter(bcTonKhoAdapterRecycler);
        xuLyEditText();
    }

    void xuLyEditText() {
        editText.setOnFocusChangeListener((view, b) -> {
            if (getContext() == null) return;
            if (view == editText) {
                if (b) {
                    // Open keyboard
                    editText.setText("");
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                            showSoftInput(editText, InputMethodManager.SHOW_FORCED);
                } else {
                    // Close keyboard
                    tonKhoListQurey.clear();
                    for (KhoHang khoHang : tonKhoList) {
                        if (khoHang.getMaSP().contains(search) ||
                                khoHang.getMaSP().toLowerCase().contains(search) ||
                                khoHang.getMaSP().contains(search) ||
                                removeAccent(khoHang.getMaSP()).contains(search) ||
                                removeAccent(khoHang.getMaSP()).toLowerCase().contains(search))
                            tonKhoListQurey.add(khoHang);
                    }
                    bcTonKhoAdapterRecycler = new BCTonKhoAdapterRecycler(tonKhoListQurey, database);
                    recyclerView.setAdapter(bcTonKhoAdapterRecycler);

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
                search = charSequence.toString();
                if (search.isEmpty()) {
                    bcTonKhoAdapterRecycler = new BCTonKhoAdapterRecycler(tonKhoList,database);
                    recyclerView.setAdapter(bcTonKhoAdapterRecycler);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

}