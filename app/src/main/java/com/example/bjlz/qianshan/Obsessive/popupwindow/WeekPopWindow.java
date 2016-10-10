package com.example.bjlz.qianshan.Obsessive.popupwindow;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.example.bjlz.qianshan.Obsessive.bluetooth.activity.BlueToothActivity;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.wheelview.OnWheelScrollListener;
import com.example.bjlz.qianshan.wheelview.WheelView;
import com.example.bjlz.qianshan.wheelview.adapter.NumericWheelAdapter;

public class WeekPopWindow extends PopupWindow {
	private Context context;
	private LayoutInflater mInflater;
	private View dateView;
	private WheelView weekView;

	public WeekPopWindow(Context context){
		this.context=context;
		initWindow();
	}
	private void initWindow() {
		mInflater= LayoutInflater.from(context);
		dateView=mInflater.inflate(R.layout.wheel_week_picker, null);
		weekView=(WheelView) dateView.findViewById(R.id.week_num);
		initWheel();
	}


	private void initWheel() {
		NumericWheelAdapter numericWheelAdapter1=new NumericWheelAdapter(context,1,7);
		numericWheelAdapter1.setLabel("星期");
		weekView.setViewAdapter(numericWheelAdapter1);
		weekView.setCyclic(true);
		weekView.addScrollingListener(scrollListener);

		weekView.setVisibleItems(7);
		setContentView(dateView);
		setWidth(LayoutParams.FILL_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);
		setBackgroundDrawable(dw);
		setFocusable(true);

	}
	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
		@Override
		public void onScrollingStarted(WheelView wheel) {

		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			int n_week = weekView.getCurrentItem();
			BlueToothActivity.message[3] =(byte) (n_week + 1);
			Log.e("message[3]",(byte) (n_week + 1)+"");
		}
	};
}
