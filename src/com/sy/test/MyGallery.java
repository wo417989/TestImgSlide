package com.sy.test;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class MyGallery extends Gallery {

	private MyImageActivity mActivity;
	public MyGallery(Context context) {
		super(context);
	}

	public MyGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setImageActivity(MyImageActivity activity){
		this.mActivity = activity;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				mActivity.timeFlag = false;
				this.cancel();
			}
		}, 5000);
		int kEvent;
		if (isScrollingLeft(e1, e2)){
			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
		}else {
			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(kEvent, null);
		//如果一开始是0 向右滑动处理
		if (this.getSelectedItemPosition() == 0){
			this.setSelection(mActivity.IMG_SIZE);
		}
		return super.onFling(e1, e2, velocityX, velocityY);
	}
	
	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2){
		return e2.getX() > e1.getX();
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		mActivity.timerTask.timerCondition = false;
		return super.onScroll(e1, e2, distanceX, distanceY);
	}
	
	

}
