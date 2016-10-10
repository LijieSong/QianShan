package com.example.bjlz.qianshan.CurrencyActivities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;

import com.android.volley.VolleyError;
import com.example.bjlz.qianshan.CurrencyApplication.Address;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.CurrencyBean.UserBean;
import com.example.bjlz.qianshan.Doctor.activity.DoctorMain;
import com.example.bjlz.qianshan.Obsessive.activity.ObsessiveMain;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.ChangeAndGetTools.JsonUtil;
import com.example.bjlz.qianshan.tools.HttpAndNetWorkTools.NetTools.NetworkUtils;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.LogUtils;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.PreferencesUtils;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.ToastUtil;
import com.example.bjlz.qianshan.tools.volley.VolleyErrorHelper;
import com.example.bjlz.qianshan.tools.volley.VolleyListenerInterface;
import com.example.bjlz.qianshan.tools.volley.VolleyRequestUtil;
import com.example.bjlz.qianshan.views.ClearEditText;
import com.example.bjlz.qianshan.views.TitleBarView;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：QianShan
 * 类描述：LoginActivity :登录界面
 * 创建人：slj
 * 创建时间：2016-5-28 17:24
 * 修改人：slj
 * 修改时间：2016-5-28 17:24
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class LoginActivity extends BaseActivity {
    private ClearEditText user_name, user_password;
    private Button btn_login;
    private String name, word, userName, userPwd;//用户名 密码
    private TitleBarView title_bar;
    private Map<String, String> map = null;//存储用户名密码
    private CheckBox cb_tv_remember;//是否保存密码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MyApplication.getInstance().addActivity(this);
        getData();
        initView();
        initData();
        setOnClick();
    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        user_name = (ClearEditText) findViewById(R.id.user_name);
        user_password = (ClearEditText) findViewById(R.id.user_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        cb_tv_remember = (CheckBox) findViewById(R.id.cb_tv_remember);
    }

    @Override
    public void initData() {
        cb_tv_remember.setChecked(true);//默认记住
        this.title_bar.setTitleText(R.string.login);
        this.title_bar.getTitleBarBg().setBackgroundColor(getResources().getColor(R.color.transparent));
        if (name != null || word != null) {
            user_name.setText(name);
            user_password.setText(word);
        }
    }

    @Override
    public void getData() {
        //        map = UserUtils.readInfo(getApplicationContext());
//        if (map !=null){
//            name=map.get("name");
//            word = map.get("word");
//        }
        String[] nameAndPwd = PreferencesUtils.GetUserNameAndPwd(getApplicationContext());
        if (nameAndPwd != null)
            name = nameAndPwd[0];
            word = nameAndPwd[1];
    }

    //设置事件监听
    @Override
    public void setOnClick() {
        btn_login.setOnClickListener(this);
        user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                user_password.setText(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void WeightOnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                userName = user_name.getText().toString().trim();
                userPwd = user_password.getText().toString().trim();
                if (userName == null || TextUtils.isEmpty(userName)) {
                    ToastUtil.shortToastByRes(getApplicationContext(), R.string.name_is_not_allow_null);
                    user_name.setShakeAnimation();
                } else if (userPwd == null || TextUtils.isEmpty(userPwd)) {
                    ToastUtil.shortToastByRes(LoginActivity.this, R.string.pwd_is_not_allow_null);
                    user_password.setShakeAnimation();
                } else {
                    if (NetworkUtils.isConnected(getApplicationContext())) {
                        if (NetworkUtils.isWifi(getApplicationContext())) {
//                            ToastUtil.shortToastBackImgStrByRes(LoginActivity.this, R.string.isWifi, R.drawable.bgtoast);
                        } else if (NetworkUtils.isMobileConnected(getApplicationContext())) {
//                            ToastUtil.shortToastByRes(getApplicationContext(), R.string.isMobile);
                        } else if (NetworkUtils.isNetworkConnected(getApplicationContext())) {
//                            ToastUtil.shortToastByRes(getApplicationContext(), R.string.isMobileNet);
                        }
                        Map<String, String> param = new HashMap<>();
                        param.put("userName", userName);
                        param.put("password", userPwd);
                        VolleyRequestUtil.RequestPost(LoginActivity.this, Address.Login_Url, "login", param, new VolleyListenerInterface() {
                            @Override
                            public void onMySuccess(String result) {
//                                LogUtils.error("loginResult:"+result);
                                if (result != null) {
                                    try {
                                        UserBean bean = JsonUtil.parseJsonToBean(result, UserBean.class);
                                        boolean msgCode = bean.getMsgCode();
                                        if (msgCode ==true){
                                            String userId = bean.getName();
                                            ToastUtil.shortToastByRes(LoginActivity.this, R.string.login_success);
//                                            SnackBarUtils.GetSnackBar(LoginActivity.this,btn_login,R.string.login_success,R.color.black);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("userName", userId);
                                            //保存账号密码
                                            PreferencesUtils.RememberUser(getApplicationContext(), userName, userPwd);
//                                          UserUtils.saveInfo(getApplicationContext(), userName,userPwd);
                                            //存储集合
                                            PreferencesUtils.putBoolean(getApplicationContext(), "isLogin", msgCode);
                                            if (bean.getIsDoctor().equals("0")){
                                                PreferencesUtils.putString(getApplicationContext(), "isDoctor", "0");
                                                MyApplication.startActivity(LoginActivity.this, ObsessiveMain.class, bundle);
                                            }else{
                                                PreferencesUtils.putString(getApplicationContext(), "isDoctor", "1");
                                                MyApplication.startActivity(LoginActivity.this, DoctorMain.class, bundle);
                                            }
                                        } else {
                                            ToastUtil.shortToastByRes(getApplicationContext(), R.string.failed_login);
//                                            SnackBarUtils.GetSnackBar(LoginActivity.this,btn_login,R.string.failed_login,R.color.black);
                                        }
                                    } catch (Exception e) {
                                        ToastUtil.shortToastByRes(getApplicationContext(), R.string.notContent);
//                                        SnackBarUtils.GetSnackBar(LoginActivity.this,btn_login,R.string.notContent,R.color.black);
                                        e.printStackTrace();
                                    }
                                } else {
                                    ToastUtil.shortToastByRes(getApplicationContext(), R.string.failed_login);
//                                    SnackBarUtils.GetSnackBar(LoginActivity.this,btn_login,R.string.failed_login,R.color.black);
                                }
                            }

                            @Override
                            public void onMyError(VolleyError error) {
                                LogUtils.error("error:" + error);
//                                SnackBarUtils.GetSnackBar(LoginActivity.this,btn_login,VolleyErrorHelper.getMessage(error, getApplicationContext()),R.color.black);
                                ToastUtil.shortDiyToast(getApplicationContext(), VolleyErrorHelper.getMessage(error, getApplicationContext()));
                            }
                        });
                    } else {
//                        SnackBarUtils.GetSnackBar(LoginActivity.this,btn_login,R.string.notContent,R.color.black);
                        ToastUtil.shortToastByRes(getApplicationContext(), R.string.notContent);
                    }
                }

                break;
        }
    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
