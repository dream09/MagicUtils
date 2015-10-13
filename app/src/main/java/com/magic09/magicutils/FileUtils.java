package com.magic09.magicutils;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.text.Html;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;



/**
 * FileUtils provides several static methods for file operations. 
 * @author dream09
 *
 */
public class FileUtils {
	static final String TAG = "FileUtils";
	
	/**
	 * Method returns an SmbFile created using the arguments path and smbAuth.
	 * @param path
	 * @param smbAuth
	 * @return
	 */
	public static SmbFile getSmbFileFromPath(String path, NtlmPasswordAuthentication smbAuth) {
		SmbFile dirToScan = null;
		try {
			dirToScan = new SmbFile(path, smbAuth);
		} catch (MalformedURLException e) {
			Log.e(TAG, "Error accessing - " + path);
			e.printStackTrace();
		}
		return dirToScan;
	}
	
	/**
	 * Method returns the full directory path including the final "/"
	 * of the argument fullPath.
	 * @param fullPath
	 * @return
	 */
	public static String getFolderPath(String fullPath) {
		String result = null;
		if (fullPath.indexOf("/") > -1)
			result = fullPath.substring(0, fullPath.lastIndexOf("/") + 1);
		return result;
	}
	
	/**
	 * Method returns the file name from the argument fullPath with or without
	 * extension depending on the argument showExt.
	 * @param fullPath
	 * @param showExt
	 * @return
	 */
	public static String getFilenameFromPath(String fullPath, boolean showExt) {
		String result = null;
		if (fullPath.indexOf("/") > -1) {
			result = fullPath.substring(fullPath.lastIndexOf("/") + 1);
			if (result.lastIndexOf(".") > -1) {
				if (!showExt)
					result = result.substring(0, result.indexOf("."));
			} else {
				result = null;
			}
		}
		return result;
	}
	
	/**
	 * Method returns the extension (if any) of the file in the
	 * argument fullPath.
	 * @param fullPath
	 * @return
	 */
	public static String getExtensionFromPath(String fullPath) {
		String result = null;
		if (fullPath.lastIndexOf("/") > -1)
			result = fullPath.substring(fullPath.lastIndexOf(".") + 1);
		return result;
	}
	
	/**
	 * Method deletes the directory (and all its contents)
	 * specified by the argument path.
	 * @param path
	 */
	public static void deleteDir(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDir(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
	    path.delete();
	}
	
	/**
	 * Method reads HTML formatted text from the text file (raw) resource specified
	 * in the argument resId and returns a String with its contents.
	 * @param context
	 * @param resId
	 * @return
	 */
	public static String readFromRawHtmlTextFile(Context context, int resId) {
		String result = readFromRawTextFile(context, resId);
		
		// Ensure we display any HTML encoded characters correctly
		result = Html.fromHtml(result).toString();
	    return result;
	}
	
	/**
	 * Method reads text from the text file (raw) resource specified
	 * in the argument resId and returns a String with its contents.
	 * @param context
	 * @param resId
	 * @return
	 */
	public static String readFromRawTextFile(Context context, int resId) {
		String result = null;
		
		try {
			InputStream inputStream = context.getResources().openRawResource(resId);
			InputStreamReader inputreader = new InputStreamReader(inputStream);
		    BufferedReader buffreader =
		    		new BufferedReader(inputreader);
		    String line;
		    StringBuilder text = new StringBuilder();
		    try {
		        while ((line = buffreader.readLine()) != null) {
		        	if (text.length() > 0) {
			            text.append('\n');
		        	}
		            text.append(line);
		        }
		        result = text.toString();
		    } catch (IOException e) {
		        result = null;
		    }
		} catch (NotFoundException e) {
			Log.e(TAG, "Cannot find text file resourse to display!");
			e.printStackTrace();
		}
		
	    return result;
	}
	
	/**
	 * Method returns the mime type for the argument extension.
	 * @param extension
	 * @return
	 */
	public static String getMimeTypeFromExtension(String extension) {
		MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
		return mimeTypeMap.getMimeTypeFromExtension(extension);
	}
	
}
