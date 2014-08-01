package com.example.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class ReviewVideoScrollView extends HorizontalScrollView {
	private ScrollViewListener scrollViewListener = null;

	public interface ScrollViewListener {

		public void onScrollChanged(ReviewVideoScrollView scrollView, int x,
				int y, int oldx, int oldy);

	}

	public ReviewVideoScrollView(Context context) {
		super(context);
	}

	public ReviewVideoScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public ReviewVideoScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setScrollViewListener(ScrollViewListener scrollViewListener) {
		this.scrollViewListener = scrollViewListener;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_UP) {

		}
		return super.onTouchEvent(ev);
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (scrollViewListener != null) {
			scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
		}
	}
}
