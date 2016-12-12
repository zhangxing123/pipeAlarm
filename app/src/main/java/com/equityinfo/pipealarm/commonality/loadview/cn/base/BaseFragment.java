package com.equityinfo.pipealarm.commonality.loadview.cn.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.equityinfo.pipealarm.R;
import com.equityinfo.pipealarm.commonality.loadview.LoadingPager;
import com.equityinfo.pipealarm.commonality.loadview.LoadingPager.LoadResult;
import com.equityinfo.pipealarm.commonality.loadview.cn.utils.UIUtils;
import com.equityinfo.pipealarm.commonality.loadview.cn.utils.ViewUtils;

public abstract class BaseFragment extends Fragment {

	private LoadingPager loadingPage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (loadingPage == null) { // ֮ǰ��frameLayout �Ѿ���¼��һ������ ����֮ǰ��ViewPager
			loadingPage = new LoadingPager(UIUtils.getContext(),
					R.layout.loadpage_loading, R.layout.loadpage_error,
					R.layout.loadpage_empty) {
//				@Override
//				protected LoadResult load() {
//					return BaseFragment.this.load();
//				}

				@Override
				protected View createSuccessView() {
					return BaseFragment.this.createSuccessView();
				}
			};
			loadingPage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					loadingPage.show();
				}
			});
		} else {
			ViewUtils.removeSelfFromParent(loadingPage);// �Ƴ�frameLayout֮ǰ�ĵ�
		}
		return loadingPage;
	}

	public void show() {
		if (loadingPage != null) {
			loadingPage.show();
		}
	}

	/**
	 * ˢ��ҳ�湤��
	 * 
	 * @return
	 */
	protected abstract View createSuccessView();

	/**
	 * ��������� ��ȡ��ǰ״̬
	 * 
	 */
	protected abstract LoadResult load();

}
