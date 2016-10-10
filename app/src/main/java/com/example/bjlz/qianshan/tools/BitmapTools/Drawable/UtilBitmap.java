package com.example.bjlz.qianshan.tools.BitmapTools.Drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 类描述：UtilBitmap 图片工具类
 * 创建人：slj
 * 创建时间：2016-9-20 14:46
 * 修改人：slj
 * 修改时间：2016-9-20 14:46
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class UtilBitmap {

    /**
     * 根据图片的url路径获得Bitmap对象
     *
     * @param url
     * @return
     */
    public static Bitmap returnBitmap(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;
        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 获取大图片的方法
     *
     * @param bmp
     * @param big
     * @return
     */
    public static Bitmap bigImage(Bitmap bmp, float big) {
        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();
        /* 产生reSize后的Bitmap对象 */
        Matrix matrix = new Matrix();
        matrix.postScale(big, big);
        Bitmap resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth,
                bmpHeight, matrix, true);
        return resizeBmp;
    }
    /**
     * 读取压缩后的图片
     * @param srcPath
     * @return Bitmap
     */
	public Bitmap getImage(String srcPath) {  
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
        newOpts.inJustDecodeBounds = true;  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空  
          
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
        float hh = 800f;//这里设置高度为800f  
        float ww = 480f;//这里设置宽度为480f  
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
        int be = 1;//be=1表示不缩放  
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
            be = (int) (newOpts.outWidth / ww);  
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
            be = (int) (newOpts.outHeight / hh);  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//设置缩放比例  
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩  
    }

    /**
     * 压缩图片
     * @param image
     * @return Bitmap
     */
	private Bitmap compressImage(Bitmap image) {  
		  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        int options = 100;  
        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
            options -= 10;//每次都减少10  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;  
    }
//        /**
//     * 高斯模糊
//     *
//     * @param ctx
//     * @param view
//     * @param textView
//     */
//    public static void ninePatchDrawable(final Context ctx, final ImageView view, final TextView textView) {
//        Drawable drawable = view.getDrawable();
//        if (drawable instanceof BitmapDrawable) {
//            synchronized (UtilBitmap.class) {
//
//                view.setImageBitmap(BlurMaskFilter.Blur.apply(ctx, ((BitmapDrawable) view.getDrawable()).getBitmap()));
//            }
//
//
//        } else if (drawable instanceof NinePatchDrawable) {
//            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//            Canvas canvas = new Canvas(bitmap);
//            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//            drawable.draw(canvas);
//            view.setImageBitmap(BlurMaskFilter.Blur.apply(ctx, bitmap));
//        }
//        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//
//            @Override
//            public boolean onPreDraw() {
//                view.getViewTreeObserver().removeOnPreDrawListener(this);
//                view.buildDrawingCache();
//                Bitmap bmp = view.getDrawingCache();
//                ViewUtils.blur(ctx, bmp, textView);
//                return true;
//            }
//        });
//
//    }
}
