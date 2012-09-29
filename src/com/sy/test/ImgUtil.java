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
	 * Drawable ת Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmapByBD(Drawable drawable) {
		BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		return bitmapDrawable.getBitmap();
	}

	/**
	 * Bitmap ת Drawable
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmapToDrawableByBD(Bitmap bitmap) {
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}
	
	/* ͼƬ�Ŵ��method */  
    public static  Bitmap big(Bitmap bmp) {   
    	 float scaleWidth=1;   
         float scaleHeight=1;  
        int bmpWidth=bmp.getWidth();   
        int bmpHeight=bmp.getHeight();   
           
        Log.i("TEST", "bmpWidth = " + bmpWidth + ", bmpHeight = " + bmpHeight);   
           
        /* ����ͼƬ�Ŵ�ı��� */  
        double scale=1.25;   
        /* �������Ҫ�Ŵ�ı��� */  
        scaleWidth=(float)(scaleWidth*scale);   
        scaleHeight=(float)(scaleHeight*scale);   
        /* ����reSize���Bitmap���� */  
        Matrix matrix = new Matrix();   
        matrix.postScale(scaleWidth, scaleHeight);   
        Bitmap resizeBmp = Bitmap.createBitmap(bmp,0,0,bmpWidth,    
                bmpHeight,matrix,true);   
        
        return resizeBmp;
       
}  


}
