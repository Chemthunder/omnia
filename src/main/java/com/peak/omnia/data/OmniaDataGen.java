package com.peak.omnia.data;

import com.peak.omnia.api.registration.DataInitializer;
import com.peak.omnia.api.registration.specific.DamageTypeRegistry;
import com.peak.omnia.api.registration.specific.JukeboxSongRegistry;
import com.peak.omnia.impl.Omnia;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class OmniaDataGen implements DataGeneratorEntrypoint {
    public static final DataInitializer PRIMARY = new DataInitializer(Omnia.MOD_ID, Arrays.asList(
            DamageTypes.DATA
    ));

	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(DynamicRegistries::new);
	}

    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, DamageTypes.DATA::bootstrap);
    }

    public static class DamageTypes {
        public static final DamageTypeRegistry DATA = new DamageTypeRegistry(Omnia.MOD_ID);

        public static final RegistryKey<DamageType> TEST = DATA.register("test", 0.0F);
    }

    public static class DynamicRegistries extends FabricDynamicRegistryProvider {
        public DynamicRegistries(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
            PRIMARY.loadConfigurations(wrapperLookup, entries);
        }

        public String getName() {
            return "Dynamic Registries Test";
        }
    }
}