package nz.co.noirland.randomgift.util;

import nz.co.noirland.zephcore.Util;

public class RandomRange {

    public Integer min;
    public Integer max;
    public boolean round;

    public RandomRange(Integer min, Integer max, boolean round) {
        this.min = min;
        this.max = max;
        this.round = round;
    }

    public int next() {
        return Util.randInt(min, max);
    }
}

