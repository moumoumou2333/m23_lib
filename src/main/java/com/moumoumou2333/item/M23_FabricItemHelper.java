package com.moumoumou2333.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public final class M23_FabricItemHelper {

    private M23_FabricItemHelper() {}

    public static ItemStack stay( ItemStack stack ) {
        return stack.copy();
    }

    public static ItemStack damage( ItemStack stack ) {
        return damage( stack, 1 );
    }

    public static ItemStack damage( ItemStack stack, int amount ) {
        if( stack.isDamageable() && stack.getDamage() < stack.getMaxDamage() - amount ) {
            ItemStack itemStack = stack.copy();
            itemStack.setDamage( itemStack.getDamage() + amount );
            return itemStack;
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack damage( ItemStack stack, PlayerEntity player, Hand hand, int amount ) {
        if( stack.isDamageable() && stack.getDamage() < stack.getMaxDamage() - 1 ) {
            ItemStack itemStack = stack.copy();
            itemStack.damage( amount, player, ( e ) -> { e.sendToolBreakStatus( hand ); } );
            return itemStack;
        }
        return ItemStack.EMPTY;
    }

}