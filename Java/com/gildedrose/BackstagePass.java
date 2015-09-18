package com.gildedrose;

public class BackstagePass extends UpdatableItem {

	public BackstagePass(String name, int sellIn, int quality) {
		super(name, sellIn, quality);
		// TODO Auto-generated constructor stub
	}

	public void update(){
		if(this.sellIn<=0){
			this.quality = 0;    
        }
        else if(this.sellIn <= 10 && this.sellIn > 5){
            this.quality += 2* qualityDec;
            
        }
        else if (this.sellIn <= 5 && this.sellIn > 0){
        	this.quality += 3* qualityDec;
        }
		
	}
	
}
