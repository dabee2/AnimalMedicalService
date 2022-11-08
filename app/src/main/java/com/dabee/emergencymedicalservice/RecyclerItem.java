package com.dabee.emergencymedicalservice;

public class RecyclerItem {

    String name;
    String time;
    String address;
    String map1;
    String map2;
    String tel;
    int select;

    public RecyclerItem(String name, String time, String address, String map1, String map2, String tel, int select) {
        this.name = name;
        this.time = time;
        this.address = address;
        this.map1 = map1;
        this.map2 = map2;
        this.tel = tel;
        this.select = select;
    }


    public RecyclerItem() {
    }
}
