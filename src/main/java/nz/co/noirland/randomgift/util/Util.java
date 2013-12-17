package nz.co.noirland.randomgift.util;

import nz.co.noirland.randomgift.MysteryGiftPlugin;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.*;

import java.util.Random;

public abstract class Util {

    private static Random rand = new Random();

    public static ItemStack createItem(String item, String data) {

        Material material = Material.getMaterial(item);
        MaterialData materialData = parseMaterialData(material, data);
        return materialData.toItemStack();
    }

    @SuppressWarnings("deprecated")
    public static MaterialData parseMaterialData(Material material, String data) {
        MaterialData ret;
        switch(material) {
            case WOOD:
            case SAPLING:
            case LOG:
            case LEAVES:
                ret = new Tree(TreeSpecies.valueOf(data));
                break;
            //TODO: Change after non-deprecated methods are added
            case LOG_2:
            case LEAVES_2:
                TreeSpecies species = TreeSpecies.valueOf(data);
                switch(species) {
                    case ACACIA:
                    default:
                        ret = new MaterialData(material, (byte) 0x0);
                        break;
                    case DARK_OAK:
                        ret = new MaterialData(material, (byte) 0x1);
                        break;
                }
                break;
            case SANDSTONE:
                ret = new Sandstone(SandstoneType.valueOf(data));
                break;
            case LONG_GRASS:
                ret = new LongGrass(GrassSpecies.valueOf(data));
                break;
            case WOOL:
                ret = new Wool(DyeColor.valueOf(data));
                break;
            //TODO: Change after non-deprecated methods are added
            case STAINED_GLASS:
            case STAINED_GLASS_PANE:
            case STAINED_CLAY:
            case CARPET:
                ret = new MaterialData(material, DyeColor.valueOf(data).getWoolData());
                break;
            case DOUBLE_STEP:
            case STEP:
                ret = new Step(Material.valueOf(data));
                break;
            case SMOOTH_BRICK:
                // STONE for Smooth, MOSSY_COBBLESTONE for mossy, COBBLESTONE for cracked, STONE_BRICK for carved
                ret = new SmoothBrick(Material.valueOf(data));
                break;
            case WOOD_DOUBLE_STEP:
            case WOOD_STEP:
                ret = new WoodenStep(TreeSpecies.valueOf(data));
                break;
            case COAL:
                ret = new Coal(CoalType.valueOf(data));
                break;
            case INK_SACK:
                Dye mat = new Dye();
                mat.setColor(DyeColor.valueOf(data));
                ret = mat;
                break;
            case MONSTER_EGG:
                ret = new SpawnEgg(EntityType.valueOf(data));
                break;
            default:
                ret = new MaterialData(material);
                break;
        }
        return ret;
    }
    @SuppressWarnings("deprecated")
    public static String getMaterialName(MaterialData data) {
        String ret;
        switch(data.getItemType()) {
            case WOOD:
            case SAPLING:
            case LOG:
            case LEAVES:
                ret = ((Tree) data).getSpecies().toString();
                break;
            //TODO: Change after non-deprecated methods are added
            case LOG_2:
            case LEAVES_2:
                switch(data.getData()) {
                    case 0x0:
                    default:
                        ret = TreeSpecies.ACACIA.toString();
                        break;
                    case 0x1:
                        ret = TreeSpecies.DARK_OAK.toString();
                        break;
                }
                break;
            case SANDSTONE:
                ret = ((Sandstone) data).getType().toString();
                break;
            case LONG_GRASS:
                ret = ((LongGrass) data).getSpecies().toString();
                break;
            case WOOL:
                ret = ((Wool) data).getColor().toString();
                break;
            //TODO: Change after non-deprecated methods are added
            case STAINED_GLASS:
            case STAINED_GLASS_PANE:
            case STAINED_CLAY:
            case CARPET:
                ret = DyeColor.getByWoolData(data.getData()).toString();
                break;
            case DOUBLE_STEP:
            case STEP:
                ret = ((Step) data).getMaterial().toString();
                break;
            case SMOOTH_BRICK:
                // STONE for Smooth, MOSSY_COBBLESTONE for mossy, COBBLESTONE for cracked, STONE_BRICK for carved
                ret = ((SmoothBrick) data).getMaterial().toString();
                break;
            case WOOD_DOUBLE_STEP:
            case WOOD_STEP:
                ret = ((WoodenStep) data).getSpecies().toString();
                break;
            case COAL:
                ret = ((Coal) data).getType().toString();
                break;
            case INK_SACK:
                ret = ((Dye) data).getColor().toString();
                break;
            case MONSTER_EGG:
                ret = ((SpawnEgg) data).getSpawnedType().toString();
                break;
            default:
                ret = "";
                break;
        }
        return ret;
    }

    /**
     * Returns a psuedo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimim value
     * @param max Maximim value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    public static double roundUp(double x, double factor) {
        return factor * Math.ceil(x / factor);
    }

    public static boolean isOnline(String player) {
        OfflinePlayer off = MysteryGiftPlugin.inst().getServer().getOfflinePlayer(player);
        return off.isOnline();
    }

    public static String stringCapitalize(String str) {
        String ret = str.toString();
        ret = ret.replaceAll("_", " ");
        ret = WordUtils.capitalizeFully(ret);
        return ret;
    }

}
