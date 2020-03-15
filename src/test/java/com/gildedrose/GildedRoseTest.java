package com.gildedrose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class GildedRoseTest {

    @Test
    @DisplayName("Test that quality decreases by 1 for standard not expired item")
    public void standardNotExpired(){
        Item[] items = new Item[] { new Item("standard", 20, 10)};

        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertThat(app.getItems()[0].name).isEqualTo("standard");
        assertThat(app.getItems()[0].sellIn).isEqualTo(19);
        assertThat(app.getItems()[0].quality).isEqualTo(9);
    }

    @Test
    @DisplayName("Test that quality decreases by 1 for standard expired item")
    public void standardExpired(){
        Item[] items = new Item[] { new Item("standard", 0, 10)};

        GildedRose app = new GildedRose(items);
        app.updateQuality();

        final Item item = app.getItems()[0];

        assertThat(item.name).isEqualTo("standard");
        assertThat(item.sellIn).isEqualTo(-1);
        assertThat(item.quality).isEqualTo(8);
    }

    @Test
    @DisplayName("Test that quality is floored to 0 for standard item with quality 0")
    public void standardExpiredQualityZero(){
        Item[] items = new Item[] { new Item("standard", 0, 0)};

        GildedRose app = new GildedRose(items);
        app.updateQuality();

        final Item item = app.getItems()[0];

        assertThat(item.name).isEqualTo("standard");
        assertThat(item.sellIn).isEqualTo(-1);
        assertThat(item.quality).isEqualTo(0);
    }

    @Test
    @DisplayName("Test Sulfuras does not change in quality and sellin")
    public void sulfurasDoesNotChange(){
        Item[] items = new Item[] {
                new Item("Sulfuras, Hand of Ragnaros", 0, 80),
                new Item("Sulfuras, Hand of Ragnaros", -1, 80),
                new Item("Sulfuras, Hand of Ragnaros", 10, 80)
        };

        GildedRose app = new GildedRose(items);
        app.updateQuality();

        Arrays.stream(app.getItems()).forEach(i -> {
            assertThat(i.name).isEqualTo("Sulfuras, Hand of Ragnaros");
            assertThat(i.quality).isEqualTo(80);
        });

        assertThat(app.getItems()[0].sellIn).isEqualTo(0);
        assertThat(app.getItems()[1].sellIn).isEqualTo(-1);
        assertThat(app.getItems()[2].sellIn).isEqualTo(10);
    }

    @Test
    @DisplayName("Test Brie quality increases only")
    public void brieQualityOnlyIncreases(){
        Item[] items = new Item[] { new Item("Aged Brie", 1, 0)};

        GildedRose app = new GildedRose(items);
        app.updateQuality();

        final Item item = app.getItems()[0];

        assertThat(item.name).isEqualTo("Aged Brie");
        assertThat(item.sellIn).isEqualTo(0);
        assertThat(item.quality).isEqualTo(1);
    }

    // Was not specified in the business requirements, but covers the previous implementation
    @Test
    @DisplayName("Test Brie quality increases twice as fast when expired")
    public void brieQualityOnlyIncreasesTwiceAsFastWhenExpired(){
        Item[] items = new Item[] { new Item("Aged Brie", 0, 0)};

        GildedRose app = new GildedRose(items);
        app.updateQuality();

        final Item item = app.getItems()[0];

        assertThat(item.name).isEqualTo("Aged Brie");
        assertThat(item.sellIn).isEqualTo(-1);
        assertThat(item.quality).isEqualTo(2);
    }

    @Test
    @DisplayName("Test Brie & Backstage quality cannot exeed the limit 50")
    public void brieAndBackstageQualityIsLimited(){
        Item[] items = new Item[] {
                new Item("Aged Brie", 5, 50),
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 50)
        };

        GildedRose app = new GildedRose(items);
        app.updateQuality();

        Arrays.stream(app.getItems()).forEach(i -> {
            assertThat(i.quality).isEqualTo(50);
            assertThat(i.sellIn).isEqualTo(4);
        });
    }

    @Test
    @DisplayName("test Backstage quality increases by 1 when sellIn is greater than 10")
    public void backstageWhenSellInGreaterThan10(){
        Item[] items = new Item[] {new Item("Backstage passes to a TAFKAL80ETC concert", 11, 10)};

        GildedRose app = new GildedRose(items);
        app.updateQuality();

        final Item item = app.getItems()[0];

        assertThat(item.name).isEqualTo("Backstage passes to a TAFKAL80ETC concert");
        assertThat(item.sellIn).isEqualTo(10);
        assertThat(item.quality).isEqualTo(11);
    }

    @Test
    @DisplayName("test Backstage quality increases by 2 when sellIn equals or less than 10 and more than 5")
    public void backstageQualityIncreasesBy2(){
        Item[] items = new Item[] {new Item("Backstage passes to a TAFKAL80ETC concert", 10, 10)};

        GildedRose app = new GildedRose(items);
        app.updateQuality();

        final Item item = app.getItems()[0];

        assertThat(item.name).isEqualTo("Backstage passes to a TAFKAL80ETC concert");
        assertThat(item.sellIn).isEqualTo(9);
        assertThat(item.quality).isEqualTo(12);
    }

    @Test
    @DisplayName("test Backstage quality increases by 3 when sellIn equals or less than 5 and more than 0")
    public void backstageQualityIncreasesBy3(){
        Item[] items = new Item[] {new Item("Backstage passes to a TAFKAL80ETC concert", 5, 10)};

        GildedRose app = new GildedRose(items);
        app.updateQuality();

        final Item item = app.getItems()[0];

        assertThat(item.name).isEqualTo("Backstage passes to a TAFKAL80ETC concert");
        assertThat(item.sellIn).isEqualTo(4);
        assertThat(item.quality).isEqualTo(13);
    }

    @Test
    @DisplayName("test Backstage quality drops to zero when expired")
    public void backstageQualityDropsToZero(){
        Item[] items = new Item[] {new Item("Backstage passes to a TAFKAL80ETC concert", 0, 50)};

        GildedRose app = new GildedRose(items);
        app.updateQuality();

        final Item item = app.getItems()[0];

        assertThat(item.name).isEqualTo("Backstage passes to a TAFKAL80ETC concert");
        assertThat(item.sellIn).isEqualTo(-1);
        assertThat(item.quality).isEqualTo(0);
    }

    @Test
    @DisplayName("test Conjured quality drops by 2 when not expired")
    public void conjuredQualityDropsBy2(){
        Item[] items = new Item[] {new Item("Conjured Mana Cake", 5, 10)};

        GildedRose app = new GildedRose(items);
        app.updateQuality();

        final Item item = app.getItems()[0];

        assertThat(item.name).isEqualTo("Conjured Mana Cake");
        assertThat(item.sellIn).isEqualTo(4);
        assertThat(item.quality).isEqualTo(8);
    }

    @Test
    @DisplayName("test Conjured quality drops by 4 when expired")
    public void conjuredQualityDropsBy4(){
        Item[] items = new Item[] { new Item("Conjured Mana Cake", 0, 10)};

        GildedRose app = new GildedRose(items);
        app.updateQuality();

        final Item item = app.getItems()[0];

        assertThat(item.name).isEqualTo("Conjured Mana Cake");
        assertThat(item.sellIn).isEqualTo(-1);
        assertThat(item.quality).isEqualTo(6);
    }




}
