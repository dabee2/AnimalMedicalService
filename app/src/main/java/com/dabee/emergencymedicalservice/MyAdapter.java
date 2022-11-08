package com.dabee.emergencymedicalservice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {

    Context context;
    ArrayList<RecyclerItem> items;
    MapView mapView;


    public MyAdapter(Context context, ArrayList<RecyclerItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.recycler_item,parent,false);
        VH holder=new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        RecyclerItem item = items.get(position);

        holder.tvName.setText(item.name);
        if(item.time.equals("정상")){
            holder.tvTime.setTextColor(Color.parseColor("#FF00FF00"));
        }else holder.tvTime.setTextColor(Color.parseColor("#ED1A4B"));
        holder.tvTime.setText(item.time);
        holder.tvMap.setText("");
        holder.tvAddress.setText(item.address);
        holder.tvTel.setText(item.tel);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,KakaoMap.class);
                intent.putExtra("addr",item.address);
                intent.putExtra("lat",item.map1);
                intent.putExtra("lon",item.map2);
                intent.putExtra("name",item.name);
                intent.putExtra("sel",item.select);
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    class VH extends RecyclerView.ViewHolder{

        TextView tvName,tvTime,tvAddress,tvMap,tvTel;


        public VH(@NonNull View itemView) {/////////
            super(itemView);

            tvName=itemView.findViewById(R.id.tv_name);
            tvTime=itemView.findViewById(R.id.tv_time);
            tvAddress=itemView.findViewById(R.id.tv_address);
            tvMap=itemView.findViewById(R.id.tv_map);
            tvTel=itemView.findViewById(R.id.tv_tel);


        }/////////////
    }
}


