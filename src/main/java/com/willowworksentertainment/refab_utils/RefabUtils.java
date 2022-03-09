package com.willowworksentertainment.refab_utils;

import com.willowworksentertainment.refab_utils.registry.RefabBlockRegistry;
import com.willowworksentertainment.refab_utils.registry.RefabItemRegistry;
import net.fabricmc.api.ModInitializer;

public class RefabUtils implements ModInitializer {

	@Override
	public void onInitialize() {
		RefabBlockRegistry.initBlocks();
		RefabItemRegistry.initItems();
		System.out.println("Hello Fabric world!");
	}
}
