package com.gildedrose;

public class ConjuredItem extends UpdatableItem {

	protected int getDegredationCoeff() { return 2; }
	
	public ConjuredItem(String name, int sellIn, int quality) {
		super(name, sellIn, quality);
	}
}
