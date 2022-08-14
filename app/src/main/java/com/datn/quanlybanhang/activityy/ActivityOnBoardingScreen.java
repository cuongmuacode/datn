package com.datn.quanlybanhang.activityy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.datn.quanlybanhang.R;
import com.datn.quanlybanhang.adapter.ViewPagerOnBoarding;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import me.relex.circleindicator.CircleIndicator3;

public class ActivityOnBoardingScreen extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private TextView textViewSkip;
    private TextView textViewNext;
    private CircleIndicator3 circleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_screen);
        viewPager2 = findViewById(R.id.viewpagerOnBoarding);
        textViewSkip = findViewById(R.id.textSkip);
        textViewNext = findViewById(R.id.tv_next);
        circleIndicator = findViewById(R.id.circleOnBoarding);
        ViewPagerOnBoarding viewPagerOnBoarding = new ViewPagerOnBoarding(this);
        viewPager2.setAdapter(viewPagerOnBoarding);
        circleIndicator.setViewPager(viewPager2);


        textViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager2.getCurrentItem()<3)
                   viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
            }
        });

        textViewSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityOnBoardingScreen.this,ActivityLoginn.class);
                setDataCache(60);
                startActivity(intent);
                finish();
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position == 3){
                    textViewSkip.setVisibility(View.INVISIBLE);
                    textViewNext.setVisibility(View.INVISIBLE);
                    circleIndicator.setVisibility(View.INVISIBLE);
                }
                else{
                    textViewSkip.setVisibility(View.VISIBLE);
                    textViewNext.setVisibility(View.VISIBLE);
                    circleIndicator.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void setDataCache(int i){
        FileOutputStream fileOutputStream;
        File file;
        try{
            file = new File(getCacheDir(),"stateOnBoarding.txt");
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(i);
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}