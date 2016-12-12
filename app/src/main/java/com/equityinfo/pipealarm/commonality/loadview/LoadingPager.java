package com.equityinfo.pipealarm.commonality.loadview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.equityinfo.pipealarm.commonality.loadview.cn.utils.UIUtils;

/**
 * �ȼ�˳�� load -->showPagerView-->createSuccessView
 * 
 * @author wanghao
 *
 *�������� ��ʱ�����ŵ� load�У�Ȼ��load����һ��״̬����showPagerView�и���״̬ѡ�� ��ʾ��ҳ��
 *���װ���ǳɹ��ġ���ô����ʾ createSuccessView
 */
public abstract class LoadingPager extends FrameLayout {

	// ����Ĭ�ϵ�״̬
	private static final int STATE_UNLOADED = 1;
	// ���ص�״̬
	private static final int STATE_LOADING = 2;
	// ����ʧ�ܵ�״̬
	private static final int STATE_ERROR = 3;
	// ���ؿյ�״̬
	private static final int STATE_EMPTY = 4;
	// ���سɹ���״̬
	private static final int STATE_SUCCEED = 5;

	private View mLoadingView;// תȦ��view
	private View mErrorView;// �����view
	private View mEmptyView;// �յ�view
	private View mSucceedView;// �ɹ���view

	private int mState;// Ĭ�ϵ�״̬

	private int loadpage_empty;
	private int loadpage_error;
	private int loadpage_loading;
	public LoadingPager(Context context, int loading, int error, int empty) {
		super(context);
		loadpage_empty = empty;
		loadpage_error = error;
		loadpage_loading = loading;
		init();
	}

	public LoadingPager(Context context, AttributeSet attrs, int defStyle,
			int loading, int error, int empty) {
		super(context, attrs, defStyle);
		loadpage_empty = empty;
		loadpage_error = error;
		loadpage_loading = loading;
		init();
	}

	public LoadingPager(Context context, AttributeSet attrs, int loading,
			int error, int empty) {
		super(context, attrs);
		loadpage_empty = empty;
		loadpage_error = error;
		loadpage_loading = loading;
		init();
	}

	private void init() {
		// ��ʼ��״̬
		mState = STATE_UNLOADED;
		// ��ʼ������ ״̬��view ���ʱ�� ����״̬��view������һ����
		mLoadingView = createLoadingView();
		if (null != mLoadingView) {
			addView(mLoadingView, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
		}
		mErrorView = createErrorView();
		if (null != mErrorView) {
			addView(mErrorView, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
		}
		mEmptyView = createEmptyView();
		if (null != mEmptyView) {
			addView(mEmptyView, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
		}
		showSafePagerView();
	}

	private void showSafePagerView() {
		// ֱ�����е����߳�
		UIUtils.runInMainThread(new Runnable() {
			@Override
			public void run() {
				showPagerView();
			}
		});
	}

	private void showPagerView() {

		// �@���r�� ������� mStateĬ�J��STATE_UNLOADED��B����ֻ�@ʾ lodaing ����� error
		// ��empty��ʱ����ʾ
		if (null != mLoadingView) {
			mLoadingView.setVisibility(mState == STATE_UNLOADED
					|| mState == STATE_LOADING ? View.VISIBLE :

			View.INVISIBLE);
		}
		if (null != mErrorView) {
			mErrorView.setVisibility(mState == STATE_ERROR ? View.VISIBLE
					: View.INVISIBLE);
		}
		if (null != mEmptyView) {
			mEmptyView.setVisibility(mState == STATE_EMPTY ? View.VISIBLE
					: View.INVISIBLE);
		}

		if (mState == STATE_SUCCEED && mSucceedView == null) {
			mSucceedView = createSuccessView();
			addView(mSucceedView, new LayoutParams

			(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		if (null != mSucceedView) {
			mSucceedView.setVisibility(mState == STATE_SUCCEED ?

			View.VISIBLE : View.INVISIBLE);
		}
	}

	public void show() {
		// ��һ�ν����϶�Ҫ תȦ�� ���Ծ����� error��empty ҲҪ��״̬�� unload
		if (mState == STATE_ERROR || mState == STATE_EMPTY) {
			mState = STATE_UNLOADED;
		}
		// �����unload �Ͱ�״̬ ��Ϊ loading�� ��ʱ��ӷ�����������
		if (mState == STATE_UNLOADED) {
			mState = STATE_LOADING;

//			TaskRunnable task = new TaskRunnable();
//			ThreadManager.getLongPool().execute(task);
		}
		showSafePagerView();
	}

	/**
	 * ��������
	 * 
	 * @return
	 */
	protected abstract View createSuccessView();

	/**
	 * �������� ��ʱ����
	 * 
	 * @return
	 */
//	protected abstract LoadResult load();

	/**
	 * �ս���
	 * 
	 * @return
	 */
	public View createEmptyView() {
		if (loadpage_empty != 0) {
			return UIUtils.inflate(getContext(),loadpage_empty);
		}
		return null;

	}

	/**
	 * ʧ�ܵ�ҳ��
	 * 
	 * @return
	 */
	public View createErrorView() {
		// View inflate = UIUtils.inflate(loadpage_error);
		// // Button btn = ViewUtils.findViewById(inflate, R.id.page_bt);
		// // btn.setOnClickListener(new OnClickListener() {
		// // @Override
		// // public void onClick(View v) {
		// // show();
		// // }
		// // });
		// return inflate;
		if (loadpage_empty != 0) {
			return UIUtils.inflate(getContext(),loadpage_error);
		}
		return null;
	}

	/**
	 * ������ת��ҳ��
	 * 
	 * @return
	 */
	public View createLoadingView() {
		if (loadpage_empty != 0) {
			return UIUtils.inflate(getContext(),loadpage_loading);
		}
		return null;
	}
    public void loaded(LoadResult loadResult){
		mState = loadResult.getValue();
		showPagerView();
	}
//	class TaskRunnable implements Runnable {
//		@Override
//		public void run() {
////			final LoadResult loadResult = load();
//			SystemClock.sleep(500);
//			UIUtils.runInMainThread(new Runnable() {
//				@Override
//				public void run() {
////					mState = loadResult.getValue();
////					showPagerView();
//				}
//			});
//		}
//	}

	public enum LoadResult {
		ERROR(3), EMPTY(4), SUCCESS(5);
		int value;

		LoadResult(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

}
