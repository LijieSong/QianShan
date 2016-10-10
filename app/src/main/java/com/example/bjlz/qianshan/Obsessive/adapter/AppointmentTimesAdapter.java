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

package com.example.bjlz.qianshan.Obsessive.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.ChangeAndGetTools.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：QianShan
 * 类描述：AppointmentTimesAdapter  预约诊疗时间匹配器
 * 创建人：slj
 * 创建时间：2016-8-5 10:12
 * 修改人：slj
 * 修改时间：2016-8-5 10:12
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class AppointmentTimesAdapter extends BaseAdapter {
    private Context context;//上下文对象
    private  List<String> times = new ArrayList<>();//时间集合
    // used to keep selected position in ListView
    private int selectedPos = -1; // init value for not-selected
    public AppointmentTimesAdapter(Context context, List<String> times) {
        this.context = context;
        this.times =times;
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
        return times.size();
    }

    @Override
    public Object getItem(int i) {
        return times.get(i);
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
        String s = times.get(i);
        if (s != null) {
            // change the row color based on selected state
            if (selectedPos == i) {
                holder.textView_text.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.radiobgcheck));
                holder.textView_text.setTextColor(CommonUtil.getColor(R.color.red));
            } else {
                holder.textView_text.setBackgroundResource(R.color.whites);
                holder.textView_text.setTextColor(CommonUtil.getColor(R.color.black));
            }
            holder.textView_text.setText(s);
        }
        return view;
    }
    private class ViewHolder {
        TextView textView_text;
    }
}
