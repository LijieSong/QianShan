package com.example.bjlz.qianshan.CurrencyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.bjlz.qianshan.R;

import java.util.ArrayList;

/**
 * 项目名称：qainshan
 * 类描述：GridAdapter 照片显示匹配器
 * 创建人：slj
 * 创建时间：2016-6-5 16:08
 * 修改人：slj
 * 修改时间：2016-7-12 10:48
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class GridAdapter extends BaseAdapter {
    private ArrayList<String> listUrls;
    private LayoutInflater inflater;
    private Context context;
    public GridAdapter(Context context, ArrayList<String> listUrls) {
        this.listUrls = listUrls;
        this.context = context;
        if(listUrls.size() == 7){
            listUrls.remove(listUrls.size()-1);
        }
        inflater = LayoutInflater.from(context);
    }

    public int getCount(){
        return  listUrls.size();
    }
    @Override
    public String getItem(int position) {
        return listUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_imggride, parent,false);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final String path=listUrls.get(position);
        if (path.equals("000000")){
            holder.image.setImageResource(R.drawable.addimage);
        }else {
            Glide.with(context)
                    .load(path)
                    .placeholder(R.mipmap.default_error)
                    .error(R.mipmap.default_error)
                    .centerCrop()
                    .crossFade()
                    .into(holder.image);
        }
        return convertView;
    }
    class ViewHolder {
        ImageView image;
    }
}
