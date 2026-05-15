package com.peak.omnia.api.registration;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryWrapper;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Chemthunder
 */
public class DataInitializer {
    private final String modid;

    private final List<AbstractDataRegistry<?>> providers;

    public DataInitializer(String modid, List<AbstractDataRegistry<?>> registries) {
        this.modid = modid;
        this.providers = registries;
    }

    public void loadConfigurations(RegistryWrapper.WrapperLookup wrapperLookup, FabricDynamicRegistryProvider.Entries entries) {
        this.providers.forEach(dataRegistry -> {
            entries.addAll(wrapperLookup.getWrapperOrThrow(dataRegistry.key));
            LoggerFactory.getLogger(modid).info("Successfully configured registry {}", dataRegistry.key);
        });
    }

    public void buildRegistries(RegistryBuilder registryBuilder) {
        this.providers.forEach(dataRegistry -> {
            dataRegistry.build(registryBuilder);
            LoggerFactory.getLogger(modid).info("Successfully built registry {}", dataRegistry.key);
        });
    }

    public List<AbstractDataRegistry<?>> getProviders() {
        return this.providers;
    }
}