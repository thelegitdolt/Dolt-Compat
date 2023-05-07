package com.dolthhaven.doltcompat.core.compat;

import com.dolthhaven.doltcompat.core.DoltCompatConfig;
import com.dolthhaven.doltcompat.core.compat.dispensers.DoltCompatDispensers;
import com.teamabnormals.blueprint.core.util.DataUtil;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import static com.dolthhaven.doltcompat.common.registry.DoltCompatItems.*;


public class DoltCompatCompat {
    public static void registerCompat() {
        registerCompostable();
        DoltCompatDispensers.registerDispenserBehavior();
    }

    public static void registerCompostable() {
        compost100(GLOWSHROOM_COLONY);
    }


    @SafeVarargs
    public static void compost100(RegistryObject<Item>... thing) {
        for (RegistryObject<Item> hello : thing) {
            DataUtil.registerCompostable(hello.get(), 1.0f);
        }
    }

}
