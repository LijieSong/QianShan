package com.example.bjlz.qianshan.CurrencyActivities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.example.bjlz.qianshan.CurrencyAdapter.GridAdapter;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.photoPicker.activity.PhotoPickerActivity;
import com.example.bjlz.qianshan.photoPicker.activity.PhotoPreviewActivity;
import com.example.bjlz.qianshan.photoPicker.configandtools.ImageCaptureManager;
import com.example.bjlz.qianshan.photoPicker.configandtools.SelectModel;
import com.example.bjlz.qianshan.photoPicker.intent.PhotoPickerIntent;
import com.example.bjlz.qianshan.photoPicker.intent.PhotoPreviewIntent;
import com.example.bjlz.qianshan.tools.BitmapTools.FileTools.FileUploadManager;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.LogUtils;
import com.example.bjlz.qianshan.views.TitleBarView;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * 项目名称：qainshandoctor
 * 类描述：InformationActivity 个人信息
 * 创建人：slj
 * 创建时间：2016-6-5 16:08
 * 修改人：slj
 * 修改时间：2016-6-5 16:08
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class InformationActivity extends BaseActivity{
    private TitleBarView title_bar;//标题
    private Context mContext = null;//上下文对象
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private ImageCaptureManager captureManager; // 相机拍照处理类
    private static final int STORAGE_PERMISSION = 0X23;
    private GridView gridView;
    private GridAdapter gridAdapter;
    private Button mButton;
    private String depp;
    private EditText textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        MyApplication.getInstance().addActivity(this);
        mContext = this;
        getData();
        initView();
        initData();
        setOnClick();
    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        gridView = (GridView) findViewById(R.id.gridView);
        mButton = (Button) findViewById(R.id.button);
        textView= (EditText)findViewById(R.id.et_context);
    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.information);
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        gridView.setNumColumns(cols);
        imagePaths.add("000000");
        gridAdapter = new GridAdapter(InformationActivity.this,imagePaths);
        gridView.setAdapter(gridAdapter);
    }

    @Override
    public void getData() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请Camera权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION);
        }
    }

    @Override
    public void setOnClick() {
        gridView.setOnItemClickListener(this);
        mButton.setOnClickListener(this);
    }

    @Override
    public void WeightOnClick(View v) {
        switch(v.getId()){
            case R.id.button:
                depp =textView.getText().toString().trim()!=null?textView.getText().toString().trim():textView.getText().toString().trim();
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        FileUploadManager.uploadMany(imagePaths, depp);
//                        FileUploadManager.upload(imagePaths,depp);
                    }
                }.start();
                finish();
            break;
        }
    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String imgs = (String) parent.getItemAtPosition(position);
        if ("000000".equals(imgs) || "000000".contains(imgs)){
            PhotoPickerIntent intent = new PhotoPickerIntent(InformationActivity.this);
            intent.setSelectModel(SelectModel.MULTI);
//            intent.setSelectModel(SelectModel.SINGLE);
            intent.setShowCarema(true); // 是否显示拍照
            intent.setMaxTotal(6); // 最多选择照片数量，默认为6
            intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
            startActivityForResult(intent, REQUEST_CAMERA_CODE);
        }else{
            PhotoPreviewIntent intent = new PhotoPreviewIntent(InformationActivity.this);
            intent.setCurrentItem(position);
            imagePaths.remove("000000");
            intent.setPhotoPaths(imagePaths);//设置显示图片
            startActivityForResult(intent, REQUEST_PREVIEW_CODE);
        }
    }
    //处理返回过来的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    LogUtils.error("list: " + "list = [" + list.size()+"]");
                    loadAdpater(list);
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    LogUtils.error("ListExtra: " + "ListExtra = [" + ListExtra.size()+"]");
                    loadAdpater(ListExtra);
                    break;
            }
        }
    }

    /**
     * 加载图片适配器
     * @param paths
     */
    private void loadAdpater(ArrayList<String> paths){
        if (imagePaths!=null&& imagePaths.size()>0){
            imagePaths.clear();
        }
        if (paths.contains("000000")){
            paths.remove("000000");
        }
        paths.add("000000");
        imagePaths.addAll(paths);
        gridAdapter  = new GridAdapter(InformationActivity.this,imagePaths);
        gridView.setAdapter(gridAdapter);
        try{
            JSONArray obj = new JSONArray(imagePaths);
            LogUtils.error("obj:"+obj.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
