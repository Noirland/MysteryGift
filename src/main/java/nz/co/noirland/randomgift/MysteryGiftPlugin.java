package nz.co.noirland.randomgift;

import net.milkbowl.vault.permission.Permission;
import nz.co.noirland.randomgift.config.PluginConfig;
import nz.co.noirland.randomgift.gifts.Gift;
import nz.co.noirland.randomgift.util.RandomGiftCollection;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class MysteryGiftPlugin extends JavaPlugin {

    private static MysteryGiftPlugin inst;
    private RandomGiftCollection gifts = new RandomGiftCollection();
    private Permission vaultPerms;

    public static MysteryGiftPlugin inst() {
        return inst;
    }

    @Override
    public void onEnable() {
        inst = this;
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider == null) {
            disable("Vault not loaded!");
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

    /**
     * Show a debug message if debug is true in config.
     * @param msg message to be shown in console
     */
    public void debug(String msg) {

        if(PluginConfig.inst().getDebug()) {
            getLogger().info("[DEBUG] " + msg);
        }

    }

    /**
     * Show an Exception's stack trace in console if debug is true.
     * @param e execption to be shown
     */
    public void debug(Throwable e) {
        debug(ExceptionUtils.getStackTrace(e));
        ExceptionUtils.getStackTrace(e);
    }

    /**
     * Show both a debug message and a stacktrace
     * @param msg message to be shown in console
     * @param e execption to be shown
     */
    public void debug(String msg, Throwable e) {
        debug(msg);
        debug(e);
    }

    /**
     * Disable plugin and show a severe message
     * @param error message to be shown
     */
    public void disable(String error) {
        getLogger().severe(error);
        getPluginLoader().disablePlugin(this);
    }

    /**
     * Disable plugin with severe message and stack trace if debug is enabled.
     * @param error message to be shown
     * @param e execption to be shown
     */
    public void disable(String error, Throwable e) {
        debug(e);
        disable(error);
    }

    public void sendMessage(Player player, String msg) {
        player.sendMessage(ChatColor.GOLD + "[MysteryGift] " + ChatColor.RESET + msg);
    }
}

