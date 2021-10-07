package moriyashiine.aylyth.common.registry;

import com.google.common.collect.ImmutableList;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.world.dimension.AylythBiomeSource;
import moriyashiine.aylyth.common.world.generator.AylthianTrunkPlacer;
import moriyashiine.aylyth.common.world.generator.BigYmpeTrunkPlacer;
import moriyashiine.aylyth.common.world.generator.YmpeTrunkPlacer;
import moriyashiine.aylyth.mixin.TrunkPlacerTypeAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.source.CheckerboardBiomeSource;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BushFoliagePlacer;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModWorldGenerators extends DefaultBiomeFeatures {
	private static final BlockStateProvider YMPE_LOG_PROVIDER = new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.YMPE_LOG.getDefaultState(), 15).add(ModBlocks.FRUIT_BEARING_YMPE_LOG.getDefaultState(), 1).build());
	public static final TrunkPlacerType<AylthianTrunkPlacer> AYLYTHIAN_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":aylythian_trunk_placer", AylthianTrunkPlacer.CODEC);
	public static final TrunkPlacerType<YmpeTrunkPlacer> YMPE_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":ympe_trunk_placer", YmpeTrunkPlacer.CODEC);
	public static final TrunkPlacerType<BigYmpeTrunkPlacer> BIG_YMPE_TRUNK_PLACER = TrunkPlacerTypeAccessor.callRegister(Aylyth.MOD_ID + ":big_ympe_trunk_placer", BigYmpeTrunkPlacer.CODEC);
	
	public static final ConfiguredFeature<TreeFeatureConfig, ?> AYLYTHIAN_DARK_OAK = Feature.TREE.configure(new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.DARK_OAK_LOG.getDefaultState()), new AylthianTrunkPlacer(), new SimpleBlockStateProvider(Blocks.DARK_OAK_LEAVES.getDefaultState()), new SimpleBlockStateProvider(Blocks.DARK_OAK_SAPLING.getDefaultState()), new DarkOakFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)), new TwoLayersFeatureSize(1, 1, 2)).ignoreVines().build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> AYLYTHIAN_MEGA_DARK_OAK = Feature.TREE.configure(new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.DARK_OAK_LOG.getDefaultState()), new AylthianTrunkPlacer(18, 6, 7), new SimpleBlockStateProvider(Blocks.DARK_OAK_LEAVES.getDefaultState()), new SimpleBlockStateProvider(Blocks.DARK_OAK_SAPLING.getDefaultState()), new DarkOakFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)), new TwoLayersFeatureSize(1, 1, 2)).ignoreVines().build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> YMPE_TREE = Feature.TREE.configure(new TreeFeatureConfig.Builder(YMPE_LOG_PROVIDER, new YmpeTrunkPlacer(), new SimpleBlockStateProvider(ModBlocks.YMPE_LEAVES.getDefaultState()), new SimpleBlockStateProvider(ModBlocks.YMPE_SAPLING.getDefaultState()), new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2), new TwoLayersFeatureSize(1, 1, 1)).ignoreVines().build());
	public static final ConfiguredFeature<TreeFeatureConfig, ?> BIG_YMPE_TREE = Feature.TREE.configure(new TreeFeatureConfig.Builder(YMPE_LOG_PROVIDER, new BigYmpeTrunkPlacer(), new SimpleBlockStateProvider(ModBlocks.YMPE_LEAVES.getDefaultState()), new SimpleBlockStateProvider(ModBlocks.YMPE_SAPLING.getDefaultState()), new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2), new TwoLayersFeatureSize(1, 1, 1)).ignoreVines().build());
	public static final ConfiguredFeature<?, ?> ROOF_TREES = AYLYTHIAN_DARK_OAK.decorate(Decorators.SQUARE_HEIGHTMAP_OCEAN_FLOOR_NO_WATER).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(8, 0.3F, 2)));
	public static final ConfiguredFeature<?, ?> DEEP_ROOF_TREES = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(AYLYTHIAN_DARK_OAK.withChance(0.25F)), AYLYTHIAN_MEGA_DARK_OAK)).decorate(Decorators.SQUARE_HEIGHTMAP_OCEAN_FLOOR_NO_WATER).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(8, 0.4F, 3)));
	public static final ConfiguredFeature<?, ?> FOREST_TREES = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(YMPE_TREE.withChance(0.1F), BIG_YMPE_TREE.withChance(0.1F)), ConfiguredFeatures.DARK_OAK)).decorate(Decorators.SQUARE_HEIGHTMAP_OCEAN_FLOOR_NO_WATER).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(3, 0.1F, 1)));

	public static void init() {
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "aylythian_dark_oak"), AYLYTHIAN_DARK_OAK);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "aylythian_mega_dark_oak"), AYLYTHIAN_MEGA_DARK_OAK);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "ympe_tree"), YMPE_TREE);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "big_ympe_tree"), BIG_YMPE_TREE);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "roof_trees"), ROOF_TREES);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "deep_roof_trees"), DEEP_ROOF_TREES);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Aylyth.MOD_ID, "forest_trees"), FOREST_TREES);
		Registry.register(Registry.BIOME_SOURCE, new Identifier(Aylyth.MOD_ID, "aylyth_biome_provider"), AylythBiomeSource.CODEC);
	}
	
	static class Decorators {
		private static final ConfiguredDecorator<?> HEIGHTMAP_OCEAN_FLOOR = Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.OCEAN_FLOOR));
		private static final ConfiguredDecorator<?> HEIGHTMAP_OCEAN_FLOOR_NO_WATER = HEIGHTMAP_OCEAN_FLOOR.decorate(Decorator.WATER_DEPTH_THRESHOLD.configure(new WaterDepthThresholdDecoratorConfig(0)));
		private static final ConfiguredDecorator<?> SQUARE_HEIGHTMAP_OCEAN_FLOOR_NO_WATER = HEIGHTMAP_OCEAN_FLOOR_NO_WATER.spreadHorizontally();
	}
}
