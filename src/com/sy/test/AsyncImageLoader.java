package com.sy.test;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;

public class AsyncImageLoader {
	public Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();
	private ExecutorService executorService = Executors.newFixedThreadPool(3);
	private final Handler handle = new Handler();

	/**
	 * 
	 * @param imageUrl
	 *            图像url地址
	 * @param imageCallback
	 *            回调接口
	 * @return 返回内存中缓存的图像，第一次加载返回null
	 */
	public Drawable loadDrawable(final Context context, final String imageUrl,
			final ImageCallback1 imageCallback) {
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			if (softReference.get() != null) {
				return softReference.get();
			}
		}
		if (HttpUtils.is_Intent(context)) {
			executorService.submit(new Runnable() {

				@Override
				public void run() {
					try {
						final Drawable drawable = HttpUtils
								.loadImageFromUrl(imageUrl);
						if (drawable != null) {
							imageCache.put(imageUrl,
									new SoftReference<Drawable>(drawable));
							handle.post(new Runnable() {

								@Override
								public void run() {
									imageCallback.imageLoaded(drawable,
											imageUrl);
								}
							});

						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new RuntimeException();
					}
				}
			});
		}

		return null;
	}

	public interface ImageCallback1 {
		public void imageLoaded(Drawable imageDrawable, String imageUrl);
	}

}
