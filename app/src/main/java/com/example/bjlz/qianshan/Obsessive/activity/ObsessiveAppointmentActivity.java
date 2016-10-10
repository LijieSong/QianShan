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
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.DatePicker.MonthDateView;
import com.example.bjlz.qianshan.Obsessive.adapter.AppointmentTimesAdapter;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.ChangeAndGetTools.CommonUtil;
import com.example.bjlz.qianshan.tools.ChangeAndGetTools.DataUtils;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.ToastUtil;
import com.example.bjlz.qianshan.views.TitleBarView;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：QianShan
 * 类描述：ObsessiveAppointmentActivity 预约诊疗
 * 创建人：slj
 * 创建时间：2016-7-21 17:25
 * 修改人：slj
 * 修改时间：2016-7-21 17:25
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class ObsessiveAppointmentActivity extends BaseActivity implements View.OnTouchListener{
    private TitleBarView title_bar;//标题
    private Context mContext = null;//上下文对象
    private RadioGroup rg_time; //选择预约时间
    private ImageView iv_left;//左箭头点击
    private ImageView iv_right;//右箭头点击
    private TextView tv_date, tv_appointment_time, tv_week, tv_today;//今天/时间/周
    private MonthDateView monthDateView;//显示的
    private int downX,downY,moveX,moveY,upX,upY =0;//按下时候的x,y坐标
//    private List<Integer> list;
    private String appointmentTime = null;//记录日历控件上的时间
    private String rbTime = null;//记录选择的时间
    //各选择按钮
    private RadioButton rb_time1, rb_time2, rb_time3, rb_time4, rb_time5, rb_time6,
            rb_time7, rb_time8, rb_time9, rb_time10, rb_time11, rb_time12;
    //提交按钮
    private Button btn_submit;
    private GridView gridView_time;//时间
    private AppointmentTimesAdapter adapter;//选择时间的记录器
    //存储时间
    private List<String> times = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
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
        rg_time = (RadioGroup) findViewById(R.id.rg_time);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        monthDateView = (MonthDateView) findViewById(R.id.monthDateView);
        tv_date = (TextView) findViewById(R.id.date_text);
        tv_week = (TextView) findViewById(R.id.week_text);
        tv_today = (TextView) findViewById(R.id.tv_today);
        tv_appointment_time = (TextView) findViewById(R.id.tv_appointment_time);
        btn_submit = (Button) findViewById(R.id.btn_submit);//提交按钮
        //各选择按钮
        //上午
        rb_time1 = (RadioButton) this.findViewById(R.id.rb_time1);
        rb_time2 = (RadioButton) this.findViewById(R.id.rb_time2);
        rb_time3 = (RadioButton) this.findViewById(R.id.rb_time3);
        rb_time4 = (RadioButton) this.findViewById(R.id.rb_time4);
        rb_time5 = (RadioButton) this.findViewById(R.id.rb_time5);
        rb_time6 = (RadioButton) this.findViewById(R.id.rb_time6);
        //下午
        rb_time7 = (RadioButton) this.findViewById(R.id.rb_time7);
        rb_time8 = (RadioButton) this.findViewById(R.id.rb_time8);
        rb_time9 = (RadioButton) this.findViewById(R.id.rb_time9);
        rb_time10 = (RadioButton) this.findViewById(R.id.rb_time10);
        rb_time11 = (RadioButton) this.findViewById(R.id.rb_time11);
        rb_time12 = (RadioButton) this.findViewById(R.id.rb_time12);

        //gridview适配
        gridView_time = (GridView) this.findViewById(R.id.gridView_time);
    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.appointment_time);
        monthDateView.setTextView(tv_date, tv_week);
