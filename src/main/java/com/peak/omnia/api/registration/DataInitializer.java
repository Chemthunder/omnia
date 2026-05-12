package com.peak.omnia.api.registration;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public class DataInitializer {
    private final String modid;

    private final String registryId;
    public final List<DataRegistry<?>> providers = new ArrayList<>();

    public DataInitializer(String modid, String registryId) {
        this.modid = modid;
        this.registryId = registryId;
    }

    public void addRegistry(DataRegistry<?> registry) {
        this.providers.add(registry);
    }

    public void loadConfigurations(RegistryWrapper.WrapperLookup wrapperLookup, FabricDynamicRegistryProvider.Entries entries) {
        this.providers.forEach(dataRegistry -> {
          //  entries.addAll(wrapperLookup.getWrapperOrThrow(dataRegistry.key));
        });
    }
}
