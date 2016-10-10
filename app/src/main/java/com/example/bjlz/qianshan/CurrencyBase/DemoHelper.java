package com.example.bjlz.qianshan.CurrencyBase;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.bjlz.qianshan.CurrencyActivities.ChatActivity;
import com.example.bjlz.qianshan.CurrencyActivities.VideoCallActivity;
import com.example.bjlz.qianshan.CurrencyActivities.VoiceCallActivity;
import com.example.bjlz.qianshan.CurrencyBroadcastRecivier.CallReceiver;
import com.example.bjlz.qianshan.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMMessage.Type;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseSettingsProvider;
import com.hyphenate.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.model.EaseNotifier.EaseNotificationInfoProvider;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.EMLog;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DemoHelper {
    /**
     * data sync listener
     */
    public interface DataSyncListener {
        /**
         * sync complete
         * @param success true：data sync successful，false: failed to sync data
         */
        void onSyncComplete(boolean success);
    }

    protected static final String TAG = "DemoHelper";
    
	private EaseUI easeUI;
	
    /**
     * EMEventListener
     */
    protected EMMessageListener messageListener = null;

	private Map<String, EaseUser> contactList;

	private static DemoHelper instance = null;
	
	private DemoModel demoModel = null;

	/**
     * sync groups status listener
     */
    private List<DataSyncListener> syncGroupsListeners;
    /**
     * sync contacts status listener
     */
    private List<DataSyncListener> syncContactsListeners;
    /**
     * sync blacklist status listener
     */
    private List<DataSyncListener> syncBlackListListeners;

    private boolean isSyncingGroupsWithServer = false;
    private boolean isSyncingContactsWithServer = false;
    private boolean isSyncingBlackListWithServer = false;
    private boolean isGroupsSyncedWithServer = false;
    private boolean isContactsSyncedWithServer = false;
    private boolean isBlackListSyncedWithServer = false;
	
	public boolean isVoiceCalling;
    public boolean isVideoCalling;

	private String username;

    private Context appContext;

    private CallReceiver callReceiver;


    private LocalBroadcastManager broadcastManager;

    private boolean isGroupAndContactListenerRegisted;

	private DemoHelper() {
	}

	public synchronized static DemoHelper getInstance() {
		if (instance == null) {
			instance = new DemoHelper();
		}
		return instance;
	}

	/**
	 * init helper
	 * 
	 * @param context
	 *            application context
	 */
	public void init(Context context) {
	    demoModel = new DemoModel(context);
	    EMOptions options = initChatOptions();
	    //use default options if options is null
		if (EaseUI.getInstance().init(context, options)) {
		    appContext = context;
		    
		    //debug mode, you'd better set it to false, if you want release your App officially.
		    EMClient.getInstance().setDebugMode(true);
		    //get easeui instance
		    easeUI = EaseUI.getInstance();
		    //to set user's profile and avatar
		    setEaseUIProviders();

			EMClient.getInstance().callManager().getVideoCallHelper().setAdaptiveVideoFlag(getModel().isAdaptiveVideoEncode());

			setGlobalListeners();
			broadcastManager = LocalBroadcastManager.getInstance(appContext);
		}
	}

	
	private EMOptions initChatOptions(){
        Log.d(TAG, "init HuanXin Options");
        
        EMOptions options = new EMOptions();
        // set if accept the invitation automatically
        options.setAcceptInvitationAlways(false);
        // set if you need read ack
        options.setRequireAck(true);
        // set if you need delivery ack
        options.setRequireDeliveryAck(false);

        //set custom servers, commonly used in private deployment
        if(demoModel.isCustomServerEnable() && demoModel.getRestServer() != null && demoModel.getIMServer() != null) {
            options.setRestServer(demoModel.getRestServer());
            options.setIMServer(demoModel.getIMServer());
            if(demoModel.getIMServer().contains(":")) {
                options.setIMServer(demoModel.getIMServer().split(":")[0]);
                options.setImPort(Integer.valueOf(demoModel.getIMServer().split(":")[1]));
            }
        }
        
        options.allowChatroomOwnerLeave(getModel().isChatroomOwnerLeaveAllowed());
        options.setDeleteMessagesAsExitGroup(getModel().isDeleteMessagesAsExitGroup());
        options.setAutoAcceptGroupInvitation(getModel().isAutoAcceptGroupInvitation());
        
        return options;
    }

    protected void setEaseUIProviders() {
    	// set profile provider if you want easeUI to handle avatar and nickname
        easeUI.setUserProfileProvider(new EaseUserProfileProvider() {
            
            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }
        });

        //set options 
        easeUI.setSettingsProvider(new EaseSettingsProvider() {
            
            @Override
            public boolean isSpeakerOpened() {
                return demoModel.getSettingMsgSpeaker();
            }
            
            @Override
            public boolean isMsgVibrateAllowed(EMMessage message) {
                return demoModel.getSettingMsgVibrate();
            }
            
            @Override
            public boolean isMsgSoundAllowed(EMMessage message) {
                return demoModel.getSettingMsgSound();
            }
            
            @Override
            public boolean isMsgNotifyAllowed(EMMessage message) {
                if(message == null){
                    return demoModel.getSettingMsgNotification();
                }
                if(!demoModel.getSettingMsgNotification()){
                    return false;
                }else{
                    String chatUsename = null;
                    List<String> notNotifyIds = null;
                    // get user or group id which was blocked to show message notifications
                    if (message.getChatType() == ChatType.Chat) {
                        chatUsename = message.getFrom();
                    } else {
                        chatUsename = message.getTo();
                    }

                    if (notNotifyIds == null || !notNotifyIds.contains(chatUsename)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        });
        //set notification options, will use default if you don't set it
        easeUI.getNotifier().setNotificationInfoProvider(new EaseNotificationInfoProvider() {
            
            @Override
            public String getTitle(EMMessage message) {
              //you can update title here
                return null;
            }
            
            @Override
            public int getSmallIcon(EMMessage message) {
              //you can update icon here
                return 0;
            }
            
            @Override
            public String getDisplayedText(EMMessage message) {
            	// be used on notification bar, different text according the message type.
                String ticker = EaseCommonUtils.getMessageDigest(message, appContext);
                if(message.getType() == Type.TXT){
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                EaseUser user = getUserInfo(message.getFrom());
                if(user != null){
                    if(EaseAtMessageHelper.get().isAtMeMsg(message)){
                        return String.format(appContext.getString(R.string.at_your_in_group), user.getNick());
                    }
                    return user.getNick() + ": " + ticker;
                }else{
                    if(EaseAtMessageHelper.get().isAtMeMsg(message)){
                        return String.format(appContext.getString(R.string.at_your_in_group), message.getFrom());
                    }
                    return message.getFrom() + ": " + ticker;
                }
            }
            
            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                // here you can customize the text.
                // return fromUsersNum + "contacts send " + messageNum + "messages to you";
            	return null;
            }
            
            @Override
            public Intent getLaunchIntent(EMMessage message) {
            	// you can set what activity you want display when user click the notification
                Intent intent = new Intent(appContext, ChatActivity.class);
                // open calling activity if there is call
                if(isVideoCalling){
                    intent = new Intent(appContext, VideoCallActivity.class);
                }else if(isVoiceCalling){
                    intent = new Intent(appContext, VoiceCallActivity.class);
                }else{
                    ChatType chatType = message.getChatType();
                    if (chatType == ChatType.Chat) { // single chat message
                        intent.putExtra("userId", message.getFrom());
                        intent.putExtra("chatType", Constant.CHATTYPE_SINGLE);
                    } else { // group chat message
                        // message.getTo() is the group id
                        intent.putExtra("userId", message.getTo());
                        if(chatType == ChatType.GroupChat){
                            intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
                        }else{
                            intent.putExtra("chatType", Constant.CHATTYPE_CHATROOM);
                        }
                        
                    }
                }
                return intent;
            }
        });
    }

    EMConnectionListener connectionListener;
    /**
     * set global listener
     */
    protected void setGlobalListeners(){
        syncGroupsListeners = new ArrayList<DataSyncListener>();
        syncContactsListeners = new ArrayList<DataSyncListener>();
        syncBlackListListeners = new ArrayList<DataSyncListener>();
        
        isGroupsSyncedWithServer = demoModel.isGroupsSynced();
        isContactsSyncedWithServer = demoModel.isContactSynced();
        isBlackListSyncedWithServer = demoModel.isBacklistSynced();
        
        // create the global connection listener
        connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(int error) {
            }

            @Override
            public void onConnected() {
                // in case group and contact were already synced, we supposed to notify sdk we are ready to receive the events
                if (isGroupsSyncedWithServer && isContactsSyncedWithServer) {
                    EMLog.d(TAG, "group and contact already synced with servre");
                }
            }
        };

        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        if(callReceiver == null){
            callReceiver = new CallReceiver();
        }

        //register incoming call receiver
        appContext.registerReceiver(callReceiver, callFilter);    
        //register connection listener
        EMClient.getInstance().addConnectionListener(connectionListener);
        //register message event listener
        registerMessageListener();
        
    }


	private EaseUser getUserInfo(String username){
		// To get instance of EaseUser, here we get it from the user list in memory
		// You'd better cache it if you get it from your server
        EaseUser user = null;

        // if user is not in your contacts, set inital letter for him/her
        if(user == null){
            user = new EaseUser(username);
            EaseCommonUtils.setUserInitialLetter(user);
        }
        return user;
	}
	
	 /**
     * Global listener
     * If this event already handled by an activity, you don't need handle it again
     * activityList.size() <= 0 means all activities already in background or not in Activity Stack
     */
    protected void registerMessageListener() {
    	messageListener = new EMMessageListener() {
            private BroadcastReceiver broadCastReceiver = null;
			
			@Override
			public void onMessageReceived(List<EMMessage> messages) {
			    for (EMMessage message : messages) {
			        EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());
			        // in background, do not refresh UI, notify it in notification bar
			        if(!easeUI.hasForegroundActivies()){
			            getNotifier().onNewMsg(message);
			        }
			    }
			}
			
			@Override
			public void onCmdMessageReceived(List<EMMessage> messages) {
			    for (EMMessage message : messages) {
                    EMLog.d(TAG, "receive command message");
                    //get message body
                    EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                    final String action = cmdMsgBody.action();//获取自定义action
                    //end of red packet code
                    //获取扩展属性 此处省略
                    //maybe you need get extension of your message
                    //message.getStringAttribute("");
                    EMLog.d(TAG, String.format("Command：action:%s,message:%s", action,message.toString()));
                }
			}

			@Override
			public void onMessageReadAckReceived(List<EMMessage> messages) {
			}
			
			@Override
			public void onMessageDeliveryAckReceived(List<EMMessage> message) {
			}
			
			@Override
			public void onMessageChanged(EMMessage message, Object change) {
				
			}
		};
		
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

	/**
	 * if ever logged in
	 * 
	 * @return
	 */
	public boolean isLoggedIn() {
		return EMClient.getInstance().isLoggedInBefore();
	}

	/**
	 * logout
	 * 
	 * @param unbindDeviceToken
	 *            whether you need unbind your device token
	 * @param callback
	 *            callback
	 */
	public void logout(boolean unbindDeviceToken, final EMCallBack callback) {
		endCall();
		Log.d(TAG, "logout: " + unbindDeviceToken);
		EMClient.getInstance().logout(unbindDeviceToken, new EMCallBack() {

			@Override
			public void onSuccess() {
				Log.d(TAG, "logout: onSuccess");
			    reset();
				if (callback != null) {
					callback.onSuccess();
				}

			}

			@Override
			public void onProgress(int progress, String status) {
				if (callback != null) {
					callback.onProgress(progress, status);
				}
			}

			@Override
			public void onError(int code, String error) {
				Log.d(TAG, "logout: onSuccess");
                reset();
				if (callback != null) {
					callback.onError(code, error);
				}
			}
		});
	}
	
	/**
	 * get instance of EaseNotifier
	 * @return
	 */
	public EaseNotifier getNotifier(){
	    return easeUI.getNotifier();
	}
	
	public DemoModel getModel(){
        return (DemoModel) demoModel;
    }
	
	/**
	 * update contact list
	 * 
	 * @param aContactList
	 */
	public void setContactList(Map<String, EaseUser> aContactList) {
		if(aContactList == null){
		    if (contactList != null) {
		        contactList.clear();
		    }
			return;
		}
		
		contactList = aContactList;
	}

	void endCall() {
		try {
			EMClient.getInstance().callManager().endCall();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


    synchronized void reset(){
        isSyncingGroupsWithServer = false;
        isSyncingContactsWithServer = false;
        isSyncingBlackListWithServer = false;
        
        demoModel.setGroupsSynced(false);
        demoModel.setContactSynced(false);
        demoModel.setBlacklistSynced(false);
        
        isContactsSyncedWithServer = false;


        setContactList(null);
    }

    public void pushActivity(Activity activity) {
        easeUI.pushActivity(activity);
    }

    public void popActivity(Activity activity) {
        easeUI.popActivity(activity);
    }

}
