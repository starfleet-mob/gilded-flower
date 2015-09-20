package com.gildedrose;

import com.gildedrose.Item;

public class UpdatableItem extends Item {

	// The rate at which the item degrades.
	protected int getDegredationCoeff() { return 1; }

	public UpdatableItem(String name, int sellIn, int quality) {
		super(name, sellIn, quality);
		this.quality = Math.max(0, this.quality);
	}

	public void update(){
		if( this.sellIn <=0){
			this.quality -= 2*this.getDegredationCoeff();
		}
		else{
			this.quality -= this.getDegredationCoeff();
		}
		this.sellIn--;
		this.quality = Math.max(0, this.quality);
	}
}
