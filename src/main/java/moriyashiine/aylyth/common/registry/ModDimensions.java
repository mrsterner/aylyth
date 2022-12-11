package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.world.dimension.AylythBiomeSource;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;

public class ModDimensions {

	public static void datagenInit() {}

	//refer to json files in data
	public static final RegistryKey<DimensionOptions> AYLYTH_DIMENSION_KEY = RegistryKey.of(RegistryKeys.DIMENSION, new Identifier(Aylyth.MOD_ID, "aylyth"));
	
	public static final RegistryKey<World> AYLYTH = RegistryKey.of(RegistryKeys.WORLD, AYLYTH_DIMENSION_KEY.getValue());

	static {
		Registry.register(Registries.BIOME_SOURCE, new Identifier(Aylyth.MOD_ID, "aylyth_biome_provider"), AylythBiomeSource.CODEC);
	}
}
