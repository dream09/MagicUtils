package com.magic09.magicutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.MalformedURLException;
import java.net.UnknownHostException;

import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbAuthException;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbSession;



/**
 * NetworkUtils provides several static methods for network operations. 
 * @author dream09
 *
 */
public class NetworkUtils {
	
	/**
	 * Method returns true if Wi-Fi is connected otherwise false.
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return mWifi.isConnected();
	}
	
	/**
	 * Method tests if the ip address passed in the argument ipaddress can
	 * be reached on the current network.
	 * @param ipaddress
	 * @return
	 */
	public static boolean testIPAddress(String ipaddress) {
		boolean result = false;
		if (getDomain(ipaddress) != null)
			result = true;
		
		return result;
	}
	
	/**
	 * Method tests the login details provided and returns true if login
	 * successful otherwise false.
	 * @param ipaddress
	 * @param user
	 * @param pass
	 * @return
	 */
	public static boolean testIPLoginDetails(String ipaddress, String user, String pass) {
		boolean result = false;
		
		// Get domain and try to login
		UniAddress domain = getDomain(ipaddress);
		if (domain != null) {
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, user, pass);
			try {
				SmbSession.logon(domain, auth);
				result = true;
			} catch (SmbAuthException ae) {
				result = false;
			} catch (SmbException se) {
				result = false;
			}
		}
		
		return result;
	}
	
	/**
	 * Helper method that returns the domain for the ip address specified
	 * in the argument ipadress.  Returns null if cannot get domain.
	 * @param ipaddress
	 * @return
	 */
	private static UniAddress getDomain(String ipaddress) {
		UniAddress domain;
		try {
			domain = UniAddress.getByName(ipaddress);
		} catch (UnknownHostException e) {
			domain = null;
		}
		
		return domain;
	}
	
	/**
	 * Method tests the login details provided and returns true if login
	 * successful otherwise false.
	 * @param smbaddress
	 * @param user
	 * @param pass
	 * @return
	 */
	public static boolean testSMBLoginDetails(String smbaddress, String user, String pass) {
		boolean result = false;
		SmbFile smbFile;
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, user, pass);
		try {
			smbFile = new SmbFile(smbaddress, auth);
		} catch (MalformedURLException e) {
			smbFile = null;
		}
		if (smbFile != null) {
			try {
				result = smbFile.exists();
			} catch (SmbException e) {
				result = false;
			}
		}
		
		return result;
	}
	
}
