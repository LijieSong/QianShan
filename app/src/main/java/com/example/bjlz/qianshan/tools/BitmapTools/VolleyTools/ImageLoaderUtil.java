package com.example.bjlz.qianshan.tools.BitmapTools.VolleyTools;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.volley.BitmapCache;

public class ImageLoaderUtil {

    /*
    * 通过ImageRequest来显示网络图片
    * */
    public static void setImageRequest(String url, final ImageView imageView) {
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                imageView.setBackgroundResource(R.mipmap.logo);
            }
        });
        MyApplication.getHttpQueue().add(imageRequest);
        MyApplication.getHttpQueue().start();
    }

    /*
    * 通过ImageLoader来显示网络图片
    * */
    public static void setImageLoader(String url, ImageView imageView, int defaultImageResId, int errorImageResId) {
        ImageLoader loader = new ImageLoader(MyApplication.getHttpQueue(), new BitmapCache());
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, defaultImageResId, errorImageResId);
        loader.get(url, imageListener);
    }

    /*
    * 通过Volley的NetWorkImageView来显示网络图片
    * */
    public static void setNetWorkImageView(String url, NetworkImageView netWorkImageView, int defaultImageResId, int errorImageResId) {
        ImageLoader loader = new ImageLoader(MyApplication.getHttpQueue(), new BitmapCache());
        netWorkImageView.setDefaultImageResId(defaultImageResId);
        netWorkImageView.setErrorImageResId(errorImageResId);
        netWorkImageView.setImageUrl(url, loader);
    }
}