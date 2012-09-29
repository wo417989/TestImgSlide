package com.sy.test;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.sy.test.AsyncImageLoader.ImageCallback1;

public class MyGalleryAdapter extends BaseAdapter {

	private Context mContext;
	private List<Img> list;
	private Gallery gallery;
	private AsyncImageLoader asyncImageLoader;
	private Bitmap bitmap;
	public MyGalleryAdapter(Context context, List<Img> imgList, Gallery gallery){
		this.mContext = context;
		this.list = imgList;
		this.gallery = gallery;
		this.asyncImageLoader = new AsyncImageLoader();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position % list.size());
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String imageUrl = list.get(position % list.size()).getUrl();
		ViewHolder viewHolder=null;
		Drawable drawable =null;
		if (convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item, null);
			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.gallery_image);
			viewHolder.imageView.setTag(imageUrl);
			
			
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		drawable = asyncImageLoader.loadDrawable(mContext, imageUrl, new ImageCallback1() {
			
			@Override
			public void imageLoaded(Drawable imageDrawable, String imageUrl) {
				ImageView imageViewByTag = (ImageView) gallery.findViewWithTag(imageUrl);
				if (imageViewByTag != null && imageDrawable != null){
					imageViewByTag.setBackgroundDrawable(imageDrawable);
					notifyDataSetChanged();
				}
			}
		});
		
		if (drawable == null){
			viewHolder.imageView.setBackgroundResource(R.drawable.ic_launcher);
			bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
				Config.bmpMap.put(position % list.size(), bitmap);
		}else {
			viewHolder.imageView.setBackgroundDrawable(drawable);
			bitmap = ImgUtil.drawableToBitmapByBD(drawable);
			
			Config.bmpMap.put(position % list.size(), bitmap);
			
		}
//		((MyImageActivity)mContext).changePointView(position % list.size());
		
//		viewHolder.imageView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Bitmap resizeBitmap = ImgUtil.big(bitmap);
//				ImageView iv = new ImageView(mContext);
//				iv.setImageBitmap(resizeBitmap);
//				
//			}
//		});
		
		return convertView;
	}
	
	static class ViewHolder{
		ImageView imageView;
	}

}
