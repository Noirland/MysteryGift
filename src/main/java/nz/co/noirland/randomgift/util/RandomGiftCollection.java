package nz.co.noirland.randomgift.util;

import nz.co.noirland.randomgift.gifts.Gift;

import java.util.Collection;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class RandomGiftCollection {

    private final NavigableMap<Double, Gift>  map = new TreeMap<Double, Gift>();
    private final Random random;
    private double total = 0;

    public RandomGiftCollection() {
        random = new Random();
    }

    public void put(Gift gift) {
        total += gift.getWeight();
        map.put(total, gift);
    }

    public void putAll(Collection<Gift> collection) {
        for(Gift gift : collection) {
            put(gift);
        }
    }

    public Gift next() {
        double value = random.nextDouble() * total;
        return map.ceilingEntry(value).getValue();
    }

    public void clear() {
        map.clear();
    }
}
