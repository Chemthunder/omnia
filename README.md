# Omnia!
Omnia is a simple and highly useful data-generation library for modders utilizing and/or diving into more complex usage of data-generation.

The mod adds several "registries", allowing for developers to register Damage Types, Banner Patterns, and the like, similar to other objects, such as items.

To register, say, a Damage Type, simply add a new `DamageTypeRegistry` in your class (at the top preferably), with your mod's id as the parameter.

```java
public class ModDamageTypes {
    public static final DamageTypeRegistry DAMAGE_TYPES = new DamageTypeRegistry(TestMod.MOD_ID);
}
```

Then, simply create a new `RegistryKey<DamageType>` (or whatever you're registering, as a `RegistryKey<?>`), using the `DAMAGE_TYPES.register(...)` method inside of your registry.

```java
public class ModDamageTypes {
    public static final DamageTypeRegistry DAMAGE_TYPES = new DamageTypeRegistry(TestMod.MOD_ID);

    RegistryKey<DamageType> TEST = DAMAGE_TYPES.register("test", 0.0F); // Damage Types take two parameters, the name of the Damage Type (for the death message and such), and the Exhaustion value as a float. Exhaustion is how much hunger is lost when the damage is inflicted.
}
```

All of Omnia's registries use a `.register(...)` method, so don't worry about differing names!

Once this is done, you simply need to do the complex part. Luckily, Omnia has an example!

Either make a sub-class or create a new class named, `ModDynamicRegistriesProvider`, extending `FabricDynamicRegistriesProvider`.

This class will be used to register all of the things you want to generate that aren't hosted by a default `FabricProvider`.

Now, in the provided `configure` method, add this line, corresponding to the `RegistryKey` of what you want to register.

```java
public class ModDynamicRegistryProvider extends FabricDynamicRegistryProvider {
    public ModDynamicRegistryProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
        entries.addAll(wrapperLookup.getWrapperOrThrow(RegistryKeys.DAMAGE_TYPE)); // this will allow for the generation of Damage Types.
    }

    public String getName() {
        return "Mod Dynamic Registries"; // this can be called whatever you like, it's only used in the datagen caches.
    }
}
```

Now that this is set up, go to your main Data Generation Entrypoint, (should be called something like `ModDataGenerator`), and add the `buildRegistry` method. It should look like this!

```java
    public void buildRegistry(RegistryBuilder registryBuilder) {
        //
    }
```

Then, in the method, add this for each of your classes that use an Omnia Registry.

```java
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, ModDamageTypes.DAMAGE_TYPES::bootstrap);
    }
```

Now, run your `runDataGeneration` task! You should see a `damage_types` package in your `generated` package.

Repeat this process for everything using an `OmniaRegistry`

## Why isn't X working?
- Did you add your Dynamic Registries Provider into your DataPack?
To fix this, go to your `ModDataGenerator`. In the `onInitializeDataGenerator`, add it as a provider to your DataPack. Like this!
```java
public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        
        pack.addProvider(ModDynamicRegistryProvider::new);
	}
```