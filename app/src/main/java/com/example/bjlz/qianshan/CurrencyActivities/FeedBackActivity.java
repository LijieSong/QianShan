package com.example.bjlz.qianshan.CurrencyActivities;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.ToastUtil;
import com.example.bjlz.qianshan.views.ClearEditText;
import com.example.bjlz.qianshan.views.TitleBarView;

/**
 * 项目名称：QianShan
 * 类描述：FeedBackActivity 反馈
 * 创建人：slj
 * 创建时间：2016-6-28 14:45
 * 修改人：slj
 * 修改时间：2016-6-28 14:45
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class FeedBackActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{
    private TitleBarView title_bar;//标题
    private Context mContext = null;//上下文对象
    private RadioGroup radioTypeId;
    private RadioButton radioTypeId1;
    private RadioButton radioTypeId2;
    private RadioButton radioTypeId3;
    private ClearEditText fb_et_content;
    private Button btnSubmit;
    private String content;//反馈内容
    private int typeId = 1;//默认为1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
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
        radioTypeId = (RadioGroup) findViewById(R.id.radioTypeId);
        radioTypeId1 = (RadioButton) findViewById(R.id.radioTypeId1);
        radioTypeId2 = (RadioButton) findViewById(R.id.radioTypeId2);
        radioTypeId3 = (RadioButton) findViewById(R.id.radioTypeId3);
        fb_et_content = (ClearEditText) findViewById(R.id.fb_et_content);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.feeBack);
    }

    @Override
    public void getData() {

    }

    @Override
    public void setOnClick() {
// 判断radiobutton的点击事件
        radioTypeId.setOnCheckedChangeListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void WeightOnClick(View v) {
        content = fb_et_content.getText().toString().trim();
        // 判断输入内容是否为空
        if (TextUtils.isEmpty(content)) {
            // 说明输入内容为空
            ToastUtil.shortDiyToast(getApplicationContext(),"意见反馈不能为空");
        } else {
            ToastUtil.shortDiyToast(getApplicationContext(),"意见反馈已成功");
            finish();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int buttonId = group.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) FeedBackActivity.this
                .findViewById(buttonId);
        switch (rb.getId()) {
            case R.id.radioTypeId1:
                typeId = 1;
                break;
            case R.id.radioTypeId2:
                typeId = 2;
                break;
            case R.id.radioTypeId3:
                typeId = 3;
                break;
        }
    }
    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
