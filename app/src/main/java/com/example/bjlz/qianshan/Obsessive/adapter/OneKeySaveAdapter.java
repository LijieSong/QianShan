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

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bjlz.qianshan.CurrencyApplication.Address;
import com.example.bjlz.qianshan.Obsessive.bean.OneKeySaveBean;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.BitmapTools.GildeTools.GlideUtils;
import com.example.bjlz.qianshan.views.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：QianShan
 * 类描述：OneKeySaveAdapter 一键急救数据匹配器
 * 创建人：slj
 * 创建时间：2016-7-22 14:25
 * 修改人：slj
 * 修改时间：2016-7-22 14:25
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class OneKeySaveAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private List<OneKeySaveBean> list;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public OneKeySaveAdapter(Context context, String[] names, String[] mobiles) {
        this.context = context;
        list = new ArrayList<OneKeySaveBean>();
        for (int i = 0; i < names.length; i++) {
            OneKeySaveBean bean = new OneKeySaveBean(names[i], mobiles[i]);
            list.add(bean);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_one_key_save, null);
        ViewHodler vh = new ViewHodler(view);
        RelativeLayout.LayoutParams lp = new RelativeLayout.
                LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHodler holde = (ViewHodler) holder;
//        holde.cimg_tou_xiang.setBackgroundDrawable(CommonUtil.getDrawable(R.drawable.logo));
        holde.tv_user_name.setText(list.get(position).getName());
        holde.tv_user_mobile.setText(list.get(position).getMobile());
        //加载头像
        GlideUtils.downLoadCircleImage(context, Address.TEXT_IMAGEURL2,holde.cimg_tou_xiang);
        //按钮
        holde.imgbtn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + list.get(position).getMobile()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity)context,new String[]{Manifest.permission.CALL_PHONE},22);
                } else {
                    context.startActivity(intent);
                }
                context.startActivity(intent);
            }
        });
        holde.itemView.setTag(list.get(position));
    }

    @Override
    public int getItemCount() {
        if (list !=null){
            return list.size();
        } else {
            return  0;
        }
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view, (OneKeySaveBean) view.getTag());
        }
    }
    public  interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, OneKeySaveBean bean);
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    private class ViewHodler extends RecyclerView.ViewHolder{

        private TextView tv_user_name,tv_user_mobile;
        private ImageView imgbtn_call;
        private CircleImageView cimg_tou_xiang;
        public ViewHodler(View itemView) {
            super(itemView);
            tv_user_name= (TextView) itemView.findViewById(R.id.tv_user_name);
            tv_user_mobile= (TextView) itemView.findViewById(R.id.tv_user_mobile);
            imgbtn_call= (ImageView) itemView.findViewById(R.id.imgbtn_call);
            cimg_tou_xiang= (CircleImageView) itemView.findViewById(R.id.cimg_tou_xiang);
        }
    }
}
