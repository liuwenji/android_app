package com.example.demo;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demo.ReviewVideoScrollView.ScrollViewListener;

public class MainActivity extends Activity implements ScrollViewListener {

	TextView top;
	ListView list_view;
	CustomView view;
	List<String> list;
	MyAdapter myAdapter;
	int height;
	DisplayMetrics displayMetrics;
	private ReviewVideoFrameView review_video_frame_view;
	private ReviewVideoScrollView review_video_frame_container;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		review_video_frame_container = (ReviewVideoScrollView) findViewById(R.id.review_video_frame_container);
		review_video_frame_container.setScrollViewListener(this);
		review_video_frame_container.clearFocus();
		review_video_frame_container.scrollTo(0, 0);
		// review_video_frame_view = (ReviewVideoFrameView)
		// findViewById(R.id.review_video_frame_view);
		review_video_frame_view = new ReviewVideoFrameView(this, null);
		review_video_frame_container.removeAllViews();
		review_video_frame_container.addView(review_video_frame_view);
		// displayMetrics = new DisplayMetrics();
		// Display display = this.getWindowManager().getDefaultDisplay();
		// display.getMetrics(displayMetrics);
		// top = (TextView) findViewById(R.id.top);
		// list_view = (ListView) findViewById(R.id.list_view);
		// view = (CustomView) findViewById(R.id.view);
		// list = new ArrayList<String>();
		// for (int i = 0; i < 20; i++) {
		// list.add("position= " + i);
		// }
		// myAdapter = new MyAdapter();
		// list_view.setAdapter(myAdapter);
		// height = getListViewHeightBasedOnChildren(list_view);
		// Log.i("Main", "height= " + height);
		// view.smoothScrollBy(0,
		// (int) (height - 60.0f * displayMetrics.scaledDensity), 5000);
		// top.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// view.smoothScrollBy(0,
		// (int) -(height + 60.0f * displayMetrics.scaledDensity),
		// 5000);
		// // TranslateAnimation translateAnimation = new
		// // TranslateAnimation(
		// // 0, 0, 0, height);
		// // translateAnimation.setDuration(5000);
		// // translateAnimation.setFillAfter(true);
		// // list_view.startAnimation(translateAnimation);
		// }
		// });
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView textView = new TextView(MainActivity.this);
			textView.setTextColor(Color.rgb(255, 255, 255));
			textView.setText(list.get(position));
			textView.measure(displayMetrics.widthPixels,
					(int) (20.0f * displayMetrics.scaledDensity));
			textView.invalidate();
			return textView;
		}
	}

	public int getListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return 0;
		}
		int totalHeight = 0;
		int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
				MeasureSpec.AT_MOST);
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
			Log.i("Main", "child_height= " + listItem.getMeasuredHeight());
			totalHeight += listItem.getMeasuredHeight();
		}
		// return totalHeight
		// + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		return 20 * (int) (20.0f * displayMetrics.scaledDensity);
	}

	@Override
	public void onScrollChanged(ReviewVideoScrollView scrollView, final int x,
			int y, final int oldx, int oldy) {
		Log.i("scroll_x", "x= " + x + " =oldx= " + oldx);
		if (scrollView == review_video_frame_container) {
			review_video_frame_container.scrollTo(x, y);
		}
		if (x > oldx) {
			new Thread() {
				@Override
				public synchronized void start() {
					super.start();
					if (x > review_video_frame_view.getViewWidth() / 2) {
						review_video_frame_view
								.addLineCell((int) ((x - oldx)) / 10);
					}
				}

			}.start();
		}
	}
}
