package com.example.bjlz.qianshan.Obsessive.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.example.bjlz.qianshan.CurrencyApplication.Address;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.Obsessive.adapter.HealthReportAdapter;
import com.example.bjlz.qianshan.Obsessive.bean.UserHealthBean;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.pull.SwipyRefreshLayout;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.ToastUtil;
import com.example.bjlz.qianshan.views.DividerItemDecoration;
import com.example.bjlz.qianshan.views.TitleBarView;

/**
 * 项目名称：QianShanDoctor
 * 类描述：HealthReportActivity 健康私人报告
 * 创建人：slj
 * 创建时间：2016-6-30 15:40
 * 修改人：slj
 * 修改时间：2016-6-30 15:40
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class HealthReportActivity extends BaseActivity implements SwipyRefreshLayout.OnRefreshListener, HealthReportAdapter.OnRecyclerViewItemClickListener {

    private TitleBarView title_bar;//标题
    private Context mContext = null;//上下文对象

    private RecyclerView v7_recyerview;//recycleview
    private SwipyRefreshLayout refreshLayout;//刷新的控制

    private final int TOP_REFRESH = 1;//下拉刷新
    private final int BOTTOM_REFRESH = 2;//上拉加载更多
    private HealthReportAdapter adapter;//适配器
    private LinearLayoutManager linearLayoutManager = null;//布局管理器

    private String name;//名字
    private String[] healthUrl = {Address.TEXT_URL1,Address.TEXT_URL2, Address.TEXT_URL3, Address.TEXT_URL4, Address.TEXT_URL2, Address.TEXT_URL4};
    private String[] user_names = {"王小二", "李晓光", "张三", "李四", "王麻子", "轩辕红"};
    private String[] reportStatus = {"报告审核界面", "报告审核界面", "报告审核界面", "报告审核界面", "报告审核界面", "报告审核界面"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthreport);
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
        this.v7_recyerview = (RecyclerView) findViewById(R.id.v7_recyerview);
        this.refreshLayout = (SwipyRefreshLayout) findViewById(R.id.refreshLayout);
    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.health_report);
        this.linearLayoutManager = new LinearLayoutManager(this);
        this.linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.v7_recyerview.setLayoutManager(linearLayoutManager);
        this.v7_recyerview.addItemDecoration(new DividerItemDecoration(mContext, linearLayoutManager.getOrientation(), getResources().getColor(R.color.devide_line)));
        adapter = new HealthReportAdapter(this, reportStatus, user_names, healthUrl);
//        adapter.setOnItemClickListener(this);
        this.v7_recyerview.setAdapter(adapter);
        this.refreshLayout.setRefreshing(false);
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
        refreshLayout.setOnRefreshListener(this);
//        adapter.setOnItemClickListener(this);
    }

    @Override
    public void WeightOnClick(View v) {
//        switch (v.getId()){
//            case R.id.btn_delete_report:
//            break;
//            case R.id.btn_look_report:
//                break;
//        }
    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemClick(View view, final UserHealthBean data) {
//        final Bundle bundle = new Bundle();
//        Button btn_look_report = (Button) view.findViewById(R.id.btn_look_report);
//        btn_look_report.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (data != null) {
//                    bundle.putString("name", data.getName());
//                    bundle.putString("title", "健康私人报告详情");
//                    bundle.putString("url", data.getUrl());
//                    MyApplication.startActivity(HealthReportActivity.this, QuestionnaireSurveyWebViewActivity.class, bundle);
//                }
//            }
//        });
    }

    @Override
    public void onRefresh(int index) {
        dataOption(TOP_REFRESH);
        ToastUtil.shortDiyToast(mContext,"已是最新数据了");
    }

    @Override
    public void onLoad(int index) {
        dataOption(BOTTOM_REFRESH);
        ToastUtil.shortDiyToast(mContext,"加载完成");
    }

    private void dataOption(int option) {
        switch (option) {
            case TOP_REFRESH:
                //下拉刷新
                initData();
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
                initData();
                break;
        }
//         adapter.notifyDataSetChanged();
    }
}
