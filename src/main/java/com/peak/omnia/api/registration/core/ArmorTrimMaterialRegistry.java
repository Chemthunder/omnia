package com.peak.omnia.api.registration.core;

import com.peak.omnia.api.registration.AbstractDataRegistry;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Chemthunder
 */
public class ArmorTrimMaterialRegistry extends AbstractDataRegistry<ArmorTrimMaterial> {
    private final List<TrimData> DATA = new ArrayList<>();

    public ArmorTrimMaterialRegistry(String modid) {
        super(modid, RegistryKeys.TRIM_MATERIAL);
    }

    public RegistryKey<ArmorTrimMaterial> register(String name, RegistryEntry<Item> ingredient, float itemModelIndex, Map<RegistryEntry<ArmorMaterial>, String> overrideArmorMaterials) {
        RegistryKey<ArmorTrimMaterial> key = RegistryKey.of(
            RegistryKeys.TRIM_MATERIAL,
            Identifier.of(
                this.modid,
                name
            )
        );

        TrimData data = new TrimData(
                key,
                name,
                ingredient,
                itemModelIndex,
                overrideArmorMaterials,
                Text.translatable("trim_material." + this.modid + "." + name)
        );

        DATA.add(data);
        return key;
    }

    public void bootstrap(Registerable<ArmorTrimMaterial> registerable) {
        this.DATA.forEach(trimData -> {
            registerable.register(trimData.key, new ArmorTrimMaterial(
                    trimData.name,
                    trimData.ingredient,
                    trimData.itemModelIndex,
                    trimData.overrideArmorMaterials,
                    trimData.description
            ));
        });
    }

    record TrimData(RegistryKey<ArmorTrimMaterial> key,
        String name,
        RegistryEntry<Item> ingredient,
        float itemModelIndex,
        Map<RegistryEntry<ArmorMaterial>, String> overrideArmorMaterials,
        Text description
    ) {}
}