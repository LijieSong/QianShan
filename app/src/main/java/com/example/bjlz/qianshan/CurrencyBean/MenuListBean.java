package com.example.bjlz.qianshan.CurrencyBean;
import java.util.List;
/**
 * 项目名称：QianShan
 * 类描述：MenuListBean  //一级菜单bean
 * 创建人：slj
 * 创建时间：2016-7-27 12:55
 * 修改人：slj
 * 修改时间：2016-7-27 12:55
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class MenuListBean {

    private List<Menulist> menulist;
    public void setMenulist(List<Menulist> menulist) {
         this.menulist = menulist;
     }
     public List<Menulist> getMenulist() {
         return menulist;
     }

}