package com.willowworksentertainment.refab_utils.blocks;

import com.willowworksentertainment.refab_utils.registry.RefabBlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.EnergySide;
import team.reborn.energy.EnergyStorage;
import team.reborn.energy.EnergyTier;

public class BaseGeneratorBlockEntity extends BlockEntity implements SidedInventory, EnergyStorage {
    public double storedEnergy;
    public double capacity = 100_000;
    public BaseGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(RefabBlockRegistry.MACHINE_BLOCK_ENTITY_TYPE, pos, state);
    }


    @Override
    public double getStored(EnergySide face) {
        return storedEnergy;
    }

    @Override
    public void setStored(double amount) {
        storedEnergy = amount;
    }

    @Override
    public double getMaxStoredPower() {
        return capacity;
    }

    @Override
    public EnergyTier getTier() {
        return EnergyTier.MEDIUM;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStack(int slot) {
        return null;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return null;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return null;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }
}
