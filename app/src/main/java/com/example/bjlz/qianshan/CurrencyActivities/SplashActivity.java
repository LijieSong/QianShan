package com.example.bjlz.qianshan.CurrencyActivities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.Doctor.activity.DoctorMain;
import com.example.bjlz.qianshan.Obsessive.activity.ObsessiveMain;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.PreferencesUtils;

/**
 * 项目名称：qianshandoctor
 * 类描述：SplashActivity :欢迎动画
 * 创建人：slj
 * 创建时间：2016-5-28 17:24
 * 修改人：slj
 * 修改时间：2016-5-28 17:24
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class SplashActivity extends BaseActivity {
    private Handler handler = new Handler();
//    private Handler handler; //水波纹进度条
    private boolean isLogin = false;//是否登录
    private String isDoctor = "0";//是否是医生 //默认是患者
//    private WaveProgressView waveProgressbar;//水波纹进度条
//    //水波纹进度条
//    private static final int one = 0X0001;//状态码
//    private static final int two = 0X0002;//第一次减少
//    private static final int three = 0X0003;//第二次减少
//    private int progress;//进度显示
//    private TextView tv_loading;//显示正在加载
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getData();
        initView();
        initData();
        setOnClick();
//        handler.sendEmptyMessageDelayed(one,1000);
    }

    @Override
    public void initView() {
        //水波纹进度条
//        waveProgressbar = (WaveProgressView) findViewById(R.id.waveProgressbar);
//        tv_loading = (TextView) findViewById(R.id.tv_loading);
    }

    @Override
    public void initData() {
//        waveProgressbar.setWaveColor("#61f25e");
//        waveProgressbar.setWaveColor("#FFFFFF");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLogin == false){
                    MyApplication.startActivity(SplashActivity.this, LoginActivity.class);
                }else{
                    if (isDoctor.equals("1")){
                        MyApplication.startActivity(SplashActivity.this, DoctorMain.class);//医生主界面
                    } else{
                        MyApplication.startActivity(SplashActivity.this, ObsessiveMain.class);//患者主界面
                    }
                }
                finish();
            }
        }, 60);

    }

    @Override
    public void getData() {
        isLogin = PreferencesUtils.getBoolean(getApplicationContext(), "isLogin", false);
        isDoctor = PreferencesUtils.getString(getApplicationContext(), "isDoctor","0");
    }

    @Override
    public void setOnClick() {
//        handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//
//                switch (msg.what) {
//                    case one:
//                        progress ++ ;
//                        if (progress <= 100) {
//                            tv_loading.setVisibility(View.VISIBLE);
//                            waveProgressbar.setCurrent(progress, progress + "%");
//                            handler.sendEmptyMessageDelayed(one, 30);
//                            if (progress == 100){
//                                tv_loading.setText(R.string.data_is_load_over);
//                                handler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        if (isLogin == false){
//                                            MyApplication.startActivity(SplashActivity.this, LoginActivity.class);
//                                        }else{
//                                            if (isDoctor == true){
//                                                MyApplication.startActivity(SplashActivity.this, DoctorMain.class);//医生主界面
//                                            } else{
//                                                MyApplication.startActivity(SplashActivity.this, ObsessiveMain.class);//患者主界面
//                                            }
//                                        }
//                                        finish();
//                                    }
//                                }, 60);
////                                handler.sendEmptyMessageDelayed(two,60);
//                            }
//                        }
//                        break;
//                    case two:
//                        progress--;
//                        if (progress >=77){
//                            tv_loading.setVisibility(View.VISIBLE);
//                            waveProgressbar.setCurrent(100, progress + "%");
//                            handler.sendEmptyMessageDelayed(two, 60);
//                            if (progress == 77){
//                                handler.sendEmptyMessageDelayed(three, 90);
//                            }
//                        }
//                        break;
//                    case three:
//                        progress--;
//                        if (progress >=44){
//                            tv_loading.setVisibility(View.VISIBLE);
//                            waveProgressbar.setCurrent(100, progress + "%");
//                            handler.sendEmptyMessageDelayed(three, 90);
//                        }
//                        break;
//                }
//            }
//        };
    }

    @Override
    public void WeightOnClick(View v) {

    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
