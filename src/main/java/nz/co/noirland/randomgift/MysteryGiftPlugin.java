package nz.co.noirland.randomgift;

import net.milkbowl.vault.permission.Permission;
import nz.co.noirland.randomgift.config.PluginConfig;
import nz.co.noirland.randomgift.gifts.Gift;
import nz.co.noirland.randomgift.util.RandomGiftCollection;
import nz.co.noirland.zephcore.Debug;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class MysteryGiftPlugin extends JavaPlugin {

    private static MysteryGiftPlugin inst;
    private RandomGiftCollection gifts = new RandomGiftCollection();
    private Permission vaultPerms;
    private static Debug debug;

    public static MysteryGiftPlugin inst() {
        return inst;
    }

    public static Debug debug() { return debug; }

    @Override
    public void onEnable() {
        inst = this;
        debug = new Debug(this);
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider == null) {
            debug().disable("Vault not loaded!");
            return;
        }
        vaultPerms = permissionProvider.getProvider();
        getCommand("mysterygift").setExecutor(new MysteryGiftCommand());
        getCommand("mgreload").setExecutor(new ReloadCommand());
        refreshGifts();
    }

    public void refreshGifts() {
        PluginConfig.inst().loadFile();
        gifts.clear();
        gifts.putAll(PluginConfig.inst().getGifts());
    }

    public Gift getRandomGift() {
        return gifts.next();
    }

    public void giveGift(Player player) {
        Gift gift = getRandomGift();
        gift.giveGift(player);
    }

    public void addPerm(Player player, String perm) {
        vaultPerms.playerAdd(player, perm);
    }

    public void sendMessage(Player player, String msg) {
        player.sendMessage(ChatColor.GOLD + "[MysteryGift] " + ChatColor.RESET + msg);
    }
}

