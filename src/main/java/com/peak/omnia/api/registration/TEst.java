package com.peak.omnia.api.registration;

import com.peak.omnia.api.registration.vague.MiscDataRegistry;
import com.peak.omnia.impl.Omnia;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;

/**
 * @author Chemthunder
 */
public class TEst {
    MiscDataRegistry<Enchantment> data = new MiscDataRegistry<>(Omnia.MOD_ID, RegistryKeys.ENCHANTMENT);
}
