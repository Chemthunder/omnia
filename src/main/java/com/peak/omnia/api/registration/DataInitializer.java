package com.peak.omnia.api.registration;

import com.peak.omnia.impl.Omnia;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public class DataInitializer {
    private final String modid;

    public List<DataRegistry<?>> providers = new ArrayList<>();

    public DataInitializer(String modid, List<DataRegistry<?>> registries) {
        this.modid = modid;
        this.providers = registries;
    }

    public void loadConfigurations(RegistryWrapper.WrapperLookup wrapperLookup, FabricDynamicRegistryProvider.Entries entries) {
        this.providers.forEach(dataRegistry -> {
            entries.addAll(wrapperLookup.getWrapperOrThrow(dataRegistry.key));
            Omnia.LOGGER.info("Loaded registry {}", dataRegistry.key);
        });
    }
}
