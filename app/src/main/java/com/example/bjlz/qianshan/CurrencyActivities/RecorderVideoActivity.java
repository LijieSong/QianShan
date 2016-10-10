package com.example.bjlz.qianshan.CurrencyActivities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.ChangeAndGetTools.CommonUtil;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.LogUtils;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.ToastUtil;
import com.example.bjlz.qianshan.tools.OtherTools.utils.ScreenUtils;
import com.example.bjlz.qianshan.views.Mobi.Utils;
import com.example.bjlz.qianshan.views.TitleBarView;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.PathUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class RecorderVideoActivity extends BaseActivity implements SurfaceHolder.Callback, MediaRecorder.OnErrorListener,
        MediaRecorder.OnInfoListener {
    private TitleBarView title_bar;//标题
    private static final String TAG = "RecorderVideoActivity";
    private final static String CLASS_LABEL = "RecorderVideoActivity";
    private PowerManager.WakeLock mWakeLock;
    private ImageView btnStart;// 开始录制按钮
    private ImageView btnStop;// 停止录制按钮
    private MediaRecorder mediarecorder;// 录制视频的类
    private VideoView mVideoView;// 显示视频的控件
    private String localPath = "";// 录制的视频路径
    private MediaScannerConnection msc = null;
    private ProgressDialog progressDialog = null;
    private Camera mCamera;
    // 预览的宽高
    private int previewWidth = 480;
    private int previewHeight = 480;
    private Chronometer chronometer;
    private int frontCamera = 0;// 0是后置摄像头，1是前置摄像头
    private ImageButton switch_btn;
    private SurfaceHolder mSurfaceHolder;
    private int defaultVideoFrameRate = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_recorder_video);
        MyApplication.getInstance().addActivity(this);
        getData();
        initView();
        initData();
        setOnClick();
    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        mVideoView = (VideoView) findViewById(R.id.mVideoView);
        switch_btn = (ImageButton) findViewById(R.id.switch_btn);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        btnStart = (ImageView) findViewById(R.id.recorder_start);
        btnStop = (ImageView) findViewById(R.id.recorder_stop);
    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.recording_video);
        mSurfaceHolder = mVideoView.getHolder();// 取得holder
        mSurfaceHolder.addCallback(this);// holder加入回调接口
        mSurfaceHolder.setKeepScreenOn(true);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); // setType必须设置，要不出错.
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWakeLock == null) {
            // 获取唤醒锁,保持屏幕常亮
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
                    CLASS_LABEL);
            mWakeLock.acquire();
        }
