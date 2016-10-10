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

package com.example.bjlz.qianshan.tools.AppManager;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bjlz.qianshan.CurrencyApplication.Address;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.BitmapTools.GildeTools.GlideUtils;
import com.example.bjlz.qianshan.tools.ChangeAndGetTools.CommonUtil;

/**
 * 项目名称：QianShan
 * 类描述：SnackBarUtils SnackBar工具类
 * 创建人：slj
 * 创建时间：2016-7-28 10:27
 * 修改人：slj
 * 修改时间：2016-7-28 10:27
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class SnackBarUtils {
    /**
     * 获取SnackBar LENGTH_SHORT
     *
     * @param context    上下文对象
     * @param view       一个控件
     * @param message    弹出的语言
     * @param actionText action的名字
     * @param listener   监听器
     * @param color      action文字的颜色
     */
    public static void GetSnackBar(Context context, View view, String message, String actionText, int color, View.OnClickListener listener) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setAction(actionText, listener)
                .setActionTextColor(CommonUtil.getColor(color)).show();
    }

    /**
     * 获取SnackBar LENGTH_SHORT
     * @param context 上下文对象
     * @param view    一个控件
     * @param message 弹出的语言
     * @param actionText action的名字
     * @param listener 监听器
     */
    public static void GetSnackBar(Context context, View view, String message, String actionText,View.OnClickListener listener) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setAction(actionText, listener).show();
    }
    /**
     * 获取SnackBar  LENGTH_SHORT
     * @param context 下下文对象
     * @param view  依附控件
     * @param message 显示的文字
     */
    public static void GetSnackBar(Context context, View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
    /**
     * 获取SnackBar 不带action文字
     *
     * @param context  上下文对象
     * @param view     控件
     * @param message  显示内容
     * @param listener 监听
     */
    public static void GetSnackBar(Context context, View view, int message, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("", listener);
        SnackbarAddView(snackbar, R.layout.custom_layout, 0);
        Snackbar.SnackbarLayout snackbarView = (Snackbar.SnackbarLayout) snackbar.getView();
        TextView snackbar_text = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        ImageView img_snackBar = (ImageView) snackbarView.findViewById(R.id.img_snackBar);
        GlideUtils.downLoadCircleImage(context, Address.TEXT_IMAGEURL1,img_snackBar);
        snackbar_text.setGravity(Gravity.CENTER);
        snackbarView.setBackgroundColor(CommonUtil.getColor(R.color.viewfinder_mask));
        snackbarView.setAlpha(0.5f);
        ViewGroup.LayoutParams vl = snackbarView.getLayoutParams();
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(vl.width, vl.height);
        ll.setMargins(120, 5, 120, 5);
        ll.gravity = Gravity.CENTER;
        snackbarView.setLayoutParams(ll);
//        snackbarView.animate();
        snackbar.show();
    }

    /**
     * 获取SnackBar 不带action文字
     *
     * @param context  上下文对象
     * @param view     控件
     * @param message  显示内容
     * @param listener 监听
     */
    public static void GetSnackBar(Context context, View view, String message, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("", listener);
        SnackbarAddView(snackbar, R.layout.custom_layout, 0);
        Snackbar.SnackbarLayout snackbarView = (Snackbar.SnackbarLayout) snackbar.getView();
        TextView snackbar_text = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        ImageView img_snackBar = (ImageView) snackbarView.findViewById(R.id.img_snackBar);
        GlideUtils.downLoadCircleImage(context, Address.TEXT_IMAGEURL1,img_snackBar);
        snackbar_text.setGravity(Gravity.CENTER);
        snackbarView.setBackgroundColor(CommonUtil.getColor(R.color.viewfinder_mask));
        snackbarView.setAlpha(0.5f);
        ViewGroup.LayoutParams vl = snackbarView.getLayoutParams();
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(vl.width, vl.height);
        ll.gravity = Gravity.TOP;
        snackbarView.setLayoutParams(ll);
//        snackbarView.animate();
        snackbar.show();
    }

    /**
     * 获取SnackBar 不带action
     *
     * @param context 上下文对象
     * @param view    控件
     * @param message 显示内容
     * @param color 内容字体颜色
     */
    public static void GetSnackBar(Context context, View view, int message, int color) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        SnackbarAddView(snackbar, R.layout.custom_layout, 0);
        Snackbar.SnackbarLayout snackbarView = (Snackbar.SnackbarLayout) snackbar.getView();
        TextView snackbar_text = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        ImageView img_snackBar = (ImageView) snackbarView.findViewById(R.id.img_snackBar);
        GlideUtils.downLoadCircleImage(context, Address.TEXT_IMAGEURL1,img_snackBar);
        snackbar_text.setGravity(Gravity.CENTER);
        snackbar_text.setTextColor(CommonUtil.getColor(color));
        snackbarView.setBackgroundColor(CommonUtil.getColor(R.color.viewfinder_mask));
        snackbarView.setAlpha(0.5f);
        ViewGroup.LayoutParams vl = snackbarView.getLayoutParams();
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(vl.width, vl.height);
        ll.gravity = Gravity.TOP;
        snackbarView.setLayoutParams(ll);
//        snackbarView.animate();
        snackbar.show();
    }

    /**
     * 获取SnackBar 不带action
     *
     * @param context 上下文对象
     * @param view    控件
     * @param message 显示内容
     * @param color   内容字体颜色
     */
    public static void GetSnackBar(Context context, View view, String message, int color) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        SnackbarAddView(snackbar, R.layout.custom_layout, 0);
        Snackbar.SnackbarLayout snackbarView = (Snackbar.SnackbarLayout) snackbar.getView();
        ImageView img_snackBar = (ImageView) snackbarView.findViewById(R.id.img_snackBar);
        GlideUtils.downLoadCircleImage(context, Address.TEXT_IMAGEURL1,img_snackBar);
        TextView snackbar_text = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        snackbar_text.setGravity(Gravity.CENTER_VERTICAL);
        snackbar_text.setTextColor(CommonUtil.getColor(color));
        snackbarView.setBackgroundColor(CommonUtil.getColor(R.color.viewfinder_mask));
        snackbarView.setAlpha(0.5f);
        ViewGroup.LayoutParams vl = snackbarView.getLayoutParams();
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(vl.width, vl.height);
        ll.gravity = Gravity.TOP;
        snackbarView.setLayoutParams(ll);
