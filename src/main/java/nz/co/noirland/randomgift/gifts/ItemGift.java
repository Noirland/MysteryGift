package nz.co.noirland.randomgift.gifts;

import nz.co.noirland.randomgift.util.RandomMaterialData;
import nz.co.noirland.randomgift.util.RandomRange;
import nz.co.noirland.randomgift.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.HashMap;

public class ItemGift extends Gift {

    private RandomMaterialData randData;
    private MaterialData latestData;

    public ItemGift(String name, RandomMaterialData data, RandomRange range) {
        this.name = name;
        this.randData = data;
        this.range = range;
    }

    public void giveGift(Player player) {
        ItemStack add;
        MaterialData data = randData.next();
        add = data.toItemStack();

        int rand = Util.randInt(range.min, range.max);
        if(range.round) {
            rand = (int) Util.roundUp(rand, add.getMaxStackSize());
        }
        amount = rand;
        add.setAmount(amount);


        HashMap<Integer, ItemStack> leftovers = player.getInventory().addItem(add);
        for(ItemStack item : leftovers.values()) {
            player.getLocation().getWorld().dropItem(player.getLocation(), item);
        }
        latestData = data;
        printMessage(player);
    }

    @Override
    public void printMessage(Player player) {
        if(isChild) return;
        String nameRepl = name.replaceAll("\\{data\\}", Util.stringCapitalize(Util.getMaterialName(latestData)));
        String amountStr = amount + "x ";
        if(range.round && latestData.getItemType().getMaxStackSize() > 1) {
            int stack = latestData.getItemType().getMaxStackSize();
            amountStr = amount / stack + " stacks of ";
        }
        plugin.sendMessage(player, "You've been gifted " + amountStr + nameRepl + "!");

        plugin.getLogger().info(player.getName() + " has been gifted " + amountStr + nameRepl);
    }

    @Override
    public String toString() {
        int numDatas = randData.data.size();
        return name + " " + numDatas + " MaterialData " + range.min + " min, " + range.max + "max";

    }
}
