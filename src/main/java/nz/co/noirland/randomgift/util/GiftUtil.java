package nz.co.noirland.randomgift.util;

import org.apache.commons.lang.WordUtils;

public abstract class GiftUtil {

    public static double roundUp(double x, double factor) {
        return factor * Math.ceil(x / factor);
    }

    public static String stringCapitalize(String str) {
        String ret = str.replaceAll("_", " ");
        ret = WordUtils.capitalizeFully(ret);
        return ret;
    }

}
