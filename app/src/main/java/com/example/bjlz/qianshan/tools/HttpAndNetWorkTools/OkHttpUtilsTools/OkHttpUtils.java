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

package com.example.bjlz.qianshan.tools.HttpAndNetWorkTools.OkHttpUtilsTools;

import android.os.Environment;

import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 项目名称：QianShan
 * 类描述：
 * 创建人：slj
 * 创建时间：2016-8-29 16:05
 * 修改人：slj
 * 修改时间：2016-8-29 16:05
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class OkHttpUtils {
    /**
     * 不带请求头的OkHttp的Get请求
     * @param url
     * @param postBody
     * @return
     * @throws IOException
     */
    public static Response sendGet(String url,String postBody) throws IOException {
        OkHttpClient client = MyApplication.GetOkHttpClient();
        client.cache();
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(null, postBody))
                .build();
        OkHttpClient.Builder builder = client.newBuilder();
        builder.readTimeout(60, TimeUnit.SECONDS).cache(new Cache(Environment.getDownloadCacheDirectory(),1024)).build();
        Response response = client.newCall(request).execute();
        return response;
    }
}
