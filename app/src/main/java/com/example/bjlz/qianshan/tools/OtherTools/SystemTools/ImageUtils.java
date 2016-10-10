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

package com.example.bjlz.qianshan.tools.OtherTools.SystemTools;

import android.content.Context;
import android.widget.ImageView;

import com.example.bjlz.qianshan.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * 类描述：ImageUtils 图片工具类
 * 创建人：slj
 * 创建时间：2016-9-20 14:55
 * 修改人：slj
 * 修改时间：2016-9-20 14:55
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class ImageUtils {

    public static void setImageUrl(Context context, String imageUrl, ImageView view) {
        Picasso.with(context).load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(view);
    }

    public static void setImageUrl(Context context, String imageUrl, Target target) {
        Picasso.with(context).load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(target);
    }
}
