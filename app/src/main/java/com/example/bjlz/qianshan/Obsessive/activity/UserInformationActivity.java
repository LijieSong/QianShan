package com.example.bjlz.qianshan.Obsessive.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bjlz.qianshan.CurrencyActivities.WebViewActivity;
import com.example.bjlz.qianshan.CurrencyApplication.Address;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.BitmapTools.GildeTools.GlideUtils;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.ToastUtil;
import com.example.bjlz.qianshan.views.TitleBarView;

/**
 * 项目名称：QianShanDoctor
 * 类描述：UserInformationActivity 用户详情
 * 创建人：slj
 * 创建时间：2016-6-28 17:17
 * 修改人：slj
 * 修改时间：2016-6-28 17:17
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class UserInformationActivity extends BaseActivity {
    private TitleBarView title_bar;//标题
    private WebView webView_userInfor;//显示患者详情
    private ProgressBar webView_ProgressBar;//进度条显示进度
    private ImageView image_user_icon, img_look_doctor, img_eat_medication;//用户头像
    private TextView tv_user_name, tv_user_sex, tv_user_age, tv_user_zhu_su;//用户姓名,年龄,性别,主诉
    private Context mContext = null;//上下文对象
    private String name, age, sex, zhusu;//用户姓名,年龄,性别,主诉
    private int imgId;//用户头像id
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinformation);
        MyApplication.getInstance().addActivity(this);
        mContext = this;
        getData();
        initView();
        initData();
        setOnClick();
    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        this.title_bar.setTitleText(R.string.user_infor);
        webView_ProgressBar = (ProgressBar) findViewById(R.id.webView_ProgressBar);
        webView_userInfor = (WebView) findViewById(R.id.webView_userInfor);

        image_user_icon = (ImageView) findViewById(R.id.image_user_icon);
        img_look_doctor = (ImageView) findViewById(R.id.img_look_doctor);
        img_eat_medication = (ImageView) findViewById(R.id.img_eat_medication);

        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_user_sex = (TextView) findViewById(R.id.tv_user_sex);
        tv_user_age = (TextView) findViewById(R.id.tv_user_age);
        tv_user_zhu_su = (TextView) findViewById(R.id.tv_user_zhu_su);

    }

    @Override
    public void initData() {
        // 设置JS交互数据
        webView_userInfor.getSettings().setJavaScriptEnabled(true);
        webView_userInfor.getSettings().setSupportZoom(true);
        webView_userInfor.getSettings().setBuiltInZoomControls(true);
        webView_userInfor.getSettings().setDisplayZoomControls(false);
        webView_userInfor.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    webView_ProgressBar.setVisibility(View.GONE);
                } else {
                    if (View.INVISIBLE == webView_ProgressBar.getVisibility()) {
                        webView_ProgressBar.setVisibility(View.VISIBLE);
                    }
                    webView_ProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
        // 加载web资源
        webView_userInfor.loadUrl(Address.TEXT_URL4);
        GlideUtils.downLoadCircleImage(this,Address.TEXT_IMAGEURL1,image_user_icon);
//        ImageLoaderUtil.setImageLoader(imgUrl,image_user_icon,R.drawable.logo,R.drawable.menu);
//        ImageLoaderUtil.setNetWorkImageView(imgUrl,image_user_icon,R.drawable.logo,R.drawable.menu);
        //设置显示
        tv_user_name.setText(name);
        tv_user_age.setText(age+"岁");
        tv_user_sex.setText(sex);
        tv_user_zhu_su.setText("主诉:" +zhusu);
    }

    @Override
    public void getData() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle !=null){
        name = bundle.getString("name");
        age = bundle.getString("age");
        sex = bundle.getString("sex");
        imgId = bundle.getInt("imgId");
        zhusu = bundle.getString("zhusu");
        }
    }

    @Override
    public void setOnClick() {
        // 设置webview的点击事件
        webView_userInfor.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        img_look_doctor.setOnClickListener(this);
        img_eat_medication.setOnClickListener(this);
        tv_user_name.setOnClickListener(this);
        image_user_icon.setOnClickListener(this);
    }

    @Override
    public void WeightOnClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.img_look_doctor://就医记录
                ToastUtil.shortDiyToast(mContext, "跳转就医记录");
                bundle.putString("title","就医记录");
                MyApplication.startActivity(UserInformationActivity.this, WebViewActivity.class,bundle);
                break;
            case R.id.img_eat_medication://用药记录
                ToastUtil.shortDiyToast(mContext, "跳转用药记录");
                bundle.putString("title","用药记录");
                MyApplication.startActivity(UserInformationActivity.this, WebViewActivity.class,bundle);
                break;
            case R.id.tv_user_name:
            case R.id.image_user_icon:
                bundle.putString("title","患者详情");
                bundle.putString("name",name);
                bundle.putString("url",Address.TEXT_IMAGEURL2);
                MyApplication.startActivity(UserInformationActivity.this, WebViewActivity.class,bundle);
                break;
        }
    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
