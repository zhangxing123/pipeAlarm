package com.equityinfo.pipealarm.view.presenter;

import android.content.Context;
import android.util.Log;

import com.equityinfo.pipealarm.R;
import com.equityinfo.pipealarm.comm.other.BasePresenter;
import com.equityinfo.pipealarm.model.MainModel;
import com.equityinfo.pipealarm.model.bean.CodeBean;
import com.equityinfo.pipealarm.model.bean.ResultBean;
import com.equityinfo.pipealarm.model.bean.WarnBean;
import com.equityinfo.pipealarm.model.bean.WarnResultBean;
import com.equityinfo.pipealarm.setting.Setting;
import com.equityinfo.pipealarm.util.Utils;
import com.equityinfo.pipealarm.util.http.AsyncCallBack;
import com.equityinfo.pipealarm.view.interfaces.MainView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by WuXiaolong
 * on 2015/9/23.
 * github:https://github.com/WuXiaolong/
 * weibo:http://weibo.com/u/2175011601
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
public class MainPresenter extends BasePresenter<MainView> {
    private static String status_both = "99";
    private static String status_unread = "0";
    private static String status_read = "1";
private Context context;
    private MainModel mainModel;
    public MainPresenter(MainView view,Context context) {
        this.context=context;
        attachView(view);
    }
       public void loadDate(){
        mainModel = new MainModel(context);
        //APP进入后先取出终端号
        mainModel.getcode(new AsyncCallBack() {
            @Override
            public void onSuccess(String result) {
                String s = result;
                if (s == null || s.equals("")) {
                    mvpView.getDataFail(context.getResources().getString(R.string.ter_error));
                    Log.d(Setting.TAG, "onSuccess：result==null");
                } else {
                    Gson gson = new Gson();
                    ResultBean<List<CodeBean>> data = gson.fromJson(s, new TypeToken<ResultBean<List<CodeBean>>>() {
                    }.getType());
                    if (data.result.equals("success")) {
                       List<CodeBean> codes = data.data;
                        mvpView.getCodes(codes);
                        if (codes.size() > 0) {
                            Log.d(Setting.TAG, "codes.size()" + codes.size());
                           List<String> times = Utils.getTimes(7);
                            mvpView.getTimes(times);
                            //终端号取出后，获取警告数据
                            mainModel.getwarning(codes.get(0).code, times.get(0), status_both, "1", "10", new AsyncCallBack() {
                                @Override
                                public void onFailure(String erroMsg) {
                                    mvpView.getDataFail(context.getResources().getString(R.string.ter_error));
                                    Log.d(Setting.TAG, "onSuccess：result==null");
                                }

                                @Override
                                public void onSuccess(String result) {
                                    String s = result;
                                    if (s == null || s.equals("")) {
                                        mvpView.getDataFail(context.getResources().getString(R.string.ter_error));
                                        Log.d(Setting.TAG, "2onSuccess：result==null");
                                    } else {
                                        Gson gson = new Gson();
                                        WarnResultBean<List<WarnBean>> data = gson.fromJson(s, new TypeToken<WarnResultBean<List<WarnBean>>>() {
                                        }.getType());
                                        if (data.result.equals("success")) {
                                            //将warn数据传给view
                                            mvpView.getWarns(data.data,data.count);
                                            mvpView.getDataSuccess();
                                        }else {
                                            mvpView.getDataFail(context.getResources().getString(R.string.ter_error));
                                        }
                                    }
                                }
                            });
                        } else {
                            mvpView.getDataFail(context.getResources().getString(R.string.ter_error));
                        }
                    } else {
                        mvpView.getDataFail(context.getResources().getString(R.string.ter_error));

                    }

                }
            }
            @Override
            public void onFailure(String erroMsg) {
                mvpView.getDataFail(context.getResources().getString(R.string.ter_error));
                Log.d(Setting.TAG, "onSuccess：result==null");
            }
        });
    }
    public void loadMore(String code, String tm, String status, String f, String r){
        MainModel mainModel=new MainModel(context);
        mainModel.getwarning(code, tm, status, f, "10", new AsyncCallBack() {
            @Override
            public void onFailure(String erroMsg) {
                mvpView.getDataFail(context.getResources().getString(R.string.ter_error));
                Log.d(Setting.TAG, "onSuccess：result==null");
            }

            @Override
            public void onSuccess(String result) {
                String s = result;
                if (s == null || s.equals("")) {
                    mvpView.getDataFail(context.getResources().getString(R.string.ter_error));
                    Log.d(Setting.TAG, "2onSuccess：result==null");
                } else {
                    Gson gson = new Gson();
                    WarnResultBean<List<WarnBean>> data = gson.fromJson(s, new TypeToken<WarnResultBean<List<WarnBean>>>() {
                    }.getType());
                    if (data.result.equals("success")) {
                        //将warn数据传给view
                        mvpView.getMoreWarns(data.data);
                        mvpView.getDataSuccess();
                    }else {
                        Utils.showToast(context,context.getResources().getString(R.string.ter_error2));
                    }
                }
            }
        });
    }

}
