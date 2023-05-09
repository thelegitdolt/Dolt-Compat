package com.dolthhaven.doltcompat.common.registry;

import com.dolthhaven.doltcompat.DoltCompat;
import net.minecraft.client.particle.HeartParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DoltCompat.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DoltCompatParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DoltCompat.MOD_ID);

    public static final RegistryObject<SimpleParticleType> POISON_HEART = PARTICLE_TYPES.register("poison_heart", () -> new SimpleParticleType(false));


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleTypes(RegisterParticleProvidersEvent event) {
        event.register(POISON_HEART.get(), HeartParticle.Provider::new);
    }
}
