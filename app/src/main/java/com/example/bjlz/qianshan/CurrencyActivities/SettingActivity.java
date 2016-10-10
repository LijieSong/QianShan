package com.example.bjlz.qianshan.CurrencyActivities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bjlz.qianshan.CurrencyApplication.Address;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.AppManager.DataCleanManagerUtils;
import com.example.bjlz.qianshan.tools.ChangeAndGetTools.DataUtils;
import com.example.bjlz.qianshan.tools.AppManager.SnackBarUtils;
import com.example.bjlz.qianshan.views.TitleBarView;

/**
 * 项目名称：QianShanDoctor
 * 类描述：SettingActivity 关于
 * 创建人：slj
 * 创建时间：2016-6-28 14:45
 * 修改人：slj
 * 修改时间：2016-6-28 14:45
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class SettingActivity extends BaseActivity {
    private TitleBarView title_bar;//标题
    private Context mContext = null;//上下文对象
    private String cacheSize,version;//缓存大小/版本号
    private TextView tv_ban_ben_hao,tv_Service_regulations,tv_Function_introduction,tv_Empty_cache_data,tv_feeBack,tv_help,tv_catch_size;//版本号/服务条例/功能介绍/清理缓存/反馈/帮助/缓存大小
    private ImageView img_Service_regulations,img_Function_introduction,img_Empty_cache_data,img_feeBack,img_help;//服务条例/功能介绍/清理缓存/反馈/帮助
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        MyApplication.getInstance().addActivity(this);
        mContext = this;
        getData();
        initView();
        initData();
        setOnClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            cacheSize = DataCleanManagerUtils.getTotalCacheSize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);

        tv_ban_ben_hao = (TextView) findViewById(R.id.tv_ban_ben_hao);
        tv_Service_regulations = (TextView) findViewById(R.id.tv_Service_regulations);
        tv_Function_introduction = (TextView) findViewById(R.id.tv_Function_introduction);
        tv_Empty_cache_data = (TextView) findViewById(R.id.tv_Empty_cache_data);
        tv_feeBack = (TextView) findViewById(R.id.tv_feeBack);
        tv_help = (TextView) findViewById(R.id.tv_help);
        tv_catch_size = (TextView) findViewById(R.id.tv_catch_size);

        img_Service_regulations = (ImageView) findViewById(R.id.img_Service_regulations);
        img_Function_introduction = (ImageView) findViewById(R.id.img_Function_introduction);
        img_Empty_cache_data = (ImageView) findViewById(R.id.img_Empty_cache_data);
        img_feeBack = (ImageView) findViewById(R.id.img_feeBack);
        img_help = (ImageView) findViewById(R.id.img_help);

    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.Setting);
        tv_ban_ben_hao.setText(version);//版本号
        tv_catch_size.setText(cacheSize);//缓存
    }

    @Override
    public void getData() {
        version = DataUtils.getVersion(getApplicationContext());
        try {
            cacheSize = DataCleanManagerUtils.getTotalCacheSize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void setOnClick() {
        tv_ban_ben_hao.setOnClickListener(this);
        tv_Service_regulations.setOnClickListener(this);
        tv_Function_introduction.setOnClickListener(this);
        tv_Empty_cache_data.setOnClickListener(this);
        tv_feeBack.setOnClickListener(this);
        tv_help.setOnClickListener(this);
        tv_catch_size.setOnClickListener(this);

        img_Service_regulations.setOnClickListener(this);
        img_Function_introduction.setOnClickListener(this);
        img_Empty_cache_data.setOnClickListener(this);
        img_feeBack.setOnClickListener(this);
        img_help.setOnClickListener(this);
    }

    @Override
    public void WeightOnClick(View v) {
        Bundle bundle = new Bundle();
        switch(v.getId()){
            case R.id.tv_Service_regulations://服务条例
            case R.id.img_Service_regulations:
                bundle.putString("title", "服务条例");
                bundle.putString("url", Address.TEXT_URL3);
                MyApplication.startActivity(SettingActivity.this, WebViewActivity.class, bundle);
                break;
            case R.id.tv_Function_introduction://功能介绍
            case R.id.img_Function_introduction:
                bundle.putString("title", "功能介绍");
                bundle.putString("url", Address.TEXT_URL2);
                MyApplication.startActivity(SettingActivity.this, WebViewActivity.class, bundle);
                break;
            case R.id.tv_feeBack://反馈
            case R.id.img_feeBack:
                MyApplication.startActivity(SettingActivity.this, FeedBackActivity.class);
                break;
            case R.id.tv_help://帮助
            case R.id.img_help:
                bundle.putString("title", "帮    助");
                bundle.putString("url", Address.TEXT_URL1);
                MyApplication.startActivity(SettingActivity.this, WebViewActivity.class, bundle);
                break;
            case R.id.tv_Empty_cache_data:
            case R.id.img_Empty_cache_data://清理缓存
            case R.id.tv_catch_size:
                try{
                    if (cacheSize !=null){
//                        CustomDialog.Builder builder = new CustomDialog.Builder(this);
//                        builder.setMessage("缓存大小为"+cacheSize+",确定要清除缓存吗?");
//                        builder.setTitle("提示");
//                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                //清理缓存
//                                DataCleanManagerUtils.clearAllCache(getApplicationContext());
////                                MyApplication.startActivity(SettingActivity.this,SettingActivity.class);
//                                SettingActivity.this.setContentView(R.layout.activity_setting);
//                                SettingActivity.this.getData();
//                                SettingActivity.this.initView();
//                                SettingActivity.this.initData();
//                                SettingActivity.this.setOnClick();
//                            }
//                        });
//
//                        builder.setNegativeButton("取消",
//                                new android.content.DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                });
//                        builder.create().show();
                        SnackBarUtils.GetSnackBarBg(SettingActivity.this, img_Empty_cache_data, "缓存大小为" + cacheSize + ",确定要清除缓存吗?", "确定",R.color.whites,new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                //清理缓存
                                DataCleanManagerUtils.clearAllCache(getApplicationContext());
//                                MyApplication.startActivity(SettingActivity.this,SettingActivity.class);
                                SettingActivity.this.setContentView(R.layout.activity_setting);
                                SettingActivity.this.getData();
                                SettingActivity.this.initView();
                                SettingActivity.this.initData();
                                SettingActivity.this.setOnClick();
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

        }
    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
