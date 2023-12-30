package com.dolthhaven.doltcompat.core.compat;

import com.dolthhaven.doltcompat.DoltCompat;
import com.dolthhaven.doltcompat.common.registry.DoltCompatParticles;
import com.dolthhaven.doltcompat.core.DoltCompatConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.world.InteractionHand.MAIN_HAND;

@Mod.EventBusSubscriber(modid = DoltCompat.MOD_ID)
public class DoltCompatEvents {
    @SubscribeEvent
    public static void removePoisonIfPlayerKillsArthropodWithBOA(LivingDeathEvent event) {
        Entity enty = event.getEntity();
        if (DoltCompatConfig.Common.COMMON.BOAClearsPoisons.get() && enty.getLevel() instanceof ServerLevel SL && event.getSource().getEntity() instanceof LivingEntity attacker) {
            boolean shouldRemovePoison = attacker.hasEffect(MobEffects.POISON)
                    && attacker.getItemInHand(MAIN_HAND).getAllEnchantments().containsKey(Enchantments.BANE_OF_ARTHROPODS)
                    && event.getEntity().getMobType() == MobType.ARTHROPOD;
            if (shouldRemovePoison) {
                attacker.removeEffect(MobEffects.POISON);
                for (int i = 0; i < 7; i++) {
                    SL.sendParticles(DoltCompatParticles.POISON_HEART.get(), attacker.getRandomX(0.5f), attacker.getRandomY(), attacker.getRandomZ(0.5), 1,
                            0, 0, 0, 0);
                }
            }
        }
    }
}
