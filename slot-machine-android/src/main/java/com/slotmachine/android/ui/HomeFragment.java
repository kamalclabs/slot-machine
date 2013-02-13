package com.slotmachine.android.ui;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.jdroid.android.fragment.AbstractFragment;
import com.jdroid.android.utils.BitmapUtils;
import com.jdroid.android.utils.SharedPreferencesUtils;
import com.jdroid.java.collections.Lists;
import com.jdroid.java.utils.IdGenerator;
import com.jdroid.slotmachine.lite.R;
import com.slotmachine.android.wheel.OnWheelScrollListener;
import com.slotmachine.android.wheel.WheelView;
import com.slotmachine.android.wheel.adapters.AbstractWheelAdapter;
import com.slotmachine.domain.Pack;
import com.slotmachine.domain.Slot;

/**
 * 
 * @author Maxi Rosson
 */
public class HomeFragment extends AbstractFragment {
	
	private final static String CREDITS_KEY = "credits";
	private final static Long INITIAL_CREDITS = 50L;
	
	private WheelView wheel1;
	private WheelView wheel2;
	private WheelView wheel3;
	private WheelView wheel4;
	private WheelView wheel5;
	
	private Pack pack;
	private Long totalCredits;
	
	/**
	 * @see com.jdroid.android.fragment.AbstractFragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		totalCredits = SharedPreferencesUtils.loadPreferenceAsLong(CREDITS_KEY, INITIAL_CREDITS);
		
		int packId = IdGenerator.getRandomIntId() % 3;
		
		List<Slot> slots = Lists.newArrayList();
		if (packId == 1) {
			slots.add(new Slot(R.drawable.simpsons1));
			slots.add(new Slot(R.drawable.simpsons2));
			slots.add(new Slot(R.drawable.simpsons3));
			slots.add(new Slot(R.drawable.simpsons4));
			slots.add(new Slot(R.drawable.simpsons5));
			slots.add(new Slot(R.drawable.simpsons6));
			pack = new Pack(slots);
		} else if (packId == 2) {
			slots.add(new Slot(R.drawable.simpsons7));
			slots.add(new Slot(R.drawable.simpsons8));
			slots.add(new Slot(R.drawable.simpsons9));
			slots.add(new Slot(R.drawable.simpsons10));
			slots.add(new Slot(R.drawable.simpsons11));
			slots.add(new Slot(R.drawable.simpsons12));
			pack = new Pack(slots);
		} else {
			slots.add(new Slot(R.drawable.simpsons13));
			slots.add(new Slot(R.drawable.simpsons14));
			slots.add(new Slot(R.drawable.simpsons15));
			slots.add(new Slot(R.drawable.simpsons16));
			slots.add(new Slot(R.drawable.simpsons17));
			slots.add(new Slot(R.drawable.simpsons18));
		}
		pack = new Pack(slots);
	};
	
	/**
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup,
	 *      android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.home_fragment, null);
	}
	
	/**
	 * @see com.jdroid.android.fragment.AbstractFragment#onViewCreated(android.view.View, android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		final TextView credits = findView(R.id.credits);
		credits.setText(getString(R.string.credits, totalCredits));
		
		final Button play = findView(R.id.play);
		play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				play.setEnabled(false);
				mixWheel(wheel1, 1000);
				mixWheel(wheel2, 1500);
				mixWheel(wheel3, 2000);
				mixWheel(wheel4, 2500);
				mixWheel(wheel5, 3000);
			}
		});
		
		final TextView gameResult = findView(R.id.gameResult);
		wheel1 = initWheel(R.id.slot1, null);
		wheel2 = initWheel(R.id.slot2, null);
		wheel3 = initWheel(R.id.slot3, null);
		wheel4 = initWheel(R.id.slot4, null);
		wheel5 = initWheel(R.id.slot5, new OnWheelScrollListener() {
			
			@Override
			public void onScrollingStarted(WheelView wheel) {
			}
			
			@Override
			public void onScrollingFinished(WheelView wheel) {
				int newCredits = getNewCredits();
				if (newCredits > 0) {
					gameResult.setText(getString(R.string.creditsWon, newCredits));
					gameResult.setVisibility(View.VISIBLE);
					totalCredits += newCredits;
				} else {
					totalCredits -= 1;
					gameResult.setVisibility(View.INVISIBLE);
				}
				credits.setText(getString(R.string.credits, totalCredits));
				play.setEnabled(true);
			}
		});
	}
	
	/**
	 * @see com.jdroid.android.fragment.AbstractFragment#onStop()
	 */
	@Override
	public void onStop() {
		super.onStop();
		SharedPreferencesUtils.savePreference(CREDITS_KEY, totalCredits);
	}
	
