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

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.ListPopupWindow;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.photoPicker.adapter.FolderAdapter;
import com.example.bjlz.qianshan.photoPicker.adapter.ImageGridAdapter;
import com.example.bjlz.qianshan.photoPicker.configandtools.Folder;
import com.example.bjlz.qianshan.photoPicker.configandtools.Image;
import com.example.bjlz.qianshan.photoPicker.configandtools.ImageCaptureManager;
import com.example.bjlz.qianshan.photoPicker.configandtools.ImageConfig;
import com.example.bjlz.qianshan.photoPicker.intent.PhotoPreviewIntent;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.LogUtils;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.ToastUtil;
import com.example.bjlz.qianshan.views.TitleBarView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhotoPickerActivity extends BaseActivity {


    private Context mCxt;

    /** 图片选择模式，int类型 */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    /** 单选 */
    public static final int MODE_SINGLE = 0;
    /** 多选 */
    public static final int MODE_MULTI = 1;
    /** 最大图片选择次数，int类型 */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    /** 默认最大照片数量 */
    public static final int DEFAULT_MAX_TOTAL= 9;
    /** 是否显示相机，boolean类型 */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    /** 默认选择的数据集 */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_result";
    /** 筛选照片配置信息 */
    public static final String EXTRA_IMAGE_CONFIG = "image_config";
    /** 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合  */
    public static final String EXTRA_RESULT = "select_result";

    // 结果数据
    private ArrayList<String> resultList = new ArrayList<>();
    // 文件夹数据
    private ArrayList<Folder> mResultFolder = new ArrayList<>();

    // 不同loader定义
    private static final int LOADER_ALL = 0;
    private static final int LOADER_CATEGORY = 1;
    //照相机的权限
    private static final int CAMERA_PERMISSION = 0x23;

    private GridView mGridView;
    private View mPopupAnchorView;
    private Button btnAlbum;
    private Button btnPreview;

    // 最大照片数量
    private ImageCaptureManager captureManager;
    private int mDesireImageCount;
    private ImageConfig imageConfig; // 照片配置

    private ImageGridAdapter mImageAdapter;
    private FolderAdapter mFolderAdapter;
    private ListPopupWindow mFolderPopupWindow;
    private int mode;//模式
    private boolean hasFolderGened = false;
    private boolean mIsShowCamera = false;
    private TitleBarView title_bar;//标题
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photopicker);
        MyApplication.getInstance().addActivity(this);
        getData();
        initView();
        initData();
        setOnClick();
        refreshActionStatus();
    }

    private void createPopupFolderList(){
        mFolderPopupWindow = new ListPopupWindow(mCxt);
        mFolderPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mFolderPopupWindow.setAdapter(mFolderAdapter);
        mFolderPopupWindow.setContentWidth(ListPopupWindow.MATCH_PARENT);
        mFolderPopupWindow.setWidth(ListPopupWindow.MATCH_PARENT);
        // 计算ListPopupWindow内容的高度(忽略mPopupAnchorView.height)，R.layout.item_foloer
        int folderItemViewHeight =
                // 图片高度
                getResources().getDimensionPixelOffset(R.dimen.folder_cover_size) +
                        // Padding Top
                        getResources().getDimensionPixelOffset(R.dimen.folder_padding) +
                        // Padding Bottom
                        getResources().getDimensionPixelOffset(R.dimen.folder_padding);
        int folderViewHeight = mFolderAdapter.getCount() * folderItemViewHeight;

        int screenHeigh = getResources().getDisplayMetrics().heightPixels;
        if(folderViewHeight >= screenHeigh){
            mFolderPopupWindow.setHeight(Math.round(screenHeigh * 0.6f));
        }else{
            mFolderPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
        }

        mFolderPopupWindow.setAnchorView(mPopupAnchorView);
        mFolderPopupWindow.setModal(true);
        mFolderPopupWindow.setAnimationStyle(R.style.Animation_AppCompat_DropDownUp);
        mFolderPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //相册列表的点击时间
                mFolderAdapter.setSelectIndex(position);
                final int index = position;
                final AdapterView v = parent;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFolderPopupWindow.dismiss();
                        if (index == 0) {
                            getSupportLoaderManager().restartLoader(LOADER_ALL, null, mLoaderCallback);
                            btnAlbum.setText(R.string.all_image);
                            mImageAdapter.setShowCamera(mIsShowCamera);
                        } else {
                            Folder folder = (Folder) v.getAdapter().getItem(index);
                            if (null != folder) {
                                mImageAdapter.setData(folder.images);
                                btnAlbum.setText(folder.name);
                                // 设定默认选择
                                if (resultList != null && resultList.size() > 0) {
                                    mImageAdapter.setDefaultSelected(resultList);
                                }
                            }
                            mImageAdapter.setShowCamera(false);
                        }

                        // 滑动到最初始位置
                        mGridView.smoothScrollToPosition(0);
                    }
                }, 100);
            }
        });
    }

    public void onSingleImageSelected(String path) {
        Intent data = new Intent();
        resultList.add(path);
        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
        setResult(RESULT_OK, data);
        finish();
    }

    public void onImageSelected(String path) {
        if(!resultList.contains(path)) {
            resultList.add(path);
        }
        refreshActionStatus();
    }

    public void onImageUnselected(String path) {
        if(resultList.contains(path)){
            resultList.remove(path);
        }
        refreshActionStatus();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                // 相机拍照完成后，返回图片路径
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:
                    if(captureManager.getCurrentPhotoPath() != null) {
                        captureManager.galleryAddPic();
                        resultList.add(captureManager.getCurrentPhotoPath());
                    }
                    complete();
                    break;
                // 预览照片
                case PhotoPreviewActivity.REQUEST_PREVIEW:
                    ArrayList<String> pathArr = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    // 刷新页面
                    if(pathArr != null && pathArr.size() != resultList.size()){
                        resultList = pathArr;
                        refreshActionStatus();
                        mImageAdapter.setDefaultSelected(resultList);
                    }
                    break;
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        LogUtils.error("on Change");
        // 重置列数
        mGridView.setNumColumns(getNumColnums());
        // 重置Item宽度
        mImageAdapter.setItemSize(getItemImageWidth());

        if(mFolderPopupWindow != null){
            if(mFolderPopupWindow.isShowing()){
                mFolderPopupWindow.dismiss();
            }

            // 重置PopupWindow高度
            int screenHeigh = getResources().getDisplayMetrics().heightPixels;
            mFolderPopupWindow.setHeight(Math.round(screenHeigh * 0.6f));
        }

        super.onConfigurationChanged(newConfig);
    }

    /**
     * 选择相机
     */
    private void showCameraAction() {
        try {
            Intent intent = captureManager.dispatchTakePictureIntent();
            startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
        } catch (IOException e) {
            ToastUtil.shortDiyToastByRec(mCxt,R.string.msg_no_camera);
            e.printStackTrace();
        }
    }

    /**
     * 选择图片操作
     * @param image
     */
    private void selectImageFromGrid(Image image, int mode) {
        if(image != null) {
            // 多选模式
            if(mode == MODE_MULTI) {
                if (resultList.contains(image.path)) {
                    resultList.remove(image.path);
                    onImageUnselected(image.path);
                } else {
                    // 判断选择数量问题
                    if(mDesireImageCount == resultList.size()){
                        ToastUtil.shortDiyToastByRec(mCxt,R.string.msg_amount_limit);
                        return;
                    }
                    resultList.add(image.path);
                    onImageSelected(image.path);
                }
                mImageAdapter.select(image);
            }else if(mode == MODE_SINGLE){
                // 单选模式
                onSingleImageSelected(image.path);
            }
        }
    }

    /**
     * 刷新操作按钮状态
     */
    private void refreshActionStatus(){
        if(resultList.contains("000000")){
            resultList.remove("000000");
        }
        String text = getString(R.string.done_with_count, resultList.size(), mDesireImageCount);
        this.title_bar.setRightBtnText(text);
        boolean hasSelected = resultList.size() > 0;
        if (hasSelected == true){
            this.title_bar.getRightBtn().setVisibility(View.VISIBLE);
//            this.title_bar.setRightBtnText(R.string.done);

        } else{
            this.title_bar.getRightBtn().setVisibility(View.GONE);
        }
        btnPreview.setEnabled(hasSelected);
        if(hasSelected){
            btnPreview.setText(getResources().getString(R.string.preview) + "(" + (resultList.size()) + ")");
        } else {
            btnPreview.setText(getResources().getString(R.string.preview));
        }
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {

        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media._ID };

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            // 根据图片设置参数新增验证条件
            StringBuilder selectionArgs = new StringBuilder();

            if(imageConfig != null){
                if(imageConfig.minWidth != 0){
                    selectionArgs.append(MediaStore.Images.Media.WIDTH + " >= " + imageConfig.minWidth);
                }

                if(imageConfig.minHeight != 0){
                    selectionArgs.append("".equals(selectionArgs.toString()) ? "" : " and ");
                    selectionArgs.append(MediaStore.Images.Media.HEIGHT + " >= " + imageConfig.minHeight);
                }

                if(imageConfig.minSize != 0f){
                    selectionArgs.append("".equals(selectionArgs.toString()) ? "" : " and ");
                    selectionArgs.append(MediaStore.Images.Media.SIZE + " >= " + imageConfig.minSize);
                }

                if(imageConfig.mimeType != null){
                    selectionArgs.append(" and (");
                    for(int i = 0, len = imageConfig.mimeType.length; i < len; i++){
                        if(i != 0){
                            selectionArgs.append(" or ");
                        }
                        selectionArgs.append(MediaStore.Images.Media.MIME_TYPE + " = '" + imageConfig.mimeType[i] + "'");
                    }
                    selectionArgs.append(")");
                }
            }

            if(id == LOADER_ALL) {
                CursorLoader cursorLoader = new CursorLoader(PhotoPickerActivity.this,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        selectionArgs.toString(), null, IMAGE_PROJECTION[2] + " DESC");
                return cursorLoader;
            }else if(id == LOADER_CATEGORY){
                String selectionStr = selectionArgs.toString();
                if(!"".equals(selectionStr)){
                    selectionStr += " and" + selectionStr;
                }
                CursorLoader cursorLoader = new CursorLoader(PhotoPickerActivity.this,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        IMAGE_PROJECTION[0] + " like '%" + args.getString("path") + "%'" + selectionStr, null,
                        IMAGE_PROJECTION[2] + " DESC");
                return cursorLoader;
            }

            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data != null) {
                List<Image> images = new ArrayList<>();
                int count = data.getCount();
                if (count > 0) {
                    data.moveToFirst();
                    do{
                        String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                        String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                        long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));

                        Image image = new Image(path, name, dateTime);
                        images.add(image);
                        if( !hasFolderGened ) {
                            // 获取文件夹名称
                            File imageFile = new File(path);
                            File folderFile = imageFile.getParentFile();
                            Folder folder = new Folder();
                            folder.name = folderFile.getName();
                            folder.path = folderFile.getAbsolutePath();
                            folder.cover = image;
                            if (!mResultFolder.contains(folder)) {
                                List<Image> imageList = new ArrayList<>();
                                imageList.add(image);
                                folder.images = imageList;
                                mResultFolder.add(folder);
                            } else {
                                // 更新
                                Folder f = mResultFolder.get(mResultFolder.indexOf(folder));
                                f.images.add(image);
                            }
                        }

                    }while(data.moveToNext());

                    mImageAdapter.setData(images);

                    // 设定默认选择
                    if(resultList != null && resultList.size()>0){
                        mImageAdapter.setDefaultSelected(resultList);
                    }

                    mFolderAdapter.setData(mResultFolder);
                    hasFolderGened = true;

                }
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    /**
     * 获取GridView Item宽度
     * @return
     */
    private int getItemImageWidth(){
        int cols = getNumColnums();
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int columnSpace = getResources().getDimensionPixelOffset(R.dimen.space_size);
        return (screenWidth - columnSpace * (cols-1)) / cols;
    }

    /**
     * 根据屏幕宽度与密度计算GridView显示的列数， 最少为三列
     * @return
     */
    private int getNumColnums(){
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        return cols < 3 ? 3 : cols;
    }

    // 返回已选择的图片数据
    private void complete(){
        Intent data = new Intent();
        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        captureManager.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        captureManager.onRestoreInstanceState(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                showCameraAction();
            } else {
                // Permission Denied
                ToastUtil.shortDiyToastByRec(mCxt,R.string.msg_no_camera);
            }
        }
    }
    @Override
    public void initView() {
        mCxt = this;
        captureManager = new ImageCaptureManager(mCxt);
        mGridView = (GridView) findViewById(R.id.grid);
        mGridView.setNumColumns(getNumColnums());
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        mPopupAnchorView = findViewById(R.id.photo_picker_footer);
        btnAlbum = (Button) findViewById(R.id.btnAlbum);
        btnPreview = (Button) findViewById(R.id.btnPreview);
    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.image);
        // 默认选择
        if(mode == MODE_MULTI) {
            ArrayList<String> tmp = getIntent().getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
            if(tmp != null && tmp.size() > 0) {
                resultList.addAll(tmp);
            }
        }
        mImageAdapter = new ImageGridAdapter(mCxt, mIsShowCamera, getItemImageWidth());
        // 是否显示选择指示器
        mImageAdapter.showSelectIndicator(mode == MODE_MULTI);
        mGridView.setAdapter(mImageAdapter);
        //文件夹匹配器
        mFolderAdapter = new FolderAdapter(mCxt);
    }

    @Override
    public void getData() {
        // 照片属性
        imageConfig = getIntent().getParcelableExtra(EXTRA_IMAGE_CONFIG);
        // 首次加载所有图片
        getSupportLoaderManager().initLoader(LOADER_ALL, null, mLoaderCallback);
        // 选择图片数量
        mDesireImageCount = getIntent().getIntExtra(EXTRA_SELECT_COUNT, DEFAULT_MAX_TOTAL);
        // 图片选择模式
        mode = getIntent().getExtras().getInt(EXTRA_SELECT_MODE, MODE_SINGLE);
        // 是否显示照相机
        mIsShowCamera = getIntent().getBooleanExtra(EXTRA_SHOW_CAMERA, false);
//        mIsShowCamera = getIntent().getBooleanExtra(EXTRA_SHOW_CAMERA, true);
    }

    @Override
    public void setOnClick() {
        mGridView.setOnItemClickListener(this);//grideview的item点击
        btnAlbum.setOnClickListener(this);//相册
        btnPreview.setOnClickListener(this);//预览
        this.title_bar.setRightBtnOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complete();
            }
        });
