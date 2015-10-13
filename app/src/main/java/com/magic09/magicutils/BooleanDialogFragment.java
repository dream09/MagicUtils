package com.magic09.magicutils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;



/**
 * BooleanDialogFragment provides a DialogFragment with a positive/negative dialog
 * that returns the result via an interface.
 * @author dream09
 *
 */
public class BooleanDialogFragment extends DialogFragment {
	public static final String TAG = "BooleanDialogFragment";
	
	/* Variables */
	public static final String LABEL_POSITIVE = "labelPositive";
	public static final String LABEL_NEGATIVE = "labelNegative";
	public static final String LABEL_TITLE = "labelTitle";
	public static final String LABEL_MESSAGE = "labelMessage";
	public static final String RETURN_KEY = "returnKey";
	
	private String labelPositive;
	private String labelNegative;
	private String labelTitle;
	private String labelMessage;
	private int returnKey;
	private BooleanDialogFragmentListener dialogListener;
	
	
	
	/**
	 * Interface to pass data back.
	 * @author dream09
	 *
	 */
	public interface BooleanDialogFragmentListener {
		void onBooleanDialogFragmentResult(int key, boolean result);
	}
	 
	
	
	/**
	 * Empty constructor required
	 */
	public BooleanDialogFragment() {}
	
	
	
	/* Overridden methods */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		labelPositive = getArguments().getString(LABEL_POSITIVE);
		labelNegative = getArguments().getString(LABEL_NEGATIVE);
		labelTitle = getArguments().getString(LABEL_TITLE);
		labelMessage = getArguments().getString(LABEL_MESSAGE);
		returnKey = getArguments().getInt(RETURN_KEY);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		// Ensure container has implemented the interface
		try {
			dialogListener = (BooleanDialogFragmentListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement BooleanDialogFragmentListener");
		}
	}
	
	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(labelTitle)
			.setMessage(labelMessage)
			.setPositiveButton(labelPositive, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialogListener.onBooleanDialogFragmentResult(returnKey, true);
				}
			})
			.setNegativeButton(labelNegative, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialogListener.onBooleanDialogFragmentResult(returnKey, false);
				}
			});
		
		return builder.create();
	}
	
	@Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
		
		dialogListener.onBooleanDialogFragmentResult(returnKey, false);
	}
	
	
	
	/* Static methods */
	
	/**
	 * Method shows a BooleanDialogFragment using the arguments provided.
	 * @param activity
	 * @param title
	 * @param message
	 * @param positive
	 * @param negative
	 * @param key
	 */
	public static void showBooleanDialog(FragmentActivity activity, String title, String message,
			String positive, String negative, int key) {
		BooleanDialogFragment dialog = new BooleanDialogFragment();
		Bundle args = new Bundle();
		args.putString(BooleanDialogFragment.LABEL_TITLE, title);
		args.putString(BooleanDialogFragment.LABEL_MESSAGE, message);
		args.putString(BooleanDialogFragment.LABEL_POSITIVE, positive);
		args.putString(BooleanDialogFragment.LABEL_NEGATIVE, negative);
		args.putInt(BooleanDialogFragment.RETURN_KEY, key);
		dialog.setArguments(args);
		FragmentManager fm = activity.getSupportFragmentManager();
		dialog.show(fm, "BooleanDialogFragment");
	}
	
}
