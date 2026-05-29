package com.peak.omnia.api.provider;

import com.mojang.serialization.Codec;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * From Fabric API #1.21.11
 */
public abstract class ModdedSoundsProvider implements DataProvider {
    private static final Codec<Map<String, SoundTypeBuilderImpl.SoundType>> CODEC = Codec.unboundedMap(Codec.STRING, SoundTypeBuilderImpl.SoundType.CODEC);
    private final CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture;
    private final DataOutput output;

    public ModdedSoundsProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        this.registriesFuture = registriesFuture;
        this.output = output;
    }

    public CompletableFuture<?> run(DataWriter writer) {
        return registriesFuture.thenCompose(lookup -> {
            final Map<String, Map<String, SoundTypeBuilderImpl.SoundType>> data = new LinkedHashMap<>();
            configure(lookup, (id, builder) -> {
                if (data.computeIfAbsent(id.getNamespace(), n -> new LinkedHashMap<>()).put(id.getPath(), ((SoundTypeBuilderImpl) builder).build()) != null) {
                    throw new IllegalStateException("Duplicate sound for event " + id);
                }
            });

            return CompletableFuture.allOf(data.entrySet().stream().map(file -> {
                Path outputPath = output.resolvePath(DataOutput.OutputType.RESOURCE_PACK).resolve(file.getKey() + "/sounds.json");
                return DataProvider.writeCodecToPath(writer, lookup, CODEC, file.getValue(), outputPath);
            }).toArray(CompletableFuture[]::new));
        });
    }

    protected abstract void configure(RegistryWrapper.WrapperLookup registryLookup, SoundExporter exporter);

    @ApiStatus.NonExtendable
    @FunctionalInterface
    public interface SoundExporter {
        default void add(SoundEvent event, SoundTypeBuilder builder) {
            add(event.getId(), builder);
        }

        default void add(RegistryEntry<SoundEvent> event, SoundTypeBuilder builder) {
            add(event.getKey().orElseThrow(() -> new IllegalArgumentException("Direct (non-registered) sound event cannot be added")).getValue(), builder);
        }

        void add(Identifier id, SoundTypeBuilder builder);
    }
}
