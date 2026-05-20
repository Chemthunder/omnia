package com.peak.omnia.api.registration.tag;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public class TagBuilder<T> {
    private final String modid;
    private final RegistryKey<Registry<T>> key;
    private final List<TagKey<T>> objs = new ArrayList<>();

    public TagBuilder(String modid, RegistryKey<Registry<T>> key) {
        this.modid = modid;
        this.key = key;
    }

    public TagKey<T> register(String name) {
        TagKey<T> gen = TagKey.of(this.key, Identifier.of(this.modid, name));
        this.objs.add(gen);
        return gen;
    }
}