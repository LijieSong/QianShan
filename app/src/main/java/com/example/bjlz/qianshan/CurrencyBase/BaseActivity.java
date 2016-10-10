package com.example.bjlz.qianshan.CurrencyBase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;

import com.example.bjlz.qianshan.tools.OtherTools.UITools.StatusBarUtils;

/**
 * 项目名称：qianshandoctor
 * 类描述：BaseActivity :基类
 * 创建人：slj
 * 创建时间：2016-5-28 17:24
 * 修改人：slj
 * 修改时间：2016-5-28 17:24
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置没有标题
        StatusBarUtils.setStatusBar(BaseActivity.this);//设置透明状态栏
    }

    /**
     * 初始化UI
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 获取传递过来的数据或者存储的数据
     */
    public abstract void getData();

    /**
     * 设置监听事件
     */
    public abstract void setOnClick();

    /**
     * 处理点击事件
     *
     * @param v
     */
    public abstract void WeightOnClick(View v);

    /**
     * 处理item点击事件
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    public abstract void OnItemClick(AdapterView<?> adapterView, View view, int i, long l);
    @Override
    public void onClick(View v) {
        WeightOnClick(v);
    }

    /**
     * 返回键关闭本页面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
      StatusBarUtils.setPortrait(this);//强制竖屏
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        OnItemClick(adapterView,view,i,l);
    }
}
