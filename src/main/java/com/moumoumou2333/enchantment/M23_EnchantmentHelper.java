package com.moumoumou2333.enchantment;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtList;

public final class M23_EnchantmentHelper {

    private M23_EnchantmentHelper() {}

    public static ItemStack removeEnchantment( ItemStack itemStack, int index ) {
        if( itemStack.isEmpty() ) {
            return itemStack;
        }
        NbtList enchantments = itemStack.isOf( Items.ENCHANTED_BOOK ) ? EnchantedBookItem.getEnchantmentNbt( itemStack ) : itemStack.getEnchantments();
        if( enchantments.isEmpty() || index < 0 || index >= enchantments.size() ) {
            return itemStack;
        }
        enchantments.remove( index );
        if( enchantments.isEmpty() && itemStack.isOf( Items.ENCHANTED_BOOK ) ) {
            itemStack = new ItemStack( Items.BOOK );
        } else {
            itemStack.removeSubNbt( itemStack.isOf( Items.ENCHANTED_BOOK ) ? "StoredEnchantments" : "Enchantments" );
            EnchantmentHelper.set( EnchantmentHelper.fromNbt( enchantments ), itemStack );
        }
        return itemStack;
    }

}