//        monthDateView.setmSelectBGColor(CommonUtil.getColor(R.color.detail_tag));
//        monthDateView.setDaysHasThingList(list);
        if (monthDateView.isClickable() == false)
            appointmentTime = DataUtils.getNowDateShortCN();
        if (rbTime ==null)
            tv_appointment_time.setText(appointmentTime);

        adapter = new AppointmentTimesAdapter(this,times);
        gridView_time.setAdapter(adapter);
    }

    @Override
    public void getData() {
        //添加数据
        times.add(CommonUtil.getString(R.string.time1));
        times.add(CommonUtil.getString(R.string.time2));
        times.add(CommonUtil.getString(R.string.time3));
        times.add(CommonUtil.getString(R.string.time4));
        times.add(CommonUtil.getString(R.string.time5));
        times.add(CommonUtil.getString(R.string.time6));
        times.add(CommonUtil.getString(R.string.time7));
        times.add(CommonUtil.getString(R.string.time8));
        times.add(CommonUtil.getString(R.string.time9));
        times.add(CommonUtil.getString(R.string.time10));
        times.add(CommonUtil.getString(R.string.time11));
        times.add(CommonUtil.getString(R.string.time12));
//        list = new ArrayList<Integer>();
//        list.add(10);
//        list.add(12);
//        list.add(15);
//        list.add(16);
    }

    @Override
    public void setOnClick() {
        btn_submit.setOnClickListener(this);//提交按钮
        //左右按钮与今天按钮
        iv_left.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        tv_today.setOnClickListener(this);
        //各按钮选择
        rb_time1.setOnClickListener(this);
        rb_time2.setOnClickListener(this);
        rb_time3.setOnClickListener(this);
        rb_time4.setOnClickListener(this);
        rb_time5.setOnClickListener(this);
        rb_time6.setOnClickListener(this);
        rb_time7.setOnClickListener(this);
        rb_time8.setOnClickListener(this);
        rb_time9.setOnClickListener(this);
        rb_time10.setOnClickListener(this);
        rb_time11.setOnClickListener(this);
        rb_time12.setOnClickListener(this);
        //gridView的item点击事件
        gridView_time.setOnItemClickListener(this);
        //日历控件的触摸事件
//        monthDateView.setOnTouchListener(this);
        //日历控件的时间点击事件
        monthDateView.setDateClick(new MonthDateView.DateClick() {

            @Override
            public void onClickOnDate() {
                appointmentTime = monthDateView.getmSelYear() + "年" + monthDateView.getmSelMonth() + "月" + monthDateView.getmSelDay() + "日";
                if (rbTime ==null || TextUtils.isEmpty(rbTime))
                    tv_appointment_time.setText(appointmentTime);
                else
                    tv_appointment_time.setText(appointmentTime+rbTime);
            }
        });
    }

    @Override
    public void WeightOnClick(View v) {
        String submitTime = tv_appointment_time.getText().toString().trim();
        switch (v.getId()) {
            case R.id.iv_left:
                monthDateView.onLeftClick();
                appointmentTime = monthDateView.getmSelYear() + "年" + monthDateView.getmSelMonth() + "月" + monthDateView.getmSelDay() + "日";
                break;
            case R.id.iv_right:
                monthDateView.onRightClick();
                appointmentTime = monthDateView.getmSelYear() + "年" + monthDateView.getmSelMonth() + "月" + monthDateView.getmSelDay() + "日";
                break;
            case R.id.tv_today:
                monthDateView.setTodayToView();
                appointmentTime = monthDateView.getmSelYear() + "年" + monthDateView.getmSelMonth() + "月" + monthDateView.getmSelDay() + "日";
                break;
            case R.id.btn_submit:
                if (submitTime != null || submitTime.length() != 0 || !TextUtils.isEmpty(submitTime))
                    if (rbTime !=null ||!TextUtils.isEmpty(rbTime)) {
                        ToastUtil.shortDiyToast(getApplicationContext(), "请于" + submitTime + "期间准时就诊");
                        finish();
                    }else {
                        ToastUtil.shortDiyToastByRec(getApplicationContext(), R.string.time_first);
                    }
                 else
                    ToastUtil.shortDiyToastByRec(getApplicationContext(), R.string.time_first);
//                if (submitTime != null || submitTime.length() != 0 || !TextUtils.isEmpty(submitTime)) {
////                    if (adapter.getSelectedPosition() >-1)
//                    ToastUtil.shortDiyToast(getApplicationContext(), "请于" + submitTime + "期间准时就诊");
//                    finish();
//                } else {
//                    ToastUtil.shortDiyToastByRec(getApplicationContext(), R.string.time_first);
//                }
                break;
            case R.id.rb_time1:
                if (appointmentTime != null) {
                    rb_time1.setChecked(true);
                    rb_time1.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time2.setChecked(false);
                    rb_time2.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time3.setChecked(false);
                    rb_time3.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time4.setChecked(false);
                    rb_time4.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time5.setChecked(false);
                    rb_time5.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time6.setChecked(false);
                    rb_time6.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time7.setChecked(false);
                    rb_time7.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time8.setChecked(false);
                    rb_time8.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time9.setChecked(false);
                    rb_time9.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time10.setChecked(false);
                    rb_time10.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time11.setChecked(false);
                    rb_time11.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time12.setChecked(false);
                    rb_time12.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rbTime = rb_time1.getText().toString().trim();
                } else {
                    ToastUtil.shortDiyToastByRec(getApplicationContext(), R.string.date_first);
                }
                break;
            case R.id.rb_time2:
                if (appointmentTime != null) {
                    rb_time1.setChecked(false);
                    rb_time1.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time2.setChecked(true);
                    rb_time2.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time3.setChecked(false);
                    rb_time3.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time4.setChecked(false);
                    rb_time4.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time5.setChecked(false);
                    rb_time5.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time6.setChecked(false);
                    rb_time6.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time7.setChecked(false);
                    rb_time7.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time8.setChecked(false);
                    rb_time8.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time9.setChecked(false);
                    rb_time9.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time10.setChecked(false);
                    rb_time10.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time11.setChecked(false);
                    rb_time11.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time12.setChecked(false);
                    rb_time12.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rbTime = rb_time2.getText().toString().trim();
                } else {
                    ToastUtil.shortDiyToastByRec(getApplicationContext(), R.string.date_first);
                }
                break;
            case R.id.rb_time3:
                if (appointmentTime != null) {
                    rb_time1.setChecked(false);
                    rb_time1.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time2.setChecked(false);
                    rb_time2.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time3.setChecked(true);
                    rb_time3.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time4.setChecked(false);
                    rb_time4.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time5.setChecked(false);
                    rb_time5.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time6.setChecked(false);
                    rb_time6.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time7.setChecked(false);
                    rb_time7.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time8.setChecked(false);
                    rb_time8.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time9.setChecked(false);
                    rb_time9.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time10.setChecked(false);
                    rb_time10.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time11.setChecked(false);
                    rb_time11.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time12.setChecked(false);
                    rb_time12.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rbTime = rb_time3.getText().toString().trim();
                } else {
                    ToastUtil.shortDiyToastByRec(getApplicationContext(), R.string.date_first);
                }
                break;
            case R.id.rb_time4:
                if (appointmentTime != null) {
                    rb_time1.setChecked(false);
                    rb_time1.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time2.setChecked(false);
                    rb_time2.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time3.setChecked(false);
                    rb_time3.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time4.setChecked(true);
                    rb_time4.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time5.setChecked(false);
                    rb_time5.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time6.setChecked(false);
                    rb_time6.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time7.setChecked(false);
                    rb_time7.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time8.setChecked(false);
                    rb_time8.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time9.setChecked(false);
                    rb_time9.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time10.setChecked(false);
                    rb_time10.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time11.setChecked(false);
                    rb_time11.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time12.setChecked(false);
                    rb_time12.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rbTime = rb_time4.getText().toString().trim();
                } else {
                    ToastUtil.shortDiyToastByRec(getApplicationContext(), R.string.date_first);
                }
                break;
            case R.id.rb_time5:
                if (appointmentTime != null) {
                    rb_time1.setChecked(false);
                    rb_time1.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time2.setChecked(false);
                    rb_time2.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time3.setChecked(false);
                    rb_time3.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time4.setChecked(false);
                    rb_time4.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time5.setChecked(true);
                    rb_time5.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time6.setChecked(false);
                    rb_time6.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time7.setChecked(false);
                    rb_time7.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time8.setChecked(false);
                    rb_time8.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time9.setChecked(false);
                    rb_time9.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time10.setChecked(false);
                    rb_time10.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time11.setChecked(false);
                    rb_time11.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time12.setChecked(false);
                    rb_time12.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rbTime = rb_time5.getText().toString().trim();
                } else {
                    ToastUtil.shortDiyToastByRec(getApplicationContext(), R.string.date_first);
                }
                break;
            case R.id.rb_time6:
                if (appointmentTime != null) {
                    rb_time1.setChecked(false);
                    rb_time1.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time2.setChecked(false);
                    rb_time2.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time3.setChecked(false);
                    rb_time3.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time4.setChecked(false);
                    rb_time4.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time5.setChecked(false);
                    rb_time5.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time6.setChecked(true);
                    rb_time6.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time7.setChecked(false);
                    rb_time7.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time8.setChecked(false);
                    rb_time8.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time9.setChecked(false);
                    rb_time9.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time10.setChecked(false);
                    rb_time10.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time11.setChecked(false);
                    rb_time11.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time12.setChecked(false);
                    rb_time12.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rbTime = rb_time6.getText().toString().trim();
                } else {
                    ToastUtil.shortDiyToastByRec(getApplicationContext(), R.string.date_first);
                }
                break;
            case R.id.rb_time7:
                if (appointmentTime != null) {
                    rb_time1.setChecked(false);
                    rb_time1.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time2.setChecked(false);
                    rb_time2.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time3.setChecked(false);
                    rb_time3.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time4.setChecked(false);
                    rb_time4.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time5.setChecked(false);
                    rb_time5.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time6.setChecked(false);
                    rb_time6.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time7.setChecked(true);
                    rb_time7.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time8.setChecked(false);
                    rb_time8.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time9.setChecked(false);
                    rb_time9.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time10.setChecked(false);
                    rb_time10.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time11.setChecked(false);
                    rb_time11.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time12.setChecked(false);
                    rb_time12.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rbTime = rb_time5.getText().toString().trim();
                } else {
                    ToastUtil.shortDiyToastByRec(getApplicationContext(), R.string.date_first);
                }
                break;
            case R.id.rb_time8:
                if (appointmentTime != null) {
                    rb_time1.setChecked(false);
                    rb_time1.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time2.setChecked(false);
                    rb_time2.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time3.setChecked(false);
                    rb_time3.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time4.setChecked(false);
                    rb_time4.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time5.setChecked(false);
                    rb_time5.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time6.setChecked(false);
                    rb_time6.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time7.setChecked(false);
                    rb_time7.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time8.setChecked(true);
                    rb_time8.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time9.setChecked(false);
                    rb_time9.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time10.setChecked(false);
                    rb_time10.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time11.setChecked(false);
                    rb_time11.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time12.setChecked(false);
                    rb_time12.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rbTime = rb_time8.getText().toString().trim();
                } else {
                    ToastUtil.shortDiyToastByRec(getApplicationContext(), R.string.date_first);
                }
                break;
            case R.id.rb_time9:
                if (appointmentTime != null) {
                    rb_time1.setChecked(false);
                    rb_time1.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time2.setChecked(false);
                    rb_time2.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time3.setChecked(false);
                    rb_time3.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time4.setChecked(false);
                    rb_time4.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time5.setChecked(false);
                    rb_time5.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time6.setChecked(false);
                    rb_time6.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time7.setChecked(false);
                    rb_time7.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time8.setChecked(false);
                    rb_time8.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time9.setChecked(true);
                    rb_time9.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time10.setChecked(false);
                    rb_time10.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time11.setChecked(false);
                    rb_time11.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time12.setChecked(false);
                    rb_time12.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rbTime = rb_time9.getText().toString().trim();
                } else {
                    ToastUtil.shortDiyToastByRec(getApplicationContext(), R.string.date_first);
                }
                break;
            case R.id.rb_time10:
                if (appointmentTime != null) {
                    rb_time1.setChecked(false);
                    rb_time1.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time2.setChecked(false);
                    rb_time2.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time3.setChecked(false);
                    rb_time3.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time4.setChecked(false);
                    rb_time4.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time5.setChecked(false);
                    rb_time5.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time6.setChecked(false);
                    rb_time6.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time7.setChecked(false);
                    rb_time7.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time8.setChecked(false);
                    rb_time8.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time9.setChecked(false);
                    rb_time9.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time10.setChecked(true);
                    rb_time10.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time11.setChecked(false);
                    rb_time11.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time12.setChecked(false);
                    rb_time12.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rbTime = rb_time10.getText().toString().trim();
                } else {
                    ToastUtil.shortDiyToastByRec(getApplicationContext(), R.string.date_first);
                }
                break;
            case R.id.rb_time11:
                if (appointmentTime != null) {
                    rb_time1.setChecked(false);
                    rb_time1.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time2.setChecked(false);
                    rb_time2.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time3.setChecked(false);
                    rb_time3.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time4.setChecked(false);
                    rb_time4.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time5.setChecked(false);
                    rb_time5.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time6.setChecked(false);
                    rb_time6.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time7.setChecked(false);
                    rb_time7.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time8.setChecked(false);
                    rb_time8.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time9.setChecked(false);
                    rb_time9.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time10.setChecked(false);
                    rb_time10.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time11.setChecked(true);
                    rb_time11.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time12.setChecked(false);
                    rb_time12.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rbTime = rb_time11.getText().toString().trim();
                } else {
                    ToastUtil.shortDiyToastByRec(getApplicationContext(), R.string.date_first);
                }
                break;
            case R.id.rb_time12:
                if (appointmentTime != null) {
                    rb_time1.setChecked(false);
                    rb_time1.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time2.setChecked(false);
                    rb_time2.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time3.setChecked(false);
                    rb_time3.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time4.setChecked(false);
                    rb_time4.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time5.setChecked(false);
                    rb_time5.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time6.setChecked(false);
                    rb_time6.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time7.setChecked(false);
                    rb_time7.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time8.setChecked(false);
                    rb_time8.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time9.setChecked(false);
                    rb_time9.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time10.setChecked(false);
                    rb_time10.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time11.setChecked(false);
                    rb_time11.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rb_time12.setChecked(true);
                    rb_time12.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.selector_radiobutton));
                    rbTime = rb_time12.getText().toString().trim();
                } else {
                    ToastUtil.shortDiyToastByRec(getApplicationContext(), R.string.date_first);
                }
                break;
        }
        if (rbTime !=null || !TextUtils.isEmpty(rbTime) ){
            tv_appointment_time.setText(appointmentTime + rbTime);
//            String[] split = rbTime.split("-");
//            rbTime = split[0]  +"至"+split[1];
//            tv_appointment_time.setText(appointmentTime + rbTime);
        }else{
            tv_appointment_time.setText(appointmentTime);
        }
    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            adapter.setSelectedPosition(i);
            rbTime = times.get(i);
            tv_appointment_time.setText(appointmentTime + rbTime);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int eventCode = motionEvent.getAction();
        switch(eventCode){
            case MotionEvent.ACTION_DOWN:
                downX = (int) motionEvent.getX();
                downY = (int) motionEvent.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = (int) motionEvent.getY();
                moveX = (int) motionEvent.getX();
                break;
            case MotionEvent.ACTION_UP:
                upX = (int) motionEvent.getX();
                upY = (int) motionEvent.getY();
                if ((upX - downX) > 0)
                    monthDateView.onLeftClick();
                if ((upX - downX) < 0)
                    monthDateView.onRightClick();
                if(Math.abs(upX-downX) < 10 && Math.abs(upY - downY) < 10){//点击事件
                    monthDateView.doClickAction((upX + downX)/2,(upY + downY)/2);
                }
                appointmentTime = monthDateView.getmSelYear() + "年" + monthDateView.getmSelMonth() + "月" + monthDateView.getmSelDay() + "日";
                if (rbTime ==null || TextUtils.isEmpty(rbTime))
                tv_appointment_time.setText(appointmentTime);
                else
                tv_appointment_time.setText(appointmentTime+rbTime);
                break;
        }
        return true;
    }
}
