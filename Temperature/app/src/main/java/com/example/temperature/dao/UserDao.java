package com.example.temperature.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.temperature.MysqliteOpenHelper;
import com.example.temperature.model.User;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserDao {
    private MysqliteOpenHelper helper;
    public UserDao(Context context){
        helper=new MysqliteOpenHelper(context);
    }

    public  boolean updateContacter(User u) {
        SQLiteDatabase db=helper.getReadableDatabase();
        // String sql="select *from user where account='"+account+"' and password='"+password+'"';
        ContentValues values=new ContentValues();
        values.put("name",u.getName());
        values.put("temperature",u.getTemperature());
        values.put("time",u.getTime());
        values.put("place",u.getPlace());
        values.put("gender",u.getGender());

        long update= db.update("user",values,"id=?",new String[]{u.getId()+""});
        System.out.println(u.getId()+"年号");
        return update>0;
    }

    public boolean insert(User u){
        SQLiteDatabase db=helper.getReadableDatabase();
        Date date=new Date();
        SimpleDateFormat simpleDateFormat  =new SimpleDateFormat("yyyy-MM-dd");
        String time = simpleDateFormat.format(date);
        ContentValues values=new ContentValues();
        values.put("time",u.getTime());
        values.put("name",u.getName());
        values.put("gender",u.getGender());
        values.put("temperature",u.getTemperature());
        values.put("place",u.getPlace());
        long insert= db.insert("user",null,values);
        System.out.println(u.getPlace());
        return insert>0;
    }
    public List<User> queryAll(){
        List<User> list=new ArrayList<>();
        User bean=null;
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor c=db.query("user",null,null,null,null,null,null);
        if(c!=null&&c.getCount()>0){
            list=new ArrayList<>();
            while(c.moveToNext()){
                bean=new User();
                bean.setName(c.getString(c.getColumnIndex("name")));
                bean.setTime(c.getString(c.getColumnIndex("time")));
                bean.setGender(c.getString(c.getColumnIndex("gender")));
                bean.setPlace(c.getString(c.getColumnIndex("place")));
                bean.setTemperature(c.getString(c.getColumnIndex("temperature")));
                bean.setId(c.getInt(c.getColumnIndex("id")));
                list.add(bean);
            }
        }
        return list;
    }
}
