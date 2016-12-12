package com.equityinfo.pipealarm.commonality.loadview.cn.fragmetn;

import android.os.Bundle;
import android.view.View;

import com.equityinfo.pipealarm.R;
import com.equityinfo.pipealarm.commonality.loadview.LoadingPager.LoadResult;
import com.equityinfo.pipealarm.commonality.loadview.cn.base.BaseFragment;
import com.equityinfo.pipealarm.commonality.loadview.cn.utils.UIUtils;

public class HomeFragment extends BaseFragment {

	// ��Fragment���ص�activity������ʱ�����
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		show();
	}
	@Override
	public View createSuccessView() {

		return UIUtils.inflate(getActivity(),R.layout.activity_main);
	}
	@Override
	public LoadResult load() {
		return LoadResult.SUCCESS;
	}

}
