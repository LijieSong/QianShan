package com.example.bjlz.qianshan.tools.OtherTools.UITools;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bjlz.qianshan.R;

/**
 * Toast工具类
 * @author lenovo
 *
 */
public class ToastUtil {
	/**
	 * 短吐司方法
	 * @param baseContext
	 * @param string
	 */
	public static void shortToast(Context baseContext, String string) {
		Toast.makeText(baseContext, string, Toast.LENGTH_SHORT).show();
	}
	/**
	 * 自定义短吐司方法
	 * @param baseContext
	 * @param string 16sp字体大小
	 */
	public static void shortDiyToast(Context baseContext, String string) {
		Toast toast = new Toast(baseContext);
		View view = View.inflate(baseContext, R.layout.diy_toast, null);
		TextView text_toast = (TextView) view.findViewById(R.id.text_toast);
		text_toast.setText(string);
		toast.setView(view);
		toast.show();
	}
	/**
	 * 自定义短吐司方法 byrec
	 * @param baseContext
	 * @param stringId 16sp字体大小
	 */
	public static void shortDiyToastByRec(Context baseContext, int stringId) {
		Toast toast = new Toast(baseContext);
		View view = View.inflate(baseContext, R.layout.diy_toast, null);
		TextView text_toast = (TextView) view.findViewById(R.id.text_toast);
		text_toast.setText(stringId);
		toast.setView(view);
		toast.show();
	}
	/**
	 * 可自定义吐司背景颜色,字体大小,字体颜色的短吐司方法
	 * @param baseContext
	 * @param string
	 */
	public static void shortDiyToastColorBgTextSizeTextColor(Context baseContext, String string,int colorId,float textSize,int textColorId) {
		Toast toast = new Toast(baseContext);
		View view = View.inflate(baseContext, R.layout.diy_toast, null);
		TextView text_toast = (TextView) view.findViewById(R.id.text_toast);
		LinearLayout ll_toast = (LinearLayout) view.findViewById(R.id.ll_toast);
		ll_toast.setBackgroundColor(colorId);
		text_toast.setTextColor(textColorId);
		text_toast.setTextSize(textSize);
		text_toast.setText(string);
		toast.setView(view);
		toast.show();
	}
	/**
	 * 可自定义吐司背景颜色,字体大小,字体颜色的短吐司方法 byrec
	 * @param baseContext
	 * @param stringId
	 */
	public static void shortDiyToastColorBgByRecTextSizeTextColor(Context baseContext, int stringId,int colorId,float textSize,int textColorId) {
		Toast toast = new Toast(baseContext);
		View view = View.inflate(baseContext, R.layout.diy_toast, null);
		TextView text_toast = (TextView) view.findViewById(R.id.text_toast);
		LinearLayout ll_toast = (LinearLayout) view.findViewById(R.id.ll_toast);
		ll_toast.setBackgroundColor(colorId);
		text_toast.setTextColor(textColorId);
		text_toast.setTextSize(textSize);
		text_toast.setText(stringId);
		toast.setView(view);
		toast.show();
	}
	/**
	 * 可自定义资源文字,吐司背景图片,文字大小的短吐司方法
	 * @param baseContext
	 * @param stringId
	 */
	public static void shortDiyToastBgByRec(Context baseContext, int stringId,int imgId,float textSize) {
		Toast toast = new Toast(baseContext);
		View view = View.inflate(baseContext, R.layout.diy_toast, null);
		TextView text_toast = (TextView) view.findViewById(R.id.text_toast);
		LinearLayout ll_toast = (LinearLayout) view.findViewById(R.id.ll_toast);
		ll_toast.setBackgroundResource(imgId);
		text_toast.setTextSize(textSize);
		text_toast.setText(stringId);
		toast.setView(view);
		toast.show();
	}
	/**
	 * 自定义资源文字,吐司背景图片,文字大小的短吐司方法
	 * @param baseContext
	 * @param string
	 */
	public static void shortDiyToastBg(Context baseContext, String string,int imgId,float textSize) {
		Toast toast = new Toast(baseContext);
		View view = View.inflate(baseContext, R.layout.diy_toast, null);
		TextView text_toast = (TextView) view.findViewById(R.id.text_toast);
		LinearLayout ll_toast = (LinearLayout) view.findViewById(R.id.ll_toast);
		ll_toast.setBackgroundResource(imgId);
		text_toast.setTextSize(textSize);
		text_toast.setText(string);
		toast.setView(view);
		toast.show();
	}
	/**
	 * 自定义吐司背景图片,资源文字,文字大小,文字颜色的短吐司方法
	 * @param baseContext
	 * @param stringId
	 */
	public static void shortDiyToastBgByTextColorRec(Context baseContext, int stringId,int imgId,float textSize,int textColor) {
		Toast toast = new Toast(baseContext);
		View view = View.inflate(baseContext, R.layout.diy_toast, null);
		TextView text_toast = (TextView) view.findViewById(R.id.text_toast);
		LinearLayout ll_toast = (LinearLayout) view.findViewById(R.id.ll_toast);
		ll_toast.setBackgroundResource(imgId);
		text_toast.setTextSize(textSize);
		text_toast.setTextColor(textColor);
		text_toast.setText(stringId);
		toast.setView(view);
		toast.show();
	}
	/**
	 * 可自定义吐司背景图片,资源文字,文字大小,文字颜色的短吐司方法
	 * @param baseContext
	 * @param string
	 */
	public static void shortDiyToastBgByTextColor(Context baseContext, String string,int imgId,float textSize,int textColor) {
		Toast toast = new Toast(baseContext);
		View view = View.inflate(baseContext, R.layout.diy_toast, null);
		TextView text_toast = (TextView) view.findViewById(R.id.text_toast);
		LinearLayout ll_toast = (LinearLayout) view.findViewById(R.id.ll_toast);
		ll_toast.setBackgroundResource(imgId);
		text_toast.setTextSize(textSize);
		text_toast.setTextColor(textColor);
		text_toast.setText(string);
		toast.setView(view);
		toast.show();
	}
	/**
	 * 可自定义吐司背景颜色,资源文字,文字大小,文字颜色的短吐司方法
	 * @param baseContext
	 * @param stringId
	 */
	public static void shortDiyToastColorBgByTextColorRec(Context baseContext, int stringId,int colorId,float textSize) {
		Toast toast = new Toast(baseContext);
		View view = View.inflate(baseContext, R.layout.diy_toast, null);
		TextView text_toast = (TextView) view.findViewById(R.id.text_toast);
		LinearLayout ll_toast = (LinearLayout) view.findViewById(R.id.ll_toast);
		ll_toast.setBackgroundColor(colorId);
		text_toast.setTextSize(textSize);
		text_toast.setText(stringId);
		toast.setView(view);
		toast.show();
	}
	/**
	 * 可自定义吐司背景颜色,资源文字,文字大小,文字颜色的短吐司方法
	 * @param baseContext
	 * @param string
	 */
	public static void shortDiyToastColorBgByTextColor(Context baseContext, String string,int colorId,float textSize,int textColor) {
		Toast toast = new Toast(baseContext);
		View view = View.inflate(baseContext, R.layout.diy_toast, null);
		TextView text_toast = (TextView) view.findViewById(R.id.text_toast);
		LinearLayout ll_toast = (LinearLayout) view.findViewById(R.id.ll_toast);
		ll_toast.setBackgroundColor(colorId);
		text_toast.setTextSize(textSize);
		text_toast.setTextColor(textColor);
		text_toast.setText(string);
		toast.setView(view);
		toast.show();
	}
	/**
	 * 短吐司方法by res
	 * @param baseContext
	 * @param Resstr 资源文件的string
	 */
	public static void shortToastByRes(Context baseContext, int Resstr) {
		Toast.makeText(baseContext, Resstr, Toast.LENGTH_SHORT).show();
	}
	/**
	 * 长吐司方法
	 * @param baseContext
	 * @param string
	 */
	public static void longToast(Context baseContext, String string) {
		Toast.makeText(baseContext, string, Toast.LENGTH_LONG).show();
	}
	/**
	 * 长吐司方法by res
	 * @param baseContext
	 * @param Resstr 资源文件的string
	 */
	public static void longToastByRes(Context baseContext, int Resstr) {
		Toast.makeText(baseContext, Resstr, Toast.LENGTH_LONG).show();
	}

