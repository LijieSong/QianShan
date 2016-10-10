package com.example.bjlz.qianshan.Obsessive.bluetooth.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.Obsessive.bluetooth.adapter.DeviceListAdapter;
import com.example.bjlz.qianshan.Obsessive.bluetooth.config.Constants;
import com.example.bjlz.qianshan.Obsessive.bluetooth.utils.BluetoothUtils;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.LogUtils;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.PreferencesUtils;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.ToastUtil;
import com.example.bjlz.qianshan.tools.PermissionsManager.EasyPermissionsEx;
import com.example.bjlz.qianshan.views.TitleBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 项目名称：qianshan
 * 类描述：BloodPressureActivity 血压
 * 创建人：slj
 * 创建时间：2016-6-18 20:33
 * 修改人：slj
 * 修改时间：2016-6-18 20:33
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class BloodPressureActivity extends BaseActivity {
    private static final UUID MY_UUID = UUID.fromString("0000FFE0-0000-1000-8000-00805F9B34FB");
    private Button scan;
    private ListView listView;
    private static List<BluetoothDevice> devices;
    private DeviceListAdapter mAdapter = null;
    public static BluetoothUtils bluetoothUtils;
    public static Handler myHandler;
    private UUID[] uuids = null;
    //权限请求吗
    private int MY_PERMISSIONS_REQUEST_BLUETOOTH_CODE = 10;

    private TitleBarView title_bar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_presser);
        bluetoothUtils = new BluetoothUtils(this);
        uuids = new UUID[]{MY_UUID};
        devices = new ArrayList<BluetoothDevice>();
        if (EasyPermissionsEx.hasPermissions(this,  Manifest.permission.ACCESS_COARSE_LOCATION ,  Manifest.permission.BLUETOOTH)){
            EasyPermissionsEx.requestPermissions(BloodPressureActivity.this,null,MY_PERMISSIONS_REQUEST_BLUETOOTH_CODE, new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH_PRIVILEGED});
        }
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            //请求权限
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH_PRIVILEGED},
//                    MY_PERMISSIONS_REQUEST_BLUETOOTH_CODE);
//        }
        getData();
        initView();
        initData();
        setOnClick();
        myHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Constants.FIND_NEW_DEVICE:
                        BluetoothDevice device = msg.getData().getParcelable(Constants.NEW_DEVICE);
                        if (!devices.contains(device)) {
                            devices.add(device);
                            mAdapter.setDevices(devices);
                            mAdapter.notifyDataSetChanged();
                        }

                        break;
                    case BluetoothProfile.STATE_CONNECTED:
                        ToastUtil.shortToast(getApplicationContext(), "连接成功");
                        MyApplication.startActivity(BloodPressureActivity.this, ServiceActivity.class);
//                        bluetoothUtils.stopScan();
                        break;
                    case BluetoothProfile.STATE_DISCONNECTED:
//                        ToastUtil.shortToast(BloodPressureActivity.this, "设备已断开连接");
                        break;
                    case 101:
                        if (devices == null || devices.size() == 0) {
                            ToastUtil.shortDiyToast(getApplicationContext(), "未发现可用血压仪，请确认血压仪电源已开启，并处于待连接状态");
//                            ToastUtil.shortToast(getApplicationContext(),"未发现可用血压仪，请确认血压仪电源已开启，并处于待连接状态");
                        }
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        scan = (Button) findViewById(R.id.scan);
        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.smart_mac_test);
        mAdapter = new DeviceListAdapter(this, devices);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void getData() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            ToastUtil.shortDiyToastByRec(getApplicationContext(), R.string.ble_not_supported);
            finish();
        } else {
            BluetoothAdapter adapter = bluetoothUtils.getBluetoothAdapter(BloodPressureActivity.this);

            if (adapter == null || !adapter.isEnabled()) {
                LogUtils.error("打开蓝牙...");
                adapter.enable();
            }
        }
    }

    @Override
    public void setOnClick() {
        listView.setOnItemClickListener(this);
        scan.setOnClickListener(this);
    }

    @Override
    public void WeightOnClick(View v) {
        switch (v.getId()) {
            case R.id.scan:
//                bluetoothUtils.startScan(null);
                myHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bluetoothUtils.stopScan();
                        myHandler.sendEmptyMessage(101);
                    }
                }, 10000);
                bluetoothUtils.startScan(uuids);
                break;
        }
    }

    @Override
    public void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        BluetoothDevice device = mAdapter.getItem(i);
        String deviceAddress = device.getAddress();
        PreferencesUtils.putString(getApplicationContext(), "device4Address", deviceAddress);
        bluetoothUtils.connect(device);
        bluetoothUtils.stopScan();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            bluetoothUtils.stopScan();
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        EasyPermissionsEx.onRequestPermissionsResult(requestCode,permissions,grantResults);

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
