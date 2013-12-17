package nz.co.noirland.randomgift.config;

import nz.co.noirland.randomgift.gifts.*;
import nz.co.noirland.randomgift.util.RandomMaterialData;
import nz.co.noirland.randomgift.util.RandomRange;
import nz.co.noirland.randomgift.util.Util;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class PluginConfig extends Config {

    private static PluginConfig inst;

    private PluginConfig() {
        super("config.yml");
    }

    public static PluginConfig inst() {
        if(inst == null) {
            inst = new PluginConfig();
        }

        return inst;
    }
    public boolean getDebug()    { return config.getBoolean("noirstore.debug", false);}

    public ArrayList<Gift> getGifts() {

        ArrayList<Gift> gifts = new ArrayList<Gift>();
        gifts.addAll(getItemGifts());
        gifts.addAll(getCommandGifts());
        gifts.addAll(getMultiGifts());
        gifts.addAll(getPermGifts());

        return gifts;

    }

    public ArrayList<Gift> getItemGifts() {

        ArrayList<Gift> gifts = new ArrayList<Gift>();
        ConfigurationSection section = config.getConfigurationSection("items");

        for(String key : section.getKeys(false)) {
            Gift gift = createItemGift(key, section.getConfigurationSection(key));
            gift.setWeight(section.getDouble(key + ".weight", 1));
            gifts.add(gift);
        }
        return gifts;

    }

    public ArrayList<Gift> getCommandGifts() {

        ArrayList<Gift> gifts = new ArrayList<Gift>();
        ConfigurationSection section = config.getConfigurationSection("commands");

        for(String key : section.getKeys(false)) {
            Gift gift = createCommandGift(key, section.getConfigurationSection(key));
            gift.setWeight(section.getDouble(key + ".weight", 1));
            gifts.add(gift);
        }
        return gifts;
    }

    public ArrayList<Gift> getMultiGifts() {
        ArrayList<Gift> gifts = new ArrayList<Gift>();
        ConfigurationSection section = config.getConfigurationSection("multi");
        for(String key : section.getKeys(false)) {
            MultiGift gift;
            ArrayList<Gift> subGifts = new ArrayList<Gift>();
            ConfigurationSection subSection = section.getConfigurationSection(key);
            for(String subKey : subSection.getKeys(false)) {
                if(subKey.equals("weight")) continue;
                Gift subGift;
                String type = subSection.getString(subKey + ".type");
                if(type.equals("item")) {
                    subGift = createItemGift("", subSection.getConfigurationSection(subKey));
                }else if(type.equals("command")) {
                    subGift = createCommandGift("", subSection.getConfigurationSection(subKey));
                }else if(type.equals("perm")) {
                    subGift = createPermGift("", subSection.getConfigurationSection(subKey));
                }else{
                    continue;
                }
                subGift.setWeight(subSection.getDouble(subKey + ".weight", 1));
                subGift.setChild(true);
                subGifts.add(subGift);
            }
            gift = new MultiGift(key, subGifts.toArray(new Gift[subGifts.size()]));
            gifts.add(gift);
        }

        return gifts;
    }

    public ArrayList<Gift> getPermGifts() {
        ArrayList<Gift> gifts = new ArrayList<Gift>();
        ConfigurationSection section = config.getConfigurationSection("perms");

        for(String key : section.getKeys(false)) {
            Gift gift = createPermGift(key, section.getConfigurationSection(key));
            gift.setWeight(section.getDouble(key + ".weight", 1));
            gifts.add(gift);
        }
        return gifts;

    }

    public ItemGift createItemGift(String name, ConfigurationSection section) {
        ItemGift gift;

        String material = section.getString("material");
        List<String> dataStr;
        if(section.isList("data")) {
            dataStr = section.getStringList("data");
        }else{
            dataStr = new ArrayList<String>();
            dataStr.add(section.getString("data", ""));
        }

        List<MaterialData> data = new ArrayList<MaterialData>();
        for(String str : dataStr) {
            data.add(Util.parseMaterialData(Material.getMaterial(material), str));
        }
        if(section.contains("damage")) {
            data.clear();
            data.add(new MaterialData(Material.getMaterial(material), (byte) section.getInt("damage", 0)));
        }
        RandomMaterialData randData = new RandomMaterialData(data);

        int min;
        int max;
        boolean round;
        if(section.contains("rand")) {
            min = section.getInt("rand.min");
            max = section.getInt("rand.max");
            round = section.getBoolean("round", true);
        }else{
            int amount = section.getInt("amount", 1);
            round = section.getBoolean("round", true);
            max = amount;
            min = amount;
        }
        RandomRange range = new RandomRange(min, max, round);
        gift = new ItemGift(name, randData, range);

        return gift;
    }


    public CommandGift createCommandGift(String name, ConfigurationSection section) {
        CommandGift gift;
        String command = section.getString("command");

        if(section.contains("rand")) {
            int min = section.getInt("rand.min");
            Integer max = section.getInt("rand.min");
            RandomRange range = new RandomRange(min, max, section.getBoolean("rand.round", false));
            gift = new CommandGift(name, command, range);
        }else{
            int amount = section.getInt("amount", 1);
            gift = new CommandGift(name, command, amount);
        }
        return gift;
    }

    public PermGift createPermGift(String name, ConfigurationSection section) {
        PermGift gift;
        String perm = section.getString("perm");

        gift = new PermGift(name, perm);
        return gift;
    }
}
