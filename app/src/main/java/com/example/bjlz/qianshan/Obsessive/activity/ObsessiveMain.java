package com.example.bjlz.qianshan.Obsessive.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.bjlz.qianshan.CurrencyActivities.WebViewActivity;
import com.example.bjlz.qianshan.CurrencyAdapter.MessageAdapter;
import com.example.bjlz.qianshan.CurrencyApplication.Address;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.CurrencyBean.Menulist;
import com.example.bjlz.qianshan.CurrencyBean.MsgBean;
import com.example.bjlz.qianshan.Obsessive.adapter.GradViewAdapter;
import com.example.bjlz.qianshan.Obsessive.bluetooth.activity.BloodPressureActivity;
import com.example.bjlz.qianshan.Obsessive.bluetooth.activity.BlueToothActivity;
import com.example.bjlz.qianshan.Obsessive.bluetooth.utils.BluetoothUtils;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.pull.SwipyRefreshLayout;
import com.example.bjlz.qianshan.tools.BitmapTools.GildeTools.GlideUtils;
import com.example.bjlz.qianshan.tools.ChangeAndGetTools.CommonUtil;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.LogUtils;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.PreferencesUtils;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.ScreenUtils;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.ToastUtil;
import com.example.bjlz.qianshan.tools.volley.VolleyListenerInterface;
import com.example.bjlz.qianshan.tools.volley.VolleyRequestUtil;
import com.example.bjlz.qianshan.views.DividerItemDecoration;
import com.example.bjlz.qianshan.views.TitleBarView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：QianShan
 * 类描述：ObsessiveMain 患者的主界面
 * 创建人：slj
 * 创建时间：2016-7-20 11:33
 * 修改人：slj
 * 修改时间：2016-7-20 11:33
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class ObsessiveMain extends BaseActivity implements View.OnTouchListener,SwipyRefreshLayout.OnRefreshListener,MessageAdapter.OnRecyclerViewItemClickListener{
    /**
     * 滚动显示和隐藏menu时，手指滑动需要达到的速度。
     */
    public static final int SNAP_VELOCITY = 200;

    /**
     * 屏幕宽度值。
     */
    private int screenWidth;

    /**
     * menu最多可以滑动到的左边缘。值由menu布局的宽度来定，marginLeft到达此值之后，不能再减少。
     */
    private int leftEdge;

    /**
     * menu最多可以滑动到的右边缘。值恒为0，即marginLeft到达0之后，不能增加。
     */
    private int rightEdge = 0;

    /**
     * menu完全显示时，留给content的宽度值。
     */
    private int menuPadding = 150;

    /**
     * 主内容的布局。
     */
    private View content;

    /**
     * menu的布局。
     */
    private View menu;

    /**
     * menu布局的参数，通过此参数来更改leftMargin的值。
     */
    private LinearLayout.LayoutParams menuParams;

    /**
     * 记录手指按下时的横坐标。
     */
    private float xDown;

    /**
     * 记录手指移动时的横坐标。
     */
    private float xMove;

    /**
     * 记录手机抬起时的横坐标。
     */
    private float xUp;

    /**
     * menu当前是显示还是隐藏。只有完全显示或隐藏menu时才会更改此值，滑动过程中此值无效。
     */
    private boolean isMenuVisible;

    /**
     * 用于计算手指滑动的速度。
     */
    private VelocityTracker mVelocityTracker;

    private boolean isLogin = false;//记录是否登录
    private long exitTime = 0;//记录点击时间
    private TitleBarView title_bar;//标题
    private String userName,userPwd;//用户名
    private Context mContext;
    private Button btn_login;//登录按钮
    private ImageView touXiang;//头像
    private TextView text_nicheng;//昵称
    private RecyclerView v7_recyerview;//recycleview
    private SwipyRefreshLayout refreshLayout;//刷新的控制
    private final int TOP_REFRESH = 1;//下拉刷新
    private final int BOTTOM_REFRESH = 2;//上拉加载更多
    //item信息
    private int[] gride_image = {R.drawable.logo, R.drawable.logo, R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo};
    private String[] gride_name = {"智能药盒", "智能血压仪", "个人中心","预约诊疗","一键急救","视频问诊"};
    //快捷键
    private GridView gridView_kuai_jie;
    //蓝牙适配器
    private BluetoothUtils bluetoothUtils;
    //权限请求吗
    private int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 10;
    //侧滑菜单
    private List<Menulist> menulists = new ArrayList<>();
    private List<Menulist> menulists2 = new ArrayList<>();
    private List<Menulist> menulists3 = new ArrayList<>();
    //侧滑三级菜单
    private LinearLayout ll_menu;
    private LayoutInflater layoutInflater;
    private String isDoctor;//是否是医生
    private int menuId;//点击条目的ID
    //临时的消息展示
    private List<String> msgContext; //消息内容
    private List<String> msgTime;//消息时间
    private MessageAdapter adapter;//待处理消息匹配器

    //handler刷新消息
    private Handler hanlder = new Handler();
    private Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.getInstance().addActivity(this);
        layoutInflater = LayoutInflater.from(this);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH,Manifest.permission.BLUETOOTH_ADMIN,Manifest.permission.BLUETOOTH_PRIVILEGED,Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        }
        mContext = this;
        bluetoothUtils = new BluetoothUtils(ObsessiveMain.this);
        getIsLogin();//获取登录状态
        getData();
        initView();
        setScreenWidth();
        initData();
        setOnClick();

    }

    private void getIsLogin() {
        isLogin = PreferencesUtils.getBoolean(getApplicationContext(), "isLogin", false);
    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        //侧滑
        content = findViewById(R.id.content);
        menu = findViewById(R.id.menu);
        this.btn_login = (Button) findViewById(R.id.btn_login);
        text_nicheng = (TextView) findViewById(R.id.text_nicheng);
        touXiang = (ImageView) findViewById(R.id.image_tou_xiang);
//        Bitmap image = GLFont.getImage(60, 60, "姜浩", 30);
//        Drawable drawable =new BitmapDrawable(image);
//        touXiang.setBackgroundDrawable(drawable);
        GlideUtils.downLoadCircleImage(this,Address.TEXT_IMAGEURL2,touXiang);
        //刷新
        this.refreshLayout = (SwipyRefreshLayout) findViewById(R.id.refreshLayout);
        v7_recyerview= (RecyclerView) findViewById(R.id.v7_recyerview);
        //三级侧滑菜单
        ll_menu= (LinearLayout) findViewById(R.id.ll_menu);
        //快捷键
        gridView_kuai_jie = (GridView) findViewById(R.id.gridView_kuai_jie);

    }
    @Override
    protected void onResume() {
        super.onResume();
        getIsLogin();
        String[] nameAndPwd = PreferencesUtils.GetUserNameAndPwd(getApplicationContext());
        if (nameAndPwd != null)
            userName = nameAndPwd[0];
            userPwd = nameAndPwd[1];
        //获取到的三级列表
        isDoctor = PreferencesUtils.getString(getApplicationContext(), "isDoctor", null);
//        GetMenuList();
        if (isLogin == true) {
            touXiang.setVisibility(View.VISIBLE);
//            this.title_bar.setTitleText(userName);
            text_nicheng.setText(userName);
            this.btn_login.setText(R.string.exit);
//            runnable = new Runnable() {
//                @Override
//                public void run() {
//                    hanlder.postDelayed(this,5000);
//                    refreshLayout.autoRefresh();
////                    LogUtils.error("自动刷新");
//                }
//            };
//            hanlder.postDelayed(runnable,5000);
        }

    }
    public void setScreenWidth(){
        menuParams = (LinearLayout.LayoutParams) menu.getLayoutParams();
        // 将menu的宽度设置为屏幕宽度减去menuPadding
        menuParams.width = screenWidth - menuPadding;
        // 左边缘的值赋值为menu宽度的负数
        leftEdge = -menuParams.width;
        // menu的leftMargin设置为左边缘的值，这样初始化时menu就变为不可见
        menuParams.leftMargin = leftEdge;
        // 将content的宽度设置为屏幕宽度
        content.getLayoutParams().width = screenWidth;

    }
    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.app_name);
        this.title_bar.setLeftBtnIcon(R.drawable.menu);
