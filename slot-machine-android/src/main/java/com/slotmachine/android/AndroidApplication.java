package com.slotmachine.android;

import android.app.Activity;
import android.support.v4.app.Fragment;
import com.google.inject.AbstractModule;
import com.jdroid.android.AbstractApplication;
import com.jdroid.android.activity.BaseActivity;
import com.jdroid.android.context.DefaultApplicationContext;
import com.jdroid.android.fragment.BaseFragment;
import com.slotmachine.android.ui.HomeActivity;

/**
 * 
 * @author Maxi Rosson
 */
public class AndroidApplication extends AbstractApplication {
	
	public static AndroidApplication get() {
		return (AndroidApplication)AbstractApplication.INSTANCE;
	}
	
	/**
	 * @see com.jdroid.android.AbstractApplication#createAndroidModule()
	 */
	@Override
	protected AbstractModule createAndroidModule() {
		return new AndroidModule();
	}
	
	/**
	 * @see com.jdroid.android.AbstractApplication#getHomeActivityClass()
	 */
	@Override
	public Class<? extends Activity> getHomeActivityClass() {
		return HomeActivity.class;
	}
	
	/**
	 * @see com.jdroid.android.AbstractApplication#createApplicationContext()
	 */
	@Override
	protected DefaultApplicationContext createApplicationContext() {
		return ApplicationContext.get();
	}
	
	/**
	 * @see com.jdroid.android.AbstractApplication#createBaseActivity(android.app.Activity)
	 */
	@Override
	public BaseActivity createBaseActivity(Activity activity) {
		return new AndroidBaseActivity(activity);
	}
	
	/**
	 * @see com.jdroid.android.AbstractApplication#createBaseFragment(android.support.v4.app.Fragment)
	 */
	@Override
	public BaseFragment createBaseFragment(Fragment fragment) {
		return new AndroidBaseFragment(fragment);
	}
}
