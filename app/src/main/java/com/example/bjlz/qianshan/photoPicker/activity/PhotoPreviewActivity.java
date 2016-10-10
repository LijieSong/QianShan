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

package com.example.bjlz.qianshan.photoPicker.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.photoPicker.adapter.PhotoPagerAdapter;
import com.example.bjlz.qianshan.photoPicker.widget.ViewPagerFixed;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.ToastUtil;
import com.example.bjlz.qianshan.views.TitleBarView;

import java.util.ArrayList;

/**
 * Created by foamtrace on 2015/8/25.
 */
public class PhotoPreviewActivity extends BaseActivity implements PhotoPagerAdapter.PhotoViewClickListener,ViewPager.OnPageChangeListener{

    public static final String EXTRA_PHOTOS = "extra_photos";
    public static final String EXTRA_CURRENT_ITEM = "extra_current_item";

    /** 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合  */
    public static final String EXTRA_RESULT = "preview_result";

    /** 预览请求状态码 */
    public static final int REQUEST_PREVIEW = 99;

    private ArrayList<String> paths;
    private ViewPagerFixed mViewPager;
    private PhotoPagerAdapter mPagerAdapter;
    private int currentItem = 0;
    private TitleBarView title_bar;//标题
    private Button btn_udo;//撤销按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        MyApplication.getInstance().addActivity(this);
        getData();
        initView();
        initData();
        setOnClick();
        updateActionBarTitle();
    }

    @Override
    public void initView() {
        mViewPager = (ViewPagerFixed) findViewById(R.id.vp_photos);
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        btn_udo = (Button) findViewById(R.id.btn_udo);
    }

    @Override
    public void initData() {
        this.title_bar.setRightBtnText(R.string.delete);
        this.title_bar.setRightBtnIcon(R.drawable.ic_action_discard);
        mPagerAdapter = new PhotoPagerAdapter(this, paths);
        mPagerAdapter.setPhotoViewClickListener(this);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(currentItem);
        mViewPager.setOffscreenPageLimit(5);
    }

    @Override
    public void getData() {
        paths = new ArrayList<>();
        ArrayList<String> pathArr = getIntent().getStringArrayListExtra(EXTRA_PHOTOS);
        if(pathArr != null){
            paths.addAll(pathArr);
        }
        currentItem = getIntent().getIntExtra(EXTRA_CURRENT_ITEM, 0);
    }

    @Override
    public void setOnClick() {
        mViewPager.addOnPageChangeListener(this);
        this.title_bar.setRightBtnOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == android.R.id.closeButton){
                    onBackPressed();
                    return;
                }
                final int index = mViewPager.getCurrentItem();
                final String deletedPath =  paths.get(index);
                if(paths.size() <= 1){
                    // 最后一张照片弹出删除提示
                    // show confirm dialog
                    new AlertDialog.Builder(PhotoPreviewActivity.this)
                            .setTitle(R.string.confirm_to_delete)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    paths.remove(index);
                                    onBackPressed();
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                }else{
                    paths.remove(index);
                    btn_udo.setVisibility(View.VISIBLE);
                    mPagerAdapter.notifyDataSetChanged();
                    ToastUtil.shortToastByRes(getApplicationContext(), R.string.deleted_a_photo);
                }
                btn_udo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (paths.size() > 0) {
                            paths.add(index, deletedPath);
                        } else {
                            paths.add(deletedPath);
                        }
                        mPagerAdapter.notifyDataSetChanged();
                        mViewPager.setCurrentItem(index, true);
                    }
                });
            }
        });
        this.title_bar.setLeftBtnOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void WeightOnClick(View v) {
    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
    @Override
    public void OnPhotoTapListener(View view, float v, float v1) {
        onBackPressed();
    }

    public void updateActionBarTitle() {
        this.title_bar.setTitleText(getString(R.string.image_index, mViewPager.getCurrentItem() + 1, paths.size()));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESULT, paths);
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        // 删除当前照片
        if(item.getItemId() == R.id.action_discard){
            final int index = mViewPager.getCurrentItem();
            final String deletedPath =  paths.get(index);
            Snackbar snackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), R.string.deleted_a_photo,
                    Snackbar.LENGTH_LONG);
            if(paths.size() <= 1){
                // 最后一张照片弹出删除提示
                // show confirm dialog
                new AlertDialog.Builder(this)
                        .setTitle(R.string.confirm_to_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                paths.remove(index);
                                onBackPressed();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }else{
                snackbar.show();
                paths.remove(index);
                mPagerAdapter.notifyDataSetChanged();
            }

            snackbar.setAction(R.string.undo, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (paths.size() > 0) {
                        paths.add(index, deletedPath);
                    } else {
                        paths.add(deletedPath);
                    }
                    mPagerAdapter.notifyDataSetChanged();
                    mViewPager.setCurrentItem(index, true);
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        updateActionBarTitle();
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
