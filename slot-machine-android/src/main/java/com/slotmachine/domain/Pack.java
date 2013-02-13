package com.slotmachine.domain;

import java.util.List;

/**
 * 
 * @author Maxi Rosson
 */
public class Pack {
	
	private List<Slot> slots;
	
	public Pack(List<Slot> slots) {
		this.slots = slots;
	}
	
	/**
	 * @return the slots
	 */
	public List<Slot> getSlots() {
		return slots;
	}
	
}
