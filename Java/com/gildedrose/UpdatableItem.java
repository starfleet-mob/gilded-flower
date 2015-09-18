package com.gildedrose;

import com.gildedrose.Item;

public class UpdatableItem extends Item {

	protected final  int qualityDec = 1;

	public UpdatableItem(String name, int sellIn, int quality) {
		super(name, sellIn, quality);
		this.quality = Math.max(0, this.quality);
		
	}
	public void update(){
		if( this.sellIn <=0){
			this.quality -= 2  * qualityDec;
		}
		else{
			this.quality -= qualityDec;
		}
		this.sellIn--;
		this.quality = Math.max(0, this.quality);

	}

}
