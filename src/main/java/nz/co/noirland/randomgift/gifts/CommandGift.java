package nz.co.noirland.randomgift.gifts;

import nz.co.noirland.randomgift.MysteryGiftPlugin;
import nz.co.noirland.randomgift.util.RandomRange;
import nz.co.noirland.zephcore.Util;
import org.bukkit.entity.Player;

public class CommandGift extends Gift {

    String command;

    public CommandGift(String name, String command) {
        this(name, command, 1);
    }


    public CommandGift(String name, String command, int amount) {
        this.name = name;
        this.command = command;
        this.amount = amount;
    }

    public CommandGift(String name, String command, RandomRange range) {
        this.name = name;
        this.command = command;
        this.range = range;
    }

    public void giveGift(Player player) {
        String comm = command.replaceAll("\\{player\\}", player.getName());
        if(range != null) {
            // Randomly generate amount
            amount = Util.randInt(range.min, range.max);
        }
        comm = comm.replaceAll("\\{amount\\}", "" + amount);
        runCommand(comm);
        printMessage(player);
        firework(player);
    }

    private void runCommand(String command) {
        MysteryGiftPlugin plugin = MysteryGiftPlugin.inst();
        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
    }

    @Override
    public String toString() {
        return name + " " + command;
    }
}
