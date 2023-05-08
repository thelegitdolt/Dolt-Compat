package com.dolthhaven.doltcompat.core.compat;

import com.dolthhaven.doltcompat.DoltCompat;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DoltCompat.MOD_ID)
public class DoltCompatEvents {
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            if (attacker.getMainHandItem().getAllEnchantments().containsKey(Enchantments.BANE_OF_ARTHROPODS) &&
                   entity.getMobType().equals(MobType.ARTHROPOD)  && attacker.getEffect(MobEffects.POISON) != null) {
                attacker.removeEffect(MobEffects.POISON);
            }
        }
    }

}
