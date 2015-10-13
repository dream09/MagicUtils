package com.magic09.magicutils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;



/**
 * Method provides several static methods for image decoding. 
 * @author dream09
 *
 */
public class ImageUtils {
	
	public static final String TAG = "ImageUtils";
	
	private static final String DIMEN_WIDTH = "width";
	private static final String DIMEN_HEIGHT = "height";
	
	
	
	/**
	 * Method decodes the bitmap at the argument filePath using the arguments reqWidth
	 * and reqHeight.
	 * @param filePath
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromFile(String filePath, int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(filePath, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(filePath, options);
	}
	
	/**
	 * Method returns a decoded bitmap from the argument inputStream at the appropriate size
	 * for the arguments reqWidth and reqHeight.
	 * @param inputStream
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromStream(InputStream inputStream, int reqWidth, int reqHeight) {
		
		// Setup a buffered input stream so we can reset it
		BufferedInputStream bufInputStream = new BufferedInputStream(inputStream);
		bufInputStream.mark(32);
		
		// Get the image size
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(bufInputStream, null, options);
		
		// Reset the input stream
		try {
			bufInputStream.reset();
		} catch (IOException e) {
			Log.e(TAG, "Error resetting the input stream!");
			e.printStackTrace();
		}
		
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		
		// Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    Bitmap result = BitmapFactory.decodeStream(bufInputStream, null, options);
	    
	    // Close the input stream
	    try {
			bufInputStream.close();
		} catch (IOException e) {
			Log.e(TAG, "Error closing the input stream!");
			e.printStackTrace();
		}
	    
	    return result;
	}
	
	/**
	 * Method returns a decoded bitmap from the argument url at the appropriate size
	 * for the arguments reqWidth and reqHeight.
	 * @param url
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromUrl(String url, int reqWidth, int reqHeight) {
		
		Bitmap result = null;
		
		// Setup a buffered input stream so we can reset it
		InputStream inputStream;
		try {
			inputStream = new URL(url).openStream();
			BufferedInputStream bufInputStream = new BufferedInputStream(inputStream);
			
			// Get the image size
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(bufInputStream, null, options);
			
			// Reset the input stream
			bufInputStream.close();
			inputStream.close();
			inputStream = new URL(url).openStream();
			bufInputStream = new BufferedInputStream(inputStream);
			
			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
			
			// Decode bitmap with inSampleSize set
		    options.inJustDecodeBounds = false;
		    result = BitmapFactory.decodeStream(bufInputStream, null, options);
		    
		    // Close streams
		    bufInputStream.close();
		    inputStream.close();
			
		} catch (IOException e) {
			Log.e(TAG, "Input error getting art from url.");
			e.printStackTrace();
		}
	    
	    return result;
	}
	
	/**
	 * Method returns the best sample size for a bitmap based on the arguments options,
	 * reqWidth and reqHeight.
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {

	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;

	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }

	    return inSampleSize;
	}
	
	/**
	 * Method returns the number pixels from the argument dp.
	 * @param dp
	 * @param context
	 * @return
	 */
	public static float pixelsFromDP(float dp, Context context) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}
	
	/**
	 * Method returns the screen width.
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Activity context) {
		return getDimension(context, DIMEN_WIDTH);
	}
	
	/**
	 * Method returns the screen height.
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Activity context) {
		return getDimension(context, DIMEN_HEIGHT);
	}
	
	/**
	 * Method returns the dimension specified in the argument dimen.  Requires
	 * Activity context.
	 * @param context
	 * @param dimen
	 * @return
	 */
	@SuppressLint("NewApi")
	private static int getDimension(Activity context, String dimen) {
		
		// Get the screen dimensions
		// Initially set to fallback values.
		@SuppressWarnings("deprecation")
		int screenWidth = context.getWindowManager().getDefaultDisplay().getWidth();
		@SuppressWarnings("deprecation")
		int screenHeight = context.getWindowManager().getDefaultDisplay().getHeight();
		// Attempt to get fullscreen sizes.
		Display display = context.getWindowManager().getDefaultDisplay();
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
			try {
				Method mGetRawH = Display.class.getMethod("getRawHeight");
				Method mGetRawW = Display.class.getMethod("getRawWidth");
				try {
					screenWidth = (Integer) mGetRawW.invoke(display);
					screenHeight = (Integer) mGetRawH.invoke(display);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		} else {
			DisplayMetrics outMetrics = new DisplayMetrics();
			display.getRealMetrics(outMetrics);
			screenWidth = outMetrics.widthPixels;
			screenHeight = outMetrics.heightPixels;
		}
		
		int result = 0;
		if (dimen.equals(DIMEN_WIDTH))
			result = screenWidth;
		if (dimen.equals(DIMEN_HEIGHT))
			result = screenHeight;
		return result;
	}
	
}
