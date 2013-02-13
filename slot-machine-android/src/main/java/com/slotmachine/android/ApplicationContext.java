package com.slotmachine.android;

import com.jdroid.android.context.DefaultApplicationContext;
import com.jdroid.java.utils.PropertiesUtils;

/**
 * 
 * @author Maxi Rosson
 */
public class ApplicationContext extends DefaultApplicationContext {
	
	private static final ApplicationContext INSTANCE = new ApplicationContext();
	
	/**
	 * @return The {@link ApplicationContext} instance
	 */
	public static ApplicationContext get() {
		return INSTANCE;
	}
	
	private String contactUsEmail;
	
	public ApplicationContext() {
		contactUsEmail = PropertiesUtils.getStringProperty("mail.contact");
	}
	
	public String getContactUsEmail() {
		return contactUsEmail;
	}
}
