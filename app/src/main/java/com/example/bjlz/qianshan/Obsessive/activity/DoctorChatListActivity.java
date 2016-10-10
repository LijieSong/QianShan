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

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.example.bjlz.qianshan.CurrencyActivities.ChatActivity;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.Obsessive.adapter.DoctorListAdapter;
import com.example.bjlz.qianshan.Obsessive.bean.OneKeySaveBean;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.pull.SwipyRefreshLayout;
import com.example.bjlz.qianshan.tools.PermissionsManager.PermissionsManager;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.ToastUtil;
import com.example.bjlz.qianshan.views.DividerItemDecoration;
import com.example.bjlz.qianshan.views.TitleBarView;

/**
 * 项目名称：QianShan
 * 类描述：DoctorChatListActivity  视频问诊医生列表
 * 创建人：slj
 * 创建时间：2016-7-22 11:47
 * 修改人：slj
 * 修改时间：2016-7-22 11:47
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class DoctorChatListActivity extends BaseActivity implements SwipyRefreshLayout.OnRefreshListener,DoctorListAdapter.OnRecyclerViewItemClickListener{
    private TitleBarView title_bar;//标题
    private Context mContext = null;//上下文对象
    private RecyclerView v7_recyerview;//recycleview
    private SwipyRefreshLayout refreshLayout;//刷新的控制
    private final int TOP_REFRESH = 1;//下拉刷新
    private final int BOTTOM_REFRESH = 2;//上拉加载更多
    private DoctorListAdapter adapter;//适配器
//    呼叫谁
    private LinearLayoutManager linearLayoutManager;//布局管理器
    private String[] names = {"王小二", "李晓光", "张三", "李四", "王麻子", "轩辕红","孤狼","小九"};
    private String[] mobiles = {"13371700146","10086","1008611","10010","10060","10000","13782551343","15712881338"};
    private int CALL_PHONE_PERMISSION_CODE = 55;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_key_save);
        MyApplication.getInstance().addActivity(this);
        mContext = this;
        // 消息监听器
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission_group.CAMERA,Manifest.permission.CALL_PHONE},CALL_PHONE_PERMISSION_CODE);
        }
        getData();
        initView();
        initData();
        setOnClick();
    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        //刷新
        this.refreshLayout = (SwipyRefreshLayout) findViewById(R.id.refreshLayout);
        v7_recyerview= (RecyclerView) findViewById(R.id.v7_recyerview);
    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.doctor_chat_list);
        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        v7_recyerview.setLayoutManager(linearLayoutManager);
        v7_recyerview.addItemDecoration(new DividerItemDecoration(mContext,linearLayoutManager.getOrientation(),getResources().getColor(R.color.devide_line)));
         initRecycleView();
    }

    /**
     * 初始化recycleView
     */
    private void initRecycleView() {
        adapter=new DoctorListAdapter(this,names,mobiles);
        v7_recyerview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void getData() {

    }

    @Override
    public void setOnClick() {
        refreshLayout.setOnRefreshListener(this);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void WeightOnClick(View v) {

    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onRefresh(int index) {
        dataOption(TOP_REFRESH);//下拉刷新
        ToastUtil.shortDiyToastByRec(getApplicationContext(), R.string.data_is_new);
    }

    @Override
    public void onLoad(int index) {
        dataOption(BOTTOM_REFRESH);//上拉加载更多
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
//         adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, OneKeySaveBean bean) {
//        Intent i=new Intent();
//        i.setClass(DoctorChatListActivity.this,ChatActivity.class);
//        i.putExtra("userId", bean.getMobile());
//        startActivity(i);
        startActivity(new Intent(DoctorChatListActivity.this,ChatActivity.class).putExtra("userId",bean.getMobile()));
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}
