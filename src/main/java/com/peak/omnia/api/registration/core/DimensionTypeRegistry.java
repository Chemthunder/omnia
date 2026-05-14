package com.peak.omnia.api.registration.core;

import com.peak.omnia.api.registration.DataRegistry;
import net.minecraft.block.Block;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionType;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;

/**
 * @author Chemthunder
 */
public class DimensionTypeRegistry extends DataRegistry<DimensionType> {
    private final List<DimensionTypeData> DATA = new ArrayList<>();

    public DimensionTypeRegistry(String modid) {
        super(modid, RegistryKeys.DIMENSION_TYPE);
    }

    public RegistryKey<DimensionType> register(
        String name,
        int fixedTime,
        boolean hasSkyLight,
        boolean hasCeiling,
        boolean ultrawarm,
        boolean natural,
        double coordinateScale,
        boolean bedWorks,
        boolean respawnAnchorWorks,
        int minY,
        int height,
        int logicalHeight,
        TagKey<Block> infiniburn,
        Identifier dimensionEffects,
        float ambientLight,
        DimensionType.MonsterSettings monsterSettings
    ) {
        RegistryKey<DimensionType> key = RegistryKey.of(
                RegistryKeys.DIMENSION_TYPE,
                Identifier.of(
                        this.modid,
                        name
                )
        );

        DimensionTypeData data = new DimensionTypeData(
                key,
                OptionalLong.of(fixedTime),
                hasSkyLight,
                hasCeiling,
                ultrawarm,
                natural,
                coordinateScale,
                bedWorks,
                respawnAnchorWorks,
                minY,
                height,
                logicalHeight,
                infiniburn,
                dimensionEffects,
                ambientLight,
                monsterSettings
        );

        this.DATA.add(data);
        return key;
    };

    public void bootstrap(Registerable<DimensionType> registerable) {
        this.DATA.forEach(dimensionTypeData -> {
            registerable.register(
                    dimensionTypeData.key,
                    new DimensionType(
                            dimensionTypeData.fixedTime,
                            dimensionTypeData.hasSkyLight,
                            dimensionTypeData.hasCeiling,
                            dimensionTypeData.ultrawarm,
                            dimensionTypeData.natural,
                            dimensionTypeData.coordinateScale,
                            dimensionTypeData.bedWorks,
                            dimensionTypeData.respawnAnchorWorks,
                            dimensionTypeData.minY,
                            dimensionTypeData.height,
                            dimensionTypeData.logicalHeight,
                            dimensionTypeData.infiniburn,
                            dimensionTypeData.effects,
                            dimensionTypeData.ambientLight,
                            dimensionTypeData.monsterSettings
                    )
            );
        });
    }

    record DimensionTypeData(
            RegistryKey<DimensionType> key,
            OptionalLong fixedTime,
            boolean hasSkyLight,
            boolean hasCeiling,
            boolean ultrawarm,
            boolean natural,
            double coordinateScale,
            boolean bedWorks,
            boolean respawnAnchorWorks,
            int minY,
            int height,
            int logicalHeight,
            TagKey<Block> infiniburn,
            Identifier effects,
            float ambientLight,
            DimensionType.MonsterSettings monsterSettings
    ) {}
}