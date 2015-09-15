package com.gildedrose;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GildedRoseTest {
    
	Item[] items;
    
	@Before
	public void setUp() throws Exception {
		items = new Item[] {
                new Item("+5 Dexterity Vest", 10, 20), //
                new Item("Aged Brie", 2, 0), //
                new Item("Elixir of the Mongoose", 5, 7), //
                new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
                new Item("Sulfuras, Hand of Ragnaros", -1, 80),
                new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 40),
                // this conjured item does not work properly yet
                new Item("Conjured Mana Cake", 3, 6) };
	}

	private Item findItem (String itemName)
	{
        for(Item i : items){
        	if(i.name.equals(itemName)){
        	    return i;
        	}
        } 	
        fail();
        return new Item("New Item",0,0);
	}
	
    @Test
    public void ItemCreationWorksTest() {
        items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertTrue(items.length == 1);
    }

    @Test
    public void sulfurasExistsAndIs80ValTest() {
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        for(Item i : items){
        	if(i.name.equals("Sulfuras, Hand of Ragnaros")){
        		assertTrue(i.quality==80);
        	}
        }
        //assertTrue()
    }
    
    @Test
    public void lowerValuesTest() {
    	GildedRose app = new GildedRose(items);  
    	
    	for ( Item i : items){
    		if( i.name.equals("Elixir of the Mongoose")){
    			int temp  = i.quality;
               app.updateQuality();
               assertTrue( temp > i.quality);
    		}
    	}
    }
    
    @Test
    public void ageBrieQualityTest() {
    	GildedRose app = new GildedRose(items);
    	
    	for ( Item i : items) {
    		if ( i.name.equals("Aged Brie")) {
    			int temp = i.quality;
    			app.updateQuality();
    			assertTrue( temp < i.quality);
    		}
    	}
    }
    
   @Test
   public void ageBrieSellInZeroTest() {
	   GildedRose app = new GildedRose(items);
	   
	   for ( Item i : items) {
   		if ( i.name.equals("Aged Brie")) {
   			int temp = i.quality;
   			while (i.sellIn >= 0) {
   				app.updateQuality();
   			}
   			assertTrue( temp < i.quality);
   		}
   	}
   }
}


/*

Pretty simple, right? Well this is where it gets interesting:

- Once the sell by date has passed, Quality degrades twice as fast
- The Quality of an item is never negative
- "Aged Brie" actually increases in Quality the older it gets
- The Quality of an item is never more than 50
- "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
- "Backstage passes", like aged brie, increases in Quality as it's SellIn value approaches;
Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
Quality drops to 0 after the concert

We have recently signed a supplier of conjured items. This requires an update to our system:

- "Conjured" items degrade in Quality twice as fast as normal items
*/