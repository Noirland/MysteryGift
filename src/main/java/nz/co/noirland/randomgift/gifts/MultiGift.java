package nz.co.noirland.randomgift.gifts;

import org.bukkit.entity.Player;

public class MultiGift extends Gift {

    Gift[] gifts;

    /*
    Multigift: Multiple gifts under one. Each child is an item or command gift, that is added to an array.
    Each gift is ran seperately, and then the overall message is sent to the player.
     */

    public MultiGift(String name, Gift[] gifts) {
        this.name = name;
        this.gifts = gifts;
    }

    @Override
    public void giveGift(Player player) {
        if(hasGift(player)) {
            plugin.giveGift(player);
            return;
        }
        for(Gift gift : gifts) {
            gift.giveGift(player);
        }
        printMessage(player);
        firework(player);
    }

    @Override
    public boolean hasGift(Player player) {
        for(Gift gift: gifts) {
            if(gift.hasGift(player)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
