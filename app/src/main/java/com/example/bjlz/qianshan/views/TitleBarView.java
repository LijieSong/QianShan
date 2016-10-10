package com.example.bjlz.qianshan.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.ChangeAndGetTools.DP2PXTools;

public class TitleBarView extends RelativeLayout
{
  private static final String TAG = TitleBarView.class.getSimpleName();
  private Button btnLeft;
  private Button btnRight;
  private Context mContext;
  private TextView txtTitle;
  private LinearLayout title_bar_bg;
  private CircleImageView imgTitle;
  private View view_title;

  public TitleBarView(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    initView();
  }

  public TitleBarView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
    initView();
    init();
  }

  private void init()
  {
    this.btnLeft.setOnClickListener(new OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        try
        {
          ((Activity)TitleBarView.this.mContext).finish();
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
  }

  private void initView()
  {
    LayoutInflater.from(this.mContext).inflate(R.layout.common_title_bar, this);
    this.title_bar_bg = (LinearLayout)findViewById(R.id.title_bar_bg);
    this.btnLeft = ((Button)findViewById(R.id.btnLeft));
    this.btnRight = ((Button)findViewById(R.id.btnRight));
    this.view_title = findViewById(R.id.view_title);
    this.imgTitle = (CircleImageView) findViewById(R.id.imgTitle);
    this.txtTitle = ((TextView)findViewById(R.id.txtTitle));
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
      setView_titleShow();
//      this.view_title.setBackgroundColor(CommonUtil.getColor(R.color.black_tr));
    }
  }

  public View getView() {
    return this.view_title;
  }
  public void setView_titleShow(){
    this.view_title.setVisibility(View.VISIBLE);
  }
  public Button getLeftBtn()
  {
    return this.btnLeft;
  }

  public CircleImageView getImageTitle()
  {
    return this.imgTitle;
  }

  public Button getRightBtn()
  {
    return this.btnRight;
  }

  public LinearLayout getTitleBarBg()
  {
    return this.title_bar_bg;
  }

  public void setCommonVisible(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.btnLeft.setVisibility(paramInt1);
    this.btnRight.setVisibility(paramInt4);
    this.txtTitle.setVisibility(paramInt2);
  }

  public void setLeftBtnIcon(int paramInt)
  {
    Drawable localDrawable = this.mContext.getResources().getDrawable(paramInt);
    int i = DP2PXTools.dip2px(this.mContext, 30);
    localDrawable.setBounds(0, 0, i * localDrawable.getIntrinsicWidth() / localDrawable.getIntrinsicHeight(), i);
    this.btnLeft.setCompoundDrawables(localDrawable, null, null, null);
  }
  public void setLeftBtnOnclickListener(OnClickListener paramOnClickListener)
  {
    this.btnLeft.setOnClickListener(paramOnClickListener);
  }

  public void setLeftBtnText(int paramInt)
  {
    this.btnLeft.setText(paramInt);
  }

  public void setLeftBtnText(String paramString)
  {
    this.btnLeft.setText(paramString);
  }

  public void setImageTitleBackGroundResource(int imgId)
  {
   this.imgTitle.setBackgroundResource(imgId);
  }
  public void setImageTitleShow()
  {
    this.imgTitle.setVisibility(View.VISIBLE);
  }
  public void setImageTitleGone()
  {
    this.imgTitle.setVisibility(View.GONE);
  }

  public void setRightBtnIcon(int paramInt)
  {
    Drawable localDrawable = this.mContext.getResources().getDrawable(paramInt);
    int i = DP2PXTools.dip2px(this.mContext, 20);
    localDrawable.setBounds(0, 0, i * localDrawable.getIntrinsicWidth() / localDrawable.getIntrinsicHeight(), i);
    this.btnRight.setCompoundDrawables(null, null, localDrawable, null);
  }

  public void setRightBtnOnclickListener(OnClickListener paramOnClickListener)
  {
    this.btnRight.setOnClickListener(paramOnClickListener);
  }

  public void setRightBtnPadding()
  {
    this.btnRight.setPadding(20, 0, 20, 0);
  }

  public void setRightBtnStyle()
  {
    GradientDrawable localGradientDrawable = new GradientDrawable();
    localGradientDrawable.setShape(0);
    localGradientDrawable.setColor(255);
    localGradientDrawable.setStroke(1, Color.rgb(73, 155, 247));
    this.btnRight.setBackgroundDrawable(localGradientDrawable);
  }

  public void setRightBtnText(int paramInt)
  {
    this.btnRight.setText(paramInt);
  }

  public void setRightBtnText(String paramString)
  {
    this.btnRight.setText(paramString);
  }

  public void setTitleText(int paramInt)
  {
    this.txtTitle.setText(paramInt);
  }

  public void setTitleText(String paramString)
  {
    this.txtTitle.setText(paramString);
  }

}