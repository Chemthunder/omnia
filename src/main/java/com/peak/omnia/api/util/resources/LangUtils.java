package com.peak.omnia.api.util.resources;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider.TranslationBuilder;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;

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
}