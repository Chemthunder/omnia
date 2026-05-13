package com.peak.omnia.api.registration;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

/**
 * @author Chemthunder
 */
public abstract class DataRegistry<Type> {
    public final String modid;
    public final RegistryKey<Registry<Type>> key;

    public DataRegistry(String modid, RegistryKey<Registry<Type>> key) {
        this.modid = modid;
        this.key = key;
    }

    public void bootstrap(Registerable<Type> registerable) {}

    public void loadConfigurations(RegistryWrapper.WrapperLookup wrapperLookup, FabricDynamicRegistryProvider.Entries entries) {
        entries.addAll(wrapperLookup.getWrapperOrThrow(this.key));
    }
}