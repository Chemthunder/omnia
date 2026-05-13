package com.peak.omnia.impl;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Chemthunder
 */
public class Omnia implements ModInitializer, DataGeneratorEntrypoint {
	public static final String MOD_ID = "omnia";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public void onInitialize() {
		LOGGER.info("Omnia has initialized internally!");
	}

    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

    //    pack.addProvider(DynamicRegistries::new);
    }

    public void buildRegistry(RegistryBuilder registryBuilder) {
      //  PRIMARY.buildRegistries(registryBuilder);
    }



//    public static final DataInitializer PRIMARY = new DataInitializer(Omnia.MOD_ID, Arrays.asList(
//            DamageTypes.DATA,
//            BannerPatterns.DATA
//    ));
//
//    public static class DamageTypes {
//        public static final DamageTypeRegistry DATA = new DamageTypeRegistry(Omnia.MOD_ID);
//
//        public static final RegistryKey<DamageType> TEST = DATA.register("test", 0.0F);
//        public static final RegistryKey<DamageType> TEST1 = DATA.register("test1", 0.5F);
//        public static final RegistryKey<DamageType> TEST2 = DATA.register("test2", 1.0F);
//    }
//
//    public static class BannerPatterns {
//        public static final BannerPatternRegistry DATA = new BannerPatternRegistry(Omnia.MOD_ID);
//
//        public static final RegistryKey<BannerPattern> TEST = DATA.register("test_banner", Identifier.of(Omnia.MOD_ID, "test_pattern"));
//    }
//
//
//
//    public static class DynamicRegistries extends FabricDynamicRegistryProvider {
//        public DynamicRegistries(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
//            super(output, registriesFuture);
//        }
//
//        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
//            PRIMARY.loadConfigurations(wrapperLookup, entries);
//        }
//
//        public String getName() {
//            return "Dynamic Registries Test";
//        }
//    }
}