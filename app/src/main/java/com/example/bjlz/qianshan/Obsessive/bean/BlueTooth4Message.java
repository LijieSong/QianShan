package com.example.bjlz.qianshan.Obsessive.bean;

/**
 * 项目名称：nrtc_demo
 * 类描述：BlueTooth4Message 获取请求下来的4.0蓝牙血压仪的值
 * 创建人：slj
 * 创建时间：2016-6-19 3:07
 * 修改人：slj
 * 修改时间：2016-6-19 3:07
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class BlueTooth4Message {
    private String blueTooth4_id;

    private long xueyaTime;

    private String user_id;

    private String data;

    private int xueya_id;

    public void setBlueTooth4_id(String blueTooth4_id) {
        this.blueTooth4_id = blueTooth4_id;
    }

    public String getBlueTooth4_id() {
        return this.blueTooth4_id;
    }

    public void setXueyaTime(int xueyaTime) {
        this.xueyaTime = xueyaTime;
    }

    public long getXueyaTime() {
        return this.xueyaTime;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return this.data;
    }

    public void setXueya_id(int xueya_id) {
        this.xueya_id = xueya_id;
    }

    public int getXueya_id() {
        return this.xueya_id;
    }
}