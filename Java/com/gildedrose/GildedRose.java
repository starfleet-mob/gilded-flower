package com.gildedrose;

class GildedRose {
    UpdatableItem[] items;

    public GildedRose(UpdatableItem[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (UpdatableItem i : items) {
        	i.update();
        }
    }
}
