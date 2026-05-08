package com.peak.omnia.api.template;

import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.entity.damage.DamageType;
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
public class JukeboxSongRegistry {
    private final String modid;
    private final List<JukeboxSongData> DATA = new ArrayList<>();

    public JukeboxSongRegistry(String modid) {
        this.modid = modid;
    }

    public RegistryKey<JukeboxSong> register(String name, RegistryEntry<SoundEvent> song, Text description, float lengthInSeconds, int comparatorOutput) {
        RegistryKey<JukeboxSong> key = RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(this.modid, name));

        JukeboxSongData data = new JukeboxSongData(
                key,
                song,
                description,
                lengthInSeconds,
                comparatorOutput
        );

        DATA.add(data);
        return key;
    }

    public void bootstrap(Registerable<JukeboxSong> registerable) {
        this.DATA.forEach(jukeboxSongData -> {
            registerable.register(
                    jukeboxSongData.key,
                    new JukeboxSong(
                            jukeboxSongData.song,
                            jukeboxSongData.description,
                            jukeboxSongData.lengthInSeconds,
                            jukeboxSongData.comparatorOutput
                    ));
        });
    }

    record JukeboxSongData(RegistryKey<JukeboxSong> key, RegistryEntry<SoundEvent> song, Text description, float lengthInSeconds, int comparatorOutput) {}
}
