package moriyashiine.aylyth.common.entity.ai.brain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.attribute.EntityAttributes;

import java.util.List;
import java.util.Optional;

public class TulpaBrain {
    private static final List<SensorType<? extends Sensor<? super TulpaEntity>>> SENSORS = List.of(
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.HURT_BY
    );
    private static final List<MemoryModuleType<?>> MEMORIES = List.of(
            MemoryModuleType.MOBS,
            MemoryModuleType.VISIBLE_MOBS,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_NEMESIS,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.ANGRY_AT,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.HOME,
            MemoryModuleType.PACIFIED,
            MemoryModuleType.NEAREST_REPELLENT,
            MemoryModuleType.AVOID_TARGET
    );

    public TulpaBrain(){}

    public static Brain<?> create(TulpaEntity tulpaEntity, Dynamic<?> dynamic) {
        Brain.Profile<TulpaEntity> profile = Brain.createProfile(MEMORIES, SENSORS);
        Brain<TulpaEntity> brain = profile.deserialize(dynamic);
        addCoreActivities(brain);
        addIdleActivities(brain);
        addFightActivities(tulpaEntity, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<TulpaEntity> brain) {
        brain.setTaskList(
                Activity.CORE,
                0,
                ImmutableList.of(
                       new StayAboveWaterTask(0.6f),
                        new LookAroundTask(45, 90),
                        new WanderAroundTask(),
                        UpdateAttackTargetTask.create(TulpaBrain::getAttackTarget)
                )
        );
    }

    private static void addIdleActivities(Brain<TulpaEntity> brain) {
        brain.setTaskList(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0, new RandomTask<>(
                                ImmutableList.of(
                                        Pair.of(StrollTask.create(0.6F), 2),
                                        Pair.of(TaskTriggerer.runIf(livingEntity -> true, GoTowardsLookTargetTask.create(0.6F, 3)), 2),
                                        Pair.of(new WaitTask(30, 60), 1)
                                )))
                )
        );
    }

    private static void addFightActivities(TulpaEntity tulpaEntity, Brain<TulpaEntity> brain) {
        brain.setTaskList(
                Activity.FIGHT,
                10,
                ImmutableList.of(
                    ForgetAttackTargetTask.create(entity -> !tulpaEntity.isEnemy(entity), TulpaBrain::setTargetInvalid, false),
                    FollowMobTask.create(mob -> isTarget(tulpaEntity, mob), (float)tulpaEntity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)),
                    RangedApproachTask.create(1.2F),
                    MeleeAttackTask.create(18)
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }

    public static void updateActivities(TulpaEntity tulpaEntity) {
        tulpaEntity.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE, Activity.EMERGE, Activity.DIG));
        tulpaEntity.setAttacking(tulpaEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
    }
    private static void setTargetInvalid(TulpaEntity tulpaEntity, LivingEntity target) {

    }

    private static Optional<? extends LivingEntity> getAttackTarget(TulpaEntity tulpaEntity) {
        return Optional.empty();//TODO
    }

    private static boolean isTarget(TulpaEntity tulpaEntity, LivingEntity entity) {
        return tulpaEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).filter(targetedEntity -> targetedEntity == entity).isPresent();
    }
}
