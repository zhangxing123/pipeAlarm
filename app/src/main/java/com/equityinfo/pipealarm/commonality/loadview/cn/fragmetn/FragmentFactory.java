package com.equityinfo.pipealarm.commonality.loadview.cn.fragmetn;

import android.support.v4.app.Fragment;

import com.equityinfo.pipealarm.commonality.loadview.cn.base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentFactory {

	private static Map<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();

	public static BaseFragment createFragment(int position) {
		BaseFragment fragment = null;
		fragment = (BaseFragment) mFragments.get(position);  //�ڼ�����ȡ����Fragment
		if (fragment == null) {  //����ټ�����û��ȡ���� ��Ҫ���´���
			if (position == 0) {
				fragment = new HomeFragment();
			} else if (position == 1) {
				fragment = new AppFragment();
			} else if (position == 2) {
				fragment = new GameFragment();
			} else if (position == 3) {
				fragment = new SubjectFragment();
			} else if (position == 4) {
				fragment = new CategoryFragment();
			} else if (position == 5) {
				fragment = new TopFragment();
			}
			if (fragment != null) {
				mFragments.put(position, fragment);// �Ѵ����õ�Fragment��ŵ������л�������
			}
		}
		return fragment;
	}
}
