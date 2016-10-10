package com.example.bjlz.qianshan.Obsessive.bean;

/**
 * 项目名称：QianShanDoctor
 * 类描述：UserHealthBean 用户健康对象
 * 创建人：slj
 * 创建时间：2016-6-28 14:51
 * 修改人：slj
 * 修改时间：2016-6-28 14:51
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class UserHealthBean {
    private String name;//患者姓名
    private String status;//报告状态
    private String url;//操作的url

    public UserHealthBean() {
        super();
    }

    public UserHealthBean(String name, String status, String url) {
        super();
        this.name = name;
        this.status = status;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
