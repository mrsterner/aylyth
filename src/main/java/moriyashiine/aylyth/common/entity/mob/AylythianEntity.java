package moriyashiine.aylyth.common.entity.mob;

import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import software.bernie.example.entity.DynamicExampleEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.ClientUtils;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;

public class AylythianEntity extends HostileEntity implements GeoAnimatable {

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	
	public AylythianEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.setCanPickUpLoot(true);
	}
	
	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 35).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5).add(EntityAttributes.GENERIC_ARMOR, 2).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
	}


	
	@Override
	public void tick() {
		super.tick();
		if (age % 200 == 0) {
			heal(1);
		}
	}
	
	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.ENTITY_AYLYTHIAN_AMBIENT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSoundEvents.ENTITY_AYLYTHIAN_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return ModSoundEvents.ENTITY_AYLYTHIAN_DEATH;
	}
	
	@Override
	public boolean spawnsTooManyForEachTry(int count) {
		return count > 3;
	}
	
	@Override
	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		return 0.5F;
	}
	
	@Override
	public int getLimitPerChunk() {
		return 3;
	}
	
	@Override
	public boolean damage(DamageSource source, float amount) {
		return super.damage(source, source.isFire() ? amount * 2 : amount);
	}
	
	@Override
	public void setTarget(@Nullable LivingEntity target) {
		if (isTargetInBush(target)) {
			target = null;
		}
		super.setTarget(target);
	}
	
	@Override
	protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
		super.dropEquipment(source, lootingMultiplier, allowDrops);
		double random = world.getRandom().nextDouble();
		if (random <= 20 && !world.isClient && world.getBlockState(getBlockPos()).getMaterial().isReplaceable() && ModBlocks.YMPE_BLOCKS.sapling.getDefaultState().canPlaceAt(world, getBlockPos())) {
			world.setBlockState(getBlockPos(), ModBlocks.YMPE_BLOCKS.sapling.getDefaultState());
			playSound(SoundEvents.BLOCK_GRASS_PLACE, getSoundVolume(), getSoundPitch());
		}else if(random <= 30){
			placeWoodyGrowths(world, getBlockPos());
		}
	}

	public void placeWoodyGrowths(World world, BlockPos blockPos){
		List<BlockPos> listPos = new ArrayList<>();
		int index = 0;
		for(int x = -1; x <= 1; x++){
			for(int z = -1; z <= 1; z++){
				for(int y = -1; y <= 1; y++){
					if(!world.isClient && world.getBlockState(blockPos.add(x,y,z)).getMaterial().isReplaceable() && world.getBlockState(blockPos.add(x,y,z).down()).isIn(BlockTags.DIRT) ){
						listPos.add(index, blockPos.add(x,y,z));
						index++;
					}
				}

			}
		}
		int random = world.getRandom().nextBetween(1, 3);
		Block largeWoodyGrowth = ModBlocks.LARGE_WOODY_GROWTH;
		for(int i = 0; i < random; i++){
			if(listPos.size() >= i){
				BlockPos placePos = listPos.get(world.getRandom().nextInt(listPos.size()));
				if(world.getRandom().nextBoolean()){
					if(largeWoodyGrowth.getDefaultState().canPlaceAt(world, placePos)){
						world.setBlockState(placePos,largeWoodyGrowth.getDefaultState());
					}
				}else{
					world.setBlockState(placePos, ModBlocks.SMALL_WOODY_GROWTH.getDefaultState());
				}
				playSound(SoundEvents.BLOCK_GRASS_PLACE, getSoundVolume(), getSoundPitch());
			}

		}
	}
	
	@Override
	protected void initGoals() {
		super.initGoals();
		goalSelector.add(0, new SwimGoal(this));
		goalSelector.add(1, new MeleeAttackGoal(this, 1.2F, false));
		goalSelector.add(2, new WanderAroundFarGoal(this, 0.5F));
		goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8));
		goalSelector.add(3, new LookAroundGoal(this));
		targetSelector.add(0, new RevengeGoal(this));
		targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	public static boolean canSpawn(EntityType<? extends MobEntity> aylythianEntityEntityType, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, Random random) {
		return canMobSpawn(aylythianEntityEntityType, serverWorldAccess, spawnReason, blockPos, random) && serverWorldAccess.getDifficulty() != Difficulty.PEACEFUL && random.nextBoolean();
	}
	
	public static boolean isTargetInBush(LivingEntity target) {
		if (target != null && target.isSneaking()) {
			for (int i = 0; i <= target.getHeight(); i++) {
				if (target.world.getBlockState(target.getBlockPos().up(i)).getBlock() != ModBlocks.AYLYTH_BUSH) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(
				new AnimationController<>(this, "Body", 10, this::poseBody),
				new AnimationController<>(this, "Arms", 0, this::poseArms)
		);
	}

	private <T extends GeoAnimatable> PlayState poseArms(AnimationState<T> state) {
		if (handSwingTicks > 0 && !isDead()) {
			state.setAnimation(RawAnimation.begin().thenLoop(getMainArm() == Arm.RIGHT ? "clawswipe_right" : "clawswipe_left"));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	private <T extends GeoAnimatable> PlayState poseBody(AnimationState<T> state) {
		float limbSwingAmount = Math.abs(state.getLimbSwingAmount());
		if (limbSwingAmount > 0.01F) {
			MoveState moveState = limbSwingAmount > 0.6F ? MoveState.RUN : limbSwingAmount > 0.3F ? MoveState.WALK : MoveState.STALK;
			switch (moveState) {
				case RUN -> state.setAnimation(RawAnimation.begin().thenLoop("run"));
				case WALK -> state.setAnimation(RawAnimation.begin().thenLoop("walk"));
				case STALK -> state.setAnimation(RawAnimation.begin().thenLoop("stalk"));
			};
		}else {
			state.setAnimation(RawAnimation.begin().thenLoop("idle"));
		}
		return PlayState.CONTINUE;
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	public double getTick(Object o) {
		return ((Entity)o).age;
	}

	enum MoveState {
		WALK, RUN, STALK
	}
}
