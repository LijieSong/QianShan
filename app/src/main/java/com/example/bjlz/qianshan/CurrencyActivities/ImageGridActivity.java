package com.example.bjlz.qianshan.CurrencyActivities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
//import android.support.v4.app.FragmentTransaction;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.bjlz.qianshan.BuildConfig;
import com.example.bjlz.qianshan.CurrencyAdapter.ImageAdapter;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
//import com.example.bjlz.qianshan.CurrencyFragment.ImageGridFragment;
import com.example.bjlz.qianshan.CurrencyBean.VideoEntity;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.ChangeAndGetTools.CommonUtil;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.LogUtils;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.ToastUtil;
import com.example.bjlz.qianshan.views.Mobi.ImageCache;
import com.example.bjlz.qianshan.views.Mobi.ImageResizer;
import com.example.bjlz.qianshan.views.Mobi.Utils;
import com.example.bjlz.qianshan.views.TitleBarView;
import java.util.ArrayList;
import java.util.List;

public class ImageGridActivity extends BaseActivity {
    private GridView mGridView;//显示视频列表
    private TitleBarView title_bar;//标题
    private int mImageThumbSize;
    private int mImageThumbSpacing;
    private ImageAdapter mAdapter;
    private ImageResizer mImageResizer;
    List<VideoEntity> mList;
//	private static final String TAG = "ImageGridActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagegrid);
//        setContentView(R.layout.em_activity_chat);
        MyApplication.getInstance().addActivity(this);
//        if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
//            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.add(R.id.container, new ImageGridFragment(), TAG);
//            ft.commit();
//        }
        getData();
        initView();
        initData();
        setOnClick();
    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        mGridView = (GridView) findViewById(R.id.gridView);
    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.video_list);
        mImageThumbSize = getResources().getDimensionPixelSize(
                R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(
                R.dimen.image_thumbnail_spacing);
        ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams();
        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of
        // app memory
        // The ImageFetcher takes care of loading images into our ImageView
        // children asynchronously
        mImageResizer = new ImageResizer(this, mImageThumbSize);
        mImageResizer.setLoadingImage(R.mipmap.em_empty_photo);
        mImageResizer.addImageCache(this.getSupportFragmentManager(),
                cacheParams);
        mList = new ArrayList<VideoEntity>();
        mAdapter = new ImageAdapter(this,mList,mImageResizer);

        mGridView.setAdapter(mAdapter);
    }

    @Override
    public void getData() {
        getVideoFile();
    }

    @Override
    public void setOnClick() {
        mGridView.setOnItemClickListener(this);
        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView,
                                             int scrollState) {
                // Pause fetcher to ensure smoother scrolling when flinging
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Before Honeycomb pause image loading on scroll to help
                    // with performance
                    if (!Utils.hasHoneycomb()) {
                        mImageResizer.setPauseWork(true);
                    }
                } else {
                    mImageResizer.setPauseWork(false);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
            }
        });
        // This listener is used to get the final width of the GridView and then
        // calculate the
        // number of columns and the width of each column. The width of each
        // column is variable
        // as the GridView has stretchMode=columnWidth. The column width is used
        // to set the height
        // of each view so we get nice square thumbnails.
        mGridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        final int numColumns = (int) Math.floor(mGridView
                                .getWidth()
                                / (mImageThumbSize + mImageThumbSpacing));
                        if (numColumns > 0) {
                            final int columnWidth = (mGridView.getWidth() / numColumns)
                                    - mImageThumbSpacing;
                            mAdapter.setItemHeight(columnWidth);
                            if (BuildConfig.DEBUG) {
                                LogUtils.error( "onCreateView - numColumns set to "
                                        + numColumns);
                            }
                            if (Utils.hasJellyBean()) {
                                mGridView.getViewTreeObserver()
                                        .removeOnGlobalLayoutListener(this);
                            } else {
                                mGridView.getViewTreeObserver()
                                        .removeGlobalOnLayoutListener(this);
                            }
                        }
                    }
                });
    }

    @Override
    public void WeightOnClick(View v) {

    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mImageResizer.setPauseWork(true);

        if (i == 0) {
            MyApplication.startActivityForResult(ImageGridActivity.this, RecorderVideoActivity.class, 100);
        } else {
            VideoEntity vEntty = mList.get(i - 1);
            // 限制大小不能超过10M
            if (vEntty.size > 1024 * 1024 * 10) {
                String st = getResources().getString(R.string.temporary_does_not);
                ToastUtil.shortToast(getBaseContext(), st);
                return;
            }
            Intent intent = ImageGridActivity.this.getIntent().putExtra("path", vEntty.filePath).putExtra("dur", vEntty.duration);
            ImageGridActivity.this.setResult(Activity.RESULT_OK, intent);
            ImageGridActivity.this.finish();
        }
    }


    private void getVideoFile() {
        ContentResolver mContentResolver = this.getBaseContext().getContentResolver();
//        Cursor cursor = mContentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.DEFAULT_SORT_ORDER);
        Cursor cursor = mContentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        LogUtils.error("cursor为空:"+cursor);
        if (cursor != null && cursor.moveToFirst()) {
            LogUtils.error("cursor不为空:"+cursor);
            do {
                // ID:MediaStore.Audio.Media._ID
                int id = cursor.getInt(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media._ID));

                // 名称：MediaStore.Audio.Media.TITLE
                String title = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                // 路径：MediaStore.Audio.Media.DATA
                String url = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));

                // 总播放时长：MediaStore.Audio.Media.DURATION
                int duration = cursor
                        .getInt(cursor
                                .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));

                // 大小：MediaStore.Audio.Media.SIZE
                int size = (int) cursor.getLong(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));

                VideoEntity entty = new VideoEntity();
                entty.ID = id;
                entty.title = title;
                entty.filePath = url;
                entty.duration = duration;
                entty.size = size;
                mList.add(entty);
                LogUtils.error("VideoList:"+ mList +"----大小:"+mList.size());
            } while (cursor.moveToNext());

        }
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                Uri uri = data.getParcelableExtra("uri");
                String[] projects = new String[]{MediaStore.Video.Media.DATA,
                        MediaStore.Video.Media.DURATION};
                Cursor cursor = this.getContentResolver().query(
                        uri, projects, null,
                        null, null);
                int duration = 0;
                String filePath = null;

                if (cursor.moveToFirst()) {
                    // 路径：MediaStore.Audio.Media.DATA
                    filePath = cursor.getString(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                    // 总播放时长：MediaStore.Audio.Media.DURATION
                    duration = cursor
                            .getInt(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                    LogUtils.error("duration:" + duration);
                }
                if (cursor != null) {
                    cursor.close();
                    cursor = null;
                }
                ImageGridActivity.this.setResult(Activity.RESULT_OK,  ImageGridActivity.this.getIntent().putExtra("path", filePath).putExtra("dur", duration));
                ImageGridActivity.this.finish();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mImageResizer.setExitTasksEarly(false);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImageResizer.closeCache();
        mImageResizer.clearCache();
    }

}