//        snackbarView.animate();
        snackbar.show();
    }

    /**
     * 获取SnackBar
     *
     * @param context    上下文对象
     * @param view       一个控件
     * @param message    弹出的语言
     * @param actionText action的名字
     * @param listener   监听器
     * @param color      action文字的颜色
     */
    public static void GetSnackBarBg(Context context, View view, String message, String actionText, int color, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(actionText, listener)
                .setActionTextColor(CommonUtil.getColor(color));
        SnackbarAddView(snackbar, R.layout.custom_layout, 0);
        Snackbar.SnackbarLayout snackbarView = (Snackbar.SnackbarLayout) snackbar.getView();
        ImageView img_snackBar = (ImageView) snackbarView.findViewById(R.id.img_snackBar);
        GlideUtils.downLoadCircleImage(context, Address.TEXT_IMAGEURL1,img_snackBar);
        TextView snackbar_text = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        snackbar_text.setGravity(Gravity.CENTER);
        snackbarView.setBackgroundColor(CommonUtil.getColor(R.color.viewfinder_mask));
        snackbarView.setAlpha(0.5f);
//        ViewGroup.LayoutParams vl = snackbarView.getLayoutParams();
//        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(vl.width,vl.height);
//        ll.gravity = Gravity.CENTER_VERTICAL;
//        snackbarView.setLayoutParams(ll);
//        snackbarView.animate();
        snackbar.show();
    }

    /**
     * 获取SnackBar
     *
     * @param context    上下文对象
     * @param view       一个控件
     * @param message    弹出的语言
     * @param actionText action的名字
     * @param listener   监听器
     * @param color      action文字的颜色
     */
    public static void GetSnackBar(Context context, View view, int message, int actionText, int color, View.OnClickListener listener) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(actionText, listener)
                .setActionTextColor(CommonUtil.getColor(color)).show();
    }

    /**
     * 获取SnackBar
     * 带背景
     *
     * @param context    上下文对象
     * @param view       一个控件
     * @param message    弹出的语言
     * @param actionText action的名字
     * @param listener   监听器
     * @param color      action文字的颜色
     */
    public static void GetSnackBarBg(Context context, View view, int message, int actionText, int color, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(actionText, listener)
                .setActionTextColor(CommonUtil.getColor(color));
        SnackbarAddView(snackbar, R.layout.custom_layout, 0);
        Snackbar.SnackbarLayout snackbarView = (Snackbar.SnackbarLayout) snackbar.getView();
        ImageView img_snackBar = (ImageView) snackbarView.findViewById(R.id.img_snackBar);
        GlideUtils.downLoadCircleImage(context, Address.TEXT_IMAGEURL1,img_snackBar);
        TextView snackbar_text = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        snackbar_text.setGravity(Gravity.CENTER);
        snackbarView.setBackgroundColor(CommonUtil.getColor(R.color.viewfinder_mask));
        snackbarView.setAlpha(0.5f);
        SnackbarAddView(snackbar, R.layout.custom_layout, 0);
//        ViewGroup.LayoutParams vl = snackbarView.getLayoutParams();
//        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(vl.width,vl.height);
//        ll.gravity = Gravity.CENTER_VERTICAL;
//        snackbarView.setLayoutParams(ll);
//        snackbarView.animate();
        snackbar.show();
    }

    /**
     * 向SnackBar中添加一个view
     *
     * @param snackbar
     * @param layoutId
     * @param index
     */
    public static void SnackbarAddView(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();//获取snackbar的View(其实就是SnackbarLayout)

        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarview;//将获取的View转换成SnackbarLayout

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId, null);//加载布局文件新建View

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);//设置新建布局参数

        p.gravity = Gravity.CENTER_VERTICAL;//设置新建布局在Snackbar内垂直居中显示

        snackbarLayout.addView(add_view, index, p);//将新建布局添加进snackbarLayout相应位置
    }
}
