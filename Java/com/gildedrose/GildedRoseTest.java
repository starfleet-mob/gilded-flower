package com.gildedrose;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class GildedRoseTest {
    
	UpdatableItem[] items;
    
	@Before
	public void setUp() throws Exception {
		items = new UpdatableItem[] {
                new UpdatableItem("+5 Dexterity Vest", 10, 20), //
                new AgedBrie("Aged Brie", 2, 0), //
                new UpdatableItem("Elixir of the Mongoose", 5, 7), //
                new Sulfuras("Sulfuras, Hand of Ragnaros", 0, 50), //
                new Sulfuras("Sulfuras, Hand of Ragnaros", -1, 50),
                new BackstagePass("Backstage passes to a TAFKAL80ETC concert", 15, 20),
                new BackstagePass("Backstage passes to a TAFKAL80ETC concert", 10, 49),
                new BackstagePass("Backstage passes to a TAFKAL80ETC concert", 5, 49),
                new BackstagePass("Backstage passes to a TAFKAL80ETC concert", 5, 40),
                // this conjured item does not work properly yet
                new UpdatableItem("Conjured Mana Cake", 3, 6) };
	}

	
    @Test
    public void ItemCreationWorksTest() {
        items = new UpdatableItem[] { new UpdatableItem("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertTrue(items.length == 1);
    }

    // Tests the requirement that "Sulfuras", being a legendary item, never has to be sold or decreases in Quality.
    @Test
    public void sulfurasBehaviorTest() {
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        for(UpdatableItem i : items){
        	if(i.name.equals("Sulfuras, Hand of Ragnaros")){
        		assertTrue(i.quality==50);
        		int sellIn = i.sellIn;
        		app.updateQuality();
        		assertTrue(i.sellIn == sellIn);

        	}
        }
        
        
    }
     
    @Test
    public void lowerValuesTest() {
    	
    	String [] decreaseQualityList = {"+5 Dexterity Vest",
    									 "Elixir of the Mongoose",
    									 //*** TODO: Figure out what's weird about this :D *** 
    									 //"Backstage passes to a TAFKAL80ETC concert",
    									 "Conjured Mana Cake"};
    	
    	HashMap<String, Integer> decQA = new HashMap<String, Integer>();
    	
    	GildedRose app = new GildedRose(items);  
    	
    	for (UpdatableItem i : items){
    		if (Arrays.asList(decreaseQualityList).contains(i.name)){    			
    			decQA.put(i.name, i.quality);                                
    		}        
    	}
    	
    	app.updateQuality();
    	
    	for (UpdatableItem i : items){
    		if (Arrays.asList(decreaseQualityList).contains(i.name)){    			
    			assertTrue(decQA.get(i.name) >= i.quality);
    		}  	
    	}
    }
    
 // -test duplicate for new value
    @Test
    public void lowerValuesTest2() {
    	GildedRose app = new GildedRose(items);  
    	
    	for ( UpdatableItem i : items){
    		if( i.name.equals("+5 Dexterity Vest")){
    			int temp  = i.quality;
               app.updateQuality();
               assertTrue( temp > i.quality);
    		}
    	}
    }    
// -end test    
    
    @Test
    public void ageBrieQualityTest() {
    	GildedRose app = new GildedRose(items);
    	
    	for ( UpdatableItem i : items) {
    		if ( i.name.equals("Aged Brie")) {
    			int temp = i.quality;
    			app.updateQuality();
    			assertTrue( temp < i.quality);
    		}
    	}
    }
    
    // Tests "Aged Brie" actually increases in Quality the older it gets
   @Test
   public void ageBriebehaviorTest() {
	   GildedRose app = new GildedRose(items);
	   
	   for ( UpdatableItem i : items) {
   		if ( i.name.equals("Aged Brie")) {
   			int temp = i.quality;
   			while (i.sellIn >= 0) {
   				app.updateQuality();
   			}
   			assertTrue( temp < i.quality);	
   		}
   	}
   }

  @Test
  public void itemToStringTest() {
	  UpdatableItem i = new UpdatableItem("TestName", 1, 2);
	  assertTrue(i.toString().equals("TestName, 1, 2"));
  }
  
  @Test
  public void noNegativeSellinTest() {
	  GildedRose app = new GildedRose(items);
	  
	  // Find max sellin.
	  int maxSellin = -1;
	  for (UpdatableItem i: items) {
	    maxSellin = Math.max(maxSellin, i.sellIn);
	  }
	  
	  // Update quality enough to ensure things would go negative.
	  for (int i = 0; i <= maxSellin+1; i++) {
		  app.updateQuality();
	  }

	  // Make sure all item qualities are never negative.
	  for (UpdatableItem i: items) {
		  assertTrue(i.quality >= 0);
	  }
  }
  
  //tests requirement:
  //Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less.
  // Also tests that the quality drops to 0 after the concert.
  @Test
  public void testBackstagePassQualityDecreasesDoubly() {
	  BackstagePass i = new BackstagePass("Backstage passes to a TAFKAL80ETC concert", 11, 10);
	  GildedRose app = new GildedRose(new UpdatableItem[]{i});
	  assertTrue(i.sellIn==11);
	  assertTrue(i.quality==10);
	  app.updateQuality();
	  assertTrue(i.sellIn==10);
	  assertTrue(i.quality==11);
	  app.updateQuality();
	  assertTrue(i.sellIn==9);
	  assertTrue(i.quality==13);
	  app.updateQuality();
	  assertTrue(i.sellIn==8);
	  assertTrue(i.quality==15);
	  app.updateQuality();
	  assertTrue(i.sellIn==7);
	  assertTrue(i.quality==17);
	  app.updateQuality();
	  assertTrue(i.sellIn==6);
	  assertTrue(i.quality==19);
	  app.updateQuality();
	  assertTrue(i.sellIn==5);
	  assertTrue(i.quality==21);
	  app.updateQuality();
	  assertTrue(i.sellIn==4);
	  assertTrue(i.quality==24);
	  app.updateQuality();
	  assertTrue(i.sellIn==3);
	  assertTrue(i.quality==27);
	  app.updateQuality();
	  assertTrue(i.sellIn==2);
	  assertTrue(i.quality==30);
	  app.updateQuality();
	  assertTrue(i.sellIn==1);
	  assertTrue(i.quality==33);
	  app.updateQuality();
	  assertTrue(i.sellIn==0);
	  assertTrue(i.quality==36);
	  app.updateQuality();
	  assertTrue(i.sellIn==-1);
	  assertTrue(i.quality==0);
	  
  }
  
  // Tests the requirement that the Quality of an item is never more than 50.
  @Test
  public void testQualityOfAnItemIsNeverMoreThanFifty() {
	  BackstagePass i = new BackstagePass("Backstage passes to a TAFKAL80ETC concert", 8, 49);
	  GildedRose app = new GildedRose(new UpdatableItem[]{i});

	  app.updateQuality();
	  app.updateQuality();

	  assertTrue(i.quality <= 50);
	  
  }
  
  //Once the sell by date has passed, Quality degrades twice as fast
  @Test
  public void testOnceSellDateIsPastQualityDecreasesTwiceAsFast() {
	  UpdatableItem i = new UpdatableItem("+5 Dexterity Vest", -1, 20);
	  GildedRose app = new GildedRose(new UpdatableItem[]{i});
	  app.updateQuality();
	  assertTrue(i.quality == 18);
  }
  
  //"Conjured" items degrade in Quality twice as fast as normal items
  @Test 
  @Ignore
  public void testConjuredItemsDegradeQuality2x() {
      UpdatableItem i = new UpdatableItem("Conjured Tester", 1, 20);
      GildedRose app = new GildedRose(new UpdatableItem[]{i});
      app.updateQuality();
      assertTrue(i.quality == 18);
      app.updateQuality();
      assertTrue(i.quality == 14);
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