//        this.title_bar.setTitleText(userName);
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
        final GradViewAdapter adapter = new GradViewAdapter(this, gradeViewList);
        gridView_kuai_jie.setAdapter(adapter);
        //初始化recycleview
        LinearLayoutManager layout = new LinearLayoutManager(this);
        v7_recyerview.setLayoutManager(layout);
        //设置为垂直布局，这也是默认的
        layout.setOrientation(OrientationHelper.VERTICAL);
        v7_recyerview.setHasFixedSize(true);
        v7_recyerview.addItemDecoration(new DividerItemDecoration(this,layout.getOrientation(),CommonUtil.getColor(R.color.devide_line)));
         GetMenuList();//获取菜单
        initRecycleView();
    }

    private void initRecycleView() {
        msgContext = new ArrayList<>();
        msgContext.add("这是一条已读消息");
        msgContext.add("这是一条未读消息");
        msgContext.add("这是一条已读消息");
        msgContext.add("这是一条未读消息");
        msgContext.add("这是一条已读消息");
        msgContext.add("这是一条未读消息");
        msgContext.add("这是一条已读消息");
        msgContext.add("这是一条未读消息");
        msgContext.add("这是一条系统消息");
        msgContext.add("这是一条系统消息");
        msgContext.add("这是一条测试消息");
        msgContext.add("这是一条测试消息");
        msgTime = new ArrayList<>();
        msgTime.add("2016-08-02 08:58:38");
        msgTime.add("2016-08-02 08:40:32");
        msgTime.add("2016-08-02 08:58:38");
        msgTime.add("2016-08-02 08:40:32");
        msgTime.add("2016-08-02 08:58:38");
        msgTime.add("2016-08-02 08:40:32");
        msgTime.add("2016-08-02 08:58:38");
        msgTime.add("2016-08-02 08:40:32");
        msgTime.add("2016-08-02 08:38:36");
        msgTime.add("2016-08-02 08:46:34");
        msgTime.add("2016-08-03 08:40:35");
        msgTime.add("2016-08-03 08:45:55");
        //待处理消息
        adapter = new MessageAdapter(this,msgContext,msgTime);
        v7_recyerview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void getData() {
        screenWidth = ScreenUtils.getScreenWidth(this);
        isDoctor = PreferencesUtils.getString(getApplicationContext(), "isDoctor", null);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            userName = bundle.getString("userName");
        }
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            ToastUtil.shortDiyToastByRec(getApplicationContext(),R.string.ble_not_supported);
//            finish();
        } else{
            BluetoothAdapter adapter = bluetoothUtils.getBluetoothAdapter(ObsessiveMain.this);

            if (adapter == null || !adapter.isEnabled()) {
                LogUtils.error( "打开蓝牙...");
                adapter.enable();
            }
        }
    }

    @Override
    public void setOnClick() {
        content.setOnTouchListener(this);//主界面触摸监听
        // 登录按钮的点击事件
        this.btn_login.setOnClickListener(this);
        //昵称的点击事件
        text_nicheng.setOnClickListener(this);
        //头像的点击事件
        touXiang.setOnClickListener(this);
        //设置刷新
        refreshLayout.setOnRefreshListener(this);
        //设置grideview点击
        gridView_kuai_jie.setOnItemClickListener(this);
        //消息的点击事件
        adapter.setOnItemClickListener(this);
        //菜单的点击事件
        this.title_bar.setLeftBtnOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMenuVisible != true) {
                    scrollToMenu();
                } else {
                    scrollToContent();
                }
            }
        });
    }

    @Override
    public void WeightOnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login://退出
                PreferencesUtils.clear(getApplicationContext());
                EMClient.getInstance().logout(true, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        LogUtils.error("退出成功");
//                        PreferencesUtils.clear(getApplicationContext());
                        MyApplication.getInstance().close();
                        System.exit(0);
                    }

                    @Override
                    public void onError(final int i, final String s) {
                        LogUtils.error("code:"+i+",错误信息:"+s);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                ToastUtil.shortDiyToast(getApplicationContext(),"code:"+i+",错误信息:"+s);
//                            }
//                        });
//                        PreferencesUtils.clear(getApplicationContext());
//                        MyApplication.getInstance().close();
//                        System.exit(0);
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
                break;
            case R.id.text_nicheng:
            case R.id.image_tou_xiang:
                Bundle bundle = new Bundle();
                bundle.putString("title","个人信息");
                bundle.putString("url", Address.TEXT_URL4);
                MyApplication.startActivity(ObsessiveMain.this, WebViewActivity.class,bundle);
//                MyApplication.startActivity(ObsessiveMain.this, InformationActivity.class);
                break;
        }
    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle bundle = new Bundle();
        switch(gride_name[i]){
            case "智能药盒":
                MyApplication.startActivity(ObsessiveMain.this, BlueToothActivity.class);
            break;
            case "智能血压仪":
                if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
                    ToastUtil.shortDiyToastByRec(getApplicationContext(),R.string.ble_not_supported);
                } else {
                    MyApplication.startActivity(ObsessiveMain.this, BloodPressureActivity.class);
                }
                break;
            case "个人中心":
                bundle.putString("title",gride_name[i]);
                bundle.putString("url", Address.TEXT_URL4);
                MyApplication.startActivity(ObsessiveMain.this, WebViewActivity.class,bundle);
                break;
            case "预约诊疗":
                MyApplication.startActivity(ObsessiveMain.this, ObsessiveAppointmentActivity.class);
                break;
            case "一键急救":
                MyApplication.startActivity(ObsessiveMain.this, OneKeySaveActivity.class);
                break;
            case "视频问诊":
                EMClient.getInstance().login("15712881338", "111111", new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        MyApplication.startActivity(ObsessiveMain.this, DoctorChatListActivity.class);
                    }

                    @Override
                    public void onError(final int i,final String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.shortDiyToast(getApplicationContext(),"code:"+i+"---原因:"+s);
                            }
                        });
