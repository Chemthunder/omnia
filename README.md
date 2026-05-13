# Omnia!
Omnia is a simple, yet powerful tool for assisting with Minecraft's data generation system, aiming to be helpful for beginners and easy to learn.

# How to Use!

## Basics
First, you need to know what you want to create. Omnia by default supports generation of **Armor Trim Materials**, **Banner Patterns**, **Damage Types**, **Dimension Types**, **Enchantments**, **Jukebox Songs**, and **Painting Variants** through **Data Registries**, which you will learn about later. 

> But, say you wanted to make your own? This is easy, typically. The steps to create a custom Data Registry will be at the bottom, in the **COMPAT** section.

Say you wanted to make a custom Damage Type. First, you'll need a class (or interface) to put them in. We will refer to this class as `ModDamageTypes`.

Inside of this class, you will (at the top) create a new `public` `static` `final` `DamageTypeRegistry`, named whatever you wish. I typically would name mine `TYPES`, `DAMAGE_TYPES`, or `DATA`. This naming scheme should be consistent with your other Data Registries.

Pre-built Data Registries take in one parameter, your mod id! Make sure it is *your* mod's id, or it won't register under your mod's namespace.

```java
public class ModDamageTypes {
    public static final DamageTypeRegistry DATA = new DamageTypeRegistry(TestMod.MOD_ID);
}
```

Then, let's actually register the Damage Type! Let's call it `SPARKLE`.

```java
public class ModDamageTypes {
    public static final DamageTypeRegistry DATA = new DamageTypeRegistry(TestMod.MOD_ID);

    public static final RegistryKey<DamageType> SPARKLE = DATA.register("sparkle", 1.0F);
}
```

Damage Types take two parameters, the *name* of the Damage Type (what to register it as, in this case it would be `death.attack.sparkle`, or `testmod:sparkle`), and the *exhaustion*. Exhaustion is the amount of hunger drained when this Damage Type is inflicted.

> All Data Registries use a method named `register`, which should be the same in custom Data Registries.

Now, we have our `SPARKLE` Damage Type, which was registered under our `DATA` DamageTypeRegistry. Next, we have to actually initialize it with our DataGen.

## Setup (Data Initializers)
> This process should be done once, only one `DataInitializer` is needed.

First, go to wherever you want to package all of your Data Registries into one `DataInitializer`. I usually do it in my actual `DataGenerationEntrypoint`, and this will be the example used. Let's go to our own `DataGenerationEntrypoint` (should be called something like `ModDataGenerator`)

At the top of your class, create a new `DataInitializer`, named whatever you wish. I usually name mine `PRIMARY`.

```java
public class ModDataGenerator implements DataGeneratorEntrypoint {
    public static final DataInitializer PRIMARY = new DataInitializer(TestMod.MOD_ID, Arrays.asList(
        ModDamageTypes.DATA
    ));

    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
    }
}
```

`DataInitializer`s take two parameters, similar to Data Registries. Your mod's id, and all of your Data Registries.

Now that we have our `DataInitializer` set up, it's time for the hard part.

You'll want a `FabricDynamicRegistriesProvider`, which in this case can be a sub class.

```java
public class ModDataGenerator implements DataGeneratorEntrypoint {
    public static final DataInitializer PRIMARY = new DataInitializer(TestMod.MOD_ID, Arrays.asList(
        ModDamageTypes.DATA
    ));

    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
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
```

This is your mod's `FabricDynamicRegistryProvider`, used for generating all files not part of Fabric's base datagen. You'll want to put the `loadConfigurations(...)` method into the `configure` method. Only do this once.

Make sure to add your `FabricDynamicRegistryProvider` to your mod's datapack!
```java
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(DynamicRegistries::new);
    }
```

Next, back in your main class, add the `buildRegistry` method, with the `PRIMARY.buildRegistries(...)` method inside.

```java
public class ModDataGenerator implements DataGeneratorEntrypoint {
    public static final DataInitializer PRIMARY = new DataInitializer(TestMod.MOD_ID, Arrays.asList(
        ModDamageTypes.DATA
    ));

    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(DynamicRegistries::new);
    }

    public void buildRegistry(RegistryBuilder registryBuilder) {
        PRIMARY.buildRegistries(registryBuilder);
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
```

and now, it's done! Simply run your data-generation task, and the registered Damage Types should appear in your `generated` package.

# COMPAT
## How to make a Custom Data Registry! (INCOMPLETE)
Making a custom Data Registry for compatibility is something that is supported by Omnia.

Simply create a new class, named something along the lines of `MyResourceRegistry`, and copy the template from any of the existing Data Registries.

```java
public class MyResourceRegistry extends DataRegistry<MyResource> {
    private final List<MyResourceData> DATA = new ArrayList<>();

    public JukeboxSongRegistry(String modid) {
        super(modid, YOUR_REGISTRY_KEY);
    }

    public RegistryKey<MyResource> register(String name, <INSERT YOUR PARAMETERS>) {
        RegistryKey<MyResource> key = RegistryKey.of(
            YOUR_REGISTRY_KEY,
            Identifier.of(
                this.modid,
                name
            )
        );

        MyResourceData data = new MyResourceData(
                key,
                <INSERT YOUR PARAMETERS>
        );

        DATA.add(data);
        return key;
    }

    public void bootstrap(Registerable<MyResource> registerable) {
        this.DATA.forEach(myResourceData -> {
            registerable.register(
                    myResourceData.key,
                    new MyResource(
                            <INSERT YOUR PARAMETERS>
                    ));
        });
    }

    record MyResourceData(RegistryKey<MyResource> key,
        <INSERT YOUR PARAMETERS>
    ) {}
}
```

Simply add the parameters of your file into the `record`, and replace the names.

Currently the field of custom Data Registries is very vague, as no clear way to create custom Data Registries is completed yet. Expect updates!