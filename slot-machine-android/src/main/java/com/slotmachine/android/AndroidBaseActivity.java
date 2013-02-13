package com.slotmachine.android;

import android.app.Activity;
import com.actionbarsherlock.view.MenuItem;
import com.jdroid.android.activity.BaseActivity;
import com.jdroid.slotmachine.lite.R;

/**
 * 
 * @author Maxi Rosson
 */
public class AndroidBaseActivity extends BaseActivity {
	
	public AndroidBaseActivity(Activity activity) {
		super(activity);
	}
	
	/**
	 * @see com.jdroid.android.activity.BaseActivity#getMenuResourceId()
	 */
	@Override
	public int getMenuResourceId() {
		return R.menu.menu;
	}
	
	/**
	 * @see com.jdroid.android.activity.BaseActivity#onOptionsItemSelected(com.actionbarsherlock.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.aboutItem:
				new AboutDialogFragment().show(getActivity());
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
