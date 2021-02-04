package com.example.temperature.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temperature.MainActivity;
import com.example.temperature.R;
import com.example.temperature.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Temp extends RecyclerView.Adapter<Temp.TempViewHolder>{

    private Context mContext;
    private List<User> list;
    public Temp (Context context, List<User> list){
        this.mContext=context;
        this.list=list;
    }
    public void setList(List<User> list) {
        this.list=list;
    }
    @NonNull
    @Override
    public TempViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TempViewHolder holder=new TempViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_view,parent,false));
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull TempViewHolder holder, int position) {
        User item=list.get(position);
        holder.tv_name.setText("姓名："+item.getName());
        holder.tv_time.setText(item.getTime());
        holder.tv_temp.setText("体温："+item.getTemperature());
        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(mContext, MainActivity.class);
                intent.putExtra("edititem",item);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }


    //绑定单元格空间
    class TempViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        TextView tv_time;
        TextView tv_temp;
        EditText et_name;
        EditText et_gender;
        EditText et_place;
        EditText et_temperature;
        Button btn_edit;


        public TempViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_temp=itemView.findViewById(R.id.tv_temp);
           btn_edit=itemView.findViewById(R.id.btn_edit);

        }
    }

}
