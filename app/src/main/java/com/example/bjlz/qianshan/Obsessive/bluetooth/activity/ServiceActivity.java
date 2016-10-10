package com.example.bjlz.qianshan.Obsessive.bluetooth.activity;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bjlz.qianshan.CurrencyApplication.Address;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyBase.BaseActivity;
import com.example.bjlz.qianshan.Obsessive.bean.BlueTooth4Message;
import com.example.bjlz.qianshan.Obsessive.bean.GetBluetooth;
import com.example.bjlz.qianshan.Obsessive.bluetooth.adapter.BluetoothMsgAdapter;
import com.example.bjlz.qianshan.Obsessive.bluetooth.config.Constants;
import com.example.bjlz.qianshan.Obsessive.bluetooth.utils.Utils;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.HttpAndNetWorkTools.HttpTools.HttpUtil;
import com.example.bjlz.qianshan.tools.ChangeAndGetTools.ByteUtils;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.LogUtils;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.PreferencesUtils;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.UserUtils;
import com.example.bjlz.qianshan.views.TitleBarView;
import com.example.bjlz.qianshan.views.WaveProgressView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ServiceActivity extends BaseActivity {
    private static final UUID MyServiceUUID = UUID.fromString("0000ffe0-0000-1000-8000-00805F9B34FB");
    private static final UUID characteristicReadUUID = UUID.fromString("0000ffe1-0000-1000-8000-00805F9B34FB");
    private static final UUID characteristicWriteUUID = UUID.fromString("0000ffe2-0000-1000-8000-00805F9B34FB");
    private static List<BluetoothGattService> services;
    public static Handler myHandler;
    private static List<BluetoothGattCharacteristic> characteristics;
    private Button btn_start_read ,btn_start_write;
    private TextView tv_read,tv_gao_ya,tv_di_ya,tv_xin_lv;//高压\低压\心率
    private WaveProgressView waveProgressbar;//水波纹进度条
    private Runnable runnable = null;
    private String userName = null;//用户名
    private Map<String, String> map = null;//记录用户名
    private String deviceAddress = null;//蓝牙4地址
    private String s = null;//获取到的蓝牙数据
    private long date = 0L;//测试结束时间
    private ListView lv_bluetooth;//展示之前上传的血压数据
    private int checkDiYa = 0;
    private int checkXinLv = 0;
    private int checkGaoYa = 0;
    private List<BlueTooth4Message> blueTooth4Message = new ArrayList<>();//获取血压数据
    private BluetoothMsgAdapter adapter=null;
    private TitleBarView title_bar = null;//标题
//    //水波纹进度条
//    private static final int one = 0X0001;
//    private static final int two = 0X0002;
//    private int progress;//进度显示
    private int GaoYa,DiYa,XinLv;//高压/低压/心率
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_service);
        MyApplication.getInstance().addActivity(this);
        getData();
        initView();
        initData();
        setOnClick();
    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        btn_start_read = (Button) findViewById(R.id.btn_start_read);
        btn_start_write = (Button) findViewById(R.id.btn_start_write);
        tv_read = (TextView) findViewById(R.id.tv_read);
        tv_gao_ya = (TextView) findViewById(R.id.tv_gao_ya);
        tv_di_ya = (TextView) findViewById(R.id.tv_di_ya);
        tv_xin_lv = (TextView) findViewById(R.id.tv_xin_lv);
        lv_bluetooth = (ListView) findViewById(R.id.lv_bluetooth);
//        //水波纹进度条
//        waveProgressbar = (WaveProgressView) findViewById(R.id.waveProgressbar);
//        waveProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.smart_mac_test);
        if (checkGaoYa !=0){
            tv_gao_ya.setText(checkGaoYa+"");
        }
        if (checkDiYa !=0){
            tv_di_ya.setText(checkDiYa+"");
        }
        if (checkXinLv !=0){
            tv_xin_lv.setText(checkXinLv +"");
        }
//        waveProgressbar.setWaveColor("#61f25e");
        runnable = new Runnable() {
            @Override
            public void run() {
                myHandler.postDelayed(this,1000);
                Message msg = new Message();
                msg.what = Constants.CHARACTERISTIC_READ_WRITES;
                myHandler.sendMessage(msg);
            }
        };
        myHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case Constants.FIND_SERVICES:
                        characteristics = BloodPressureActivity.bluetoothUtils.discoverCharacteristic(MyServiceUUID);
                        byte[] bytes = Utils.wirteCharacteristic("ffa0ffa0ffa0ffa0ffa0ffa0ffa0ffa0ffa0ffa0");
                        BloodPressureActivity.bluetoothUtils.writeCharacteristic(MyServiceUUID, characteristicWriteUUID,bytes);
                        break;
                    case Constants.CHARACTERISTIC_READ_WRITES:
//                        btn_start_read.setVisibility(View.VISIBLE);
//                        myHandler.sendEmptyMessageDelayed(one, 1000);
                        BloodPressureActivity.bluetoothUtils.readCharacteristic(MyServiceUUID, characteristicReadUUID);
                        break;
                    case Constants.CHARACTERISTIC_READ:
                        byte[] value = msg.getData().getByteArray("value");
                        s = Utils.bytes2HexString(value);
                        if (s.contains("FFFE08")){
                            LogUtils.error("s11:" + s);
                            Date dt= new Date();
                            date = dt.getTime();
                            SendBluetooth4Message();
                            btn_start_write.setEnabled(true);
                            btn_start_write.setText("开始测量");
                            Toast.makeText(ServiceActivity.this, "测量完毕", Toast.LENGTH_SHORT).show();
                            String s1 = s.substring(4, 20);
                            String s2 = s1.substring(8, 10);
                            String gaoya = ByteUtils.hexStringToBinary(s2);
                            GaoYa = ByteUtils.binaryToAlgorism(gaoya);
                            String s3 = s1.substring(12, 14);
                            String diya = ByteUtils.hexStringToBinary(s3);
                            DiYa = ByteUtils.binaryToAlgorism(diya);
                            String s4 = s1.substring(14, s1.length());
                            String xinlv = ByteUtils.hexStringToBinary(s4);
                             XinLv = ByteUtils.binaryToAlgorism(xinlv);
                            PreferencesUtils.putInt(getApplicationContext(),"checkGaoYa",GaoYa);
                            PreferencesUtils.putInt(getApplicationContext(),"checkDiYa",DiYa);
                            PreferencesUtils.putInt(getApplicationContext(),"checkXinLv",XinLv);
                            tv_read.setText(s1);
                            tv_gao_ya.setText(GaoYa+"");
                            tv_di_ya.setText(DiYa+"");
                            tv_xin_lv.setText(XinLv+"");
                            myHandler.removeCallbacks(runnable);
                        }
                        break;
//                    case one:
//                        progress++;
//                        if (progress <= 200) {
//                            waveProgressbar.setCurrent(GaoYa, progress+"");
//                            myHandler.sendEmptyMessageDelayed(one, 30);
//                            if (progress == GaoYa){
//                                myHandler.sendEmptyMessageDelayed(two, 60);
//                            }
//                        }
//
//                    break;
//                    case two:
//                        if (progress >= DiYa) {
//                            waveProgressbar.setCurrent(DiYa, progress+"");
//                            myHandler.sendEmptyMessageDelayed(two, 60);
//                        }
//                        break;
                }
                super.handleMessage(msg);
            }
        };

        adapter = new BluetoothMsgAdapter(ServiceActivity.this,blueTooth4Message);
        lv_bluetooth.setAdapter(adapter);
    }

    @Override
    public void getData() {
        map = UserUtils.readInfo(getApplicationContext());
        if (map != null) {
            userName = map.get("name");
        }
        deviceAddress = PreferencesUtils.getString(getApplicationContext(), "device4Address");
        services = new ArrayList<BluetoothGattService>();
        checkGaoYa = PreferencesUtils.getInt(getApplicationContext(), "checkGaoYa",0);
        checkDiYa = PreferencesUtils.getInt(getApplicationContext(), "checkDiYa",0);
        checkXinLv = PreferencesUtils.getInt(getApplicationContext(), "checkXinLv",0);
//        LogUtils.error("checkGaoYa:" +checkGaoYa +"----checkDiYa:"+checkDiYa+"----checkXinLv:"+checkXinLv);
        GetBluetooth4Message();
    }
    void SendBluetooth4Message(){
        SendBluetooth4MessageTask task = new SendBluetooth4MessageTask();
        task.execute();
    }
    void GetBluetooth4Message(){
        GetBluetooth4MessageTask getTagk = new GetBluetooth4MessageTask();
        getTagk.execute();
    }
    @Override
    public void setOnClick() {
        btn_start_read.setOnClickListener(this);
        btn_start_write.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetBluetooth4Message();
        adapter.notifyDataSetChanged();
    }
    @Override
    public void WeightOnClick(View v) {
        switch(v.getId()){
            case R.id.btn_start_read:
                BloodPressureActivity.bluetoothUtils.readCharacteristic(MyServiceUUID, characteristicReadUUID);
            break;
            case R.id.btn_start_write:
                BloodPressureActivity.bluetoothUtils.discoverServices();
                myHandler.postDelayed(runnable, 1000);
//                myHandler.sendEmptyMessageDelayed(one, 1000);
                btn_start_write.setEnabled(false);
                btn_start_write.setText("正在测量中...");
            break;
        }
    }

    public static void setServices(List<BluetoothGattService> services) {
        ServiceActivity.services = services;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK&& event.getAction() == KeyEvent.ACTION_DOWN){
            BloodPressureActivity.bluetoothUtils.disconnect();
            BloodPressureActivity.myHandler.removeCallbacks(runnable);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private class SendBluetooth4MessageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                String bluetoothMessage = HttpUtil.SendBluetooth4Message(Address.SendXueYa_Url, userName, deviceAddress,s,date);
//                LogUtils.error(bluetoothMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class GetBluetooth4MessageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                String BluetoothMessage = HttpUtil.GetBluetooth4Message(Address.GetXueYaList_Url, userName);
                if (BluetoothMessage !=null ||BluetoothMessage.length()!=0 ){
                    return BluetoothMessage;
                }else{
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null || s.length() == 0 || TextUtils.isEmpty(s)){

            }else{
                blueTooth4Message.clear();
                Gson gson = new Gson();
                GetBluetooth getBluetooth = gson.fromJson(s, GetBluetooth.class);
                if (getBluetooth !=null){
                    List<BlueTooth4Message> tooth4Messages = getBluetooth.getBlueTooth4Message();
                    blueTooth4Message.addAll(tooth4Messages);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