//        this.title_bar.setLeftBtnOnclickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                complete();
//            }
//        });
    }

    @Override
    public void WeightOnClick(View v) {
        switch(v.getId()){
            case R.id.btnAlbum://打开相册
                if (mFolderPopupWindow == null) {
                    createPopupFolderList();
                }

                if (mFolderPopupWindow.isShowing()) {
                    mFolderPopupWindow.dismiss();
                } else {
                    mFolderPopupWindow.show();
                    int index = mFolderAdapter.getSelectIndex();
                    index = index == 0 ? index : index - 1;
                    mFolderPopupWindow.getListView().setSelection(index);
                }
            break;
            case R.id.btnPreview://预览
                PhotoPreviewIntent intent = new PhotoPreviewIntent(mCxt);
                intent.setCurrentItem(0);
                intent.setPhotoPaths(resultList);
                startActivityForResult(intent, PhotoPreviewActivity.REQUEST_PREVIEW);
            break;
        }
    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //相机的点击时间
        if (mImageAdapter.isShowCamera()) {
            // 如果显示照相机，则第一个Grid显示为照相机，处理特殊逻辑
            if (i == 0) {
                if(mode == MODE_MULTI){
                    // 判断选择数量问题
                    if(mDesireImageCount == resultList.size()-1){
                        Toast.makeText(mCxt, R.string.msg_amount_limit, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                //判断有无该权限
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请Camera权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                            CAMERA_PERMISSION);
                }else{
                    showCameraAction();
                }
            } else {
                // 正常操作
                Image image = (Image) adapterView.getAdapter().getItem(i);
                selectImageFromGrid(image, mode);
            }
        } else {
            // 正常操作
            Image image = (Image) adapterView.getAdapter().getItem(i);
            selectImageFromGrid(image, mode);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            complete();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
