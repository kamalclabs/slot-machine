package com.slotmachine.domain;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import android.util.Log;
import com.jdroid.java.collections.Lists;
import com.jdroid.java.collections.Maps;

/**
 * 
 * @author Maxi Rosson
 */
public class SlotMachine {
	
	private static final String TAG = SlotMachine.class.getSimpleName();
	private static final int WHEELS = 5;
	private static final int SLOTS_PER_WHEEL = 6;
	
	private Map<Integer, List<Slot>> slotsMap = Maps.newHashMap();
	
	public SlotMachine(List<Slot> slots) {
		for (int i = 1; i <= WHEELS; i++) {
			List<Slot> list = Lists.newArrayList(slots);
			Collections.shuffle(list);
			slotsMap.put(i, list);
			Log.d(TAG, "Wheel " + i + ": " + list);
		}
	}
	
	public List<Slot> getSlots(int wheelNumber) {
		return slotsMap.get(wheelNumber);
	}
	
	public int getNewCredits(int index1, int index2, int index3, int index4, int index5) {
		
		int mainLineCredits = getLineCredits(index1, index2, index3, index4, index5);
		Log.d(TAG, "Main line credits: " + mainLineCredits);
		
		int upperLineCredits = getLineCredits(getUpIndex(index1), getUpIndex(index2), getUpIndex(index3),
			getUpIndex(index4), getUpIndex(index5));
		Log.d(TAG, "Up line credits: " + upperLineCredits);
		
		int downLineCredits = getLineCredits(getDownIndex(index1), getDownIndex(index2), getDownIndex(index3),
			getDownIndex(index4), getDownIndex(index5));
		Log.d(TAG, "Down line credits: " + downLineCredits);
		
		return mainLineCredits + upperLineCredits + downLineCredits;
	}
	
	private int getUpIndex(int index) {
		return index == 0 ? SLOTS_PER_WHEEL - 1 : (index - 1) % SLOTS_PER_WHEEL;
	}
	
	private int getDownIndex(int index) {
		return (index + 1) % SLOTS_PER_WHEEL;
	}
	
	private int getLineCredits(int index1, int index2, int index3, int index4, int index5) {
		
		Log.d(TAG, "Indexes: " + index1 + " - " + index2 + " - " + index3 + " - " + index4 + " - " + index5);
		
		int credits = 0;
		
		Slot slot1 = getSlots(1).get(index1);
		Slot slot2 = getSlots(2).get(index2);
		Slot slot3 = getSlots(3).get(index3);
		Slot slot4 = getSlots(4).get(index4);
		Slot slot5 = getSlots(5).get(index5);
		Log.d(TAG, "Slots: " + slot1 + " - " + slot2 + " - " + slot3 + " - " + slot4 + " - " + slot5);
		
		// List<Slot> mainLineSlots = Lists.newArrayList();
		// mainLineSlots.add(getSlots(1).get(index1));
		// mainLineSlots.add(getSlots(2).get(index2));
		// mainLineSlots.add(getSlots(3).get(index3));
		// mainLineSlots.add(getSlots(4).get(index4));
		// mainLineSlots.add(getSlots(5).get(index5));
		
		// int count = 0;
		// Slot lastSlot = null;
		// for (Slot each : mainLineSlots) {
		// if (lastSlot == null) {
		// lastSlot = each;
		// } else if (lastSlot.equals(each)){
		// count++;
		// } else if (){
		// count =
		// }
		// }
		
		if (slot1.equals(slot2) && slot2.equals(slot3) && slot3.equals(slot4) && slot4.equals(slot5)) {
			credits = credits + 200;
		} else if (slot1.equals(slot2) && slot2.equals(slot3) && slot3.equals(slot4)) {
			credits = credits + 50;
		} else if (slot2.equals(slot3) && slot3.equals(slot4) && slot4.equals(slot5)) {
			credits = credits + 50;
		} else if (slot1.equals(slot2) && slot2.equals(slot3)) {
			credits = credits + 10;
		} else if (slot2.equals(slot3) && slot3.equals(slot4)) {
			credits = credits + 10;
		} else if (slot3.equals(slot4) && slot4.equals(slot5)) {
			credits = credits + 10;
		}
		return credits;
	}
}
