package com.dabee.emergencymedicalservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import androidx.viewpager2.widget.ViewPager2;


import android.os.Bundle;

import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kakao.util.maps.helper.Utility;

import net.daum.mf.map.api.MapView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class MainActivity extends AppCompatActivity {




    TabLayout tabLayout;
    ViewPager2 pager;
    PagerAdapter adapter;
    String[] tabTitles= new String[]{"동물 약국","동물 병원"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);



        // 키 해시값을 얻어오는 기능을 가진 클래스에게 디버그용 키해시값 얻어오기
        String keyHash= Utility.getKeyHash(this);
        Log.d("TAG1",keyHash);


        tabLayout = findViewById(R.id.tab_layout);
        pager = findViewById(R.id.pager);
        adapter = new PagerAdapter(this);
        pager.setAdapter(adapter);


        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {


                tab.setText(tabTitles[position]);
                switch (position){
                    case 0:
                        tab.setIcon(R.drawable.free_icon_animal_clinic1);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.free_icon_animal_clinic3);
                        break;
                }
            }
        });
        mediator.attach();
    }


}








