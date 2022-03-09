package com.willowworksentertainment.refab_utils.registry;

import com.willowworksentertainment.refab_utils.items.EnergyReaderItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.willowworksentertainment.refab_utils.registry.RefabBlockRegistry.MODID;

public class RefabItemRegistry {
    public static final Item READER_ITEM = new EnergyReaderItem(new Item.Settings().maxCount(1));

    public static void initItems() {
        Registry.register(Registry.ITEM, new Identifier(MODID, "reader_item"), READER_ITEM);
    }
}
