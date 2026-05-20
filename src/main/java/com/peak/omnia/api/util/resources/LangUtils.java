package com.peak.omnia.api.util.resources;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider.TranslationBuilder;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;

/**
 * @author Chemthunder
 */
public class LangUtils {
    public static void damageType(TranslationBuilder translationBuilder, RegistryKey<DamageType> damageType, String base, String player, String item) {
        String apply = "death.attack." + damageType.getValue().getPath();
        translationBuilder.add(apply, base);
        translationBuilder.add(apply + ".player", player);
        translationBuilder.add(apply + ".item", item);
    }

    public static void singularDamageType(TranslationBuilder translationBuilder, RegistryKey<DamageType> damageType, String value) {
        damageType(translationBuilder, damageType, value, value, value);
    }

    public static void enchantment(TranslationBuilder translationBuilder, RegistryKey<Enchantment> enchant, String name, String desc) {
        String apply = "enchantment." + enchant.getValue().getNamespace() + "." + enchant.getValue().getPath();
        translationBuilder.add(apply, name);
        translationBuilder.add(apply + ".desc", desc);
    }

    public static void subtitle(TranslationBuilder translationBuilder, SoundEvent event, String subtitle) {
        String apply = "subtitle." + event.getId().getNamespace() + "." + event.getId().getPath();
        translationBuilder.add(apply, subtitle);
    }

    public static void itemGroup(TranslationBuilder translationBuilder, ItemGroup group, String name) {
        String apply = "itemGroup." + group.toString();
        translationBuilder.add(apply, name);
    }
}