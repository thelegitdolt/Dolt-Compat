package com.dolthhaven.doltcompat.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class DoltCompatConfig {
    public static class Common {
        public ForgeConfigSpec.ConfigValue<Boolean> DoDispenseCauldron;
        public ForgeConfigSpec.ConfigValue<Boolean> DisableSlimeChunks;
        public ForgeConfigSpec.ConfigValue<Boolean> UnrealisticDolphins;
        public ForgeConfigSpec.ConfigValue<Boolean> LeatherHorseThing;

        Common(ForgeConfigSpec.Builder builder) {
            builder.push("vanilla_changes");
            DoDispenseCauldron = builder.comment("If dispensers can interact with cauldrons using buckets").define("Cauldron Dispensers", true);
            DisableSlimeChunks = builder.comment("If slime chunks should be smited into ten thousand pieces.").define("Disable slime chunks", false);
            UnrealisticDolphins = builder.comment("If dolphins shouldn't need air to survive.").define("Dolphins don't need air", false);
            LeatherHorseThing = builder.comment("If horses wearing leather horse armor can walk on top of powdered snow.").define("Warm Horse Armor", true);

            builder.pop();
        }

        public static final ForgeConfigSpec COMMON_SPEC;
        public static final Common COMMON;

        static {
            final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
            COMMON_SPEC = specPair.getRight();
            COMMON = specPair.getLeft();
        }
    }
}
