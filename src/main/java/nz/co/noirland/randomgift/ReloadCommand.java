package nz.co.noirland.randomgift;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    MysteryGiftPlugin plugin = MysteryGiftPlugin.inst();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("mysterygift.reload")) {
            sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to use that command.");
            return  true;
        }
        plugin.refreshGifts();
        sender.sendMessage(ChatColor.GOLD + "MysteryGifts config reloaded successfully.");
        return true;
    }

}
