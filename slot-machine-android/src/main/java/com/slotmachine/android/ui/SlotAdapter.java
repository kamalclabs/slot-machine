package com.slotmachine.android.ui;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.jdroid.android.utils.BitmapUtils;
import com.jdroid.slotmachine.lite.R;
import com.slotmachine.android.wheel.adapters.AbstractWheelAdapter;
import com.slotmachine.domain.Slot;

/**
 * 
 * @author Maxi Rosson
 */
public class SlotAdapter extends AbstractWheelAdapter {
	
	private static final int IMAGE_WIDTH = 100;
	private static final int IMAGE_HEIGHT = 100;
	
	private List<Slot> slots;
	private LayoutInflater inflater;
	
	// Cached images
	private List<SoftReference<Bitmap>> images;
	
	public SlotAdapter(Context context, List<Slot> slots) {
		this.slots = slots;
		images = new ArrayList<SoftReference<Bitmap>>(slots.size());
		for (Slot slot : slots) {
			images.add(new SoftReference<Bitmap>(loadImage(slot)));
		}
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	/**
	 * Loads image from resources
	 */
	private Bitmap loadImage(Slot slot) {
		return BitmapUtils.toBitmap(slot.getImageResId(), IMAGE_WIDTH, IMAGE_HEIGHT);
	}
	
	@Override
	public int getItemsCount() {
		return slots.size();
	}
	
	/**
	 * @see com.slotmachine.android.wheel.adapters.WheelViewAdapter#getItem(int, android.view.View,
	 *      android.view.ViewGroup)
	 */
	@Override
	public View getItem(int index, View cachedView, ViewGroup parent) {
		ImageView imageView;
		if (cachedView != null) {
			imageView = (ImageView)cachedView;
		} else {
			imageView = (ImageView)inflater.inflate(R.layout.slot_wheel_item, parent, false);
		}
		SoftReference<Bitmap> bitmapRef = images.get(index);
		Bitmap bitmap = bitmapRef.get();
		if (bitmap == null) {
			bitmap = loadImage(slots.get(index));
			images.set(index, new SoftReference<Bitmap>(bitmap));
		}
		imageView.setImageBitmap(bitmap);
		
		return imageView;
	}
}
