package com.example.bjlz.qianshan.Obsessive.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bjlz.qianshan.CurrencyApplication.Address;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.Obsessive.activity.UserInformationActivity;
import com.example.bjlz.qianshan.Obsessive.bean.UserManagerBean;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.BitmapTools.GildeTools.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：QianShanDoctor
 * 类描述：UserManagerAdapter 用户管理匹配器
 * 创建人：slj
 * 创建时间：2016-6-28 14:45
 * 修改人：slj
 * 修改时间：2016-6-28 14:45
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class UserManagerAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    private Context context;
    private List<UserManagerBean> list;
//    private String imgUrl = " http://img.youguoquan.com/uploads/magazine/content/2015/08/07/f3a726eed61d52ea9cff522af5854ac7_magazine_web_m.jpg";
    public UserManagerAdapter(Context context, int[] img , String[] names, String[] zhusu) {
        this.context = context;
        list = new ArrayList<UserManagerBean>();
        for (int i = 0; i < names.length ; i++) {
            UserManagerBean bean = new UserManagerBean(names[i],img[i],zhusu[i]);
            list.add(bean);
        }

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_usermanager, null);
        ViewHodler vh=new ViewHodler(view);
        RelativeLayout.LayoutParams lp = new RelativeLayout.
                LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        view.setOnClickListener(this);
//        vh.tv_user_age.setOnClickListener(this);
//        vh.tv_user_sex.setOnClickListener(this);
//        vh.tv_user_name.setOnClickListener(this);
//        vh.image_user_icon.setOnClickListener(this);
//        vh.image_rightBack.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHodler mHodler = (ViewHodler) holder;
        mHodler.tv_user_name.setText(list.get(position).getName());
        mHodler.tv_user_zhu_su.setText("主诉: " +list.get(position).getZhusu());
        mHodler.tv_user_sex.setText("男");
        mHodler.tv_user_age.setText("40");
//        ImageLoaderUtil.setImageLoader(imgUrl,mHodler.image_user_icon,R.drawable.logo,R.drawable.menu);
//        mHodler.image_user_icon.setBackgroundResource(list.get(position).getImgId());
//        Glide.with(context)
//                .load(Address.TEXT_IMAGEURL1)
//                .placeholder(R.drawable.logo)
//                .error(R.drawable.menu)
//                .into(mHodler.image_user_icon);
        GlideUtils.downLoadCircleImage(context,Address.TEXT_IMAGEURL1,mHodler.image_user_icon);
        mHodler.itemView.setTag(list.get(position));

    }

    @Override
    public int getItemCount() {
        if (list !=null){
            return list.size();
        } else {
            return  0;
        }
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (UserManagerBean) v.getTag());
        }
        Bundle bundle = new Bundle();
        switch(v.getId()){
            case R.id.image_user_icon:
            case R.id.tv_user_name:
            case R.id.tv_user_age:
//                ToastUtil.shortDiyToast(context,"跳转患者详情界面");
                bundle.putString("bean",list.toString());
                MyApplication.startActivity(context, UserInformationActivity.class,bundle);
                break;
            case R.id.image_rightBack:
//                ToastUtil.shortDiyToast(context,"跳转患者详情界面");
                bundle.putString("bean",list.toString());
                MyApplication.startActivity(context, UserInformationActivity.class,bundle);
                break;
        }
    }


    public  interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, UserManagerBean data);
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    private class ViewHodler extends RecyclerView.ViewHolder{

        private TextView tv_user_name,tv_user_sex,tv_user_age,tv_user_zhu_su;
        private ImageView image_rightBack,image_user_icon;
        public ViewHodler(View itemView) {
            super(itemView);
            tv_user_name= (TextView) itemView.findViewById(R.id.tv_user_name);
            tv_user_sex= (TextView) itemView.findViewById(R.id.tv_user_sex);
            tv_user_age= (TextView) itemView.findViewById(R.id.tv_user_age);
            tv_user_zhu_su= (TextView) itemView.findViewById(R.id.tv_user_zhu_su);
            image_rightBack= (ImageView) itemView.findViewById(R.id.image_rightBack);
            image_user_icon= (ImageView) itemView.findViewById(R.id.image_user_icon);
        }
    }
}
