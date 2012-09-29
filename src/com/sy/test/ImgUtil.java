package com.sy.test;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

public class ImgUtil {
	/**
	 * Drawable 转 Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmapByBD(Drawable drawable) {
		BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		return bitmapDrawable.getBitmap();
	}

	/**
	 * Bitmap 转 Drawable
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmapToDrawableByBD(Bitmap bitmap) {
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}
	
	/* 图片放大的method */  
    public static  Bitmap big(Bitmap bmp) {   
    	 float scaleWidth=1;   
         float scaleHeight=1;  
        int bmpWidth=bmp.getWidth();   
        int bmpHeight=bmp.getHeight();   
           
        Log.i("TEST", "bmpWidth = " + bmpWidth + ", bmpHeight = " + bmpHeight);   
           
        /* 设置图片放大的比例 */  
        double scale=1.25;   
        /* 计算这次要放大的比例 */  
        scaleWidth=(float)(scaleWidth*scale);   
        scaleHeight=(float)(scaleHeight*scale);   
        /* 产生reSize后的Bitmap对象 */  
        Matrix matrix = new Matrix();   
        matrix.postScale(scaleWidth, scaleHeight);   
        Bitmap resizeBmp = Bitmap.createBitmap(bmp,0,0,bmpWidth,    
                bmpHeight,matrix,true);   
        
        return resizeBmp;
       
}  


}
