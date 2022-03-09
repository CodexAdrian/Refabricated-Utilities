package com.willowworksentertainment.refab_utils.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.Tag;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PlayerBreakingUtils {
    public static BlockHitResult getPlayerPOVHitResult(World level, PlayerEntity player, RaycastContext.FluidHandling fluid) {
        float pitch = player.getPitch();
        float yaw = player.getYaw();
        Vec3d eyePos = player.getEyePos();
        double yawOffsetZ = Math.cos(Math.toRadians(-yaw) - Math.PI);
        double yawOffsetX = Math.sin(Math.toRadians(-yaw) - Math.PI);
        double pitchOffsetZ = -Math.cos(Math.toRadians(pitch));
        double pitchOffsetX = Math.sin(Math.toRadians(-pitch));
        double xOffset = yawOffsetX * pitchOffsetZ;
        double zOffset = yawOffsetZ * pitchOffsetZ;
        Vec3d vec3d2 = eyePos.add(xOffset * 5.0D, pitchOffsetX * 5.0D, zOffset * 5.0D);
        return level.raycast(new RaycastContext(eyePos, vec3d2, RaycastContext.ShapeType.OUTLINE, fluid, player));
    }

    public static int veinMine(int limit, World world, BlockState state, Tag<Block> blockTag, BlockPos pos, LivingEntity miner) {
        if (state.isIn(blockTag)) {
            return veinMine(limit, world, state, pos, miner);
        }
        return 0;
    }

    public static int veinMine(int limit, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        AtomicInteger index = new AtomicInteger();
        if (miner instanceof PlayerEntity player) {
            List<BlockPos> cachedPositions = new ArrayList<>();
            cachedPositions.add(pos);
            while (index.get() < limit) {
                List<BlockPos> newCachedPositions = new ArrayList<>();
                for (BlockPos logPos : cachedPositions) {
                    BlockBox box = BlockBox.create(logPos.add(1, 1, 1), logPos.add(-1, -1, -1));
                    Set<BlockPos> logList = BlockPos.stream(box).filter(blockPos -> world.getBlockState(blockPos).isOf(state.getBlock())).filter(blockPos -> index.getAndIncrement() < limit).map(blockPos -> playerBreak(world, player, blockPos).toImmutable()).collect(Collectors.toSet());
                    newCachedPositions.addAll(logList);
                }
                if (newCachedPositions.isEmpty()) break;
                cachedPositions = newCachedPositions;
            }
        }
        return index.get();
    }

    public static BlockPos playerBreak(World world, PlayerEntity player, BlockPos pos) {
        if(!world.isClient() && world.canPlayerModifyAt(player, pos)) {
            ServerWorld serverWorld = (ServerWorld) world;
            BlockState state = world.getBlockState(pos);
            world.removeBlock(pos, false);
            serverWorld.spawnParticles(DustParticleEffect.DEFAULT, pos.getX(), pos.getY(), pos.getZ(), 10, 1, 1, 1, 3);
            state.getBlock().afterBreak(world, player, pos, state, world.getBlockEntity(pos), player.getMainHandStack());
            state.getBlock().onBreak(world, pos, state, player);
        }
        return pos;
    }

    public static void areaUseOnBlock(ItemUsageContext context, Consumer<ItemUsageContext> consumer, int range) {
        BlockBox box = BlockBox.create(context.getBlockPos().add(range, 0, range), context.getBlockPos().add(-range, 0, -range));
        BlockPos.stream(box).map(blockPos -> new BlockHitResult(context.getHitPos(), context.getSide(), blockPos.toImmutable(), false)).map(blockHitResult -> new ItemUsageContext(Objects.requireNonNull(context.getPlayer()), context.getHand(), blockHitResult)).forEach(consumer);
    }
}
