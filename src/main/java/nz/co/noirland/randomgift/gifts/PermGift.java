package nz.co.noirland.randomgift.gifts;

import org.bukkit.entity.Player;

public class PermGift extends Gift {

    String perm;

    public PermGift(String name, String perm) {
        this.name = name;
        this.perm = perm;
    }


    @Override
    public void giveGift(Player player) {
        if(hasGift(player)) {
            plugin.giveGift(player);
            return;
        }
        plugin.addPerm(player, perm);
        printMessage(player);
        firework(player);
    }

    @Override
    public boolean hasGift(Player player) {
        return player.hasPermission(perm);
    }

    @Override
    public String toString() {
        return name + " " + perm;
    }
}
