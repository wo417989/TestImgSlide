package com.sy.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

public class TestImgSlideActivity extends Activity {
    /** Called when the activity is first created. */
	private Gallery gallery;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_sys_layout);
        
        gallery = (Gallery) this.findViewById(R.id.gallery_sys);
        ImageAdapter ia = new ImageAdapter(this);
        gallery.setAdapter(ia);
        gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(TestImgSlideActivity.this, " " +position + "±»µã»÷ÁË£¡", Toast.LENGTH_SHORT).show();
			}
		});
    }
    
    private class ImageAdapter extends BaseAdapter{
    	private Context mContext;
    	private Integer[] imgArr = {
    			R.drawable.img1,
    			R.drawable.img2,
    			R.drawable.img3,
    			R.drawable.img4,
    			R.drawable.img5,
    			R.drawable.img6,
    			R.drawable.img7,
    			R.drawable.img8,
    			R.drawable.img9
    	};
    	public ImageAdapter(Context context){
    		this.mContext = context;
    	}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imgArr.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ImageView iv = new ImageView(mContext);
			iv.setImageResource(imgArr[position]);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.WRAP_CONTENT,Gallery.LayoutParams.WRAP_CONTENT));
			return iv;
		}
    	
    }
}