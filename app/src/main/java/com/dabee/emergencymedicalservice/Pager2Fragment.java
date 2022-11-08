package com.dabee.emergencymedicalservice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Pager2Fragment extends Fragment {

    Spinner spinner1,spinner2;
    ArrayAdapter adapter1,adapter2;


    ArrayList<RecyclerItem> items=new ArrayList<RecyclerItem>();
    RecyclerView recyclerView;
    MyAdapter recyclerAdapter;

    String apikey= "4c56464e6c627a323130346a75567878";

    String[] gus= new String[]{null,"GR","GC","GN","GD","GB","GS","GA","GJ","NW","DB","DD","DJ","MP","SD","SC","SD","SB","SP","YC","YD","EP","YS","JN","JG","JR"};
    String gu;
    String si,api,api2;

    // 서울 열린데이터 광장
    // https://data.seoul.go.kr/dataList/datasetList.do
    // 경기데이터드림
    // https://data.gg.go.kr/portal/data/service/selectServicePage.do?page=1&rows=10&sortColumn=&sortDirection=&infId=Y5M0CVS8XM2C821G09A813809578&infSeq=3&order=&loc=&searchWord=%EB%8F%99%EB%AC%BC%EB%B3%91%EC%9B%90


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager2,container,false);

        recyclerView=view.findViewById(R.id.recycler);
        recyclerAdapter= new MyAdapter(getContext(),items);
        recyclerView.setAdapter(recyclerAdapter);


        spinner1=view.findViewById(R.id.spinner1);
        spinner2=view.findViewById(R.id.spinner2);
        adapter1=ArrayAdapter.createFromResource(getActivity(),R.array.city,R.layout.selct_item);
        adapter2=ArrayAdapter.createFromResource(getActivity(),R.array.gu,R.layout.selct_item);
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        adapter1.setDropDownViewResource(R.layout.selcted_item);
        adapter2.setDropDownViewResource(R.layout.selcted_item);
        spinner1.setDropDownVerticalOffset(160);
        spinner2.setDropDownVerticalOffset(160);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





        spinner1.setOnItemSelectedListener(listener);




        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (spinner1.getSelectedItem().equals("경기도")) api2 = spinner2.getSelectedItem().toString();

                if(i==0) {
                    items.clear();
                    recyclerAdapter.notifyDataSetChanged();
                }


                if (i != 0) {
                    start();
                }

                if (spinner1.getSelectedItem().toString().equals("서울특별시")) gu = gus[i];
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(i==0) adapter2=ArrayAdapter.createFromResource(getActivity(),R.array.gu,R.layout.selct_item);
            if(i==1){
                adapter2=ArrayAdapter.createFromResource(getActivity(),R.array.seoul_gu,R.layout.selct_item);
                si = "http://openapi.seoul.go.kr:8088/";
                apikey="4c56464e6c627a323130346a75567878";
                api = "/xml/LOCALDATA_020301_";
                api2 = "/1/1000/";
            }
            if(i==2) {
                adapter2=ArrayAdapter.createFromResource(getActivity(),R.array.gyung,R.layout.selct_item);
                si = "https://openapi.gg.go.kr/Animalhosptl?KEY=";
                apikey="969816575ca04c56ae9a85e65647e2f9";
                api = "&xml&plndex=1&pSize=1000&SIGUN_NM=";
                gu="";
            }

            adapter2.setDropDownViewResource(R.layout.selcted_item);
            spinner2.setAdapter(adapter2);



        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


    void start(){



        Thread thread= new Thread(){
            @Override
            public void run() {


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        items.clear();
                        recyclerAdapter.notifyDataSetChanged();

                    }
                });

                String address= si + apikey + api + gu + api2;

                try {

                    URL url = new URL(address);

                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser xpp=factory.newPullParser();


                    xpp.setInput(isr);



                    int eventType = xpp.getEventType();


                    RecyclerItem item= null;


                    if(spinner1.getSelectedItem().toString().equals("서울특별시")){

                        while( eventType != XmlPullParser.END_DOCUMENT){

                            switch (eventType){
                                case XmlPullParser.START_DOCUMENT:

                                    break;

                                case XmlPullParser.START_TAG:
                                    String tagName= xpp.getName();
                                    if (tagName.equals("row")){
                                        item= new RecyclerItem();
                                        item.select=2;
                                    }else if(tagName.equals("SITEWHLADDR")){
                                        xpp.next();
                                        item.address=xpp.getText();

                                    }else if(tagName.equals("DUTYNAME33")){
                                        xpp.next();
                                        item.map1=xpp.getText();

                                    }else if(tagName.equals("BPLCNM")){
                                        xpp.next();
                                        item.name=xpp.getText();

                                    }else if(tagName.equals("SITETEL")){
                                        String tel = null;
                                        xpp.next();
                                        tel=xpp.getText();
                                        if(tel==null){
                                            tel="";
                                        }else{
                                            tel="Tel : "+tel;
                                        }

                                        item.tel=tel;




                                    }else if(tagName.equals("DTLSTATENM")){

                                        xpp.next();

                                        item.time=xpp.getText();

                                    }

                                    break;

                                case XmlPullParser.TEXT:
                                    break;

                                case XmlPullParser.END_TAG:
                                    if(xpp.getName().equals("row")){
                                        items.add(item);
                                    }
                                    break;
                            }

                            eventType=xpp.next();

                        }//while

                    }


                    if(spinner1.getSelectedItem().toString().equals("경기도")){

                        while( eventType != XmlPullParser.END_DOCUMENT){

                            switch (eventType){
                                case XmlPullParser.START_DOCUMENT:

                                    break;

                                case XmlPullParser.START_TAG:
                                    String tagName= xpp.getName();
                                    if (tagName.equals("row")){
                                        item= new RecyclerItem();
                                        item.select=2;
                                    }else if(tagName.equals("REFINE_LOTNO_ADDR")){
                                        xpp.next();
                                        item.address=xpp.getText();

                                    }else if(tagName.equals("REFINE_WGS84_LOGT")){
                                        xpp.next();
                                        item.map1=xpp.getText();

                                    }else if(tagName.equals("REFINE_WGS84_LAT")){
                                        xpp.next();
                                        item.map2=xpp.getText();

                                    }else if(tagName.equals("BIZPLC_NM")){
                                        xpp.next();
                                        item.name=xpp.getText();

                                    }else if(tagName.equals("LOCPLC_FACLT_TELNO")){
                                        String tel = null;
                                        xpp.next();
                                        tel=xpp.getText();
                                        if(tel==null){
                                            tel="";
                                        }else{
                                            tel="Tel : "+tel;
                                        }
                                        item.tel=tel;

                                    }else if(tagName.equals("BSN_STATE_NM")){
                                        String s;
                                        xpp.next();
                                        s= xpp.getText();
                                        s= s.replace(" ","");
                                        item.time = s;

                                    }
                                    break;

                                case XmlPullParser.TEXT:
                                    break;

                                case XmlPullParser.END_TAG:
                                    if(xpp.getName().equals("row")){
                                        items.add(item);
                                    }
                                    break;
                            }

                            eventType=xpp.next();

                        }//while

                    }




                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            recyclerAdapter.notifyDataSetChanged();

                        }
                    });


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }


            }


        };
        thread.start();







    }



}