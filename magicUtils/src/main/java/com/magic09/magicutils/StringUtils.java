package com.magic09.magicutils;

import java.util.Formatter;
import java.util.Locale;



/**
 * StringUtils provides several static methods for file operations. 
 * @author magic09
 *
 */
public class StringUtils {
	static final String TAG = "StringUtils";
	
	/**
	 * Method returns a hh:MM:SS formatted time string based on
	 * the milliseconds provide in the argument millis. 
	 * @param millis
	 * @return
	 */
	public static String getStringFromTimeMillis(long millis) {
        long totalSeconds = millis / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours   = totalSeconds / 3600;
        StringBuilder mFormatBuilder;
        Formatter mFormatter;
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        mFormatBuilder.setLength(0);
        String result;
        if (hours > 0) {
            result = mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            result = mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
        mFormatter.close();
        
        return result;
    }
	
}
