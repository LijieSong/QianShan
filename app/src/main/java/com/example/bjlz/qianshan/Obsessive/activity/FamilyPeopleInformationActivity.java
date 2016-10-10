/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
*/
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；

package com.example.bjlz.qianshan.Obsessive.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.views.TitleBarView;

/**
 * 项目名称：QianShan
 * 类描述：FamilyPeopleInformationActivity 家庭成员详情界面
 * 创建人：slj
 * 创建时间：2016-7-22 15:49
 * 修改人：slj
 * 修改时间：2016-7-22 15:49
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class FamilyPeopleInformationActivity extends BaseActivity {
    private TitleBarView title_bar;//标题
    private Context mContext = null;//上下文对象
    private TextView tv_user_name, tv_user_mobile;//显示家庭成员名字与手机号
    private String name, mobile;//家庭成员名字与手机号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_people_info);
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
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_user_mobile = (TextView) findViewById(R.id.tv_user_mobile);
    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.customer_info);
        if (name !=null ||mobile!=null)
        tv_user_name.setText(name);
        tv_user_mobile.setText(mobile);
    }

    @Override
    public void getData() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            name = bundle.getString("name");
            mobile = bundle.getString("mobile");
        }
    }

    @Override
    public void setOnClick() {

    }

    @Override
    public void WeightOnClick(View v) {

    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
