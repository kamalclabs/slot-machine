package com.slotmachine.android.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.jdroid.android.activity.FragmentContainerActivity;

/**
 * 
 * @author Maxi Rosson
 */
public class HomeActivity extends FragmentContainerActivity {
	
	/**
	 * @see com.jdroid.android.activity.FragmentContainerActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getSupportActionBar().setDisplayUseLogoEnabled(false);
	}
	
	/**
	 * @see com.jdroid.android.activity.FragmentContainerActivity#createNewFragment()
	 */
	@Override
	protected Fragment createNewFragment() {
		return new HomeFragment();
	}
	
}
