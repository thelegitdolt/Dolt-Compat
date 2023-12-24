package com.dolthhaven.doltcompat.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class DoltCompatConfig {
    public static class Common {
        public ForgeConfigSpec.ConfigValue<Boolean> DoDispenseSheepDye;
        public ForgeConfigSpec.ConfigValue<Boolean> BahHumbug;
        public ForgeConfigSpec.ConfigValue<Boolean> DoExplodeCreepers;
        public ForgeConfigSpec.ConfigValue<Boolean> DisableSlimeChunks;
        public ForgeConfigSpec.ConfigValue<Boolean> BOAClearsPoisons;
        public ForgeConfigSpec.ConfigValue<Boolean> ReliableSleep;
        public ForgeConfigSpec.ConfigValue<Boolean> LeatherHorseThing;
        public ForgeConfigSpec.ConfigValue<Boolean> WillowReplacement;
        public ForgeConfigSpec.ConfigValue<Boolean> DuckweedReplacement;

        Common(ForgeConfigSpec.Builder builder) {
            builder.push("vanilla_changes");
            DoDispenseSheepDye = builder.comment("If dispensers can dye sheep").define("Dye-ing Dispensers", true);
            DoExplodeCreepers = builder.comment("If dispensers ignite creepers in front of it with flint and steel").define("Explosive Dispensers", true);
            LeatherHorseThing = builder.comment("If horses wearing leather horse armor can walk on top of powdered snow.").define("Warm Horse Armor", true);
            BOAClearsPoisons = builder.comment("If killing an arthropod with a Bane of Arthropod sword will clear any poison effects the player has.").define("BOA cure", true);
            ReliableSleep = builder.comment("If monsters & being too far away from a bed shouldn't prevent you from sleeping").define("Reliable Sleep", true);

            DisableSlimeChunks = builder.comment("If slime chunks should be smote into ten thousand pieces.").define("Disable slime chunks", false);
            BahHumbug = builder.comment("If the texture change of chests during Christmas should be removed").define("Bah Humbug", false);

            builder.pop();

            builder.push("Environmental changes");
            WillowReplacement = builder.comment("If Good Ending's Muddy Oak should instead use Environmental's Willow blocks.").define("Based Trees", true);
            DuckweedReplacement = builder.comment("If all of Good Ending's patches should instead generate Environmental's duckweeds blocks.").define("Based Duckweed", true);
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
