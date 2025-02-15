package moriyashiine.aylyth.datagen.worldgen.terrain;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.GenerationShapeConfig;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import net.minecraft.world.gen.noise.NoiseRouter;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

import java.util.List;

import static moriyashiine.aylyth.datagen.worldgen.terrain.AylythDensityFunctionTypes.*;
import static moriyashiine.aylyth.datagen.worldgen.terrain.AylythNoiseTypes.*;
import static net.minecraft.world.gen.densityfunction.DensityFunctionTypes.*;

public class AylythNoiseSettings {

    public static void datagenInit() {
        AylythNoiseTypes.init();
        AylythDensityFunctionTypes.init();
        Registry.register(BuiltinRegistries.CHUNK_GENERATOR_SETTINGS, new Identifier(Aylyth.MOD_ID, "aylyth_settings"), createSettings());
    }

    static ChunkGeneratorSettings createSettings() {
        var seaLevel = 47;
        var disableMobGen = false;
        var aquifers = true;
        var oreVeins = false;
        var legacyRandom = false;
        return new ChunkGeneratorSettings(shapeConfig(), defaultState(Blocks.DEEPSLATE), defaultState(Blocks.WATER), noiseRouter(), AylythMaterialRules.materialRules(), spawnTargets(), seaLevel, disableMobGen, aquifers, oreVeins, legacyRandom);
    }

    static BlockState defaultState(Block block) {
        return block.getDefaultState();
    }

    static GenerationShapeConfig shapeConfig() {
        var minY = -64;
        var height = 304;
        var horizontal = 1;
        var vertical = 1;
        return new GenerationShapeConfig(minY, height, horizontal, vertical);
    }

    static NoiseRouter noiseRouter() {
        return new NoiseRouter(
                zero(),  // barrierNoise
                noise(FLOODEDNESS, 1.0D, 0.67D),  // fluidLevelFloodednessNoise
                noise(FLUID_SPREAD, 1.0D, 1D / 1.4D),  // fluidLevelSpreadNoise
                zero(),  // lavaNoise
                shiftedNoise(holderFunction(SHIFT_X), holderFunction(SHIFT_Z), 0.25, TEMPERATURE),  // temperature
                shiftedNoise(holderFunction(SHIFT_X), holderFunction(SHIFT_Z), 0.25, VEGETATION),  // vegetation
                holderFunction(CONTINENTS_FUNCTION),  // continents
                holderFunction(EROSION_FUNCTION),  // erosion
                holderFunction(DEPTH_FUNCTION),  // depth
                holderFunction(RIDGES_FUNCTION),  // ridges
                initialDensity(),  // initialDensityWithoutJaggedness
                finalDensity(),  // finalDensity
                zero(),  // veinToggle
                zero(),  // veinRidged
                zero()); // veinGap
    }

    static DensityFunction initialDensity() {
        return slide(
                add(
                        constant(-0.703125),
                        mul(
                                constant(4),
                                mul(
                                        holderFunction(DEPTH_FUNCTION),
                                        cache2d(holderFunction(FACTOR_FUNCTION))
                                ).quarterNegative()
                        )
                ).clamp(-64, 64),
                -64,
                272,
                80, 64, -0.078125,
                0, 24, 0.1171875
        );
//        return slide(
//                add(
//                        constant(-0.703125),
//                        mul(
//                                constant(4.0),
//                                mul(
//                                        ,
//                                ).quarterNegative()
//                        )
//                ).clamp(-64.0, 64.0),
//                -64,
//                272,
//                80, 64, -0.078125,
//                0, 24, 0.1171875
//        );
    }

    static DensityFunction finalDensity() {
        return min(
                postProcess(
                        slide(
                                rangeChoice(
                                        holderFunction(SLOPED_CHEESE_FUNCTION),
                                        -1000000.0,
                                        1.5625,
                                        min(
                                                holderFunction(SLOPED_CHEESE_FUNCTION),
                                                mul(
                                                        constant(5.0),
                                                        holderFunction(CAVES_ENTRANCES_FUNCTION)
                                                )
                                        ),
                                        max(
                                                min(
                                                        min(
                                                                add(
                                                                        mul(
                                                                                constant(4.0),
                                                                                noise(CAVE_LAYER, 1.0, 8.0).square()
                                                                        ),
                                                                        add(
                                                                                add(
                                                                                        constant(0.27),
                                                                                        noise(CAVE_CHEESE, 2.0, 0.95)
                                                                                ).clamp(-1.0, 1.0),
                                                                                add(
                                                                                        constant(1.5),
                                                                                        mul(
                                                                                                constant(-0.64),
                                                                                                holderFunction(SLOPED_CHEESE_FUNCTION)
                                                                                        )
                                                                                ).clamp(0.0, 0.5)
                                                                        )
                                                                ),
                                                                holderFunction(CAVES_ENTRANCES_FUNCTION)
                                                        ),
                                                        add(
                                                                holderFunction(CAVES_SPAGHETTI_2D_FUNCTION),
                                                                holderFunction(CAVES_SPAGHETTI_ROUGHNESS_FUNCTION)
                                                        )
                                                ),
                                                rangeChoice(
                                                        holderFunction(CAVES_PILLARS_FUNCTION),
                                                        -1000000.0,
                                                        0.03,
                                                        constant(-1000000.0),
                                                        holderFunction(CAVES_PILLARS_FUNCTION)
                                                )
                                        )
                                ),
                                -64,
                                272,
                                80, 64, -0.078125,
                                0, 24, 0.1171875
                        )
                ),
                holderFunction(CAVES_NOODLE_FUNCTION)
        );
    }

    static MaterialRules.MaterialRule emptyRules() {
        return MaterialRules.block(Blocks.STONE.getDefaultState());
    }

    static List<MultiNoiseUtil.NoiseHypercube> spawnTargets() {
        return List.of();
    }
}