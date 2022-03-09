package com.willowworksentertainment.refab_utils.registry;

import com.willowworksentertainment.refab_utils.blocks.BaseGeneratorBlock;
import com.willowworksentertainment.refab_utils.blocks.BaseGeneratorBlockEntity;
import dev.technici4n.fasttransferlib.api.energy.EnergyApi;
import dev.technici4n.fasttransferlib.api.energy.base.SimpleEnergyIo;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RefabBlockRegistry {
    public static final String MODID = "refab_utils";
    public static final Block TEST_MACHINE = new BaseGeneratorBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK));
    public static final BlockItem MACHINE_ITEM = new BlockItem(TEST_MACHINE, new Item.Settings());
    public static final BlockEntityType<BaseGeneratorBlockEntity> MACHINE_BLOCK_ENTITY_TYPE = FabricBlockEntityTypeBuilder.create(BaseGeneratorBlockEntity::new, TEST_MACHINE).build(null);

    public static void initBlocks() {
        registerMachine("test_machine", TEST_MACHINE, MACHINE_BLOCK_ENTITY_TYPE, MACHINE_ITEM);
    }

    public static void registerMachine(String name, Block block, BlockEntityType entityType, Item item) {
        Registry.register(Registry.BLOCK, new Identifier(MODID, name), block);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, name), entityType);
        Registry.register(Registry.ITEM, new Identifier(MODID, name), item);
    }
}
