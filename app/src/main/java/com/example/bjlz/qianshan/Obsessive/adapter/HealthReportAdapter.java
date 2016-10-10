package com.example.bjlz.qianshan.Obsessive.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bjlz.qianshan.CurrencyActivities.WebViewActivity;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.Obsessive.bean.UserHealthBean;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：QianShanDoctor
 * 类描述：HealthReportAdapter 健康私人报告匹配器
 * 创建人：slj
 * 创建时间：2016-6-28 14:45
 * 修改人：slj
 * 修改时间：2016-6-28 14:45
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class HealthReportAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private List<UserHealthBean> list;
    private int reportId;//记录reportId

    public HealthReportAdapter(Context context, String[] status, String[] names, String[] urls) {
        this.context = context;
        list = new ArrayList<UserHealthBean>();
        for (int i = 0; i < names.length; i++) {
            UserHealthBean bean = new UserHealthBean(names[i], status[i], urls[i]);
            list.add(bean);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_report_list, null);
        ViewHodler vh = new ViewHodler(view);
        RelativeLayout.LayoutParams lp = new RelativeLayout.
                LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        view.setOnClickListener(this);
        vh.btn_look_report.setOnClickListener(this);
        vh.btn_delete_report.setOnClickListener(this);
        vh.tv_user_name.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHodler mHodler = (ViewHodler) holder;
        mHodler.tv_user_name.setText(list.get(position).getName());
        mHodler.tv_report_status.setText(list.get(position).getStatus());
        mHodler.tv_report_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.shortDiyToast(context,"报告审查状态");
            }
        });
        mHodler.btn_look_report.setText("查看");
        mHodler.btn_look_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("name", list.get(position).getName());
                bundle.putString("title", "健康私人报告详情");
                bundle.putString("url", list.get(position).getUrl());
                MyApplication.startActivity(context, WebViewActivity.class, bundle);
            }
        });
        mHodler.btn_delete_report.setText("删除");
        mHodler.btn_delete_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        mHodler.itemView.setTag(list.get(position));
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (UserHealthBean) v.getTag());
        }
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, UserHealthBean data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    private class ViewHodler extends RecyclerView.ViewHolder {

        private TextView tv_user_name, tv_report_status;
        private Button btn_look_report, btn_delete_report;

        public ViewHodler(View itemView) {
            super(itemView);
            tv_user_name = (TextView) itemView.findViewById(R.id.tv_user_name);
            tv_report_status = (TextView) itemView.findViewById(R.id.tv_report_status);
            btn_look_report = (Button) itemView.findViewById(R.id.btn_look_report);
            btn_delete_report = (Button) itemView.findViewById(R.id.btn_delete_report);
        }
    }
}
