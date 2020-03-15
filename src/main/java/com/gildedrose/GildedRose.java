package com.gildedrose;

import java.util.Arrays;

class GildedRose {

    private static final int QUALITY_INCREMENT_NON_EXPIRED = 1;
    private static final int QUALITY_INCREMENT_EXPIRED = QUALITY_INCREMENT_NON_EXPIRED + QUALITY_INCREMENT_NON_EXPIRED;
    private static final int MAX_QUALITY = 50;
    private static final int MIN_QUALITY = 0;

    private Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        Arrays.stream(items).forEach(this::handleItem);
    }

    public void handleItem(Item item){
        switch (item.name){
            case "Aged Brie":
                handleBrieItem(item);
                break;
            case "Backstage passes to a TAFKAL80ETC concert":
                handleBackstageItem(item);
                break;
            case "Conjured Mana Cake":
                handleConjuredItem(item);
                break;
            case "Sulfuras, Hand of Ragnaros":
                break;
            default:
                handleStandardItem(item);
        }
    }

    public void handleStandardItem(Item item){
        if (item.sellIn > 0){
            item.sellIn -= 1;
            item.quality -= QUALITY_INCREMENT_NON_EXPIRED;
        } else {
            item.sellIn -= 1;
            item.quality -= QUALITY_INCREMENT_EXPIRED;
        }
        floorQualityToZero(item);
    }

    public void handleBrieItem(Item item){
        if (item.sellIn > 0){
            item.sellIn -= 1;
            item.quality += QUALITY_INCREMENT_NON_EXPIRED;
        } else {
            item.sellIn -= 1;
            item.quality += QUALITY_INCREMENT_EXPIRED;
        }
        limitQualityTo50(item);
    }

    public void handleBackstageItem(Item item){
        if (item.sellIn > 10){
            item.sellIn -= 1;
            item.quality += QUALITY_INCREMENT_NON_EXPIRED;
        } else if (item.sellIn <= 10 && item.sellIn > 5){
            item.sellIn -= 1;
            item.quality += 2;
        } else if (item.sellIn <= 5 && item.sellIn > 0){
            item.sellIn -= 1;
            item.quality += 3;
        } else if(item.sellIn == 0){
            item.sellIn -= 1;
            item.quality = MIN_QUALITY;
        }
        limitQualityTo50(item);
    }

    public void handleConjuredItem(Item item){
        if (item.sellIn > 0){
            item.sellIn -= 1;
            item.quality -= QUALITY_INCREMENT_NON_EXPIRED + QUALITY_INCREMENT_NON_EXPIRED;
        } else {
            item.sellIn -= 1;
            item.quality -= QUALITY_INCREMENT_EXPIRED + QUALITY_INCREMENT_EXPIRED;
        }
        floorQualityToZero(item);
    }

    private void floorQualityToZero(Item item){
        if(item.quality <= MIN_QUALITY){
            item.quality = MIN_QUALITY;
        }
    }

    private void limitQualityTo50(Item item){
        if(item.quality >= MAX_QUALITY){
            item.quality = MAX_QUALITY;
        }
    }

    public Item[] getItems() {
        return Arrays.copyOf(items, items.length);
    }
}