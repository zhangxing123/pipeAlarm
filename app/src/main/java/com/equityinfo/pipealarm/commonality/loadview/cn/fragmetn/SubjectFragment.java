package com.equityinfo.pipealarm.commonality.loadview.cn.fragmetn;

import android.view.View;

import com.equityinfo.pipealarm.R;
import com.equityinfo.pipealarm.commonality.loadview.cn.base.BaseFragment;
import com.equityinfo.pipealarm.commonality.loadview.cn.utils.UIUtils;
import com.equityinfo.pipealarm.commonality.loadview.LoadingPager.LoadResult;

public class SubjectFragment extends BaseFragment {


	@Override
	public View createSuccessView() {

		return UIUtils.inflate(getActivity(),R.layout.activity_main);
	}

	@Override
	public LoadResult load() {
		return LoadResult.ERROR;
	}
}
