package com.slotmachine.android;

import com.jdroid.android.dialog.AbstractAboutDialogFragment;
import com.jdroid.android.utils.AndroidUtils;
import com.jdroid.slotmachine.lite.R;

/**
 * 
 * @author Maxi Rosson
 */
public class AboutDialogFragment extends AbstractAboutDialogFragment {
	
	/**
	 * @see com.jdroid.android.dialog.AbstractAboutDialogFragment#getAppName()
	 */
	@Override
	protected String getAppName() {
		return getString(AndroidApplication.get().getAndroidApplicationContext().isFreeApp() ? R.string.appNameFree
				: R.string.appName);
	}
	
	/**
	 * @see com.jdroid.android.dialog.AbstractAboutDialogFragment#getShareEmailSubject()
	 */
	@Override
	protected String getShareEmailSubject() {
		return getString(R.string.shareSubject);
	}
	
	/**
	 * @see com.jdroid.android.dialog.AbstractAboutDialogFragment#getShareEmailContent()
	 */
	@Override
	protected String getShareEmailContent() {
		return getString(R.string.shareContent, AndroidUtils.getPackageName());
	}
	
	/**
	 * @see com.jdroid.android.dialog.AbstractAboutDialogFragment#getContactUsEmail()
	 */
	@Override
	protected String getContactUsEmail() {
		return ApplicationContext.get().getContactUsEmail();
	}
}
