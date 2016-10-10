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

package com.example.bjlz.qianshan.CurrencyActivities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.ToastUtil;
import com.example.bjlz.qianshan.views.TitleBarView;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：QianShan
 * 类描述：
 * 创建人：slj
 * 创建时间：2016-8-2 15:19
 * 修改人：slj
 * 修改时间：2016-8-2 15:19
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class TextActivity extends BaseActivity {
    private ListView listView_Text;//listView
    private GridView gridView_Text;//girdView
    private List<String> Dates;//日期
    private List<String> Times;//时间
    private TimeAdapter timeAdapter;
    private DateAdapter dateAdapter;
    private TitleBarView title_bar;//标题
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        getData();
        initView();
        initData();
        setOnClick();
    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        listView_Text = (ListView) findViewById(R.id.listView_Text);
        gridView_Text = (GridView) findViewById(R.id.gridView_Text);
        gridView_Text.setGravity(Gravity.CENTER);
    }

    @Override
    public void initData() {
        this.title_bar.setRightBtnText(R.string.ok);
        Dates = new ArrayList<>();
        Dates.add("7月28日");
        Dates.add("7月29日");
        Dates.add("7月30日");
        dateAdapter = new DateAdapter(this, Dates);
        listView_Text.setAdapter(dateAdapter);
        Times = new ArrayList<>();
        Times.add("08:00");
        Times.add("09:00");
        Times.add("10:00");
        Times.add("11:00");
        Times.add("12:00");
        Times.add("13:00");
        Times.add("14:00");
        Times.add("15:00");
        Times.add("16:00");
        Times.add("17:00");
        timeAdapter = new TimeAdapter(this, Times);
        gridView_Text.setAdapter(timeAdapter);
    }

    @Override
    public void getData() {

    }

    @Override
    public void setOnClick() {
        listView_Text.setOnItemClickListener(this);
        gridView_Text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                timeAdapter.setSelectedPosition(i);
            }
        });
        this.title_bar.setRightBtnOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dateAdapter.getSelectedPosition() < 0 || timeAdapter.getSelectedPosition() < 0) {
                    ToastUtil.shortDiyToast(getApplicationContext(), "请选择具体日期和时间");
                } else {
                    String date = Dates.get(dateAdapter.getSelectedPosition());
                    String time = Times.get(timeAdapter.getSelectedPosition());
                    if (date != null || time != null)
                        ToastUtil.shortDiyToast(getApplicationContext(), date + time);
//                SnackBarUtils.GetSnackBar(getApplicationContext(),title_bar,date+time);
                    finish();
                }
            }
        });
    }

    @Override
    public void WeightOnClick(View v) {

    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        dateAdapter.setSelectedPosition(i);
        gridView_Text.setVisibility(View.VISIBLE);
    }

    /**
     * 日期匹配器
     */
    private class DateAdapter extends BaseAdapter {
        // used to keep selected position in ListView
        private int selectedPos = -1; // init value for not-selected
        private Context context;
        private List<String> date = new ArrayList<>();

        public DateAdapter(Context context, List<String> date) {
            this.context = context;
            this.date = date;
        }

        public void setSelectedPosition(int pos) {
            selectedPos = pos;
            // inform the view of this change
            notifyDataSetChanged();
        }

        public int getSelectedPosition() {
            return selectedPos;
        }

        @Override
        public int getCount() {
            return date.size();
        }

        @Override
        public Object getItem(int i) {
            return date.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = View.inflate(context, R.layout.item_text, null);
                holder = new ViewHolder();
                holder.textView_text = (TextView) view.findViewById(R.id.textView_text);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            String s = date.get(i);
            if (s != null) {
                // change the row color based on selected state
                if (selectedPos == i) {
                    holder.textView_text.setBackgroundResource(R.color.red);
                    holder.textView_text.setTextColor(Color.WHITE);
                } else {
                    holder.textView_text.setBackgroundResource(R.color.whites);
                    holder.textView_text.setTextColor(Color.BLACK);
                }
                holder.textView_text.setText(s);
            }
            return view;
        }

        private class ViewHolder {
            TextView textView_text;
        }
    }

    /**
     * 时间匹配器
     */
    private class TimeAdapter extends BaseAdapter {
        private int selectedPos = -1; // init value for not-selected
        private Context context;
        private List<String> date = new ArrayList<>();

        public TimeAdapter(Context context, List<String> date) {
            this.context = context;
            this.date = date;
        }

        @Override
        public int getCount() {
            return date.size();
        }

        public void setSelectedPosition(int pos) {
            selectedPos = pos;
            // inform the view of this change
            notifyDataSetChanged();
        }

        public int getSelectedPosition() {
            return selectedPos;
        }

        @Override
        public Object getItem(int i) {
            return date.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = View.inflate(context, R.layout.item_text, null);
                holder = new ViewHolder();
                holder.textView_text = (TextView) view.findViewById(R.id.textView_text);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            if (selectedPos == i) {
                holder.textView_text.setBackgroundResource(R.color.red);
                holder.textView_text.setTextColor(Color.WHITE);
            } else {
                holder.textView_text.setBackgroundResource(R.color.whites);
                holder.textView_text.setTextColor(Color.BLACK);
            }
            String s = date.get(i);
            if (s != null) {
                holder.textView_text.setText(s);
            }
            return view;
        }

        private class ViewHolder {
            TextView textView_text;
        }
    }
}