//                        CommonUtil.runOnUIThreadToast(getApplicationContext(),"code:"+i+"---原因:"+s);
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//            if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION) {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    MyApplication.startActivity(ObsessiveMain.this, BloodPressureActivity.class);
//                    // permission was granted, yay! Do the
//                    // contacts-related task you need to do.
//
//                } else {
//                    ToastUtil.shortDiyToastByRec(getApplicationContext(),R.string.ble_not_permissions);
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                }
//                return;
//            }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onItemClick(View view, MsgBean data) {
        Bundle bundle = new Bundle();
        bundle.putString("msgContent",data.getMsg());
        bundle.putString("msgTime",data.getMsgTime());
        bundle.putString("url",Address.TEXT_URL4);
        bundle.putString("title","消息详情");
//        MyApplication.startActivity(ObsessiveMain.this,TextActivity.class,bundle);
        MyApplication.startActivity(ObsessiveMain.this,WebViewActivity.class,bundle);
//        MyApplication.startActivity(ObsessiveMain.this,WebViewTextActivity.class,bundle);
    }
    @Override
    public void onRefresh(int index) {
        dataOption(TOP_REFRESH);
        ToastUtil.shortDiyToastByRec(getApplicationContext(),R.string.data_is_new);
    }

    @Override
    public void onLoad(int index) {
        dataOption(BOTTOM_REFRESH);
        ToastUtil.shortDiyToastByRec(getApplicationContext(),R.string.data_is_load_over);
    }
    private void dataOption(int option){
        switch (option) {
            case TOP_REFRESH:
                //下拉刷新
//                initData();
                initRecycleView();
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
//                initData();
                initRecycleView();
                break;
        }
    }

    /**
     * 获取一级列表
     */
    public void GetMenuList(){
        Map<String,String> map =new HashMap<>();
        map.put("isDoctor",isDoctor);
        VolleyRequestUtil.RequestPost(mContext, Address.GetMenu1_URL, "menuList", map, new VolleyListenerInterface() {
            @Override
            public void onMySuccess(String result) {
                if (result !=null){
                    try {
                        ll_menu.removeAllViews();
                        menulists.clear();
                        JSONObject object = new JSONObject(result);
                        String menuList = object.getString("menuList");
                        JSONArray array = new JSONArray(menuList);
                        for (int i = 0; i <array.length() ; i++) {
                            Menulist menulist = new Menulist();
                            String activityName = array.getJSONObject(i).getString("activity_name");
                            int id = array.getJSONObject(i).getInt("id");
                            menulist.setActivityName(activityName);
                            menulist.setId(id);
                            menulists.add(menulist);
                        }
                        //获取成功后填充数据
                        for (int i = 0; i < menulists.size(); i++) {
                            ll_menu.addView(getView1(menulists.get(i)));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onMyError(VolleyError error) {
                LogUtils.error("menuList--error:" +error);
            }
        });
    }

    /**
     * 获取二级列表
     * @param ll
     */
    public void GetMenuList2(final LinearLayout ll){
        Map<String,String> map =new HashMap<>();
        map.put("menuId",menuId+"");
        VolleyRequestUtil.RequestPost(mContext, Address.GetMenu2_URL, "menuList", map, new VolleyListenerInterface() {
            @Override
            public void onMySuccess(String result) {
                if (result !=null){
                    try {
                        ll.removeAllViews();
                        menulists2.clear();
                        JSONObject object = new JSONObject(result);
                        String menuList = object.getString("menuList");
                        JSONArray array = new JSONArray(menuList);
                        for (int i = 0; i <array.length() ; i++) {
                            Menulist menulist = new Menulist();
                            String activityName = array.getJSONObject(i).getString("activity_name");
                            int id = array.getJSONObject(i).getInt("id");
                            menulist.setActivityName(activityName);
                            menulist.setId(id);
                            menulists2.add(menulist);
                        }
                        for (int i = 0; i < menulists2.size(); i++) {
                            ll.addView(getView2(menulists2.get(i)));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onMyError(VolleyError error) {
                LogUtils.error("menuList--error:" +error);
            }
        });
    }

    /**
     * 获取三级列表
     * @param ll
     */
    public void GetMenuList3(final LinearLayout ll){
        final Map<String,String> map =new HashMap<>();
        map.put("menuId",menuId+"");
        VolleyRequestUtil.RequestPost(mContext, Address.GetMenu2_URL, "menuList", map, new VolleyListenerInterface() {
            @Override
            public void onMySuccess(String result) {
                if (result !=null){
                    try {
                        ll.removeAllViews();
                        menulists3.clear();
                        JSONObject object = new JSONObject(result);
                        String menuList = object.getString("menuList");
                        JSONArray array = new JSONArray(menuList);
                        for (int i = 0; i <array.length() ; i++) {
                            Menulist menulist = new Menulist();
                            String activityName = array.getJSONObject(i).getString("activity_name");
                            int id = array.getJSONObject(i).getInt("id");
                            menulist.setActivityName(activityName);
                            menulist.setId(id);
                            menulists3.add(menulist);
                        }
                        for (int i = 0; i < menulists3.size(); i++) {
                            ll.addView(getView3(menulists3.get(i)));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onMyError(VolleyError error) {
                LogUtils.error("menuList--error:" +error);
            }
        });
    }
    /**
     * 按2次退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //退出的判断
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(ObsessiveMain.this, "再按一次退出千山健康", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            MyApplication.getInstance().close();
            System.exit(0);
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时，记录按下时的横坐标
                xDown = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                // 手指移动时，对比按下时的横坐标，计算出移动的距离，来调整menu的leftMargin值，从而显示和隐藏menu
                xMove = event.getRawX();
                int distanceX = (int) (xMove - xDown);
                content.setFocusableInTouchMode(true);
                menu.setFocusableInTouchMode(true);
                if (isMenuVisible) {
                    menuParams.leftMargin = distanceX;
                } else {
                    menuParams.leftMargin = leftEdge + distanceX;
                }
                if (menuParams.leftMargin < leftEdge) {
                    menuParams.leftMargin = leftEdge;
                } else if (menuParams.leftMargin > rightEdge) {
                    menuParams.leftMargin = rightEdge;
                }
                menu.setLayoutParams(menuParams);
                break;
            case MotionEvent.ACTION_UP:
                // 手指抬起时，进行判断当前手势的意图，从而决定是滚动到menu界面，还是滚动到content界面
                xUp = event.getRawX();
                if (wantToShowMenu()) {
                    if (shouldScrollToMenu()) {
                        scrollToMenu();
                    } else {
                        scrollToContent();
                    }
                } else if (wantToShowContent()) {
                    if (shouldScrollToContent()) {
                        scrollToContent();
                    } else {
                        scrollToMenu();
                    }
                }
                recycleVelocityTracker();
                break;
        }
        return true;
    }

    /**
     * 判断当前手势的意图是不是想显示content。如果手指移动的距离是负数，且当前menu是可见的，则认为当前手势是想要显示content。
     *
     * @return 当前手势想显示content返回true，否则返回false。
     */
    private boolean wantToShowContent() {
        return xUp - xDown < 0 && isMenuVisible;
    }

    /**
     * 判断当前手势的意图是不是想显示menu。如果手指移动的距离是正数，且当前menu是不可见的，则认为当前手势是想要显示menu。
     *
     * @return 当前手势想显示menu返回true，否则返回false。
     */
    private boolean wantToShowMenu() {
        return xUp - xDown > 0 && !isMenuVisible;
    }

    /**
     * 判断是否应该滚动将menu展示出来。如果手指移动距离大于屏幕的1/2，或者手指移动速度大于SNAP_VELOCITY，
     * 就认为应该滚动将menu展示出来。
     *
     * @return 如果应该滚动将menu展示出来返回true，否则返回false。
     */
    private boolean shouldScrollToMenu() {
        return xUp - xDown > screenWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    /**
     * 判断是否应该滚动将content展示出来。如果手指移动距离加上menuPadding大于屏幕的1/2，
     * 或者手指移动速度大于SNAP_VELOCITY， 就认为应该滚动将content展示出来。
     *
     * @return 如果应该滚动将content展示出来返回true，否则返回false。
     */
    private boolean shouldScrollToContent() {
        return xDown - xUp + menuPadding > screenWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    /**
     * 将屏幕滚动到menu界面，滚动速度设定为50.
     */
    private void scrollToMenu() {
        new ScrollTask().execute(50);
    }

    /**
     * 将屏幕滚动到content界面，滚动速度设定为-50.
     */
    private void scrollToContent() {
        new ScrollTask().execute(-50);
    }

    /**
     * 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。
     *
     * @param event content界面的滑动事件
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 获取手指在content界面滑动的速度。
     *
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(velocity);
    }

    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }
    private String MIUI = "Xiaomi";
    private String FLYME = "Meizu";
    private String HUAWEI = "HUAWEI";
    private String QIHU = "360";

    /**
     * 处理手指移动速度
     */
    class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... speed) {
            int leftMargin = menuParams.leftMargin;
            // 根据传入的速度来滚动界面，当滚动到达左边界或右边界时，跳出循环。
            while (true) {
                leftMargin = leftMargin + speed[0];
                if (leftMargin > rightEdge) {
                    leftMargin = rightEdge;
                    break;
                }
                if (leftMargin < leftEdge) {
                    leftMargin = leftEdge;
                    break;
                }
                publishProgress(leftMargin);
                // 为了要有滚动效果产生，每次循环使线程睡眠20毫秒，这样肉眼才能够看到滚动动画。
                String brand = CommonUtil.getBuildBrand();
                if (brand!=null){
                    if (brand.equals(FLYME) || brand.equals(MIUI)||brand.equals(QIHU)){
                        sleep(0);
                    }else{
                        sleep(20);
                    }
                }else{
                    sleep(20);
                }
            }
            if (speed[0] > 0) {
                isMenuVisible = true;
            } else {
                isMenuVisible = false;
            }
            return leftMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... leftMargin) {
            menuParams.leftMargin = leftMargin[0];
            menu.setLayoutParams(menuParams);
        }

        @Override
        protected void onPostExecute(Integer leftMargin) {
            menuParams.leftMargin = leftMargin;
            menu.setLayoutParams(menuParams);
        }
    }

    /**
     * 使当前线程睡眠指定的毫秒数。
     *
     * @param millis 指定当前线程睡眠多久，以毫秒为单位
     */
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取view
     * @param test
     * @return
     */
    View getView1(final Menulist test) {
        View view = layoutInflater.inflate(R.layout.item1, null);
        TextView tv = (TextView) view.findViewById(R.id.tv);//一级标题
        final ImageView img_check = (ImageView) view.findViewById(R.id.img_check);//一级标题展开
        final LinearLayout ll_2 = (LinearLayout) view.findViewById(R.id.ll);//二级列表
        img_check.setBackgroundResource(R.drawable.icon_plusminus_add_black);//展开
        tv.setText(test.getActivityName());
        img_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ll_2.getVisibility() == View.GONE) {//展开
                    ll_2.setVisibility(View.VISIBLE);
                    img_check.setBackgroundResource(R.drawable.icon_plusminus_reduce_black);//展开
//                    ToastUtil.shortDiyToast(ObsessiveMain.this, "打开"+test.getActivityName());
                    if (ll_2.getChildCount() <= 0) {//若没有数据则重新获取
                        menuId = test.getId();
                        GetMenuList2(ll_2);
                    }
                } else {//隐藏
                    ll_2.setVisibility(View.GONE);
                    img_check.setBackgroundResource(R.drawable.icon_plusminus_add_black);//关闭
//                    ToastUtil.shortDiyToast(ObsessiveMain.this, "关闭"+test.getActivityName());
                }
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ll_2.getVisibility() == View.GONE) {//展开
                    ll_2.setVisibility(View.VISIBLE);
                    img_check.setBackgroundResource(R.drawable.icon_plusminus_reduce_black);//展开
//                    ToastUtil.shortDiyToast(ObsessiveMain.this, "打开"+test.getActivityName());
                    if (ll_2.getChildCount() <= 0) {//若没有数据则重新获取
                        menuId = test.getId();
                        GetMenuList2(ll_2);
                    }
                } else {//隐藏
                    ll_2.setVisibility(View.GONE);
                    img_check.setBackgroundResource(R.drawable.icon_plusminus_add_black);//关闭
//                    ToastUtil.shortDiyToast(ObsessiveMain.this, "关闭"+test.getActivityName());
                }
            }
        });
        return view;
    }

    /**
     * 获取view
     * @param test
     * @return
     */
    View getView2(final Menulist test) {
        View view = layoutInflater.inflate(R.layout.item2, null);
        TextView tv = (TextView) view.findViewById(R.id.tv);//二级标题
        final ImageView img_check = (ImageView) view.findViewById(R.id.img_check);//二级列表
        img_check.setBackgroundResource(R.drawable.icon_plusminus_add_black);//展开
        final LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll);//三级列表
        tv.setText(test.getActivityName());
        img_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ll.getVisibility() == View.GONE) {//展开
                    ll.setVisibility(View.VISIBLE);
                    img_check.setBackgroundResource(R.drawable.icon_plusminus_reduce_black);//展开
//                    ToastUtil.shortDiyToast(ObsessiveMain.this, "打开"+test.getActivityName());
                    if (ll.getChildCount() <= 0) {//若没有数据则重新获取
                        menuId = test.getId();
                        GetMenuList3(ll);
                    }
                } else {//隐藏
                    ll.setVisibility(View.GONE);
                    img_check.setBackgroundResource(R.drawable.icon_plusminus_add_black);//关闭
//                    ToastUtil.shortDiyToast(ObsessiveMain.this, "关闭"+test.getActivityName());
                }
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ll.getVisibility() == View.GONE) {//展开
                    ll.setVisibility(View.VISIBLE);
                    img_check.setBackgroundResource(R.drawable.icon_plusminus_reduce_black);//展开
//                    ToastUtil.shortDiyToast(ObsessiveMain.this, "打开"+test.getActivityName());
                    if (ll.getChildCount() <= 0) {//若没有数据则重新获取
                        menuId = test.getId();
                        GetMenuList3(ll);
                    }
                    if (test.getId() !=0){
                        switch(test.getActivityName()) {
                            case "一键急救":
                                MyApplication.startActivity(ObsessiveMain.this, OneKeySaveActivity.class);
                                break;
                            case "问卷":
                                MyApplication.startActivity(ObsessiveMain.this, HealthMessageActivity.class);
                                break;
                            case "健康档案":
                                MyApplication.startActivity(ObsessiveMain.this, UserManagerActivity.class);
                                break;
                        }
                    }
                } else {//隐藏
                    ll.setVisibility(View.GONE);
                    img_check.setBackgroundResource(R.drawable.icon_plusminus_add_black);//关闭
//                    ToastUtil.shortDiyToast(ObsessiveMain.this, "关闭"+test.getActivityName());
                }
            }
        });
        return view;
    }

    /**
     * 获取view
     * @param test
     * @return
     */
    View getView3(final Menulist test) {
        View view = layoutInflater.inflate(R.layout.item3, null);
        TextView tv = (TextView) view.findViewById(R.id.tv);//二级标题
        tv.setText(test.getActivityName());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                switch(test.getActivityName()){
                    case "一键急救":
                        MyApplication.startActivity(ObsessiveMain.this,OneKeySaveActivity.class);
                        break;
                    case "我的信息":
                        bundle.putString("title",test.getActivityName());
                        bundle.putString("url",Address.TEXT_URL4);
                        MyApplication.startActivity(ObsessiveMain.this,WebViewActivity.class,bundle);
                        break;
                    case "我的家人":
                        MyApplication.startActivity(ObsessiveMain.this, OneKeySaveActivity.class);
                        break;
//                    case "一键急救":
//                        MyApplication.startActivity(ObsessiveMain.this,OneKeySaveActivity.class);
//                        break;
//                    case "一键急救":
//                        MyApplication.startActivity(ObsessiveMain.this,OneKeySaveActivity.class);
//                        break;
                }
                ToastUtil.shortDiyToast(ObsessiveMain.this,test.getActivityName());
            }
        });
        return view;
    }
}
