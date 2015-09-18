package com.gildedrose;

public class AgedBrie extends UpdatableItem {

	public AgedBrie(String name, int sellIn, int quality) {
		super(name, sellIn, quality);
		// TODO Auto-generated constructor stub
	}
	
	public void update() {
		this.sellIn--;
		this.quality = Math.min(++this.quality, 50);
	}

}
