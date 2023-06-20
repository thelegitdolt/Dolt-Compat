package com.dolthhaven.doltcompat.core.compat.dispensers;

import com.teamabnormals.neapolitan.core.registry.NeapolitanBlocks;
import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import static com.dolthhaven.doltcompat.core.compat.dispensers.DoltCompatCauldronDispensers.getEmptyCauldronBottleBehavior;

public class DoltCompatNeapolitanCauldronDispensers {

    public static void registerNeapolitanDispenseBehavior() {
        DispenseItemBehavior MILK_BOTTLE = DispenserBlock.DISPENSER_REGISTRY.get(NeapolitanItems.MILK_BOTTLE.get());
        DispenseItemBehavior MILK_BUCKET = DispenserBlock.DISPENSER_REGISTRY.get(Items.MILK_BUCKET);
        DispenseItemBehavior GLASS_BOTTLE = DispenserBlock.DISPENSER_REGISTRY.get(Items.MILK_BUCKET);
        DispenseItemBehavior BUCKET = DispenserBlock.DISPENSER_REGISTRY.get(Items.BUCKET);


        DispenserBlock.registerBehavior(NeapolitanItems.MILK_BOTTLE.get(), getEmptyCauldronBottleBehavior((LayeredCauldronBlock) NeapolitanBlocks.MILK_CAULDRON.get(), MILK_BOTTLE));


        DispenserBlock.registerBehavior(Items.BUCKET, new OptionalDispenseItemBehavior() {
            @Override
            protected @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
                BlockPos affectPos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                Level level = source.getLevel();
                if (level.getBlockState(affectPos).is(NeapolitanBlocks.MILK_CAULDRON.get())) {
                    addTo(source, Items.MILK_BUCKET);
                    this.setSuccess(true);
                    stack.shrink(1);
                    level.setBlockAndUpdate(affectPos, Blocks.CAULDRON.defaultBlockState());
                    level.playSound(null, affectPos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 0.5f, 0.5f);
                }
                else {
                    return BUCKET.dispense(source, stack);
                }
                return stack;
            }
        });

        DispenserBlock.registerBehavior(Items.GLASS_BOTTLE, new OptionalDispenseItemBehavior() {
            @Override
            protected @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
                BlockPos affectPos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                Level level = source.getLevel();
                BlockState state = level.getBlockState(affectPos);
                if (state.is(NeapolitanBlocks.MILK_CAULDRON.get())) {
                    addTo(source, NeapolitanItems.MILK_BOTTLE.get());
                    this.setSuccess(true);
                    int fill = state.getValue(LayeredCauldronBlock.LEVEL);
                    stack.shrink(1);
                    level.setBlockAndUpdate(affectPos, fill == 1 ? Blocks.CAULDRON.defaultBlockState() :
                            NeapolitanBlocks.MILK_CAULDRON.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, fill - 1));
                    level.playSound(null, affectPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 0.5f, 0.5f);
                    return stack;
                }
                else {
                    return GLASS_BOTTLE.dispense(source, stack);
                }
            }
        });

        DispenserBlock.registerBehavior(Items.MILK_BUCKET, DoltCompatCauldronDispensers.getEmptyCauldronBucketBehavior(NeapolitanBlocks.MILK_CAULDRON.get(), SoundEvents.BUCKET_EMPTY, MILK_BUCKET));

    }


    private static void addTo(BlockSource source, ItemStack stack) {
        if (source.<DispenserBlockEntity>getEntity().addItem(stack) < 0) {
            new DefaultDispenseItemBehavior().dispense(source, stack);
        }
    }

    private static void addTo(BlockSource source, Item item) {
        addTo(source, new ItemStack(item));
    }
}
