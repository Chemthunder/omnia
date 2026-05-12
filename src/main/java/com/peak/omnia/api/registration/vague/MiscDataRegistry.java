package com.peak.omnia.api.registration.vague;

import net.minecraft.registry.RegistryKey;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public class MiscDataRegistry<T> {
    private final String modid;
    private final List<T> objs = new ArrayList<>();

    private final RegistryKey<?> key;

    public MiscDataRegistry(String modid, RegistryKey<?> key) {
        this.modid = modid;
        this.key = key;
    }

    public T register(T obj) {
        this.objs.add(obj);
        return obj;
    }

    public List<T> getObjs() {
        return objs;
    }
}
