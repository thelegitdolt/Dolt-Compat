package com.dolthhaven.doltcompat.core.data.tags;

import com.dolthhaven.doltcompat.DoltCompat;
import com.dolthhaven.doltcompat.common.registry.DoltCompatItems;
import com.teamabnormals.blueprint.common.advancement.modification.AdvancementModifierProvider;
import com.teamabnormals.blueprint.common.advancement.modification.modifiers.CriteriaModifier;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

public class DoltCompatAdvancementMods extends AdvancementModifierProvider {

    public DoltCompatAdvancementMods(DataGenerator generator) {
        super(generator, DoltCompat.MOD_ID);
    }

    @Override
    protected void registerEntries() {
        this.entry("fd/get_mushroom_colony").selects(new ResourceLocation("farmersdelight", "main/get_mushroom_colony"))
                .addModifier(CriteriaModifier.builder(this.modId).addCriterion("glowshroomies",
        InventoryChangeTrigger.TriggerInstance.hasItems(
                ItemPredicate.Builder.item().of(DoltCompatItems.GLOWSHROOM_COLONY.get()).build()))
                        .addIndexedRequirements(0, false, "glowshroomies").build());
    }
}
