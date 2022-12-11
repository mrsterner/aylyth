package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSoundEvents {
	public static final SoundEvent BLOCK_YMPE_LOG_PICK_FRUIT = register(new Identifier(Aylyth.MOD_ID, "block.ympe_log.pick_fruit"));

	public static final SoundEvent BLOCK_STREWN_LEAVES_STEP = register(new Identifier(Aylyth.MOD_ID, "block.strewn_leaves.step"));
	public static final SoundEvent BLOCK_STREWN_LEAVES_PILE_DESTROY = register(new Identifier(Aylyth.MOD_ID, "block.strewn_leaves.pile_destroy"));
	public static final SoundEvent BLOCK_STREWN_LEAVES_PILE_STEP = register(new Identifier(Aylyth.MOD_ID, "block.strewn_leaves.pile_step"));
	public static final SoundEvent BLOCK_STICK_BREAK = register(new Identifier(Aylyth.MOD_ID, "block.stick_break"));
	public static final BlockSoundGroup STREWN_LEAVES = new BlockSoundGroup(1.0F, 1.0F, BLOCK_STREWN_LEAVES_STEP, BLOCK_STREWN_LEAVES_STEP, BLOCK_STREWN_LEAVES_STEP, BLOCK_STREWN_LEAVES_STEP, BLOCK_STREWN_LEAVES_STEP);
	public static final BlockSoundGroup LEAF_PILES = new BlockSoundGroup(1.0F, 1.0F, BLOCK_STREWN_LEAVES_PILE_DESTROY, BLOCK_STREWN_LEAVES_PILE_STEP, BLOCK_STREWN_LEAVES_PILE_DESTROY, BLOCK_STREWN_LEAVES_STEP, BLOCK_STREWN_LEAVES_PILE_DESTROY);
	public static final BlockSoundGroup STREWN_LEAVES_STICK = new BlockSoundGroup(1.0F, 1.0F, BLOCK_STREWN_LEAVES_STEP, BLOCK_STICK_BREAK, BLOCK_STREWN_LEAVES_STEP, BLOCK_STREWN_LEAVES_STEP, BLOCK_STREWN_LEAVES_STEP);
	public static final BlockSoundGroup LEAF_PILES_STICK = new BlockSoundGroup(1.0F, 1.0F, BLOCK_STREWN_LEAVES_PILE_DESTROY, BLOCK_STICK_BREAK, BLOCK_STREWN_LEAVES_PILE_DESTROY, BLOCK_STREWN_LEAVES_STEP, BLOCK_STREWN_LEAVES_PILE_DESTROY);

	public static final SoundEvent ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE = register(new Identifier(Aylyth.MOD_ID, "entity.player.increase_ympe_infestation_stage"));
	public static final SoundEvent ENTITY_GENERIC_SHUCKED = register(new Identifier(Aylyth.MOD_ID, "entity.generic.shucked"));
	
	public static final SoundEvent ENTITY_AYLYTHIAN_AMBIENT = register(new Identifier(Aylyth.MOD_ID, "entity.aylythian.ambient"));
	public static final SoundEvent ENTITY_AYLYTHIAN_HURT = register(new Identifier(Aylyth.MOD_ID, "entity.aylythian.hurt"));
	public static final SoundEvent ENTITY_AYLYTHIAN_DEATH = register(new Identifier(Aylyth.MOD_ID, "entity.aylythian.death"));
	
	public static final SoundEvent ENTITY_ELDER_AYLYTHIAN_AMBIENT = register(new Identifier(Aylyth.MOD_ID, "entity.elder_aylythian.ambient"));
	public static final SoundEvent ENTITY_ELDER_AYLYTHIAN_HURT = register(new Identifier(Aylyth.MOD_ID, "entity.elder_aylythian.hurt"));
	public static final SoundEvent ENTITY_ELDER_AYLYTHIAN_DEATH = register(new Identifier(Aylyth.MOD_ID, "entity.elder_aylythian.death"));
	


	public static final SoundEvent ENTITY_SOULMOULD_AMBIENT = register(new Identifier(Aylyth.MOD_ID, "entity.soulmould.ambient"));
	public static final SoundEvent ENTITY_SOULMOULD_ATTACK = register(new Identifier(Aylyth.MOD_ID, "entity.soulmould.attack"));
	public static final SoundEvent ENTITY_SOULMOULD_HURT = register(new Identifier(Aylyth.MOD_ID, "entity.soulmould.hurt"));

	public static final SoundEvent ENTITY_SOULMOULD_DEATH = register(new Identifier(Aylyth.MOD_ID, "entity.soulmould.death"));

	public static final SoundEvent ENTITY_SCION_AMBIENT = register(new Identifier(Aylyth.MOD_ID, "entity.scion.ambient"));
	public static final SoundEvent ENTITY_SCION_HURT = register(new Identifier(Aylyth.MOD_ID, "entity.scion.hurt"));
	public static final SoundEvent ENTITY_SCION_DEATH = register(new Identifier(Aylyth.MOD_ID, "entity.scion.death"));

	public static final RegistryEntry.Reference<SoundEvent> AMBIENT_FOREST_ADDITIONS = registerReference(new Identifier(Aylyth.MOD_ID,"ambient.forest.additions"));
	private static RegistryEntry.Reference<SoundEvent> registerReference(Identifier id) {
		return Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(id));
	}

	private static SoundEvent register(Identifier id) {
		return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
	}
	public static void init() {
	}
}
