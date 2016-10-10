package com.example.bjlz.qianshan.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆角的imageView
 * Created by lz on 2016/5/6.
 */
public class RoundImageView extends ImageView {
//    public RoundImageView(Context context) {
//        super(context);
//    }
//    public RoundImageView (Context context, AttributeSet attrs){
//        super(context,attrs);
//    }
//    public RoundImageView (Context context, AttributeSet attrs,int defStyle){
//        super(context,attrs,defStyle);
//    }
//
//    /**
//     * 画圆角的方法
//     * @param canvas
//     */
//    @Override
//    protected void onDraw(Canvas canvas) {
//        Path clipPath = new Path();//画笔位置
//        int w = this.getWidth();//获取宽
//        int h = this.getHeight();//获取高
//        clipPath.addRoundRect(new RectF(0,0,w,h),10.0f,10.0f,Path.Direction.CW);//设置圆角和画笔类型 10.0f指圆角的大小
//        canvas.clipPath(clipPath);//开始画
//        super.onDraw(canvas);
//    }
        private Paint paint = new Paint();

        public RoundImageView(Context context) {
            super(context);
        }

        public RoundImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        protected void onDraw(Canvas canvas) {

            Drawable drawable = getDrawable();
            if (null != drawable) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                Bitmap b = toRoundCorner(bitmap, 14);
                final Rect rect = new Rect(0, 0, b.getWidth(), b.getHeight());
                paint.reset();
                canvas.drawBitmap(b, rect, rect, paint);

            } else {
                super.onDraw(canvas);
            }
        }

        private Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            int x = bitmap.getWidth();
            canvas.drawCircle(x / 2, x / 2, x / 2, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            return output;
        }
    }
