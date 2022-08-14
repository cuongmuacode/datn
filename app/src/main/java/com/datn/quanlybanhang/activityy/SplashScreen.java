package com.datn.quanlybanhang.activityy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(getData()==0)
                    startActivity(new Intent(SplashScreen.this,ActivityOnBoardingScreen.class));
                else
                    startActivity(new Intent(SplashScreen.this,ActivityLoginn.class));
                finish();
            }
        },0);
    }
    public int getData(){
        int i = 0;
        try {
            File file = new File(getCacheDir(),"stateOnBoarding.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            i = fileInputStream.read();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }
}