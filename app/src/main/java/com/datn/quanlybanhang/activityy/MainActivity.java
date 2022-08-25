package com.datn.quanlybanhang.activityy;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.adapter.MyViewPager2Adapter;
import com.datn.quanlybanhang.fragment.FragmentXemThem;
import com.datn.quanlybanhang.model.NhanVien;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    public ViewPager2 mViewPager2;
    private BottomNavigationView  mBottomNavigationView;
    public static NhanVien nhanVien;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode()==10)
            MainActivity.this.finish();
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mViewPager2 = findViewById(R.id.viewpager2);
        MyViewPager2Adapter adapter = new MyViewPager2Adapter(this);
        mViewPager2.setAdapter(adapter);
        if(savedInstanceState==null) {
            mViewPager2.setOffscreenPageLimit(4);
            mViewPager2.setCurrentItem(1);
            mViewPager2.setCurrentItem(0);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_colse);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);

        NavigationView mNavigationView = findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        View view = mNavigationView.getHeaderView(0);
        CircleImageView circleImageView = view.findViewById(R.id.imgAvatar);
        TextView textHoTen = view.findViewById(R.id.hotenUser);
        TextView textEmail = view.findViewById(R.id.emailUser);
        TextView textSoDT = view.findViewById(R.id.soDTUser);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DataUser");
        nhanVien = (NhanVien) bundle.getSerializable("User");
        byte[] bytes = nhanVien.getByteImgs();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        circleImageView.setImageBitmap(bitmap);

        if(nhanVien.getQuyen()==1)
            textHoTen.setText(R.string.name_Dev);
        else
            textHoTen.setText(nhanVien.getHoTenNV());
        String str = "Email : "+nhanVien.getEmailNhanVien();
        textEmail.setText(str);
        str = "Số điện thoại : "+nhanVien.getSoDT();
        textSoDT.setText(str);

        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnItemSelectedListener(
                item -> {
                    int id = item.getItemId();
                    if(id == R.id.nav_banhang){
                        mViewPager2.setCurrentItem(0);
                        mBottomNavigationView.getMenu().findItem(R.id.nav_banhang).setChecked(true);
                    }
                    else if(id == R.id.nav_hoadon){
                        mViewPager2.setCurrentItem(1);
                        mBottomNavigationView.getMenu().findItem(R.id.nav_hoadon).setChecked(true);
                    }
                    else if(id == R.id.nav_baocao){
                        mViewPager2.setCurrentItem(2);
                        mBottomNavigationView.getMenu().findItem(R.id.nav_baocao).setChecked(true);
                    }
                    else if(id == R.id.nav_xemthem){
                        mViewPager2.setCurrentItem(3);
                        mBottomNavigationView.getMenu().findItem(R.id.nav_xemthem).setChecked(true);
                    }
                    return true;
                });
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        toolbar.setTitle(R.string.nav_banhang);
                        mBottomNavigationView.getMenu().findItem(R.id.nav_banhang).setChecked(true);
                        break;
                    case 1:
                        toolbar.setTitle(R.string.nav_hoadon);
                        mBottomNavigationView.getMenu().findItem(R.id.nav_hoadon).setChecked(true);
                        break;
                    case 2:
                        toolbar.setTitle(R.string.nav_baocao);
                        mBottomNavigationView.getMenu().findItem(R.id.nav_baocao).setChecked(true);
                        break;
                    case 3:
                        toolbar.setTitle(R.string.nav_xemthem);
                        mBottomNavigationView.getMenu().findItem(R.id.nav_xemthem).setChecked(true);
                        break;
                }
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        int id = item.getItemId();
//        if(id == R.id.nav_huongdan){
//            Intent intent = new Intent(this, ActivityThongTin.class);
//            intent.putExtra("Data", FragmentXemThem.ACT_HUONGDAN);
//            if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
//                mDrawerLayout.closeDrawer(GravityCompat.START);
//            startActivity(intent);
//        }
//        else if(id == R.id.nav_chiase) {
//            Log.i("","");
//        }
//         if(id == R.id.nav_gioithieu){
//            Intent intent = new Intent(this, ActivityThongTin.class);
//            intent.putExtra("Data", FragmentXemThem.ACT_GIOITHIEU);
//            if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
//                mDrawerLayout.closeDrawer(GravityCompat.START);
//            startActivity(intent);
//        }
         if(id == R.id.nav_dangxuat){
            Intent intent = new Intent(this, ActivityLoginn.class);
            if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
                mDrawerLayout.closeDrawer(GravityCompat.START);
            try {
                File file = new File(getCacheDir(), "User.txt");
                if (file.delete()) 
                    startActivity(intent);
                    finish();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(id == R.id.nav_doimatkhau){
            Intent intent = new Intent(this, ActivityThongTin.class);
            intent.putExtra("Data", FragmentXemThem.ACT_DOIMATKHAU);
            if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
                mDrawerLayout.closeDrawer(GravityCompat.START);
            launcher.launch(intent);

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        int id = mViewPager2.getCurrentItem();
        switch (id){
            case 3:
            case 2:
            case 1:
                mViewPager2.setCurrentItem(0); return;
        }
        super.onBackPressed();


    }


}