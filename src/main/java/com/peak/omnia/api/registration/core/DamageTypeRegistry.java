package com.peak.omnia.api.registration.core;

import com.peak.omnia.api.registration.DataRegistry;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public class DamageTypeRegistry extends DataRegistry<DamageType> {
    private final List<DamageSourceData> DATA = new ArrayList<>();

    public DamageTypeRegistry(String modid) {
        super(modid, RegistryKeys.DAMAGE_TYPE);
    }

    public RegistryKey<DamageType> register(String name, float exhaustion) {
        RegistryKey<DamageType> key = RegistryKey.of(
            RegistryKeys.DAMAGE_TYPE,
            Identifier.of(
                this.modid,
                name
            )
        );

        DamageSourceData data = new DamageSourceData(
            key,
            name,
            exhaustion
        );

        DATA.add(data);
        return key;
    }

    public void bootstrap(Registerable<DamageType> registerable) {
        this.DATA.forEach(damageSourceData -> {
            registerable.register(damageSourceData.key, new DamageType(
                damageSourceData.name,
                damageSourceData.exhaustion
            ));
        });
    }

    record DamageSourceData(RegistryKey<DamageType> key,
        String name,
        float exhaustion
    ) {}
}