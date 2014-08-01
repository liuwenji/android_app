package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class ReviewVideoFrameView extends View {

	private int view_left = 0;
	private int view_right;
	private List<LineCell> lineCells;
	private Context mContext;

	private final float line_windth;
	private int line_margin;
	private int line_height;
	private int line_top_margin;
	private int line_bottom_margint;
	private DisplayMetrics displayMetrics;
	private int view_width = 0;
	private int view_height;
	private float scale;

	public ReviewVideoFrameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		displayMetrics = mContext.getResources().getDisplayMetrics();
		scale = displayMetrics.widthPixels / 480.0f;
		line_windth = 2.0f * scale;
		view_height = (int) (50.0f * scale);
		line_margin = (int) (40.0f * scale);
		line_height = view_height;
		line_top_margin = (int) (8.0f * scale);
		line_bottom_margint = (int) (8.0f * scale);
		setListDatas();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.i("line_cell", "onDraw");
		if (lineCells != null && lineCells.size() > 0) {
			for (int i = 0; i < lineCells.size(); i++) {
				LineCell lineCell = lineCells.get(i);
				Log.i("line_cell", "x= " + lineCell.line_x);
				lineCell.draw(canvas);
			}
		}
	}

	public void setListDatas() {
		lineCells = new ArrayList<ReviewVideoFrameView.LineCell>();
		for (int i = 0; i < 50; i++) {
			LineCell lineCell;
			if (i % 2 == 0) {
				lineCell = new LineCell(view_left + line_margin * i, 0,
						line_height);
			} else {
				lineCell = new LineCell(view_left + line_margin * i,
						0 + line_top_margin, line_height - line_bottom_margint);
			}
			lineCells.add(lineCell);
		}
		invalidate();
	}

	public int getViewWidth() {
		return view_width;
	}

	public void addLineCell(int size) {
		Log.i("add_cell_size", "add_cell_size=  " + size);
		layout(0, 0,
				(int) ((lineCells.size() + size) * line_windth + line_margin
						* ((lineCells.size() + size) - 1)),
				displayMetrics.heightPixels);
		view_width = (int) ((lineCells.size() + size) * line_windth + line_margin
				* (lineCells.size() - 1));
		LineCell last_lineCell = lineCells.get(lineCells.size() - 1);
		int last_line_height = (int) Math.abs(last_lineCell.line_stop_y
				- last_lineCell.line_start_y);
		float last_line_x = last_lineCell.line_x;

		for (int i = 0; i < size; i++) {
			LineCell lineCell;
			if (last_line_height > line_height - line_bottom_margint * 2) {// 最后的为长线
				if (i % 2 != 0) {
					lineCell = new LineCell(last_line_x + line_margin * i, 0,
							line_height);
				} else {
					lineCell = new LineCell(last_line_x + line_margin * i,
							0 + line_top_margin, line_height
									- line_bottom_margint);
				}
			} else {// 最后的为短线
				if (i % 2 == 0) {
					lineCell = new LineCell(last_line_x + line_margin * i, 0,
							line_height);
				} else {
					lineCell = new LineCell(last_line_x + line_margin * i,
							0 + line_top_margin, line_height
									- line_bottom_margint);
				}
			}
			lineCells.add(lineCell);
		}
		// super.onMeasure((int) (lineCells.size() * line_windth + line_margin
		// * (lineCells.size() - 1)), displayMetrics.heightPixels);
		postInvalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (lineCells != null && lineCells.size() > 0) {
			view_width = (int) (lineCells.size() * line_windth + line_margin
					* (lineCells.size() - 1));
			view_height = (int) (50.0f * scale);
			setMeasuredDimension(
					(int) (lineCells.size() * line_windth + line_margin
							* (lineCells.size() - 1)), view_height);
		}
	}

	class LineCell {

		private float line_x;
		private float line_start_y;
		private float line_stop_y;

		public LineCell(float line_x, float line_start_y, float line_stop_y) {
			this.line_x = line_x;
			this.line_start_y = line_start_y;
			this.line_stop_y = line_stop_y;
		}

		protected void draw(Canvas canvas) {
			Paint paint = new Paint();
			paint.setStrokeWidth(line_windth);
			paint.setStyle(Style.FILL);
			paint.setColor(Color.WHITE);
			canvas.drawLine(line_x, line_start_y, line_x, line_stop_y, paint);
		}

	}
}
