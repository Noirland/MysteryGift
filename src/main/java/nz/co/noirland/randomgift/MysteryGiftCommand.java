package nz.co.noirland.randomgift;

import nz.co.noirland.randomgift.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MysteryGiftCommand implements CommandExecutor {

    private MysteryGiftPlugin plugin = MysteryGiftPlugin.inst();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {



        // /randomgift [player]
        if(!sender.hasPermission("mysterygift.give")) {
            sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use that command.");
            return  true;
        }
        if(args.length != 1) {
            return false;
        }
        String pString = args[0];
        if(!Util.isOnline(pString)) {
            sender.sendMessage("Player is not online.");
            return true;
        }
        Player player = plugin.getServer().getPlayer(pString);

        plugin.giveGift(player);
        return true;
    }
}
