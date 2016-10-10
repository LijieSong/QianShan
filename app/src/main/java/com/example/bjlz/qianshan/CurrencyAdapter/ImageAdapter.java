package com.example.bjlz.qianshan.CurrencyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bjlz.qianshan.CurrencyBean.VideoEntity;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.views.Mobi.ImageResizer;
import com.example.bjlz.qianshan.views.Mobi.RecyclingImageView;
import com.hyphenate.util.DateUtils;
import com.hyphenate.util.TextFormater;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private final Context mContext;
    private int mItemHeight = 0;
    private ImageResizer mImageResizer = null;
    private RelativeLayout.LayoutParams mImageViewLayoutParams;
    private List<VideoEntity> vList = new ArrayList<VideoEntity>();

    public ImageAdapter(Context context, List<VideoEntity> mList, ImageResizer mImageResizer) {
        super();
        this.mContext = context;
        this.vList = mList;
        this.mImageResizer = mImageResizer;
        mImageViewLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public int getCount() {
        return vList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return (position == 0) ? null : vList.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.choose_griditem, container, false);
            holder.imageView = (RecyclingImageView) convertView.findViewById(R.id.imageView);
            holder.icon = (ImageView) convertView.findViewById(R.id.video_icon);
            holder.tvDur = (TextView) convertView.findViewById(R.id.chatting_length_iv);
            holder.tvSize = (TextView) convertView.findViewById(R.id.chatting_size_iv);
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.imageView.setLayoutParams(mImageViewLayoutParams);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Check the height matches our calculated column width
        if (holder.imageView.getLayoutParams().height != mItemHeight) {
            holder.imageView.setLayoutParams(mImageViewLayoutParams);
        }

        // Finally load the image asynchronously into the ImageView, this
        // also takes care of
        // setting a placeholder image while the background thread runs
        String st1 = mContext.getResources().getString(R.string.Video_footage);
        if (position == 0) {
            holder.icon.setVisibility(View.GONE);
            holder.tvDur.setVisibility(View.GONE);
            holder.tvSize.setText(st1);
            holder.imageView.setImageResource(R.mipmap.em_actionbar_camera_icon);
        } else {
            holder.icon.setVisibility(View.VISIBLE);
            VideoEntity entty = vList.get(position - 1);
            holder.tvDur.setVisibility(View.VISIBLE);

            holder.tvDur.setText(DateUtils.toTime(entty.duration));
            holder.tvSize.setText(TextFormater.getDataSize(entty.size));
            holder.imageView.setImageResource(R.mipmap.em_empty_photo);
            mImageResizer.loadImage(entty.filePath, holder.imageView);
        }
        return convertView;
//             END_INCLUDE(load_gridview_item)
    }

    /**
     * Sets the item height. Useful for when we know the column width so the
     * height can be set to match.
     *
     * @param height
     */
    public void setItemHeight(int height) {
        if (height == mItemHeight) {
            return;
        }
        mItemHeight = height;
        mImageViewLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, mItemHeight);
        mImageResizer.setImageSize(height);
        notifyDataSetChanged();
    }

    class ViewHolder {

        RecyclingImageView imageView;
        ImageView icon;
        TextView tvDur;
        TextView tvSize;
    }
}