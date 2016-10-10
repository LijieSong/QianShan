package com.example.bjlz.qianshan.Obsessive.bluetooth.utils;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;

import com.example.bjlz.qianshan.Obsessive.bluetooth.activity.BloodPressureActivity;
import com.example.bjlz.qianshan.Obsessive.bluetooth.activity.ServiceActivity;
import com.example.bjlz.qianshan.Obsessive.bluetooth.config.Constants;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.LogUtils;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.ToastUtil;

import java.util.List;
import java.util.UUID;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BluetoothUtils {

    public static final UUID NOTIFICATION_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter adapter;
    private BluetoothGatt bluetoothGatt;
    private Context mContext;
    private int REQUEST_CODE = 20;

    public BluetoothUtils(Context context) {
        LogUtils.error( "setContext");
        this.mContext = context;
        adapter = getBluetoothAdapter(context);
    }

    /**
     * 获取蓝牙适配器
     * @param context
     * @return
     */
    public BluetoothAdapter getBluetoothAdapter(Context context){
        BluetoothManager mBluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter mBluetoothAdapter = mBluetoothManager.getAdapter();
        return mBluetoothAdapter;
    }
    /**
     * 开始扫描设备
     *
     * @param uuids 可选项，该项为扫描指定的服务，uuids为指定服务的uuid集合
     */
    @SuppressWarnings("deprecation")
    public void startScan(UUID[] uuids) {
        if (adapter == null || !adapter.isEnabled()) {
            LogUtils.error( "打开蓝牙...");
//            Toast.makeText(mContext, "正在打开蓝牙,请稍候...", Toast.LENGTH_SHORT).show();
//            BloodPressureActivity.myHandler.sendEmptyMessage(15);
            adapter.enable();
        } else {
            LogUtils.error("开始扫描...");
            if (uuids == null || uuids.length < 1) {
                adapter.startLeScan(mLeScanCallback);
            } else {
                adapter.startLeScan(uuids, mLeScanCallback);
            }
//		Toast.makeText(mContext, "开始扫描", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 停止扫描
     */
    @SuppressWarnings("deprecation")
    public void stopScan() {
        LogUtils.error("扫描停止...");
        adapter.stopLeScan(mLeScanCallback);
    }

    /**
     * 连接设备，注册连接回调方法
     *
     * @param device 要连接的蓝牙设备
     */
    public void connect(final BluetoothDevice device) {
        ToastUtil.shortToast(mContext,"蓝牙连接中,请稍候...");
        bluetoothGatt = device.connectGatt(mContext, true, mGattCallback);
    }

    /**
     * 发现设备服务，回调方法onServicesDiscovered
     */
    public void discoverServices() {
        bluetoothGatt.discoverServices();
    }


    private LeScanCallback mLeScanCallback = new LeScanCallback() {

        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            LogUtils.error("find new device: " + device.getName() + " address: " + device.getAddress());
            Message msg = new Message();
            msg.what = Constants.FIND_NEW_DEVICE;
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.NEW_DEVICE, device);
            msg.setData(bundle);
            BloodPressureActivity.myHandler.sendMessage(msg);
//			Toast.makeText(mContext, "发现设备 设备名称: "+device.getName()+" 设备地址: "+device.getAddress(), Toast.LENGTH_SHORT).show();
        }
    };


    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            LogUtils.error("onCharacteristicChanged");
            super.onCharacteristicChanged(gatt, characteristic);
            Message msg = new Message();
            msg.what = Constants.CHARACTERISTIC_NOTIFY;
            Bundle bundle = new Bundle();
            bundle.putByteArray("value", characteristic.getValue());
            msg.setData(bundle);
            ServiceActivity.myHandler.sendMessage(msg);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            LogUtils.error("onCharacteristicRead:" +status);
            super.onCharacteristicRead(gatt, characteristic, status);
            Message msg = new Message();
            msg.what = Constants.CHARACTERISTIC_READ;
            Bundle bundle = new Bundle();
            bundle.putByteArray("value", characteristic.getValue());
            msg.setData(bundle);
            ServiceActivity.myHandler.sendMessage(msg);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            LogUtils.error("onCharacteristicWrite:" + status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                LogUtils.error("写入成功......");
                Message msg = new Message();
                msg.what = Constants.CHARACTERISTIC_READ_WRITES;
                ServiceActivity.myHandler.sendMessage(msg);
            }
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            LogUtils.error("连接状态改变。。。");
            super.onConnectionStateChange(gatt, status, newState);
            Message msg = new Message();
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                LogUtils.error("连接成功。。。");
                msg.what = BluetoothProfile.STATE_CONNECTED;
                BloodPressureActivity.myHandler.sendMessage(msg);
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                LogUtils.error("断开连接。。。");
                msg.what = BluetoothProfile.STATE_DISCONNECTED;
                BloodPressureActivity.myHandler.sendMessage(msg);
            }
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            LogUtils.error( "onDescriptorRead");
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            LogUtils.error("onDescriptorWrite");
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            LogUtils.error("onReadRemoteRssi");
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            List<BluetoothGattService> bluetoothGattServices = gatt.getServices();
            LogUtils.error( "设备:" + gatt.getDevice().getName() + "发现了 " + bluetoothGattServices.size() + " 个服务");
            Message msg = new Message();
            msg.what = Constants.FIND_SERVICES;
            ServiceActivity.setServices(gatt.getServices());
            ServiceActivity.myHandler.sendMessage(msg);
        }
    };


    /**
     * 发现服务下的特征信息
     *
     * @param uuid 需要发现特征信息的服务
     * @return
     */
    public List<BluetoothGattCharacteristic> discoverCharacteristic(UUID uuid) {
        BluetoothGattService service = bluetoothGatt.getService(uuid);
        List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
        LogUtils.error("服务: " + service.getUuid().toString() + " 发现了 " + characteristics.size() + "个特征信息");
        return characteristics;
    }

    /**
     * 根据服务ID,特征ID得到特征对象
     *
     * @param serviceUUID
     * @param characterUUID
     * @return
     */
    public BluetoothGattCharacteristic getCharacteristic(UUID serviceUUID, UUID characterUUID) {
        return bluetoothGatt.getService(serviceUUID).getCharacteristic(characterUUID);
    }

    /**
     * 调用特征读取方法，读取特征数据，数据从回调中返回 onCharacteristicRead
     *
     * @param serviceUUID
     * @param characterUUID
     */
    public void readCharacteristic(UUID serviceUUID, UUID characterUUID) {
        bluetoothGatt.readCharacteristic(getCharacteristic(serviceUUID, characterUUID));
    }

    /**
     * 给某个特征写入值
     *
     * @param serviceUUID
     * @param characterUUID
     * @param value
     */
    public void writeCharacteristic(UUID serviceUUID, UUID characterUUID, byte[] value) {
        BluetoothGattCharacteristic characteristic = getCharacteristic(serviceUUID, characterUUID);
        characteristic.setValue(value);
        bluetoothGatt.writeCharacteristic(characteristic);
    }

    /**
     * 发送notify命令，会隔几秒通过onCharacteristicChange返回改特征的值
     *
     * @param serviceUUID
     * @param characterUUID
     * @param flag          开关值,true开启,false关闭
     */
    public void setNotify(UUID serviceUUID, UUID characterUUID, boolean flag) {
        BluetoothGattCharacteristic characteristic = getCharacteristic(serviceUUID, characterUUID);
        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(NOTIFICATION_UUID);
        if (flag) {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        } else {
            descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
        }
        bluetoothGatt.writeDescriptor(descriptor);
        bluetoothGatt.setCharacteristicNotification(characteristic, flag);
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        LogUtils.error("disconnect");
        bluetoothGatt.disconnect();
    }
}
