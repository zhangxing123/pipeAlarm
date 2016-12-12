package com.equityinfo.pipealarm.commonality.loadview.cn.base;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.equityinfo.pipealarm.R;
import com.equityinfo.pipealarm.comm.SystemBarTintManager;
import com.equityinfo.pipealarm.commonality.loadview.LoadingPager;

/**
 * @author wang
 * @version ����ʱ�䣺2015��7��8�� ����11:31:11 ��˵��
 */
public abstract class BaseActivity extends AppCompatActivity {
	public LoadingPager loadingPage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(R.color.transparent);//通知栏所需颜色
		}

	loadingPage = new LoadingPager(this,
				R.layout.loadpage_loading, R.layout.loadpage_error,
				R.layout.loadpage_empty) {
//			@Override
//			protected LoadResult load() {
//				return BaseActivity.this.load();
//			}
			@Override
			protected View createSuccessView() {
				return BaseActivity.this.createSuccessView();
			}
		};
//		���Ե��


//		��ʾ loading��ҳ��
		loadingPage.show();
		setContentView(loadingPage);

	}

	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
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
//	protected abstract LoadingPager.LoadResult load();
	public ProgressDialog progressDialog;

	public ProgressDialog showProgressDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("加载中");
		progressDialog.show();
		return progressDialog;
	}

	public ProgressDialog showProgressDialog(CharSequence message) {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(message);
		progressDialog.show();
		return progressDialog;
	}

	public void dismissProgressDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			// progressDialog.hide();会导致android.view.WindowLeaked
			progressDialog.dismiss();
		}
	}
}
