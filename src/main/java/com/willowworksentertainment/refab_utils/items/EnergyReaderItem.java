package com.willowworksentertainment.refab_utils.items;

import com.mojang.brigadier.LiteralMessage;
import dev.technici4n.fasttransferlib.api.energy.EnergyApi;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;

import java.util.Objects;

public class EnergyReaderItem extends Item {

    public EnergyReaderItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var io = EnergyApi.SIDED.find(context.getWorld(), context.getBlockPos(), context.getSide());
        if(io != null) {
            Objects.requireNonNull(context.getPlayer()).sendMessage(new LiteralText("" + io.getEnergy()), true);
        }
        return ActionResult.PASS;
    }
}
