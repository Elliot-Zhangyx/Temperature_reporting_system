package com.example.temperature;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.temperature.adapter.Temp;
import com.example.temperature.dao.UserDao;
import com.example.temperature.model.User;

import java.util.List;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_back;
    private Button btn_add;
    private Button tv_time;
    private LocationService locationService;
    private RecyclerView rec;
    private UserDao userDao;
    private Temp adapter;
    private final Activity mContext=IndexActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initUI();
        initData();
    }
    @Override
    protected void onResume() {
        super.onResume();
        adapter.setList(userDao.queryAll());
        adapter.notifyDataSetChanged();
    }
    public void initUI(){
        btn_back=findViewById(R.id.btn_back);
        btn_add=findViewById(R.id.btn_add);
        btn_back.setOnClickListener(this);
        btn_add.setOnClickListener(this);

        rec=findViewById(R.id.rec);
        rec.setLayoutManager(new LinearLayoutManager(mContext));
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.recycler_item));
        rec.addItemDecoration(divider);
    }
    private void initData(){
        userDao =new UserDao(mContext);
        List<User> list=userDao.queryAll();
        adapter=new Temp(mContext,list);
        rec.setAdapter(adapter);
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        Intent intent=new Intent();
        switch (id){
            case R.id.btn_back:finish();break;
            case R.id.btn_add:
            intent.setClass(IndexActivity.this,MainActivity.class);
            startActivity(intent);
                break;
        }
    }
}