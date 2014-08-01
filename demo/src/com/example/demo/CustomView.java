package com.example.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class CustomView extends LinearLayout {

	private static final String TAG = "Scroller";

	private Scroller mScroller;

	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);
	}

	// ���ô˷���������Ŀ��λ��
	public void smoothScrollTo(int fx, int fy) {
		int dx = fx - mScroller.getFinalX();
		int dy = fy - mScroller.getFinalY();
		smoothScrollBy(dx, dy, 0);
	}

	// ���ô˷������ù��������ƫ��
	public void smoothScrollBy(int dx, int dy, int duration) {

		Log.i("Main", "final_y= " + mScroller.getFinalY());
		// ����mScroller�Ĺ���ƫ����
		mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx,
				dy, duration);
		invalidate();// ����������invalidate()���ܱ�֤computeScroll()�ᱻ���ã�����һ����ˢ�½��棬����������Ч��
	}

	@Override
	public void computeScroll() {

		// ���ж�mScroller�����Ƿ����
		if (mScroller.computeScrollOffset()) {

			// �������View��scrollTo()���ʵ�ʵĹ���
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

			// ������ø÷���������һ���ܿ�������Ч��
			postInvalidate();
		}
		super.computeScroll();
	}
}