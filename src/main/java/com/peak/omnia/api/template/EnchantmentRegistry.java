package com.peak.omnia.api.template;

import net.minecraft.block.Block;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public class EnchantmentRegistry {
    private final String modid;
    private final List<EnchantmentData> DATA = new ArrayList<>();

    public EnchantmentRegistry(String modid) {
        this.modid = modid;
    }

    public RegistryKey<Enchantment> register(String name, TagKey<Item> acceptableItems, ComponentType<Unit> effect, int weight, int maxLevel, int cost, int anvilCost) {
        RegistryKey<Enchantment> key = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(this.modid, name));

        EnchantmentData data = new EnchantmentData(
                key,
                name,
                acceptableItems,
                effect,
                AttributeModifierSlot.MAINHAND,
                weight,
                maxLevel,
                cost,
                anvilCost
        );

        DATA.add(data);
        return key;
    }

    public RegistryKey<Enchantment> register(String name, TagKey<Item> acceptableItems, ComponentType<Unit> effect, AttributeModifierSlot slot, int weight, int maxLevel, int cost, int anvilCost) {
        RegistryKey<Enchantment> key = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(this.modid, name));

        EnchantmentData data = new EnchantmentData(
                key,
                name,
                acceptableItems,
                effect,
                slot,
                weight,
                maxLevel,
                cost,
                anvilCost
        );

        DATA.add(data);
        return key;
    }

    public void bootstrap(Registerable<Enchantment> registerable) {
        RegistryEntryLookup<Enchantment> enchantmentLookup = registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT);
        RegistryEntryLookup<EntityType<?>> entityTypeLookup = registerable.getRegistryLookup(RegistryKeys.ENTITY_TYPE);
        RegistryEntryLookup<Block> blockLookup = registerable.getRegistryLookup(RegistryKeys.BLOCK);
        RegistryEntryLookup<Item> itemLookup = registerable.getRegistryLookup(RegistryKeys.ITEM);

        this.DATA.forEach(enchantmentData -> {
            registerable.register(
                    enchantmentData.key,
                    Enchantment.builder(Enchantment.definition(
                                    itemLookup.getOrThrow(enchantmentData.acceptableItems),
                                    enchantmentData.weight,
                                    enchantmentData.maxLevel,
                                    Enchantment.leveledCost(enchantmentData.cost, 0),
                                    Enchantment.leveledCost(enchantmentData.cost + 6, 0),
                                    enchantmentData.anvilCost,
                                    enchantmentData.slot
                            ))
                            .addEffect(enchantmentData.effect)
                            .build(enchantmentData.key.getValue())
            );
        });
    }

    record EnchantmentData(RegistryKey<Enchantment> key, String name, TagKey<Item> acceptableItems, ComponentType<Unit> effect, AttributeModifierSlot slot, int weight, int maxLevel, int cost, int anvilCost) {}
}
