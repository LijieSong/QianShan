package com.example.bjlz.qianshan.CurrencyApplication;

/**
 * 项目名称：QianShan
 * 类描述：Address 所用网址
 * 创建人：slj
 * 创建时间：2016-7-1 10:11
 * 修改人：slj
 * 修改时间：2016-7-1 10:11
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class Address {
 /**
  *     通用网址
  */
 //BASE_URL
// private static final String BASE_URL = "http://192.168.1.47:8080/IAS/";//开发用
 private static final String BASE_URL = "http://123.59.1.182:8080/IAS/";//外网用
// private static final String BASE_URL = "http://192.168.1.27:8080/IAS/";//测试用
 //login网址
// public static final String Login_Url = BASE_URL +"login/login.do";
 public static final String Login_Url = BASE_URL +"login/appLogin.do";
 //获取一级列表网址
 public static final String GetMenu1_URL = BASE_URL +"login/appGetMenuList.do";
 // 获取二/三级列表网址
 public static final String GetMenu2_URL = BASE_URL +"login/appGetChildMenu.do";
 //患者列表获取的网址  1 医生列表  2 患者列表
 public static final String Obsessive_Url = BASE_URL +"doctor/getDoctorList.do";
 //获取血压列表的网址
 public static final String GetXueYaList_Url = BASE_URL +"blueTooth/getXueYaList.do";
 //获取个人信息的网址
 public static final String GetUserInfo_Url = BASE_URL +"blueTooth/xueyaSaveOrUpdate.do";
 //反馈信息的网址
 public static final String FeedBack_Url = BASE_URL +"blueTooth/xueyaSaveOrUpdate.do";
 //获取我的团队的地址
 public static final String GetMyTeam_Url = BASE_URL +"blueTooth/xueyaSaveOrUpdate.do";
 //上传预约时间的网址
 public static final String AppointmentTime_Url = BASE_URL +"blueTooth/xueyaSaveOrUpdate.do";
 //获取健康报告的网址
 public static final String GetJianKangBaoGao_Url = BASE_URL +"blueTooth/xueyaSaveOrUpdate.do";
 //获取健康档案的网址
 public static final String GetJianKangDangAn_Url = BASE_URL +"blueTooth/xueyaSaveOrUpdate.do";
 //获取问卷调查的网址
 public static final String GetWenJuanDiaoCha_Url = BASE_URL +"blueTooth/xueyaSaveOrUpdate.do";
 //一键急救的网址
 public static final String OneKeySave_Url = BASE_URL +"blueTooth/xueyaSaveOrUpdate.do";
 /**
  *    患者端网址
  */
 //上传智能药盒的地址
 public static final String  SendYaoHeBluetooth_Url = BASE_URL+"/blueTooth/blueToothSaveOrUpdate.do";
 //上传血压记录的网址
 public static final String SendXueYa_Url = BASE_URL +"blueTooth/xueyaSaveOrUpdate.do";
 //购买服务的网址
 public static final String BuyServices_Url = BASE_URL +"blueTooth/xueyaSaveOrUpdate.do";

 /**
  * 医生端网址
  */

 //获取患者信息的网址
 public static final String GetObsessiveInfo_Url = BASE_URL +"blueTooth/xueyaSaveOrUpdate.do";

 /**
  * 测试网址
  */
 public static final String TEXT_URL1 = "http://www.chinasun.com.cn";
 public static final String TEXT_URL2 = "http://25065034.pe168.com";
 public static final String TEXT_URL3= "http://www.chinasunhealth.com";
 public static final String TEXT_URL4 = "https://www.baidu.com";
 /**
  * 测试图片网址
  */
 public static final String TEXT_IMAGEURL1 = "http://pics.sc.chinaz.com/Files/pic/icons128/6201/2r.png";
 public static final String TEXT_IMAGEURL2 = "http://pics.sc.chinaz.com/Files/pic/icons128/6201/3r.png";
}
