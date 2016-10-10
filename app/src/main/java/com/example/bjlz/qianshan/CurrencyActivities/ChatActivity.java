package com.example.bjlz.qianshan.CurrencyActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import com.example.bjlz.qianshan.CurrencyApplication.MyApplication;
import com.example.bjlz.qianshan.CurrencyFragment.ChatFragment;
import com.example.bjlz.qianshan.R;
import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.LogUtils;
import com.example.bjlz.qianshan.tools.OtherTools.UITools.StatusBarUtils;
import com.example.bjlz.qianshan.tools.PermissionsManager.PermissionsManager;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.easeui.ui.EaseChatFragment;

/**
 * 聊天界面
 */
public class ChatActivity extends EaseBaseActivity {
    public static ChatActivity activityInstance;
    private ChatFragment chatFragment;
//    private EaseChatFragment chatFragment;
    String toChatUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.em_activity_chat);
        MyApplication.getInstance().addActivity(this);
        activityInstance = this;
        getData();
        initview();
    }
    private void getData() {
        //聊天人或群id
        toChatUserName = getIntent().getExtras().getString("userId");
//        LogUtils.error("ChatActivity---toChatUserName:" +toChatUserName);
    }

    private void initview() {
        chatFragment = new ChatFragment();
//        chatFragment = new EaseChatFragment();
//        //传入参数
        //pass parameters to chat fragment       将参数传递给聊天片段
        chatFragment.setArguments(getIntent().getExtras());
         getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra("userId");
        if (toChatUserName.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        finish();
//        if (EasyUtils.isSingleActivity(this)) {
//            Intent intent = new Intent(this, ObsessiveMain.class);
//            startActivity(intent);
//        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            chatFragment.onBackPressed();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public String getToChatUsername(){
        return toChatUserName;
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}
