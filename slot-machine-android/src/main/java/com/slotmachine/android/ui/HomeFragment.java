package com.slotmachine.android.ui;

import java.util.List;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.jdroid.android.fragment.AbstractFragment;
import com.jdroid.android.utils.SharedPreferencesUtils;
import com.jdroid.java.collections.Lists;
import com.jdroid.java.utils.IdGenerator;
import com.jdroid.slotmachine.lite.R;
import com.slotmachine.android.wheel.OnWheelScrollListener;
import com.slotmachine.android.wheel.WheelView;
import com.slotmachine.domain.Slot;
import com.slotmachine.domain.SlotMachine;

/**
 * 
 * @author Maxi Rosson
 */
public class HomeFragment extends AbstractFragment {
	
	private final static String CREDITS_KEY = "credits";
	private final static Long INITIAL_CREDITS = 1000L;
	
	private WheelView wheel1;
	private WheelView wheel2;
	private WheelView wheel3;
	private WheelView wheel4;
	private WheelView wheel5;
	
	private SlotMachine slotMachine;
	private Long totalCredits;
	
	private OnWheelScrollListener listener;
	
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
			slotMachine = new SlotMachine(slots);
		} else if (packId == 2) {
			slots.add(new Slot(R.drawable.simpsons7));
			slots.add(new Slot(R.drawable.simpsons8));
			slots.add(new Slot(R.drawable.simpsons9));
			slots.add(new Slot(R.drawable.simpsons10));
			slots.add(new Slot(R.drawable.simpsons11));
			slots.add(new Slot(R.drawable.simpsons12));
			slotMachine = new SlotMachine(slots);
		} else {
			slots.add(new Slot(R.drawable.simpsons13));
			slots.add(new Slot(R.drawable.simpsons14));
			slots.add(new Slot(R.drawable.simpsons15));
			slots.add(new Slot(R.drawable.simpsons16));
			slots.add(new Slot(R.drawable.simpsons17));
			slots.add(new Slot(R.drawable.simpsons18));
		}
		slotMachine = new SlotMachine(slots);
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
		
		final TextView gameResult = findView(R.id.gameResult);
		
		final Button play = findView(R.id.play);
		play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gameResult.setVisibility(View.INVISIBLE);
				play.setEnabled(false);
				mixWheel(wheel1, 1000);
				mixWheel(wheel2, 1500);
				mixWheel(wheel3, 2000);
				mixWheel(wheel4, 2500);
				mixWheel(wheel5, 3000);
			}
		});
		
		wheel1 = initWheel(R.id.slot1, 1, null);
		wheel2 = initWheel(R.id.slot2, 2, null);
		wheel3 = initWheel(R.id.slot3, 3, null);
		wheel4 = initWheel(R.id.slot4, 4, null);
		wheel5 = initWheel(R.id.slot5, 5, listener = new OnWheelScrollListener() {
			
			@Override
			public void onScrollingStarted(WheelView wheel) {
			}
			
			@Override
			public void onScrollingFinished(WheelView wheel) {
				int newCredits = slotMachine.getNewCredits(wheel1.getCurrentItem(), wheel2.getCurrentItem(),
					wheel3.getCurrentItem(), wheel4.getCurrentItem(), wheel5.getCurrentItem());
				if (newCredits > 0) {
					gameResult.setText(getString(R.string.creditsWon, newCredits));
					gameResult.setVisibility(View.VISIBLE);
					totalCredits += newCredits;
				} else {
					totalCredits -= 5;
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
	 * @see com.jdroid.android.fragment.AbstractFragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		wheel5.removeScrollingListener(listener);
	}
	
	/**
	 * Initializes wheel
	 * 
	 * @param id the wheel widget Id
	 */
	private WheelView initWheel(int id, int wheelNumber, OnWheelScrollListener scrolledListener) {
		WheelView wheel = findView(id);
		wheel.setViewAdapter(new SlotAdapter(getActivity(), slotMachine.getSlots(wheelNumber)));
		wheel.setCurrentItem((int)(Math.random() * 10));
		if (scrolledListener != null) {
			wheel.addScrollingListener(scrolledListener);
		}
		wheel.setCyclic(true);
		wheel.setEnabled(false);
		return wheel;
	}
	
	/**
	 * Mixes wheel
	 * 
	 * @param id the wheel id
	 */
	private void mixWheel(WheelView wheelView, int scrollingDuration) {
		wheelView.scroll(-350 + (int)(Math.random() * 50), scrollingDuration);
	}
}
