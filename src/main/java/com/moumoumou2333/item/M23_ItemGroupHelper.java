package com.moumoumou2333.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public final class M23_ItemGroupHelper {

    private M23_ItemGroupHelper() {}

    public static ItemStack getCustomTextureItemStack( ItemConvertible item, boolean customTexture, int num ) {
        ItemStack itemStack = new ItemStack( item );
        if( customTexture ) {
            NbtCompound nbt = new NbtCompound();
            nbt.putInt( "CustomModelData", num );
            itemStack.setNbt( nbt );
        }
        return itemStack;
    }

    public static ItemStack getEnchantedBook( Enchantment enchantment ) {
        return EnchantedBookItem.forEnchantment( new EnchantmentLevelEntry( enchantment, enchantment.getMaxLevel() ) );
    }

    public static ItemStack getEnchantedBook( Enchantment enchantment, int level ) {
        int max_level = enchantment.getMaxLevel();
        return EnchantedBookItem.forEnchantment( new EnchantmentLevelEntry( enchantment, level >= 1 ? ( level < max_level ? level : max_level ) : 1 ) );
    }

}