package com.peak.omnia.api.template;

import com.peak.omnia.api.DataRegistry;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public class BannerPatternRegistry extends DataRegistry {
    private final List<BannerPatternData> DATA = new ArrayList<>();

    public BannerPatternRegistry(String modid) {
        super(modid);
    }


    public RegistryKey<BannerPattern> register(String name, Identifier texture) {
        RegistryKey<BannerPattern> key = RegistryKey.of(
            RegistryKeys.BANNER_PATTERN,
                Identifier.of(
                    this.modid,
                    name
                )
        );

        BannerPatternData data = new BannerPatternData(
                key,
                texture,
                name
        );

        DATA.add(data);
        return key;
    }

    public void bootstrap(Registerable<BannerPattern> registerable) {
        this.DATA.forEach(bannerPatternData -> {
            registerable.register(bannerPatternData.key, new BannerPattern(
                    bannerPatternData.texture,
                    bannerPatternData.name
            ));
        });
    }

    record BannerPatternData(RegistryKey<BannerPattern> key,
        Identifier texture,
        String name
    ) {}
}