package com.peak.omnia.api.registration;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;

/**
 * @author Chemthunder
 */
public abstract class DataRegistry<Type> {
    public final String modid;
    public final RegistryKey<?> key;

    public DataRegistry(String modid, RegistryKey<?> key) {
        this.modid = modid;
        this.key = key;
    }

    public void bootstrap(Registerable<Type> registerable) {}
}