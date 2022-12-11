package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.item.*;
import moriyashiine.aylyth.common.registry.util.ItemWoodSuite;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModItems {
	public static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

	public static final ItemGroup GROUP = FabricItemGroup.builder(new Identifier(Aylyth.MOD_ID, Aylyth.MOD_ID))
			.icon(() -> new ItemStack(ModItems.YMPE_DAGGER)).entries((enabledFeatures, entries, operatorEnabled) -> {
				for(Item item : ITEMS.keySet()) {
					entries.add(item);
				}
			}).build();

	public static final Item DEBUG_WAND = register("debug_wand", new DebugWandItem(new FabricItemSettings()));
	public static final Item FRUIT_BEARING_YMPE_LOG = register("fruit_bearing_ympe_log", new BlockItem(ModBlocks.FRUIT_BEARING_YMPE_LOG, settings()));

	public static final Item YMPE_LEAVES = register("ympe_leaves",new BlockItem(ModBlocks.YMPE_LEAVES, settings()));
	public static final ItemWoodSuite YMPE_ITEMS = ItemWoodSuite.of(new Identifier(Aylyth.MOD_ID, "ympe"), ModBlocks.YMPE_BLOCKS, new ItemWoodSuite.GroupedSettings(GROUP), Registries.ITEM, () -> ModBoatTypes.YMPE_BOAT_TYPE, () -> ModBoatTypes.YMPE_CHEST_BOAT_TYPE);

	public static final Item POMEGRANATE_LEAVES = register("pomegranate_leaves", new BlockItem(ModBlocks.POMEGRANATE_LEAVES, settings()));
	public static final ItemWoodSuite POMEGRANATE_ITEMS = ItemWoodSuite.of(new Identifier(Aylyth.MOD_ID, "pomegranate"), ModBlocks.POMEGRANATE_BLOCKS, new ItemWoodSuite.GroupedSettings(GROUP), Registry.ITEM, () -> ModBoatTypes.POMEGRANATE_BOAT_TYPE, () -> ModBoatTypes.POMEGRANATE_CHEST_BOAT_TYPE);

	public static final Item WRITHEWOOD_LEAVES =register("writhewood_leaves", new BlockItem(ModBlocks.WRITHEWOOD_LEAVES, settings()));
	public static final ItemWoodSuite WRITHEWOOD_ITEMS = ItemWoodSuite.of(new Identifier(Aylyth.MOD_ID, "writhewood"), ModBlocks.WRITHEWOOD_BLOCKS, new ItemWoodSuite.GroupedSettings(GROUP), Registry.ITEM, () -> ModBoatTypes.WRITHEWOOD_BOAT_TYPE, () -> ModBoatTypes.WRITHEWOOD_CHEST_BOAT_TYPE);

	public static final Item AYLYTH_BUSH = register("aylyth_bush",new BlockItem(ModBlocks.AYLYTH_BUSH, settings()));
	public static final Item ANTLER_SHOOTS = register("antler_shoots",new BlockItem(ModBlocks.ANTLER_SHOOTS, settings()));
	public static final Item GRIPWEED = register("gripweed",new BlockItem(ModBlocks.GRIPWEED, settings()));
	public static final Item CORIC_SEED = register("coric_seed",new CoricSeedItem(settings()));

	public static final Item NYSIAN_GRAPE_VINE = register("nysian_grape_vine",new BlockItem(ModBlocks.NYSIAN_GRAPE_VINE, settings()));
	public static final Item MARIGOLD = register("marigolds",new BlockItem(ModBlocks.MARIGOLD, settings()));
	public static final Item OAK_STREWN_LEAVES = register("oak_strewn_leaves",new BlockItem(ModBlocks.OAK_STREWN_LEAVES, settings()));
	public static final Item YMPE_STREWN_LEAVES = register("ympe_strewn_leaves",new BlockItem(ModBlocks.YMPE_STREWN_LEAVES, settings()));
	public static final Item JACK_O_LANTERN_MUSHROOM = register("jack_o_lantern_mushroom",new WallStandingBlockItem(ModBlocks.JACK_O_LANTERN_MUSHROOM, ModBlocks.SHELF_JACK_O_LANTERN_MUSHROOM, settings()));
	public static final Item GHOSTCAP_MUSHROOM_SPORES = register("ghostcap_mushroom_spores",new BlockItem(ModBlocks.GHOSTCAP_MUSHROOM, settings()));
	public static final Item SMALL_WOODY_GROWTH = register("small_woody_growth",new BlockItem(ModBlocks.SMALL_WOODY_GROWTH, settings()));
	public static final Item LARGE_WOODY_GROWTH = register("large_woody_growth",new BlockItem(ModBlocks.LARGE_WOODY_GROWTH, settings()));
	public static final Item WOODY_GROWTH_CACHE = register("woody_growth_cache",new BlockItem(ModBlocks.WOODY_GROWTH_CACHE, settings()));

	public static final Item OAK_SEEP = register("oak_seep",new BlockItem(ModBlocks.OAK_SEEP, settings()));
	public static final Item SPRUCE_SEEP = register("spruce_seep",new BlockItem(ModBlocks.SPRUCE_SEEP, settings()));
	public static final Item DARK_OAK_SEEP = register("dark_oak_seep",new BlockItem(ModBlocks.DARK_OAK_SEEP, settings()));
	public static final Item YMPE_SEEP = register("ympe_seep",new BlockItem(ModBlocks.YMPE_SEEP, settings()));

	public static final Item YMPE_DAGGER = register("ympe_dagger",new YmpeDaggerItem(ToolMaterials.NETHERITE, 1, -2, settings()));
	public static final Item YMPE_LANCE = register("ympe_lance",new YmpeLanceItem(312, settings()));

	public static final Item YMPE_FRUIT = register("ympe_fruit",new Item(settings().food(ModFoodComponents.YMPE_FRUIT)));
	public static final Item SHUCKED_YMPE_FRUIT = register("shucked_ympe_fruit",new ShuckedYmpeFruitItem(settings().maxCount(1)));

	public static final Item NYSIAN_GRAPES = register("nysian_grapes",new Item(settings().food(ModFoodComponents.NYSIAN_GRAPES)));
	public static final Item GHOSTCAP_MUSHROOM = register("ghostcap_mushroom",new Item(settings().food(ModFoodComponents.GHOSTCAPS)));
	public static final Item POMEGRANATE = register("pomegranate",new PomegranateItem(settings().food(ModFoodComponents.POMEGRANATE)));

	public static final Item AYLYTHIAN_HEART = register("aylythian_heart",new AylythianHeartItem(settings()));
	public static final Item WRONGMEAT = register("wrongmeat",new Item(settings().food(ModFoodComponents.WRONGMEAT)));

	public static final Item SOUL_HEARTH = register("soul_hearth",new BlockItem(ModBlocks.SOUL_HEARTH, settings()));
	public static final Item VITAL_THURIBLE = register("vital_thurible",new BlockItem(ModBlocks.VITAL_THURIBLE, settings()));
	public static final Item DARK_WOODS_TILES = register("dark_woods_tiles",new BlockItem(ModBlocks.DARK_WOODS_TILES, settings()));

	public static final Item PILOT_LIGHT_SPAWN_EGG = register("pilot_light_spawn_egg",new SpawnEggItem(ModEntityTypes.PILOT_LIGHT, 0xFFD972, 0x9FD9F6, settings()));
	public static final Item AYLYTHIAN_SPAWN_EGG = register("aylythian_spawn_egg",new SpawnEggItem(ModEntityTypes.AYLYTHIAN, 0x6A4831, 0xE58E03, settings()));
	public static final Item ELDER_AYLYTHIAN_SPAWN_EGG = register("elder_aylythian_spawn_egg",new SpawnEggItem(ModEntityTypes.ELDER_AYLYTHIAN, 0x513425, 0xFFDC9B, settings()));
	public static final Item SCION_SPAWN_EGG = register("scion_spawn_egg",new SpawnEggItem(ModEntityTypes.SCION, 0x463428, 0xE58E03, settings()));

	public static final Item YMPE_EFFIGY_ITEM =  register("ympe_effigy",new YmpeEffigyItem((settings()).fireproof().rarity(Rarity.RARE).maxCount(1)));
	public static final Item YMPE_GLAIVE =  register("ympe_glaive",new YmpeGlaiveItem(4, -3.1F, (settings()).fireproof().rarity(Rarity.UNCOMMON).maxCount(1)));

	public static final Item YMPEMOULD_ITEM = register("ympemould",new YmpemouldItem((settings()).fireproof().rarity(Rarity.UNCOMMON).maxCount(16)));


	private static <T extends Item> T register(String name, T item) {
		ITEMS.put(item, new Identifier(Aylyth.MOD_ID, name));
		return item;
	}


	private static Item.Settings settings() {
		return new FabricItemSettings();
	}

	public static void init() {
		ITEMS.keySet().forEach(item -> Registry.register(Registries.ITEM, ITEMS.get(item), item));
		YMPE_ITEMS.register();
		POMEGRANATE_ITEMS.register();
		WRITHEWOOD_ITEMS.register();

		FuelRegistry fuelRegistry = FuelRegistry.INSTANCE;
		fuelRegistry.add(YMPE_ITEMS.fence, 300);
		fuelRegistry.add(YMPE_ITEMS.fenceGate, 300);
		fuelRegistry.add(POMEGRANATE_ITEMS.fence, 300);
		fuelRegistry.add(POMEGRANATE_ITEMS.fenceGate, 300);
		CompostingChanceRegistry compostRegistry = CompostingChanceRegistry.INSTANCE;
		compostRegistry.add(YMPE_LEAVES, 0.3f);
		compostRegistry.add(YMPE_ITEMS.sapling, 0.3f);
		compostRegistry.add(POMEGRANATE_LEAVES, 0.3f);
		compostRegistry.add(POMEGRANATE_ITEMS.sapling, 0.3f);
		compostRegistry.add(OAK_STREWN_LEAVES, 0.3f);
		compostRegistry.add(YMPE_STREWN_LEAVES, 0.3f);
		compostRegistry.add(JACK_O_LANTERN_MUSHROOM, 0.3f);
		compostRegistry.add(GHOSTCAP_MUSHROOM, 0.3f);
		compostRegistry.add(POMEGRANATE, 0.3f);
	}
}
