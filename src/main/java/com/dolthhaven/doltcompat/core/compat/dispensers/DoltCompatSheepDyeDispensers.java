package com.dolthhaven.doltcompat.core.compat.dispensers;

import com.dolthhaven.doltcompat.core.DoltCompatConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DoltCompatSheepDyeDispensers {
    public static void registerSheepDispensers() {
        if (DoltCompatConfig.Common.COMMON.DoDispenseSheepDye.get()) {
            for (DyeColor dye : DyeColor.values()) {
                DispenserBlock.registerBehavior(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", dye.getName() + "_dye"))),
                        dispenserByDye(dye));
            }
        }
    }

    private static HashMap<DyeColor, DispenseItemBehavior> getDyeList() {
        HashMap<DyeColor, DispenseItemBehavior> map = new HashMap<>();
        for (DyeColor dye : DyeColor.values()) {
            map.put(dye, DispenserBlock.DISPENSER_REGISTRY.get(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", dye.getName() + "_dye"))));
        }
        return map;
    }

    private static final HashMap<DyeColor, DispenseItemBehavior> DYE_MAP = getDyeList();

    private static DispenseItemBehavior dispenserByDye(DyeColor dye) {
        return new OptionalDispenseItemBehavior() {
            @Override
            protected @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
                BlockPos affectPos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                Level level = source.getLevel();
                List<Sheep> Sheepies = level.getEntitiesOfClass(Sheep.class, new AABB(affectPos), EntitySelector.NO_SPECTATORS).stream()
                        .filter((sheepie) -> !sheepie.getColor().equals(dye)).toList();
                if (!Sheepies.isEmpty()) {
                    for (Sheep sheep : Sheepies) {
                        this.setSuccess(true);
                        stack.shrink(1);
                        sheep.setColor(dye);
                        return stack;
                    }
                }
                return DYE_MAP.get(dye).dispense(source, stack);
            }
        };
    }
}
