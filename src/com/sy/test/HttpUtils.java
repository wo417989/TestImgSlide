package com.sy.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class HttpUtils {
	/**
	 * ����ͼƬ
	 * decodeByteArray��ʽ
	 */
	public static Bitmap getImgByURL(String urlString){
//		System.out.println(urlString);
		HttpGet httpGet = new HttpGet(urlString);
		
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpGet);
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 1;
				InputStream is = httpResponse.getEntity().getContent();
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        byte[] b = new byte[1024];
		        int len = 0;

		        while ((len = is.read(b, 0, 1024)) != -1) 
		        {
		         baos.write(b, 0, len);
		         baos.flush();
		        }
		        byte[] bytes = baos.toByteArray();
		        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
			}else{
				return null;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	} 
	
	/**
	 * decodeStream��ʽ
	 * @param url
	 * @return Bitmap
	 */
	public static Bitmap getImageFormUrl2(String url){
        //��InputStream��ΪBitmap  
		Bitmap image;
		try {
			URL url1 = new URL(url);  
			//ȡ������  
			URLConnection conn = url1.openConnection();  
			conn.connect();  
			//ȡ�÷��ص�InputStream  
			InputStream is = conn.getInputStream();  
			image = BitmapFactory.decodeStream(is);  
			is.close();
			return image;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
	}
	
	/**
	 * 
	 * @param url
	 * @return Drawable
	 */
	 public static Drawable loadImageFromUrl(String url) {
         URL m;
         InputStream i = null;
         Drawable d;
         try {
             m = new URL(url);
             i = (InputStream) m.getContent();
             d = Drawable.createFromStream(i, "");
//             d = Drawable.createFromStream(m.openStream(), "");//�������ȡ
//             d = Drawable.createFromPath("");//�ӱ��ػ�ȡ
             return d;
         } catch (MalformedURLException e1) {
             e1.printStackTrace();
             return null;
         } catch (IOException e) {
             e.printStackTrace();
             return null;
         }
         
     }


	/**
	 * ������������
	 * 
	 * @return
	 */
	public static boolean is_Intent(Context context) {
		ConnectivityManager con = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = con.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			// ��ǰ���粻����
			return false;
		}
		return true;

	}
	
	/**
	 * 
	  * ��������������xml�ļ�
	  * ����������@param{@link } 
	  * �������ͣ�String
	  * �������ߣ�sy
	  * ����ʱ�䣺2011-6-16
	 */
	public static String downXML(String urlString){		
		try {
			HttpGet httpGet = new HttpGet(urlString);	
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String string = null;
				string = EntityUtils.toString(httpResponse.getEntity());
		    	if(string == null || string.trim().equals("")){
		    		return null;
		    	} 	
		    	string = new String(string.getBytes(),"UTF-8");	
		    	return string;			
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		
		/*	
		StringBuffer sb = new StringBuffer();
		String line = null;
		//�������ص��ַ���
		BufferedReader buffer = null;
		try {
			// ����һ��URL����
			URL url = new URL(urlString);
			// ����һ��Http����
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			// ʹ��IO����ȡ����
			buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));//gb2312
			while ((line = buffer.readLine()) != null) {
			//	System.out.println(line);
				try{
					sb.append(line);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();*/
	}
}
