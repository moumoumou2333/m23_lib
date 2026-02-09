package com.moumoumou2333.item;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class M23_ItemGroupHelper {

    public static ItemStack get_c_ItemStack( ItemConvertible item, boolean customTexture, int num ) {
        ItemStack itemStack = new ItemStack( item );
        if( customTexture ) {
            NbtCompound nbt = new NbtCompound();
            nbt.putInt( "CustomModelData", num );
            itemStack.setNbt( nbt );
        }
        return itemStack;
    }

}