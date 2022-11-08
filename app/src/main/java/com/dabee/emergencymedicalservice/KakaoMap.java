package com.dabee.emergencymedicalservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.List;

public class KakaoMap extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_map);


        Intent secondIntent = getIntent();
        Double lat= secondIntent.getDoubleExtra("lat",0);
        Double lon= secondIntent.getDoubleExtra("lon",0);
        String name = secondIntent.getStringExtra("name");
        String str = secondIntent.getStringExtra("addr");




        MapView mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        final Geocoder geocoder = new Geocoder(this);

        List<Address> list;


        try {

            list = geocoder.getFromLocationName(str,8);
            if (list != null) {
                String city = "";
                String country = "";
                if (list.size() == 0) {
                    Toast.makeText(this, "올바른주소를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Address address = list.get(0);
                    lat = address.getLatitude();
                    lon = address.getLongitude();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }

        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(lat, lon), 0, true);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(name);
//        marker.setTag(3);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(lat,lon));
        marker.setMarkerType(MapPOIItem.MarkerType.RedPin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        marker.setCustomImageAutoscale(false);
        marker.setCustomImageAnchor(0.5f, 1.0f);

        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());

        mapView.addPOIItem(marker);

        mapView.selectPOIItem(marker,true);




    }

    public class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        Intent secondIntent = getIntent();
        String name = secondIntent.getStringExtra("name");
        String str = secondIntent.getStringExtra("addr");
        int sel = secondIntent.getIntExtra("sel",0);

        private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.custom_callout_balloon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            if(sel==1)((ImageView) mCalloutBalloon.findViewById(R.id.badge)).setImageResource(R.drawable.free_icon_animal_clinic1);
            if(sel==2)((ImageView) mCalloutBalloon.findViewById(R.id.badge)).setImageResource(R.drawable.free_icon_animal_clinic3);
            ((TextView) mCalloutBalloon.findViewById(R.id.title)).setText(name);
            ((TextView) mCalloutBalloon.findViewById(R.id.desc)).setText(str);
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return mCalloutBalloon;
        }
    }


}