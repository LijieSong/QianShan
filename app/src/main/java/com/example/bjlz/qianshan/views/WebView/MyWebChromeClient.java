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

package com.example.bjlz.qianshan.views.WebView;

import android.content.Context;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * 项目名称：QianShan
 * 类描述：MyWebChromeClient 辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度
 * 创建人：slj
 * 创建时间：2016-8-22 13:26
 * 修改人：slj
 * 修改时间：2016-8-22 13:26
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class MyWebChromeClient extends WebChromeClient {
    private Context context;
    private ProgressBar webView_ProgressBar;//进度条显示进度
    private WebView webView;//进度条
    public MyWebChromeClient(Context context) {
        this.context = context;
    }
    public void setWebView(WebView view,ProgressBar webView_ProgressBar){
        this.webView_ProgressBar = webView_ProgressBar;
        onProgressChanged(view,0);
    }
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress == 100) {
            webView_ProgressBar.setVisibility(View.GONE);
        } else {
            if (View.INVISIBLE == webView_ProgressBar.getVisibility()) {
                webView_ProgressBar.setVisibility(View.VISIBLE);
            }
            webView_ProgressBar.setProgress(newProgress);
        }
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        return super.onJsBeforeUnload(view, url, message, result);
    }

}
