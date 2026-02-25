package com.moumoumou2333.item;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public interface M23_FabricItem extends FabricItem {

    @Override
    default ItemStack getRecipeRemainder( ItemStack stack ) {
        return this.select( stack );
    }

    ItemStack select( ItemStack stack );

    default ItemStack getRecipeRemainder( ItemStack stack, PlayerEntity player, Hand hand ) {
        return this.select( stack );
    }

}