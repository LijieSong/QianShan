package com.example.bjlz.qianshan.Obsessive.bean;

import java.util.List;

/**
 * 项目名称：QianShan
 * 类描述：GetBluetooth 获取请求下来的4.0蓝牙血压仪的对象
 * 创建人：slj
 * 创建时间：2016-6-19 3:07
 * 修改人：slj
 * 修改时间：2016-6-19 3:07
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class GetBluetooth {
    private List<BlueTooth4Message> BlueTooth4 ;

    public void setBlueTooth4(List<BlueTooth4Message> BlueTooth4){
        this.BlueTooth4 = BlueTooth4;
    }
    public List<BlueTooth4Message> getBlueTooth4Message(){
        return this.BlueTooth4;
    }
}
