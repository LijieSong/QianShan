package com.example.bjlz.qianshan.Obsessive.bluetooth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.bjlz.qianshan.Obsessive.bean.BlueTooth4Message;
import com.example.bjlz.qianshan.Obsessive.bluetooth.activity.ServiceActivity;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.ChangeAndGetTools.ByteUtils;
import com.example.bjlz.qianshan.tools.ChangeAndGetTools.DataUtils;
import com.example.bjlz.qianshan.tools.ChangeAndGetTools.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：nrtc_demo
 * 类描述：
 * 创建人：slj
 * 创建时间：2016-6-19 3:35
 * 修改人：slj
 * 修改时间：2016-6-19 3:35
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class BluetoothMsgAdapter extends BaseAdapter {
    private Context context;
    private List<BlueTooth4Message> list = new ArrayList<>();
    public BluetoothMsgAdapter(ServiceActivity context, List<BlueTooth4Message> list){
        this.context = context;
        this.list = list;
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.item_bluetoothmsglist,null);
            holder = new ViewHolder();
            holder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            holder.tv_gao_ya = (TextView) convertView.findViewById(R.id.tv_gao_ya);
            holder.tv_di_ya = (TextView) convertView.findViewById(R.id.tv_di_ya);
            holder.tv_xin_lv = (TextView) convertView.findViewById(R.id.tv_xin_lv);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
        }
        BlueTooth4Message tooth4Message = list.get(position);
        String data = tooth4Message.getData();
        String s1 = data.substring(4, 20);
        String s2 = s1.substring(8, 10);
        String gaoya = ByteUtils.hexStringToBinary(s2);
        int checkGaoYa = ByteUtils.binaryToAlgorism(gaoya);
        String s3 = s1.substring(12, 14);
        String diya = ByteUtils.hexStringToBinary(s3);
        int checkDiYa = ByteUtils.binaryToAlgorism(diya);
        String s4 = s1.substring(14, s1.length());
        String xinlv = ByteUtils.hexStringToBinary(s4);
        int checkXinLv = ByteUtils.binaryToAlgorism(xinlv);
        holder.tv_gao_ya.setText("收缩压:"+checkGaoYa);
        holder.tv_di_ya.setText("舒张压:"+checkDiYa);
        holder.tv_xin_lv.setText("心率:" +checkXinLv);
        int xueya_id = tooth4Message.getXueya_id();
        holder.tv_id.setText("编号:"+ xueya_id);
        long xueyaTime = tooth4Message.getXueyaTime();
        String dateTime = DataUtils.getFormatDateTime("yyyy-MM-dd HH:mm:ss", xueyaTime);
        holder.tv_date.setText(dateTime);
        return convertView;
    }
   private class ViewHolder{
        TextView tv_gao_ya,tv_di_ya,tv_xin_lv,tv_date,tv_id;
    }
}
