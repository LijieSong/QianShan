package com.example.bjlz.qianshan.Obsessive.bluetooth.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.bjlz.qianshan.CurrencyApplication.Address;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.Obsessive.popupwindow.AccessPopWindow;
import com.example.bjlz.qianshan.Obsessive.popupwindow.EndTimePopWindow;
import com.example.bjlz.qianshan.Obsessive.popupwindow.StartTimePopWindow;
import com.example.bjlz.qianshan.Obsessive.popupwindow.WeekPopWindow;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.HttpAndNetWorkTools.HttpTools.HttpUtil;
import com.example.bjlz.qianshan.tools.ChangeAndGetTools.ByteUtils;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.LogUtils;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.PreferencesUtils;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.ToastUtil;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.UserUtils;
import com.example.bjlz.qianshan.views.TitleBarView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class BlueToothActivity extends BaseActivity {
    private TitleBarView title_bar;//标题
    private Button btn_bta_connect = null;//连接蓝牙
    private BluetoothAdapter bluetoothAdapter;//适配器
    private OutputStream outputStream = null;//输出流
    private Context mContext;
    private boolean mConnect = false;//连接
    private GridView grid_button;
    private byte[] b = null;
    private boolean click = false;
//    private String url = MyApplication.BASE_URL + "/blueTooth/blueToothSaveOrUpdate.do";
    //水阀按键
    private Button btn_all, hy1, hy2, hy3, hy4, btn_send_bluetooth, btn_bta_open;
    private ImageView timing;
    private TextView get_text, txt_mactime, get_time, timing_text;
    private int[] button_image = {R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};
    private String[] button_name = {"定时周期", "设备通道", "开启时间", "关闭时间"};
    public static byte[] message = new byte[24];//发送消息
    private byte[] longByte = null;
    PopupWindow popWindow = null;
    private final Timer mTimer = new Timer();
    private TimerTask task = null;
    private BluetoothSocket socket = null;//连接流
    private String address = null;//蓝牙地址
    private String userName = null;//用户名
    private Map<String, String> map = null;//记录用户名
    private String bluetoothAddress = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    try {
                        if (socket != null) {
                            if (socket.isConnected() == true) {
                                getMesssage();
                            }
                            if (b != null) {
                                String s = ByteUtils.bytesToHexString(b);
                                get_text.setText(String.valueOf(b[6]) + "年" + String.valueOf(b[4]) + "月" + String.valueOf(b[3]) + "日");
                                get_time.setText(String.valueOf(b[2]) + "时" + String.valueOf(b[1]) + "分" + String.valueOf(b[0]) + "秒");
                                longByte = new byte[]{b[6], b[4], b[3], b[2], b[1], b[0]};
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    btn_bta_connect.setText(R.string.success_connected);
                    btn_bta_connect.setEnabled(false);
                    ToastUtil.shortDiyToastColorBgByRecTextSizeTextColor(mContext, R.string.success_connected,getResources().getColor(R.color.transparent),14f,getResources().getColor(R.color.black));
                    break;
                case 0:
                    ToastUtil.shortDiyToastColorBgByRecTextSizeTextColor(mContext, R.string.failed_connected,getResources().getColor(R.color.transparent),14f,getResources().getColor(R.color.black));
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);
        MyApplication.getInstance().addActivity(this);
        mContext = this;
        message[0] = 85;
        message[1] = 85;
        getData();
        initView();
        initData();
        setOnClick();
        task = new TimerTask() {
            public void run() {
                //execute the task
                handler.sendEmptyMessage(2);
            }
        };
        mTimer.schedule(task, 0, 1000);
        bluetoothAddress = PreferencesUtils.getString(getApplicationContext(), "bluetoothAddress");
        if (bluetoothAddress != null) {
            connectDevice(bluetoothAddress, true);
            address = bluetoothAddress;
        }
        if (socket != null) {
            if (socket.isConnected() == true) {
                getMesssage();
                send();
            }
        }
    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        btn_bta_connect = (Button) findViewById(R.id.btn_bta_connect);
        btn_bta_open = (Button) findViewById(R.id.btn_bta_open);
        grid_button = (GridView) findViewById(R.id.grid_button);
        hy1 = (Button) findViewById(R.id.hy1);
        hy2 = (Button) findViewById(R.id.hy2);
        hy3 = (Button) findViewById(R.id.hy3);
        hy4 = (Button) findViewById(R.id.hy4);
        btn_all = (Button) findViewById(R.id.btn_all);
        btn_send_bluetooth = (Button) findViewById(R.id.btn_send_bluetooth);
        timing = (ImageView) findViewById(R.id.timing);
        get_text = (TextView) findViewById(R.id.get_text);
        txt_mactime = (TextView) findViewById(R.id.txt_mactime);
        timing_text = (TextView) findViewById(R.id.timing_text);
        get_time = (TextView) findViewById(R.id.get_time);
    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.smart_box);
        //get button name and image
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < button_image.length; i++) {
            HashMap<String, Object> map1 = new HashMap<String, Object>();
            map1.put("image", button_image[i]);
            map1.put("text", button_name[i]);
            list.add(map1);
        }
        SimpleAdapter adapter = new SimpleAdapter(mContext, list, R.layout.grid_item,
                new String[]{"image", "text"}, new int[]{R.id.grid_image, R.id.grid_text});
        grid_button.setAdapter(adapter);

    }

    void send() {
        SendBluetoothTask task = new SendBluetoothTask();
        task.execute();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void getData() {
        android.bluetooth.BluetoothManager systemService = (android.bluetooth.BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        //获取蓝牙适配器
        bluetoothAdapter = systemService.getAdapter();
//        获取蓝牙适配器
//        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        map = UserUtils.readInfo(getApplicationContext());
        if (map != null) {
            userName = map.get("name");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bluetoothAdapter.enable() != false) {
            btn_bta_connect.setVisibility(View.VISIBLE);
            btn_bta_open.setVisibility(View.GONE);
        } else{
            btn_bta_connect.setVisibility(View.INVISIBLE);
            btn_bta_open.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void setOnClick() {
        grid_button.setOnItemClickListener(this);
        btn_bta_connect.setOnClickListener(this);
        btn_bta_open.setOnClickListener(this);
        btn_all.setOnClickListener(this);
        timing.setOnClickListener(this);
        timing_text.setOnClickListener(this);
        btn_send_bluetooth.setOnClickListener(this);
        hy1.setOnClickListener(this);
        hy2.setOnClickListener(this);
        hy3.setOnClickListener(this);
        hy4.setOnClickListener(this);
    }

    @Override
    public void WeightOnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bta_open:
                if (mConnect == false){
                    if (bluetoothAdapter !=null){
                        if (!bluetoothAdapter.isEnabled()) {
                            bluetoothAdapter.enable();
//                            ToastUtil.shortDiyToastColorBgTextSizeTextColor(mContext, "正在打开蓝牙,请稍等...",getResources().getColor(R.color.transparent),14f,getResources().getColor(R.color.black));
                            mConnect =true;
                            btn_bta_open.setVisibility(View.GONE);
                            btn_bta_connect.setVisibility(View.VISIBLE);
                        }else{
//                            ToastUtil.shortDiyToastColorBgTextSizeTextColor(mContext, "蓝牙已打开,请连接设备...",getResources().getColor(R.color.transparent),14f,getResources().getColor(R.color.black));
                            btn_bta_open.setVisibility(View.GONE);
                            btn_bta_connect.setVisibility(View.VISIBLE);
                        }
                    }
                }
                break;
            case R.id.btn_bta_connect:
                    MyApplication.startActivityForResult(BlueToothActivity.this, DeviceListActivity.class, 1);
                break;
            case R.id.btn_all:
                if (click == false) {
                    message[2] = (byte) 204;
                    message[3] = 5;
                    sendMessage();
                    message[3] = 0;
                    click = true;
                    if (socket != null) {
                        if (socket.isConnected() == true) {
                            btn_all.setBackgroundResource(R.drawable.checkbox_on);
                            hy1.setBackgroundResource(R.drawable.checkbox_on);
                            hy2.setBackgroundResource(R.drawable.checkbox_on);
                            hy3.setBackgroundResource(R.drawable.checkbox_on);
                            hy4.setBackgroundResource(R.drawable.checkbox_on);
                        }
                    }
                } else {
                    message[2] = (byte) 204;
                    message[3] = 80;
                    sendMessage();
                    message[3] = 0;
                    click = false;
                    if (socket != null) {
                        if (socket.isConnected() == true) {
                            btn_all.setBackgroundResource(R.drawable.checkbox_off);
                            hy1.setBackgroundResource(R.drawable.checkbox_off);
                            hy2.setBackgroundResource(R.drawable.checkbox_off);
                            hy3.setBackgroundResource(R.drawable.checkbox_off);
                            hy4.setBackgroundResource(R.drawable.checkbox_off);
                        }
                    }
                }
                break;
            case R.id.hy1:
                if (click == false) {
                    hy1.setBackgroundResource(R.drawable.checkbox_on);
                    message[2] = (byte) 204;
                    message[3] = 1;
                    sendMessage();
                    message[3] = 0;
                    click = true;
                } else {
                    message[2] = (byte) 204;
                    message[3] = 16;
                    sendMessage();
                    message[3] = 0;
                    click = false;
                    hy1.setBackgroundResource(R.drawable.checkbox_off);

                }
                break;
            case R.id.hy2:
                if (click == false) {
                    hy2.setBackgroundResource(R.drawable.checkbox_on);
                    message[2] = (byte) 204;
                    message[3] = 2;
                    sendMessage();
                    message[3] = 0;
                    click = true;
                } else {
                    message[2] = (byte) 204;
                    message[3] = 32;
                    sendMessage();
                    message[3] = 0;
                    click = false;
                    hy2.setBackgroundResource(R.drawable.checkbox_off);

                }
                break;
            case R.id.hy3:
                if (click == false) {
                    hy3.setBackgroundResource(R.drawable.checkbox_on);
                    message[2] = (byte) 204;
                    message[3] = 3;
                    sendMessage();
                    message[3] = 0;
                    click = true;
                } else {
                    message[2] = (byte) 204;
                    message[3] = 48;
                    sendMessage();
                    message[3] = 0;
                    click = false;
                    hy3.setBackgroundResource(R.drawable.checkbox_off);

                }
                break;
            case R.id.hy4:
                if (click == false) {
                    hy4.setBackgroundResource(R.drawable.checkbox_on);
                    message[2] = (byte) 204;
                    message[3] = 4;
                    sendMessage();
                    message[3] = 0;
                    click = true;
                } else {
                    message[2] = (byte) 204;
                    message[3] = 64;
                    sendMessage();
                    message[3] = 0;
                    click = false;
                    hy4.setBackgroundResource(R.drawable.checkbox_off);
                }
                break;
            case R.id.timing://定时
                if (socket != null) {
                    if (message[3] != 0) {
                        message[2] = (byte) 170;
                        sendMessage();
                        ToastUtil.shortDiyToastColorBgTextSizeTextColor(mContext, "定时成功",getResources().getColor(R.color.transparent),14f,getResources().getColor(R.color.black));
                    } else {
                        ToastUtil.shortDiyToastColorBgTextSizeTextColor(mContext,"请先设定时间",getResources().getColor(R.color.transparent),14f,getResources().getColor(R.color.black));
                    }
                } else {
                    ToastUtil.shortDiyToastColorBgByRecTextSizeTextColor(mContext, R.string.bluetooth_connected_first,getResources().getColor(R.color.transparent),14f,getResources().getColor(R.color.black));
                }
                break;
            case R.id.timing_text://定时
                if (socket != null) {
                    if (message[3] != 0) {
                        message[2] = (byte) 170;
                        sendMessage();
                        ToastUtil.shortDiyToastColorBgTextSizeTextColor(mContext,"定时成功",getResources().getColor(R.color.transparent),14f,getResources().getColor(R.color.black));
                    } else {
                        ToastUtil.shortDiyToastColorBgTextSizeTextColor(mContext,"请先设定时间",getResources().getColor(R.color.transparent),14f,getResources().getColor(R.color.black));
                    }
                } else {
                    ToastUtil.shortDiyToastColorBgByRecTextSizeTextColor(mContext, R.string.bluetooth_connected_first,getResources().getColor(R.color.transparent),14f,getResources().getColor(R.color.black));
                }
                break;
            case R.id.btn_send_bluetooth:
                    if (socket !=null){
                        if (mConnect != false){
                            send();
                    }
                }else {
                        ToastUtil.shortDiyToastColorBgByRecTextSizeTextColor(mContext, R.string.bluetooth_connected_first,getResources().getColor(R.color.transparent),14f,getResources().getColor(R.color.black));
                    }
                break;
        }
    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                popWindow = new WeekPopWindow(mContext);
                getPopWindow();
                break;
            case 1:
                popWindow = new AccessPopWindow(mContext);
                getPopWindow();
                break;
            case 2:
                popWindow = new StartTimePopWindow(mContext);
                getPopWindow();
                break;
            case 3:
                popWindow = new EndTimePopWindow(mContext);
                getPopWindow();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                // 当DeviceListActivity返回设备连接
                if (data != null) {
                    if (resultCode == Activity.RESULT_OK) {
                        connectDevice(data, true);
                    }
                }
                break;
        }
    }

    /**
     * 连接蓝牙的方法
     *
     * @param data
     * @param secure
     */
    private void connectDevice(final Intent data, boolean secure) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                // 设备的MAC地址
                address = data.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                PreferencesUtils.putString(getApplicationContext(), "bluetoothAddress", address);
                //得到BluetoothDevice对象
                BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
                // 试图连接到设备
                try {
                    getConnect(device);
                } catch (Exception e) {
                    handler.sendEmptyMessage(0);
                }
            }
        }.start();
    }

    /**
     * 连接蓝牙的方法
     *
     * @param bluetoothAddress
     * @param secure
     */
    private void connectDevice(final String bluetoothAddress, boolean secure) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                //得到BluetoothDevice对象
                BluetoothDevice device = bluetoothAdapter.getRemoteDevice(bluetoothAddress);
                // 试图连接到设备
                try {
                    getConnect(device);
                } catch (Exception e) {
                    handler.sendEmptyMessage(0);
                }
            }
        }.start();
    }


    //连接方法
    private void getConnect(final BluetoothDevice device) {
        final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
        final UUID uuid = UUID.fromString(SPP_UUID);
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    socket = device.createRfcommSocketToServiceRecord(uuid);
                    socket.connect();
                    mConnect = true;
                    handler.sendEmptyMessage(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //弹出窗
    private void getPopWindow() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        popWindow.showAtLocation(findViewById(R.id.root),
                Gravity.CENTER, 0, 0);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }


    //给终端发送数据
    public void sendMessage() {

        if (socket != null) {
            if (socket.isConnected() == true) {
                try {
                    outputStream = socket.getOutputStream();
                    outputStream.write(message);
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            ToastUtil.shortDiyToastColorBgByRecTextSizeTextColor(mContext, R.string.bluetooth_connected_first,getResources().getColor(R.color.transparent),14f,getResources().getColor(R.color.black));
        }
    }

    //给终端发送字节数据

    public void sendMessage(final byte[] msg) {
        if (socket != null) {
            if (socket.isConnected() == true) {
                try {
                    outputStream = socket.getOutputStream();
                    outputStream.write(msg);
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            ToastUtil.shortDiyToastColorBgByRecTextSizeTextColor(mContext, R.string.bluetooth_connected_first,getResources().getColor(R.color.transparent),14f,getResources().getColor(R.color.black));
        }
    }

    //接收信息
    private void getMesssage() {
        if (socket.isConnected() == true) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        InputStream is = socket.getInputStream();
                        b = new byte[11];
                        if (b != null) {
                            is.read(b);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    private class SendBluetoothTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            long i = ByteUtils.unsigned4BytesToInt(b,6);
            ByteUtils.bytesToHexString(b);
            try {
                String bluetoothMessage = HttpUtil.SendBluetoothMessage(Address.SendYaoHeBluetooth_Url, userName, address, String.valueOf(i));
                LogUtils.error(bluetoothMessage + "------i:" + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
