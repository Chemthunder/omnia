package com.peak.omnia.api.registration;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.*;

/**
 * @author Chemthunder
 */
public abstract class AbstractDataRegistry<Type> {
    public final String modid;
    public final RegistryKey<Registry<Type>> key;

    public AbstractDataRegistry(String modid, RegistryKey<Registry<Type>> key) {
        this.modid = modid;
        this.key = key;
    }

    public void bootstrap(Registerable<Type> registerable) {}

    public void build(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(this.key, this::bootstrap);
    }

    public void addEntries(RegistryWrapper.WrapperLookup wrapperLookup, FabricDynamicRegistryProvider.Entries entries) {
        entries.addAll(wrapperLookup.getWrapperOrThrow(this.key));
    }
}