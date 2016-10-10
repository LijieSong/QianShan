package com.example.bjlz.qianshan.Obsessive.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.Obsessive.adapter.GradViewAdapter;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.views.TitleBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 项目名称：QianShanDoctor
 * 类描述：UserHealthMessageActivity 患者的健康全信息
 * 创建人：slj
 * 创建时间：2016-6-29 16:42
 * 修改人：slj
 * 修改时间：2016-6-29 16:42
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class UserHealthMessageActivity  extends BaseActivity{
    private Context mContext = null;//上下文对象
    private TitleBarView title_bar;//标题
    private GridView gridView_health;//布局button
    //item信息
    private int[] gride_image = {R.drawable.logo, R.drawable.logo, R.drawable.logo,
            R.drawable.logo, R.drawable.logo, R.drawable.logo, R.drawable.logo, R.drawable.logo};
    private String[] gride_name = {"问卷调查", "体检报告", "动态血压记录数据", "中医问诊", "西医问诊", "评估","电子检验处方","诊疗方案"};
    //用户信息
    private String name, age, sex, zhusu;//用户姓名,年龄,性别,主诉
    private int imgId;//用户头像id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthmessage);
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
        gridView_health = (GridView) findViewById(R.id.gridView_health);
    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(name+"的"+getResources().getString(R.string.health_message));
        /**
         * 主页面的适配器
         */
        List<HashMap<String, Object>> gradeViewList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < gride_image.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("image", gride_image[i]);
            map.put("text", gride_name[i]);
            gradeViewList.add(map);
        }
            GradViewAdapter adapter = new GradViewAdapter(mContext, gradeViewList);
            gridView_health.setAdapter(adapter);
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
        gridView_health.setOnItemClickListener(this);
    }

    @Override
    public void WeightOnClick(View v) {

    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        switch (gride_name[i]){
            case "问卷调查"://问卷调查
                MyApplication.startActivity(UserHealthMessageActivity.this,QuestionnaireSurveyActivity.class,bundle);
                break;
            case "体检报告"://Physical examination report
                MyApplication.startActivity(UserHealthMessageActivity.this,PhysicalExaminationReportActivity.class,bundle);
                break;
            case "动态血压记录数据"://动态血压记录数据

                break;
            case "中医问诊"://中医问诊

                break;
            case "西医问诊"://西医问诊

                break;
            case "评估"://评估

                break;
            case "电子检验处方"://电子检验处方

                break;
            case "诊疗方案"://诊疗方案

                break;
        }
    }
}
