package nz.co.noirland.randomgift.gifts;

import nz.co.noirland.randomgift.MysteryGiftPlugin;
import nz.co.noirland.randomgift.util.RandomRange;
import nz.co.noirland.randomgift.util.Util;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

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

    public void firework(Player player) {
        if(isChild) return;
        Firework fw = (Firework) player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        FireworkEffect.Type type = FireworkEffect.Type.BALL;

        Color color = Util.randInArray(DyeColor.values()).getFireworkColor();
        Color fade = Util.randInArray(DyeColor.values()).getFireworkColor();

        FireworkEffect effect = FireworkEffect.builder().flicker(false).withColor(color).withFade(fade).with(type).trail(true).build();

        fwm.addEffect(effect);
        fw.setFireworkMeta(fwm);

        fw.detonate();
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
