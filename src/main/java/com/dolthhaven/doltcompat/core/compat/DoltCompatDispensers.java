package com.dolthhaven.doltcompat.core.compat;

import com.teamabnormals.neapolitan.core.registry.NeapolitanBlocks;
import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DoltCompatDispensers {
    public static void registerDispenserBehavior() {
        registerCauldronDispenseBehavior();
    }

    public static void registerCauldronDispenseBehavior() {

        final DispenseItemBehavior BUCKET = DispenserBlock.DISPENSER_REGISTRY.get(Items.BUCKET);
        final DispenseItemBehavior WATER_BUCKET = DispenserBlock.DISPENSER_REGISTRY.get(Items.WATER_BUCKET);
        final DispenseItemBehavior MILK_BUCKET = DispenserBlock.DISPENSER_REGISTRY.get(Items.MILK_BUCKET);
        final DispenseItemBehavior LAVA_BUCKET = DispenserBlock.DISPENSER_REGISTRY.get(Items.LAVA_BUCKET);
        final DispenseItemBehavior POWDER_SNOW_BUCKET = DispenserBlock.DISPENSER_REGISTRY.get(Items.POWDER_SNOW_BUCKET);
        final DispenseItemBehavior GLASS_BOTTLE = DispenserBlock.DISPENSER_REGISTRY.get(Items.GLASS_BOTTLE);
        final DispenseItemBehavior MILK_BOTTLE = DispenserBlock.DISPENSER_REGISTRY.get(NeapolitanItems.MILK_BOTTLE.get());
        final DispenseItemBehavior WATER_BOTTLE = DispenserBlock.DISPENSER_REGISTRY.get(Items.POTION);

        DispenserBlock.registerBehavior(Items.BUCKET, new OptionalDispenseItemBehavior() {
                    @Override
                    protected @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {


                        BlockPos affectPos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                        Level level = source.getLevel();
                        if (level.getBlockState(affectPos).is(Blocks.WATER_CAULDRON)) {
                            stack.shrink(1);
                            level.setBlockAndUpdate(affectPos, Blocks.CAULDRON.defaultBlockState());
                            level.playSound(null, affectPos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 0.5f, 0.5f);
                            addTo(source, Items.WATER_BUCKET);
                        }
                        else if (level.getBlockState(affectPos).is(Blocks.LAVA_CAULDRON)) {
                            stack.shrink(1);
                            level.setBlockAndUpdate(affectPos, Blocks.CAULDRON.defaultBlockState());
                            level.playSound(null, affectPos, SoundEvents.BUCKET_FILL_LAVA, SoundSource.BLOCKS, 0.5f, 0.5f);
                            addTo(source, Items.LAVA_BUCKET);
                        }
                        else if (level.getBlockState(affectPos).is(Blocks.POWDER_SNOW_CAULDRON)) {
                            stack.shrink(1);
                            level.setBlockAndUpdate(affectPos, Blocks.CAULDRON.defaultBlockState());
                            level.playSound(null, affectPos, SoundEvents.BUCKET_FILL_POWDER_SNOW, SoundSource.BLOCKS, 0.5f, 0.5f);
                            addTo(source, Items.POWDER_SNOW_BUCKET);
                        }
                        else if (level.getBlockState(affectPos).is(NeapolitanBlocks.MILK_CAULDRON.get())) {
                            stack.shrink(1);
                            level.setBlockAndUpdate(affectPos, Blocks.CAULDRON.defaultBlockState());
                            level.playSound(null, affectPos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 0.5f, 0.5f);
                            addTo(source, Items.MILK_BUCKET);
                        }
                        else {
                            return BUCKET.dispense(source, stack);
                        }
                        return stack;
                    }
                }
        );
        DispenserBlock.registerBehavior(Items.WATER_BUCKET, getEmptyCauldronBucketBehavior(Blocks.WATER_CAULDRON, SoundEvents.BUCKET_EMPTY, WATER_BUCKET));
        DispenserBlock.registerBehavior(Items.LAVA_BUCKET, getEmptyCauldronBucketBehavior(Blocks.LAVA_CAULDRON, SoundEvents.BUCKET_EMPTY_LAVA, LAVA_BUCKET));
        DispenserBlock.registerBehavior(Items.POWDER_SNOW_BUCKET, getEmptyCauldronBucketBehavior(Blocks.POWDER_SNOW_CAULDRON, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, POWDER_SNOW_BUCKET));
        DispenserBlock.registerBehavior(Items.MILK_BUCKET, getEmptyCauldronBucketBehavior(NeapolitanBlocks.MILK_CAULDRON.get(), SoundEvents.BUCKET_EMPTY, MILK_BUCKET));

        DispenserBlock.registerBehavior(NeapolitanItems.MILK_BOTTLE.get(), getEmptyCauldronBottleBehavior((LayeredCauldronBlock) NeapolitanBlocks.MILK_CAULDRON.get(), MILK_BOTTLE));
        DispenserBlock.registerBehavior(Items.POTION, DispenseWaterPotion(WATER_BOTTLE));
        DispenserBlock.registerBehavior(Items.GLASS_BOTTLE, new OptionalDispenseItemBehavior() {
            @Override
            protected @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
                BlockPos affectPos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                Level level = source.getLevel();
                BlockState state = level.getBlockState(affectPos);
                if (state.is(Blocks.WATER_CAULDRON)) {
                    addTo(source, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER));
                    int fill = state.getValue(LayeredCauldronBlock.LEVEL);
                    stack.shrink(1);
                    level.setBlockAndUpdate(affectPos, fill == 1 ? Blocks.CAULDRON.defaultBlockState() :
                            Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, fill - 1));
                    level.playSound(null, affectPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 0.5f, 0.5f);
                    return stack;
                }
                else if (state.is(NeapolitanBlocks.MILK_CAULDRON.get())) {
                    addTo(source, NeapolitanItems.MILK_BOTTLE.get());
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
    }

    public static OptionalDispenseItemBehavior getEmptyCauldronBucketBehavior(Block cauldron, SoundEvent sound, DispenseItemBehavior behave) {
        return new OptionalDispenseItemBehavior() {
            @Override
            protected @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
                BlockPos affectPos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                Level level = source.getLevel();
                BlockState state = level.getBlockState(affectPos);

                boolean canFill = state.is(BlockTags.CAULDRONS) && !state.is(cauldron);
                if (state.getBlock() instanceof LayeredCauldronBlock CB && !CB.isFull(state)) {
                    canFill = true;
                }

                if (canFill) {
                    addTo(source, Items.BUCKET);
                    stack.shrink(1);
                    level.setBlockAndUpdate(affectPos, cauldron instanceof LayeredCauldronBlock ? cauldron.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3) : cauldron.defaultBlockState());
                    level.playSound(null, affectPos, sound, SoundSource.BLOCKS, 0.5f, 0.5f);
                    return stack;
                }
                else {
                    return behave.dispense(source, stack);
                }
            }
        };
    }

    public static OptionalDispenseItemBehavior getEmptyCauldronBottleBehavior(LayeredCauldronBlock cauldron, DispenseItemBehavior behavior) {
        return new OptionalDispenseItemBehavior() {
            @Override
            protected @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
                BlockPos affectPos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                Level level = source.getLevel();
                BlockState state = level.getBlockState(affectPos);
                if (state.is(Blocks.CAULDRON)) {
                    addTo(source, Items.GLASS_BOTTLE);
                    stack.shrink(1);
                    level.playSound(null, affectPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 0.5f, 0.5f);
                    level.setBlockAndUpdate(affectPos, cauldron.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 1));
                    return stack;
                }
                else if (state.is(cauldron)) {
                    int fill = state.getValue(LayeredCauldronBlock.LEVEL);
                    if (fill < 3) {
                        addTo(source, Items.GLASS_BOTTLE);
                        stack.shrink(1);
                        level.playSound(null, affectPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 0.5f, 0.5f);
                        level.setBlockAndUpdate(affectPos, cauldron.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, fill + 1));
                    }
                    return stack;
                }
                else {
                    return behavior.dispense(source, stack);
                }
            }
        };
    }

    private static void addTo(BlockSource source, Item item) {
        if (source.<DispenserBlockEntity>getEntity().addItem(new ItemStack(item)) < 0) {
            new DefaultDispenseItemBehavior().dispense(source, new ItemStack(item));
        }
    }

    private static void addTo(BlockSource source, ItemStack stack) {
        if (source.<DispenserBlockEntity>getEntity().addItem(stack) < 0) {
            new DefaultDispenseItemBehavior().dispense(source, stack);
        }
    }

    public static OptionalDispenseItemBehavior DispenseWaterPotion(DispenseItemBehavior behaveior) {
        return new OptionalDispenseItemBehavior() {
            @Override
            protected @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
                BlockPos affectPos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                Level level = source.getLevel();
                BlockState state = level.getBlockState(affectPos);
                if (PotionUtils.getPotion(stack) == Potions.WATER) {
                    if (state.is(Blocks.CAULDRON)) {
                        stack.shrink(1);
                        level.playSound(null, affectPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 0.5f, 0.5f);
                        level.setBlockAndUpdate(affectPos, Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 1));
                        addTo(source, Items.GLASS_BOTTLE);
                        return stack;
                    }
                    else if (state.is(Blocks.WATER_CAULDRON)) {
                        int fill = state.getValue(LayeredCauldronBlock.LEVEL);
                        if (fill < 3) {
                            stack.shrink(1);
                            level.playSound(null, affectPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 0.5f, 0.5f);
                            level.setBlockAndUpdate(affectPos, Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, fill + 1));
                            addTo(source, Items.GLASS_BOTTLE);
                        }
                        return stack;
                    }
                }
                return behaveior.dispense(source, stack);
            }
        };
    }
}
