package com.sy.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MyImageActivity extends Activity {
	private MyGallery myGallery;
	private LinearLayout circleLayout;
	public static int IMG_SIZE = 6;
	private int oldPosition = 0;
	private RelativeLayout resizeLayout;
	private ImageView ivResize;
	public ImageTimerTask timerTask;
	private boolean isExit = false;
	public boolean timeFlag = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_gallery_layout);
		initView();
		
		timerTask = new ImageTimerTask();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 5000, 5000);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (!isExit){
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					synchronized (timerTask) {
						if (!timeFlag){
							timerTask.timerCondition = true;
							timerTask.notifyAll();
						}
					}
					timeFlag = true;
				}
			}
		}).start();
	}
	private void initView(){
		myGallery = (MyGallery) this.findViewById(R.id.my_gallery);
		
		List<Img> list = new ArrayList<Img>();
		for (int i=0; i<IMG_SIZE; i++){
			Img img = new Img();
			img.setId(i);
			img.setUrl("http://192.168.2.17:8058/testimg/"+(i+1)+".jpg");
			list.add(img);
		}
		MyGalleryAdapter myGalleryAdapter = new MyGalleryAdapter(this,list, myGallery);
		myGallery.setAdapter(myGalleryAdapter);
		myGallery.setImageActivity(this);
		
		circleLayout = (LinearLayout) this.findViewById(R.id.circle_images_layout);
		for (int j=0; j<IMG_SIZE; j++){
			ImageView iv = new ImageView(this);
			if (j ==0 ){
				iv.setBackgroundResource(R.drawable.feature_point_cur);
			}else {
				iv.setBackgroundResource(R.drawable.feature_point);
			}
			circleLayout.addView(iv);
		}
		
		myGallery.setOnItemClickListener(new MyGalleryClickListener());
		myGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				changePointView(position % IMG_SIZE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		
		resizeLayout = (RelativeLayout) this.findViewById(R.id.img_resize_layout);
		ivResize = (ImageView) this.findViewById(R.id.iv_resize_img);
		ivResize.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (ivResize.getVisibility() == View.GONE){
					resizeLayout.setVisibility(View.VISIBLE);
					ivResize.setVisibility(View.VISIBLE);
				}else {
					resizeLayout.setVisibility(View.GONE);
					ivResize.setVisibility(View.GONE);
				}
			}
		});
	}
	
	public void changePointView(int newPosition){
		View oldView = circleLayout.getChildAt(oldPosition);
		View newView = circleLayout.getChildAt(newPosition);
		if (oldView != null && newView != null){
			ImageView ivOld = (ImageView) oldView;
			ImageView ivNew = (ImageView) newView;
			ivOld.setBackgroundResource(R.drawable.feature_point);
			ivNew.setBackgroundResource(R.drawable.feature_point_cur);
			oldPosition = newPosition;
		}
	}
	
	class MyGalleryClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Toast.makeText(MyImageActivity.this, " "+position +"±»µã»÷ÁË", Toast.LENGTH_SHORT).show();
			if (resizeLayout.getVisibility() == View.GONE){
				resizeLayout.setVisibility(View.VISIBLE);
				ivResize.setVisibility(View.VISIBLE);
				ivResize.setImageBitmap(Config.bmpMap.get(position % IMG_SIZE));
			}else {
				resizeLayout.setVisibility(View.GONE);
				ivResize.setVisibility(View.GONE);
			}
		}
	}
	
	private Handler myHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what){
			case 1:
				int pos = msg.getData().getInt("pos");
				myGallery.setSelection(pos);
				
				break;
			}
		};
	};
	class ImageTimerTask extends TimerTask{
		public volatile boolean timerCondition = true;
		public int galleryPosition = 0;
		@Override
		public void run() {
			synchronized (this) {
				while (!timerCondition){
					try {
						Thread.sleep(100);
						this.wait();
					} catch (InterruptedException e) {
						Thread.interrupted();
					}
				}
			}
			galleryPosition = myGallery.getSelectedItemPosition() + 1;
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putInt("pos", galleryPosition);
			msg.setData(bundle);
			msg.what = 1;
			myHandler.sendMessage(msg);
		}
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		timerTask.timerCondition = false;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		timeFlag = false;
	}
}
