package com.equityinfo.pipealarm.view.interfaces;

import com.equityinfo.pipealarm.comm.other.BaseView;
import com.equityinfo.pipealarm.model.bean.CodeBean;
import com.equityinfo.pipealarm.model.bean.WarnBean;

import java.util.List;

/**
 * Created by WuXiaolong on 2015/9/23.
 * 处理业务需要哪些方法
 * github:https://github.com/WuXiaolong/
 * weibo:http://weibo.com/u/2175011601
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
public interface MainView extends BaseView {

    void getDataSuccess();
    void getDataFail(String msg);
    void getCodes(List<CodeBean> codes);
    void getTimes(List<String> codes);
    void getWarns(List<WarnBean> warnBeanList,String count);
    void getMoreWarns(List<WarnBean> warnBeanList);
}