	/**
	 * Initializes wheel
	 * 
	 * @param id the wheel widget Id
	 */
	private WheelView initWheel(int id, OnWheelScrollListener scrolledListener) {
		WheelView wheel = findView(id);
		wheel.setViewAdapter(new SlotMachineAdapter(getActivity(), pack));
		wheel.setCurrentItem((int)(Math.random() * 10));
		if (scrolledListener != null) {
			wheel.addScrollingListener(scrolledListener);
		}
		wheel.setCyclic(true);
		wheel.setEnabled(false);
		return wheel;
	}
	
	private int getNewCredits() {
		int credits = 0;
		
		int value1 = wheel1.getCurrentItem();
		int value2 = wheel2.getCurrentItem();
		int value3 = wheel3.getCurrentItem();
		int value4 = wheel4.getCurrentItem();
		int value5 = wheel5.getCurrentItem();
		
		if ((value1 == value2) && (value2 == value3) && (value3 == value4) && (value4 == value5)) {
			credits = credits + 75;
		} else if ((value1 == value2) && (value2 == value3) && (value3 == value4)) {
			credits = credits + 15;
		} else if ((value2 == value3) && (value3 == value4) && (value4 == value5)) {
			credits = credits + 15;
		} else if ((value1 == value2) && (value2 == value3)) {
			credits = credits + 5;
		} else if ((value2 == value3) && (value3 == value4)) {
			credits = credits + 5;
		} else if ((value3 == value4) && (value4 == value5)) {
			credits = credits + 5;
		}
		return credits;
	}
	
	/**
	 * Mixes wheel
	 * 
	 * @param id the wheel id
	 */
	private void mixWheel(WheelView wheelView, int scrollingDuration) {
		wheelView.scroll(-350 + (int)(Math.random() * 50), scrollingDuration);
	}
	
	/**
	 * Slot machine adapter
	 */
	private class SlotMachineAdapter extends AbstractWheelAdapter {
		
		// Image size
		private static final int IMAGE_WIDTH = 80;
		private static final int IMAGE_HEIGHT = 80;
		
		private Pack pack;
		
		// Cached images
		private List<SoftReference<Bitmap>> images;
		private Context context;
		// Layout params for image view
		private LayoutParams params = new LayoutParams(IMAGE_WIDTH, IMAGE_HEIGHT);
		
		public SlotMachineAdapter(Context context, Pack pack) {
			this.context = context;
			this.pack = pack;
			images = new ArrayList<SoftReference<Bitmap>>(pack.getSlots().size());
			for (Slot slot : pack.getSlots()) {
				images.add(new SoftReference<Bitmap>(loadImage(slot)));
			}
		}
		
		/**
		 * Loads image from resources
		 */
		private Bitmap loadImage(Slot slot) {
			return BitmapUtils.toBitmap(slot.getImageResId(), IMAGE_WIDTH, IMAGE_HEIGHT);
		}
		
		@Override
		public int getItemsCount() {
			return pack.getSlots().size();
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
				imageView = new ImageView(context);
				imageView.setLayoutParams(params);
			}
			SoftReference<Bitmap> bitmapRef = images.get(index);
			Bitmap bitmap = bitmapRef.get();
			if (bitmap == null) {
				bitmap = loadImage(pack.getSlots().get(index));
				images.set(index, new SoftReference<Bitmap>(bitmap));
			}
			imageView.setImageBitmap(bitmap);
			
			return imageView;
		}
	}
}
