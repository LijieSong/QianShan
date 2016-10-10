package com.example.bjlz.qianshan.CurrencyActivities;

import android.content.Context;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.pull.SwipyRefreshLayout;
import com.example.bjlz.qianshan.views.TitleBarView;
import com.example.bjlz.qianshan.views.WebView.MyWebChromeClient;

/**
 * 项目名称：QianShan
 * 类描述：
 * 创建人：slj
 * 创建时间：2016-7-20 13:41
 * 修改人：slj
 * 修改时间：2016-7-20 13:41
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class WebViewActivity extends BaseActivity implements SwipyRefreshLayout.OnRefreshListener{
    private Context mContext = null;//上下文对象
    private TitleBarView title_bar;//标题
    private WebView webView_userInfor;//显示患者详情
    private String url,name,title = null;
    private ProgressBar webView_ProgressBar;//进度条显示进度
    private SwipyRefreshLayout refreshLayout;//刷新的控制
    private final int TOP_REFRESH = 1;//下拉刷新
    private final int BOTTOM_REFRESH = 2;//上拉加载更多
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
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
        webView_ProgressBar = (ProgressBar) findViewById(R.id.webView_ProgressBar);
        this.refreshLayout = (SwipyRefreshLayout) findViewById(R.id.refreshLayout);
        webView_userInfor = (WebView) findViewById(R.id.webView_userInfor);
    }

    @Override
    public void initData() {
        if (name !=null){
            this.title_bar.setTitleText(name+"的"+title);
        }else{
            this.title_bar.setTitleText(title);
        }
        // 设置JS交互数据
        webView_userInfor.getSettings().setJavaScriptEnabled(true);
        webView_userInfor.getSettings().setSupportZoom(true);
        webView_userInfor.getSettings().setBuiltInZoomControls(true);
        webView_userInfor.getSettings().setDisplayZoomControls(false);
        webView_userInfor.setWebViewClient(new WebViewClient() {
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                    //handler.cancel(); // Android默认的处理方式
                    handler.proceed();  // 接受所有网站的证书
                    //handleMessage(Message msg); // 进行其他处理
                }
            });
        MyWebChromeClient myWebChromeClient = new MyWebChromeClient(this);
        myWebChromeClient.setWebView(webView_userInfor,webView_ProgressBar);
        webView_userInfor.setWebChromeClient(myWebChromeClient);
//        webView_userInfor.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress == 100) {
//                    webView_ProgressBar.setVisibility(View.GONE);
//                } else {
//                    if (View.INVISIBLE == webView_ProgressBar.getVisibility()) {
//                        webView_ProgressBar.setVisibility(View.VISIBLE);
//                    }
//                    webView_ProgressBar.setProgress(newProgress);
//                }
//                super.onProgressChanged(view, newProgress);
//            }
//
//        });
        // 加载web资源
        webView_userInfor.loadUrl(url);
        this.refreshLayout.setRefreshing(false);
    }

    @Override
    public void getData() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle !=null){
            name = bundle.getString("name");
            url = bundle.getString("url");
            title = bundle.getString("title");
        }
    }

    @Override
    public void setOnClick() {
        refreshLayout.setOnRefreshListener(this);//设置刷新监听
        // 设置webview的点击事件
        webView_userInfor.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void WeightOnClick(View v) {

    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
    @Override
    public void onRefresh(int index) {
        dataOption(TOP_REFRESH);
//        ToastUtil.shortDiyToastColorBgTextSizeTextColor(mContext, "已是最新数据了", getResources().getColor(R.color.transparent), 16f, getResources().getColor(R.color.black));
    }

    @Override
    public void onLoad(int index) {
        dataOption(BOTTOM_REFRESH);
//        ToastUtil.shortDiyToastColorBgTextSizeTextColor(mContext, "加载完成", getResources().getColor(R.color.transparent), 16f, getResources().getColor(R.color.black));
    }

    private void dataOption(int option) {
        switch (option) {
            case TOP_REFRESH:
                //下拉刷新
//                initView();
                initData();
                break;
            case BOTTOM_REFRESH:
                //上拉加载更多
//                initView();
                initData();
                break;
        }
//         adapter.notifyDataSetChanged();
    }
}
