package com.gildedrose;

import com.gildedrose.Item;

public class UpdatableItem extends Item {

	public UpdatableItem(String name, int sellIn, int quality) {
		super(name, sellIn, quality);
		this.quality = Math.max(0, this.quality);
		
	}
	public void update(){
		if( this.sellIn <=0){
			this.quality -= 2;
		}
		else{
			this.quality -= 1;
		}
		this.sellIn--;
		this.quality = Math.max(0, this.quality);

	}

}
