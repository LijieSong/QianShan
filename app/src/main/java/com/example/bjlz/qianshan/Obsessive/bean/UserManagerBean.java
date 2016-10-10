package com.example.bjlz.qianshan.Obsessive.bean;

/**
 * 项目名称：QianShanDoctor
 * 类描述：UserManagerBean 用户管理对象
 * 创建人：slj
 * 创建时间：2016-6-28 14:51
 * 修改人：slj
 * 修改时间：2016-6-28 14:51
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class UserManagerBean {
    private String name;//患者姓名
    private int imgId;//患者头像
    private String zhusu;//患者主诉
    private String age;//患者年龄

    private String sex;//患者性别

    public UserManagerBean() {
        super();
    }

    public UserManagerBean(String name, int imgId, String zhusu) {
        super();
        this.name = name;
        this.imgId = imgId;
        this.zhusu = zhusu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getZhusu() {
        return zhusu;
    }

    public void setZhusu(String zhusu) {
        this.zhusu = zhusu;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
