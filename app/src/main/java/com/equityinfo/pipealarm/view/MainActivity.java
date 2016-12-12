package com.equityinfo.pipealarm.view;


import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.equityinfo.pipealarm.R;
import com.equityinfo.pipealarm.comm.other.MvpActivity;
import com.equityinfo.pipealarm.commonality.loadview.LoadingPager;
import com.equityinfo.pipealarm.commonality.loadview.cn.utils.UIUtils;
import com.equityinfo.pipealarm.commonality.loadview.cn.utils.ViewUtils;
import com.equityinfo.pipealarm.model.bean.CodeBean;
import com.equityinfo.pipealarm.model.bean.WarnBean;
import com.equityinfo.pipealarm.util.Utils;
import com.equityinfo.pipealarm.view.adapter.MainAdapter;
import com.equityinfo.pipealarm.view.adapter.SpinnerAdapter;
import com.equityinfo.pipealarm.view.fragment.SpinnerTimeAdapter;
import com.equityinfo.pipealarm.view.interfaces.MainView;
import com.equityinfo.pipealarm.view.presenter.MainPresenter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

/**
 * @author ZX
 */
public class MainActivity extends MvpActivity<MainPresenter> implements MainView {
    private List<CodeBean> codes;
    private List<String> times;
    private List<WarnBean> warnBeanList;
    private String count;
    private String terminal;
    private String tm;
    private String status=status_both;
    private int pages=1;
    private Spinner terminalSP;
    private Spinner timeSP;
    private TextView countTV;
    private PullToRefreshListView mPullRefreshListView;
    private static String status_both = "99";
    private static String status_unread = "0";
    private static String status_read = "1";
    private MainAdapter iAdapter;
    ListView actualListView;
    @Override
    protected View createSuccessView() {
        View inflate = UIUtils.inflate(this, R.layout.activity_main);
        terminalSP = ViewUtils.findViewById(inflate, R.id.sp_terminal);
        timeSP = ViewUtils.findViewById(inflate, R.id.sp_tm);
        countTV = ViewUtils.findViewById(inflate, R.id.tx_home_add);
        countTV.setText(count);
        if (codes != null) {
            SpinnerAdapter terminalAdapter = new SpinnerAdapter(this, R.layout.spinner_item, R.id.tvCateItem, codes);
            terminalSP.setAdapter(terminalAdapter);
        }
        if (times != null) {
            SpinnerTimeAdapter terminalAdapter = new SpinnerTimeAdapter(this, R.layout.spinner_item, R.id.tvCateItem, times);
            timeSP.setAdapter(terminalAdapter);
        }
        initPullview(inflate);
        return inflate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter.loadDate();
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this, this);
    }

    @Override
    public void getCodes(List<CodeBean> codes) {
        this.codes = codes;
        terminal=codes.get(0).code;
    }

    @Override
    public void getDataFail(String msg) {
        loadingPage.loaded(LoadingPager.LoadResult.ERROR);
        Utils.showToast(MainActivity.this, msg);
    }

    @Override
    public void getDataSuccess() {
        loadingPage.loaded(LoadingPager.LoadResult.SUCCESS);
    }

    @Override
    public void getTimes(List<String> times) {
        this.times = times;
        this.tm=times.get(0);
    }

    @Override
    public void getWarns(List<WarnBean> warnBeanList,String count) {
        this.warnBeanList=warnBeanList;
        this.count=count;
    }

    @Override
    public void getMoreWarns(List<WarnBean> moreWarnBeanList) {
        warnBeanList.addAll(moreWarnBeanList);
        iAdapter.notifyDataSetChanged();
        mPullRefreshListView.onRefreshComplete();

    }

    private void initPullview(View view) {
        mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullRefreshListView.setPullToRefreshOverScrollEnabled(false);
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新数据
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                // Do work to refresh the list here.
//                getNewTasklist();
//
                new GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                pages++;
//                getNextPageTasklist(pages);
                // Do work to refresh the list here.
                mvpPresenter.loadMore(terminal,tm,status,""+pages,"10");
//                new GetDataTask().execute();
            }
        });
        actualListView= mPullRefreshListView.getRefreshableView();
        actualListView.setFooterDividersEnabled(true);
        iAdapter = new MainAdapter(this, warnBeanList, actualListView);
        actualListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (actualListView.getLastVisiblePosition() == (actualListView.getCount() - 1)) {
                            mvpPresenter.loadMore(terminal,tm,status,""+pages,"10");

                        }
                        // 判断滚动到顶部

                        if(actualListView.getFirstVisiblePosition() == 0){
                        }

                        break;
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) { }});
//        initTasklist();
        //这两个绑定方法用其一z
        // 方法一
//       mPullRefreshListView.setAdapter(iAdapter);
        //方法二


        actualListView.setAdapter(iAdapter);
        actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private class GetDataTask extends AsyncTask<Void, Void, String> {

        //后台处理部分
        @Override
        protected String doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            String str = "Added after refresh...I add";
            return str;
        }

        //这里是对刷新的响应，可以利用addFirst（）和addLast()函数将新加的内容加到LISTView中
        //根据AsyncTask的原理，onPostExecute里的result的值就是doInBackground()的返回值
        @Override
        protected void onPostExecute(String result) {
            //在头部增加新添内容

            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshListView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }
}