	/**
	 * 带图片的短吐司方法
	 * @param baseContext
	 * @param str
	 * @param imgId
     */
	public static void shortToastImgStr(Context baseContext, String str,int imgId){
		Toast toast = Toast.makeText(baseContext, str, Toast.LENGTH_SHORT);
//		toast.setGravity(Gravity.CENTER,0,0);
		LinearLayout view = (LinearLayout) toast.getView();
		ImageView img = new ImageView(baseContext);
		img.setImageResource(imgId);
		img.setMinimumWidth(30);
		img.setMinimumHeight(30);
		view.addView(img,0);
		toast.show();
	}
	/**
	 * 带图片的短吐司方法by ResString
	 * @param baseContext
	 * @param Resstr
	 * @param imgId
	 */
	public static void shortToastImgStrByRes(Context baseContext, int Resstr,int imgId){
		Toast toast = Toast.makeText(baseContext, Resstr, Toast.LENGTH_SHORT);
//		toast.setGravity(Gravity.CENTER,0,0);
		LinearLayout view = (LinearLayout) toast.getView();
		ImageView img = new ImageView(baseContext);
		img.setImageResource(imgId);
		img.setMinimumWidth(30);
		img.setMinimumHeight(30);
		view.addView(img,0);
		toast.show();
	}
	/**
	 * 带图片的长吐司方法
	 * @param baseContext
	 * @param str
	 * @param imgId
	 */
	public static void longToastImgStr(Context baseContext, String str,int imgId){
		Toast toast = Toast.makeText(baseContext, str, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER,0,0);
		LinearLayout view = (LinearLayout) toast.getView();
		ImageView img = new ImageView(baseContext);
		img.setImageResource(imgId);
		img.setMinimumWidth(30);
		img.setMinimumHeight(30);
		view.addView(img,0);
		toast.show();
	}
	/**
	 * 带图片的长吐司方法by ResString
	 * @param baseContext
	 * @param Resstr
	 * @param imgId
	 */
	public static void longToastImgStrByRes(Context baseContext, int Resstr,int imgId){
		Toast toast = Toast.makeText(baseContext, Resstr, Toast.LENGTH_LONG);
//		toast.setGravity(Gravity.CENTER,0,0);
		LinearLayout view = (LinearLayout) toast.getView();
		ImageView img = new ImageView(baseContext);
		img.setImageResource(imgId);
		img.setMinimumWidth(30);
		img.setMinimumHeight(30);
		view.addView(img,0);
		toast.show();
	}
	/**
	 * 带背景图片的短吐司方法
	 * @param baseContext
	 * @param str
	 * @param imgId
	 */
	public static void shortToastBackImgStr(Context baseContext, String str,int imgId){
		Toast toast = Toast.makeText(baseContext, str, Toast.LENGTH_SHORT);
//		toast.setGravity(Gravity.CENTER,25,25);
		LinearLayout view = (LinearLayout) toast.getView();
		view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,30));
		view.setBackgroundResource(imgId);
		toast.show();
	}
	/**
	 * 带背景图片的短吐司方法by ResString
	 * @param baseContext
	 * @param Resstr
	 * @param imgId
	 */
	public static void shortToastBackImgStrByRes(Context baseContext, int Resstr,int imgId){
		Toast toast = Toast.makeText(baseContext, Resstr, Toast.LENGTH_SHORT);
//		toast.setGravity(Gravity.CENTER,0,0);
		LinearLayout view = (LinearLayout) toast.getView();
		view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,30));
		view.setBackgroundResource(imgId);
		toast.show();
	}
	/**
	 * 带背景图片的长吐司方法
	 * @param baseContext
	 * @param str
	 * @param imgId
	 */
	public static void longToastBackImgStr(Context baseContext, String str,int imgId){
		Toast toast = Toast.makeText(baseContext, str, Toast.LENGTH_LONG);
//		toast.setGravity(Gravity.CENTER,0,0);
		LinearLayout view = (LinearLayout) toast.getView();
		view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,30));
		view.setBackgroundResource(imgId);
		toast.show();
	}
	/**
	 * 带背景图片的长吐司方法by ResString
	 * @param baseContext
	 * @param Resstr
	 * @param imgId
	 */
	public static void longToastBackImgStrByRes(Context baseContext, int Resstr,int imgId){
		Toast toast = Toast.makeText(baseContext, Resstr, Toast.LENGTH_LONG);
//		toast.setGravity(Gravity.CENTER,0,0);
		LinearLayout view = (LinearLayout) toast.getView();
		view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,30));
		view.setBackgroundResource(imgId);
		toast.show();
	}
}
