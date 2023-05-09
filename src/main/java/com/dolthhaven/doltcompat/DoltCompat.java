package com.dolthhaven.doltcompat;

import com.dolthhaven.doltcompat.common.registry.DoltCompatParticles;
import com.dolthhaven.doltcompat.core.DoltCompatConfig;
import com.dolthhaven.doltcompat.core.compat.DoltCompatCompat;
import com.dolthhaven.doltcompat.core.compat.DoltCompatVillaging;
import com.dolthhaven.doltcompat.core.data.tags.DoltCompatAdvancementMods;
import com.dolthhaven.doltcompat.core.data.tags.DoltSecretBlockTags;
import com.mojang.logging.LogUtils;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(DoltCompat.MOD_ID)
public class DoltCompat {
    public static final String MOD_ID = "dolt_compat";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);


    public DoltCompat() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext context = ModLoadingContext.get();

        bus.addListener(this::commonSetup);
        bus.addListener(this::dataSetup);

        DoltCompatParticles.PARTICLE_TYPES.register(bus);
        REGISTRY_HELPER.register(bus);
        context.registerConfig(ModConfig.Type.COMMON, DoltCompatConfig.Common.COMMON_SPEC);


        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(
           () -> {
              DoltCompatCompat.registerCompat();
              DoltCompatVillaging.registerAllVillaging();
           }
        );
    }

    private void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper EFH = event.getExistingFileHelper();
        boolean includeServer = event.includeServer();

        DoltSecretBlockTags taggies = new DoltSecretBlockTags(generator, EFH);

        generator.addProvider(includeServer, taggies);
        generator.addProvider(includeServer, new DoltCompatAdvancementMods(generator));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {}
    }
}
