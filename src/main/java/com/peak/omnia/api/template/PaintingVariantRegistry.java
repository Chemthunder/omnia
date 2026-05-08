package com.peak.omnia.api.template;

import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public class PaintingVariantRegistry {
    private final String modid;
    private final List<PaintingVariantData> DATA = new ArrayList<>();

    public PaintingVariantRegistry(String modid) {
        this.modid = modid;
    }

    public RegistryKey<PaintingVariant> register(String name, int width, int height, Identifier texture) {
        RegistryKey<PaintingVariant> key = RegistryKey.of(RegistryKeys.PAINTING_VARIANT, Identifier.of(this.modid, name));

        PaintingVariantData data = new PaintingVariantData(
                key,
                width,
                height,
                texture
        );

        DATA.add(data);
        return key;
    }

    public void bootstrap(Registerable<PaintingVariant> registerable) {
        this.DATA.forEach(paintingVariantData -> {
            registerable.register(paintingVariantData.key, new PaintingVariant(
                    paintingVariantData.width,
                    paintingVariantData.height,
                    paintingVariantData.texture
            ));
        });
    }

    record PaintingVariantData(RegistryKey<PaintingVariant> key, int width, int height, Identifier texture) {}
}
