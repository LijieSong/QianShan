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

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import com.example.bjlz.qianshan.CurrencyApplication.Address;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.views.WebView.MyWebChromeClient;
import com.example.bjlz.qianshan.views.WebView.MyWebViewClient;
import com.example.bjlz.qianshan.views.TitleBarView;

/**
 * 项目名称：QianShan
 * 类描述：
 * 创建人：slj
 * 创建时间：2016-8-22 12:51
 * 修改人：slj
 * 修改时间：2016-8-22 12:51
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class WebViewTextActivity extends BaseActivity {
   private WebView webView_webView;
    private TitleBarView title_bar;
    private String url,name,title = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        MyApplication.getInstance().addActivity(this);
        getData();
        initView();
        initData();
        setOnClick();
    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        webView_webView = (WebView) findViewById(R.id.webView_webView);
    }

    @Override
    public void initData() {
        if (name !=null){
            this.title_bar.setTitleText(name+"的"+title);
        }else{
            this.title_bar.setTitleText(title);
        }
        // 设置JS交互数据
        webView_webView.getSettings().setJavaScriptEnabled(true);
        webView_webView.getSettings().setSupportZoom(true);
        webView_webView.getSettings().setBuiltInZoomControls(true);
        webView_webView.getSettings().setDisplayZoomControls(false);
        MyWebViewClient mMyWebViewClient = new MyWebViewClient(this);
        mMyWebViewClient.setMessage("这是测试的加载消息");
        mMyWebViewClient.setWebView(webView_webView,Address.TEXT_URL4);
        webView_webView.setWebViewClient(mMyWebViewClient);
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

    }

    @Override
    public void WeightOnClick(View v) {

    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
