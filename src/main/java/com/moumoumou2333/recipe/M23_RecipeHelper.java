package com.moumoumou2333.recipe;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

public class M23_RecipeHelper {

    public static ItemStack outputFromJson( JsonObject json ) {
        if ( json.has( "data" ) ) {
            throw new JsonParseException( "Disallowed data tag found" );
        } else {
            int i = JsonHelper.getInt( json, "count", 1 );
            if ( i < 1 ) {
                throw new JsonSyntaxException( "Invalid output count: " + i );
            } else {
                Item item = ShapedRecipe.getItem( json );
                int j = JsonHelper.getInt( json, "customtexture", 0 );
                ItemStack itemStack = new ItemStack( item, i );
                if( j != 0  ) {
                    NbtCompound nbt = new NbtCompound();
                    nbt.putInt( "CustomModelData", j );
                    itemStack.setNbt( nbt );
                }
                return itemStack;
            }
        }
    }

    public static boolean RepairItemRecipeMatch ( RecipeInputInventory recipeInputInventory, World world ) {
        List< ItemStack > list = Lists.newArrayList();
        for( int i = 0; i < recipeInputInventory.size(); i ++ ) {
            ItemStack itemStack = recipeInputInventory.getStack( i );
            if ( !itemStack.isEmpty() ) {
                list.add( itemStack );
                if ( list.size() > 1 ) {
                    ItemStack itemStack2 = ( ItemStack )list.get( 0 );
                    if ( !itemStack.isOf( itemStack2.getItem() ) || itemStack2.getCount() != 1 || itemStack.getCount() != 1 || !itemStack2.getItem().isDamageable() ) {
                        return false;
                    }
                }
            }
        }
        return list.size() == 2;
    }

}