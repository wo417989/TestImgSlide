package com.sy.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityMain extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btn1 = (Button) this.findViewById(R.id.gallery1);
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(ActivityMain.this, TestImgSlideActivity.class);
				startActivity(it);
			}
		});
		Button btn2 = (Button) this.findViewById(R.id.gallery2);
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(ActivityMain.this, MyImageActivity.class);
				startActivity(it);
			}
		});
	}
}