//		if (!initCamera()) {
//			showFailDialog();
//		}
    }

    @Override
    public void getData() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
                CLASS_LABEL);
        mWakeLock.acquire();
    }

    @Override
    public void setOnClick() {
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        switch_btn.setOnClickListener(this);
        this.title_bar.setLeftBtnOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back(v);
            }
        });
    }

    @Override
    public void WeightOnClick(View v) {
        switch (v.getId()) {
            case R.id.recorder_start://开始录像
                // start recording
                if (!startRecording())
                    return;
                ToastUtil.shortDiyToastByRec(this, R.string.The_video_to_start);
                switch_btn.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.INVISIBLE);
                btnStart.setEnabled(false);
                btnStop.setVisibility(View.VISIBLE);
                // 重置其他
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                break;
            case R.id.recorder_stop://停止录像
                btnStop.setEnabled(false);
                // 停止拍摄
                stopRecording();
                switch_btn.setVisibility(View.VISIBLE);
                chronometer.stop();
                btnStart.setVisibility(View.VISIBLE);
                btnStop.setVisibility(View.INVISIBLE);
                new AlertDialog.Builder(this)
                        .setMessage(R.string.Whether_to_send)
                        .setPositiveButton(R.string.ok,
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                        sendVideo(null);
                                    }
                                })
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        if (localPath != null) {
                                            File file = new File(localPath);
                                            if (file.exists())
                                                file.delete();
                                        }
                                        finish();

                                    }
                                }).setCancelable(false).show();
                break;
            case R.id.switch_btn:
                switchCamera();
                break;
        }
    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mCamera == null) {
            if (!initCamera()) {
                showFailDialog();
                return;
            }

        }
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
            handleSurfaceChanged();
        } catch (Exception e1) {
            LogUtils.error("start preview fail " + e1.getMessage());
            showFailDialog();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
        mSurfaceHolder = holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtils.error("surfaceDestroyed");
    }

    @SuppressLint("NewApi")
    private boolean initCamera() {
        try {
            if (frontCamera == 0) {
                mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            } else {
                mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            }
            Camera.Parameters camParams = mCamera.getParameters();
            mCamera.lock();
            mSurfaceHolder = mVideoView.getHolder();
            mSurfaceHolder.addCallback(this);
            mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            mCamera.setDisplayOrientation(90);

        } catch (RuntimeException ex) {
            LogUtils.error("init Camera fail " + ex.getMessage());
            return false;
        }
        return true;
    }

    private void handleSurfaceChanged() {
        if (mCamera == null) {
            finish();
            return;
        }
        boolean hasSupportRate = false;
        List<Integer> supportedPreviewFrameRates = mCamera.getParameters()
                .getSupportedPreviewFrameRates();
        if (supportedPreviewFrameRates != null
                && supportedPreviewFrameRates.size() > 0) {
            Collections.sort(supportedPreviewFrameRates);
            for (int i = 0; i < supportedPreviewFrameRates.size(); i++) {
                int supportRate = supportedPreviewFrameRates.get(i);

                if (supportRate == 15) {
                    hasSupportRate = true;
                }

            }
            if (hasSupportRate) {
                defaultVideoFrameRate = 15;
            } else {
                defaultVideoFrameRate = supportedPreviewFrameRates.get(0);
            }

        }
        // 获取摄像头的所有支持的分辨率
        List<Camera.Size> resolutionList = Utils.getResolutionList(mCamera);
        if (resolutionList != null && resolutionList.size() > 0) {
            Collections.sort(resolutionList, new Utils.ResolutionComparator());
            Camera.Size previewSize = null;
            boolean hasSize = false;
            // 如果摄像头支持640*480，那么强制设为640*480
            for (int i = 0; i < resolutionList.size(); i++) {
                Camera.Size size = resolutionList.get(i);
                if (size != null && size.width == 640 && size.height == 480) {
                    previewSize = size;
                    previewWidth = previewSize.width;
                    previewHeight = previewSize.height;
                    hasSize = true;
                    break;
                }
            }
            // 如果不支持设为中间的那个
            if (!hasSize) {
                int mediumResolution = resolutionList.size() / 2;
                if (mediumResolution >= resolutionList.size())
                    mediumResolution = resolutionList.size() - 1;
                previewSize = resolutionList.get(mediumResolution);
                previewWidth = previewSize.width;
                previewHeight = previewSize.height;

            }

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWakeLock != null) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    public boolean startRecording() {
        if (mediarecorder == null) {
            if (!initRecorder())
                return false;
        }
        mediarecorder.setOnInfoListener(this);
        mediarecorder.setOnErrorListener(this);
        mediarecorder.start();
        return true;
    }

    @SuppressLint("NewApi")
    private boolean initRecorder() {
        if (!EaseCommonUtils.isSdcardExist()) {
            showNoSDCardDialog();
            return false;
        }
//        previewWidth = ScreenUtils.getScreenWidth(this);
//        previewHeight = ScreenUtils.getScreenHeight(this);
        if (mCamera == null) {
            if (!initCamera()) {
                showFailDialog();
                return false;
            }
        }
        mVideoView.setVisibility(View.VISIBLE);
        // TODO init button
        mCamera.stopPreview();
        mediarecorder = new MediaRecorder();
        mCamera.unlock();
        mediarecorder.setCamera(mCamera);
        mediarecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        // 设置录制视频源为Camera（相机）
        mediarecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        if (frontCamera == 1) {
            mediarecorder.setOrientationHint(270);
        } else {
            mediarecorder.setOrientationHint(90);
        }
        // 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
        mediarecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediarecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        // 设置录制的视频编码h263 h264
        mediarecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        // 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
        mediarecorder.setVideoSize(previewWidth, previewHeight);
        // 设置视频的比特率
        mediarecorder.setVideoEncodingBitRate(384 * 1024);
        // // 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
        if (defaultVideoFrameRate != -1) {
            mediarecorder.setVideoFrameRate(defaultVideoFrameRate);
        }
        // 设置视频文件输出的路径
        localPath = PathUtil.getInstance().getVideoPath() + "/"
                + System.currentTimeMillis() + ".mp4";
        mediarecorder.setOutputFile(localPath);
        mediarecorder.setMaxDuration(30000);
        mediarecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
        try {
            mediarecorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public void stopRecording() {
        if (mediarecorder != null) {
            mediarecorder.setOnErrorListener(null);
            mediarecorder.setOnInfoListener(null);
            try {
                mediarecorder.stop();
            } catch (IllegalStateException e) {
                LogUtils.error("stopRecording error:" + e.getMessage());
            }
        }
        releaseRecorder();

        if (mCamera != null) {
            mCamera.stopPreview();
            releaseCamera();
        }
    }

    private void releaseRecorder() {
        if (mediarecorder != null) {
            mediarecorder.release();
            mediarecorder = null;
        }
    }

    protected void releaseCamera() {
        try {
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        } catch (Exception e) {
        }
    }

    @SuppressLint("NewApi")
    public void switchCamera() {

        if (mCamera == null) {
            return;
        }
        if (Camera.getNumberOfCameras() >= 2) {
            switch_btn.setEnabled(false);
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }

            switch (frontCamera) {
                case 0:
                    mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                    frontCamera = 1;
                    break;
                case 1:
                    mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                    frontCamera = 0;
                    break;
            }
            try {
                mCamera.lock();
                mCamera.setDisplayOrientation(90);
                mCamera.setPreviewDisplay(mVideoView.getHolder());
                mCamera.startPreview();
            } catch (IOException e) {
                mCamera.release();
                mCamera = null;
            }
            switch_btn.setEnabled(true);
        }
    }


    public void sendVideo(View view) {
        if (TextUtils.isEmpty(localPath)) {
            LogUtils.error("recorder fail please try again!");
            return;
        }
        if (msc == null)
            msc = new MediaScannerConnection(this,
                    new MediaScannerConnection.MediaScannerConnectionClient() {

                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            LogUtils.error("scanner completed");
                            msc.disconnect();
                            progressDialog.dismiss();
                            setResult(RESULT_OK, getIntent().putExtra("uri", uri));
                            finish();
                        }

                        @Override
                        public void onMediaScannerConnected() {
                            msc.scanFile(localPath, "video/*");
                        }
                    });


        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("processing...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
        msc.connect();

    }

    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {
        LogUtils.error("onInfo");
        if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
            LogUtils.error("max duration reached");
            stopRecording();
            switch_btn.setVisibility(View.VISIBLE);
            chronometer.stop();
            btnStart.setVisibility(View.VISIBLE);
            btnStop.setVisibility(View.INVISIBLE);
            chronometer.stop();
            if (localPath == null) {
                return;
            }
            String st3 = getResources().getString(R.string.Whether_to_send);
            new AlertDialog.Builder(this)
                    .setMessage(st3)
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    arg0.dismiss();
                                    sendVideo(null);

                                }
                            }).setNegativeButton(R.string.cancel, null)
                    .setCancelable(false).show();
        }

    }

    @Override
    public void onError(MediaRecorder mr, int what, int extra) {
        LogUtils.error("recording onError:" + what);
        stopRecording();
        ToastUtil.shortToast(this, "Recording error has occurred. Stopping the recording");
    }

    public void saveBitmapFile(Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory(), "a.jpg");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseCamera();

        if (mWakeLock != null) {
            mWakeLock.release();
            mWakeLock = null;
        }

    }

    public void back(View view) {
        releaseRecorder();
        releaseCamera();
        finish();
    }

    @Override
    public void onBackPressed() {
        back(null);
    }

    private void showFailDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.prompt)
                .setMessage(R.string.Open_the_equipment_failure)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                finish();

                            }
                        }).setCancelable(false).show();

    }

    private void showNoSDCardDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.prompt)
                .setMessage(R.string.no_sdcard)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                finish();

                            }
                        }).setCancelable(false).show();
    }
}
