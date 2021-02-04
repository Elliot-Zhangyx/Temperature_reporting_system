package com.example.temperature;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.example.temperature.adapter.Temp;
import com.example.temperature.dao.UserDao;
import com.example.temperature.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button btn_submit;
    private Button btn_adr;
    private Button btn_back;
    private EditText et_name;
    private TextView tv_time;
    private EditText et_temperature;
    private EditText et_place;
    private RadioGroup rg_gender;
    private  RadioButton radioButton;
    private LocationService locationService;
    private MysqliteOpenHelper dbHelper;
    private MysqliteOpenHelper dbHelper1;
    private User edititem=null;
    private UserDao userDao;
    private RadioButton rb_boy;
    private RadioButton rb_girl;
    private String waterIn;
    Date date=new Date();
    SimpleDateFormat simpleDateFormat  =new SimpleDateFormat("yyyy-MM-dd");
    String time = simpleDateFormat.format(date);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initData();

        locationService = new LocationService(this);
        locationService.registerListener(mListener);
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
    }
    public void initUI(){
        btn_submit=findViewById(R.id.btn_submit);
        et_name=findViewById(R.id.et_name);
        tv_time=findViewById(R.id.tv_time);
        et_temperature=findViewById(R.id.et_temperature);
        et_place=findViewById(R.id.et_place);
        rg_gender=findViewById(R.id.rg_gender);
        btn_adr=findViewById(R.id.btn_adr);
        btn_back=findViewById(R.id.btn_back);
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                   radioButton = (RadioButton)findViewById(rg_gender.getCheckedRadioButtonId());
                     waterIn = radioButton.getText().toString();
                    Log.i("radio", waterIn);
                }
            });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // radioButton = (RadioButton)findViewById(rg_gender.getCheckedRadioButtonId());
                UserDao userDao=new UserDao(MainActivity.this);
                String name=et_name.getText().toString();
                String gender=waterIn;
                String temperature=et_temperature.getText().toString();
                String place=et_place.getText().toString();
                if(name.trim().length()==0){
                    Toast.makeText(MainActivity.this,"姓名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(rg_gender.getCheckedRadioButtonId()==(-1)){
                    Toast.makeText(MainActivity.this,"性别不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                System.out.println(rg_gender.getCheckedRadioButtonId());
                if(temperature.trim().length()==0){
                    Toast.makeText(MainActivity.this,"体温不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(place.trim().length()==0){
                    Toast.makeText(MainActivity.this,"地点不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edititem==null){
                    User u=new User(0,time,name,gender,temperature,place);
                    boolean f=userDao.insert(u);
                    if(f){
                        Toast.makeText(MainActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this,"提交失败",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                   //String time_true=userDao.queryTime();
                    edititem.setTime(edititem.getTime());
                    edititem.setName(name);
                    edititem.setTemperature(temperature);
                    edititem.setPlace(place);
                    edititem.setGender(gender);
                    userDao.updateContacter(edititem);
                }
               // locationService.stop();
                finish();
            }
        });
        btn_adr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationService.start();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void initData(){
        rb_boy=findViewById(R.id.rb_boy);
        rb_girl=findViewById(R.id.rb_girl);
        userDao=new UserDao(MainActivity.this);
        edititem=(User)getIntent().getSerializableExtra("edititem");
        if(edititem==null){
            tv_time.setText(time);
        }
        if(edititem!=null){
            et_name.setText(edititem.getName());
            et_temperature.setText(edititem.getTemperature());
           et_place.setText(edititem.getPlace());
            if(rb_boy.getText().toString().equals(edititem.getGender()))    ((RadioButton) rg_gender.findViewById(R.id.rb_boy)).setChecked(true);
            if((rb_girl.getText().toString()).equals(edititem.getGender()))    ((RadioButton) rg_gender.findViewById(R.id.rb_girl)).setChecked(true);
           tv_time.setText(edititem.getTime());
           System.out.println(edititem.getTime()+"77");
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                //button.setText("停止定位");
                StringBuilder sb = new StringBuilder(256);
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */

                sb.append(location.getAddrStr());
                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                logMsg(sb.toString());
            }
        }

    };

    /**
     * 显示请求字符串
     */
    public void logMsg(final String str) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    et_place.post(new Runnable() {
                        @Override
                        public void run() {
                            et_place.setText(str);
                        }
                    });
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}