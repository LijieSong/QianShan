package com.example.bjlz.qianshan.zbar;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bjlz.qianshan.CurrencyActivities.WebViewActivity;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.LogUtils;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.ToastUtil;
import com.example.bjlz.qianshan.views.TitleBarView;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

/**
 * CameraScanningActivity 二维码扫描
 * Created by ZhongHang on 2016/4/12.
 */
public class CameraScanningActivity extends BaseActivity {

    private TitleBarView title_bar;//标题
    private Context mContext = null;//上下文对象
    private static final int CAMERA_PERMISSION = 0x23;
    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;
    private FrameLayout preview;
    private TextView scanText;
    private ImageScanner scanner;
    private ImageView mImageViewLine;
    private boolean barcodeScanned = false;
    private boolean previewing = true;

    static {
        System.loadLibrary("iconv");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zbar);
        MyApplication.getInstance().addActivity(this);
        mContext = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getData();
        initView();
        initData();
        setOnClick();

    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        mImageViewLine = (ImageView) findViewById(R.id.imageview_line);
        scanText = (TextView) findViewById(R.id.scanText);

        preview = (FrameLayout) findViewById(R.id.cameraPreview);
    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.app_name);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.up_down);
        mImageViewLine.startAnimation(animation);
        //判断有无该权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请Camera权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION);
        } else {
            initMethod();//初始化相机
        }
    }

    @Override
    public void getData() {
        autoFocusHandler = new Handler();
    }

    @Override
    public void setOnClick() {
        this.title_bar.setLeftBtnOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseCamera();
                finish();
            }
        });
    }

    @Override
    public void WeightOnClick(View v) {

    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private void initMethod() {
        mCamera = getCameraInstance();
        /* Instance barcode scanner */
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);
        mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
        preview.addView(mPreview);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                initMethod();
            } else {
                // Permission Denied
                ToastUtil.shortDiyToast(CameraScanningActivity.this,"请打开使用相机的权限");
            }
        }
    }

    public void onPause() {
        super.onPause();
        releaseCamera();
    }
    /**
     * 返回键关闭本页面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            releaseCamera();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
        }
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    Camera.PreviewCallback previewCb = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = parameters.getPreviewSize();

            Image barcode = new Image(size.width, size.height, "Y800");
            barcode.setData(data);

            int result = scanner.scanImage(barcode);

            if (result != 0) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();

                SymbolSet syms = scanner.getResults();
                for (Symbol sym : syms) {
                    String results = sym.getData();
                    if (!TextUtils.isEmpty(results)) {
                        scanText.setText(sym.getData());//扫描出来的内容
                        barcodeScanned = true;
                        String content = sym.getData();
                        //&& content.contains("www.whjfs.com")
                        if (!TextUtils.isEmpty(content) && content.contains("url")) {
//                            http://www.whjfs.com/online/grave.html?id=1065
                            Bundle bundle = new Bundle();
                            bundle.putString("title","扫描详情");
                            String s = content.substring(content.indexOf("{")+5, content.lastIndexOf("}"));
                            LogUtils.error(s);
                            bundle.putString("url",s);
                            MyApplication.startActivity(CameraScanningActivity.this, WebViewActivity.class,bundle);
                            finish();
                        } else {
                            AlertDialog dialog = new AlertDialog.Builder(CameraScanningActivity.this).setTitle("扫描错误").setMessage("扫描二维码信息有误，请重新扫描").setNeutralButton("重新扫描", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    if (barcodeScanned) {
                                        barcodeScanned = false;
                                        scanText.setText(R.string.scanning);
                                        mCamera.setPreviewCallback(previewCb);
                                        mCamera.startPreview();
                                        previewing = true;
                                        mCamera.autoFocus(autoFocusCB);
                                    }
                                }
                            }).setCancelable(false).show();
                        }

                    }
                }
            }
        }
    };

    // Mimic continuous auto-focusing
    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };
}


