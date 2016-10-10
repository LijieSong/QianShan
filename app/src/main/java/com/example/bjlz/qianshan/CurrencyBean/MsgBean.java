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

package com.example.bjlz.qianshan.CurrencyBean;

import java.util.List;

/**
 * 项目名称：QianShan
 * 类描述：MsgBean 待处理消息的适配器
 * 创建人：slj
 * 创建时间：2016-8-2 9:01
 * 修改人：slj
 * 修改时间：2016-8-2 9:01
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class MsgBean {
    private String msg;
    private String msgTime;

    public String getMsg() {
        return msg;
    }
    public MsgBean(String msg ,String msgTime){
        this.msg = msg;
        this.msgTime = msgTime;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }
}
