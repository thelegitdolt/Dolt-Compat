package com.dolthhaven.doltcompat.core.data.tags;

import com.dolthhaven.doltcompat.DoltCompat;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.tag.ModTags;

import static com.dolthhaven.doltcompat.common.registry.DoltCompatBlocks.*;

public class DoltSecretBlockTags extends BlockTagsProvider {
    public DoltSecretBlockTags(DataGenerator gen, @Nullable ExistingFileHelper EFH) {
        super(gen, DoltCompat.MOD_ID, EFH);
    }

    @Override
    public void addTags() {
        add(ModTags.UNAFFECTED_BY_RICH_SOIL, GLOWSHROOM_COLONY);
    }

    @SafeVarargs
    public final void add(TagKey<Block> tag, RegistryObject<Block>... adding) {
        for (RegistryObject<Block> add : adding) this.tag(tag).add(add.get());
    }
}
