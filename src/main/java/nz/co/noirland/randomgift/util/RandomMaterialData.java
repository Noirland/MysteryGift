package nz.co.noirland.randomgift.util;

import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomMaterialData {

    public List<MaterialData> data = new ArrayList<MaterialData>();

    public RandomMaterialData(List<MaterialData> data) {
        this.data = data;
    }

    public MaterialData next() {
        Random rand = new Random();
        return data.get(rand.nextInt(data.size()));
    }

}
