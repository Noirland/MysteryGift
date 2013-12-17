package nz.co.noirland.randomgift.gifts;

import nz.co.noirland.randomgift.MysteryGiftPlugin;
import nz.co.noirland.randomgift.util.RandomRange;
import org.bukkit.entity.Player;

public abstract class Gift {

    protected MysteryGiftPlugin plugin = MysteryGiftPlugin.inst();
    protected double weight = 1;
    protected int amount = 1;
    protected RandomRange range;
    protected String name;
    protected boolean isChild = false;


    public abstract void giveGift(Player player);

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void printMessage(Player player) {
        if(isChild) return;
        plugin.sendMessage(player, "You've been gifted " + amount + "x " + name + "!");

        plugin.getLogger().info(player.getName() + " has been gifted " + amount + "x " + name);
    }

    public boolean hasGift(Player player) {
        return false;
    }

    public void setChild(boolean isChild) {
        this.isChild = isChild;
    }

    @Override
    public String toString() {
        return name;
    }
}
