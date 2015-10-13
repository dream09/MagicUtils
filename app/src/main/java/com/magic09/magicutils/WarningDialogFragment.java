package com.magic09.magicutils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;



/**
 * WarningDialogFragment provides a DialogFragment that presents a message to a user
 * that can only be dismissed.
 * @author dream09
 *
 */
public class WarningDialogFragment extends DialogFragment {
	public static final String TAG = "WarningDialogFragment";
	
	/* Variables */
	public static final String LABEL_TITLE = "labelTitle";
	public static final String LABEL_MESSAGE = "labelMessage";
	public static final String LABEL_DISMISS = "labelDismiss";
	
	private String labelTitle;
	private String labelMessage;
	private String labelDismiss;
	
	
	
	/**
	 * Empty constructor required
	 */
	public WarningDialogFragment() {}
	
	
	
	/* Overridden methods */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		labelTitle = getArguments().getString(LABEL_TITLE);
		labelMessage = getArguments().getString(LABEL_MESSAGE);
		labelDismiss = getArguments().getString(LABEL_DISMISS);
	}
	
	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(labelTitle)
			.setMessage(labelMessage)
			.setNeutralButton(labelDismiss, null);
		
		return builder.create();
	}
	
	
	
	/* Static methods */
	
	/**
	 * Method shows a WarningDialogFragment using the arguments provided.
	 * @param activity
	 * @param title
	 * @param message
	 * @param dismiss
	 */
	public static void showWarningDialog(FragmentActivity activity, String title, String message,
			String dismiss) {
		WarningDialogFragment dialog = new WarningDialogFragment();
		Bundle args = new Bundle();
		args.putString(WarningDialogFragment.LABEL_TITLE, title);
		args.putString(WarningDialogFragment.LABEL_MESSAGE, message);
		args.putString(WarningDialogFragment.LABEL_DISMISS, dismiss);
		dialog.setArguments(args);
		FragmentManager fm = activity.getSupportFragmentManager();
		dialog.show(fm, "WarningDialogFragment");
	}

}
