package com.dolthhaven.doltcompat.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class DoltCompatConfig {
    public static class Common {
        public ForgeConfigSpec.ConfigValue<Boolean> DoDispenseCauldron;
        public ForgeConfigSpec.ConfigValue<Boolean> DoDispenseSheepDye;
        public ForgeConfigSpec.ConfigValue<Boolean> DisableSlimeChunks;
        public ForgeConfigSpec.ConfigValue<Boolean> UnrealisticDolphins;
        public ForgeConfigSpec.ConfigValue<Boolean> LeatherHorseThing;
//        public ForgeConfigSpec.ConfigValue<Boolean> BasedCattail;
        public ForgeConfigSpec.ConfigValue<Boolean> WillowReplacement;
        public ForgeConfigSpec.ConfigValue<Boolean> DuckweedReplacement;

        Common(ForgeConfigSpec.Builder builder) {
            builder.push("vanilla_changes");
            DoDispenseCauldron = builder.comment("If dispensers can interact with cauldrons using buckets").define("Cauldron Dispensers", true);
            DoDispenseSheepDye = builder.comment("If dispensers can dye sheep").define("Dye-ing Dispensers", true);
            DisableSlimeChunks = builder.comment("If slime chunks should be smited into ten thousand pieces.").define("Disable slime chunks", false);
            UnrealisticDolphins = builder.comment("If dolphins shouldn't need air to survive.").define("Dolphins don't need air", false);
            LeatherHorseThing = builder.comment("If horses wearing leather horse armor can walk on top of powdered snow.").define("Warm Horse Armor", true);

            builder.pop();

            builder.push("Environmental changes");
            WillowReplacement = builder.comment("If Good Ending's Muddy Oak should instead use Environmental's Willow blocks.").define("Based Trees", true);
//           BasedCattail = builder.comment("If Enviromental's cattails should be changed to not fill the entire swamp with seeding cattails.").define("Based Cattails", true);
            DuckweedReplacement = builder.comment("If all of Good Ending's patches should instead generate Environmental's duckweeds blocks.").define("Based Cattails", true);
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
