package com.example.bjlz.qianshan.Obsessive.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.bjlz.qianshan.CurrencyActivities.WebViewActivity;
import com.example.bjlz.qianshan.CurrencyApplication.Address;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.ChangeAndGetTools.CommonUtil;
import com.example.bjlz.qianshan.views.TitleBarView;

/**
 * 项目名称：QianShan
 * 类描述：PhysicalExaminationReportActivity 体检报告
 * 创建人：slj
 * 创建时间：2016-6-29 19:31
 * 修改人：slj
 * 修改时间：2016-6-29 19:31
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class PhysicalExaminationReportActivity extends BaseActivity {
    private Context mContext = null;//上下文对象
    private TitleBarView title_bar;//标题
    private ImageView img_blood_sugar, img_routine_blood_test, img_function, img_lipid_routine;//血糖,血常规,甲功能,血脂常规
    private String name;//名字

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical);
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
        img_blood_sugar = (ImageView) findViewById(R.id.img_blood_sugar);
        img_routine_blood_test = (ImageView) findViewById(R.id.img_routine_blood_test);
        img_function = (ImageView) findViewById(R.id.img_function);
        img_lipid_routine = (ImageView) findViewById(R.id.img_lipid_routine);
    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(name + "的" + CommonUtil.getString(R.string.physical));
    }

    @Override
    public void getData() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            name = bundle.getString("name");
        }
    }

    @Override
    public void setOnClick() {
        img_blood_sugar.setOnClickListener(this);
        img_routine_blood_test.setOnClickListener(this);
        img_function.setOnClickListener(this);
        img_lipid_routine.setOnClickListener(this);
    }

    @Override
    public void WeightOnClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        switch (v.getId()) {
            case R.id.img_blood_sugar://血糖
                bundle.putString("title","血糖体检报告");
                bundle.putString("url", Address.TEXT_URL1);
                MyApplication.startActivity(PhysicalExaminationReportActivity.this,WebViewActivity.class,bundle);
            break;
            case R.id.img_routine_blood_test://血常规
                bundle.putString("title","血常规体检报告");
                bundle.putString("url",Address.TEXT_URL2);
                MyApplication.startActivity(PhysicalExaminationReportActivity.this,WebViewActivity.class,bundle);
            break;
            case R.id.img_function://甲功能
                bundle.putString("title","甲功能体检报告");
                bundle.putString("url",Address.TEXT_URL3);
                MyApplication.startActivity(PhysicalExaminationReportActivity.this,WebViewActivity.class,bundle);
            break;
            case R.id.img_lipid_routine://血脂常规
                bundle.putString("title","血脂常规体检报告");
                bundle.putString("url",Address.TEXT_URL4);
                MyApplication.startActivity(PhysicalExaminationReportActivity.this,WebViewActivity.class,bundle);
            break;
        }
    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
