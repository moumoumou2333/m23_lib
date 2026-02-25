package com.moumoumou2333.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.moumoumou2333.item.M23_FabricItem;
import com.moumoumou2333.recipe.M23_RecipeHelper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;

@Mixin( CraftingResultSlot.class )
public abstract class M23_CraftingResultSlotMixin {

    @Accessor( "input" )
    abstract RecipeInputInventory getInput();

    @ModifyVariable( method = "onTakeItem(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;)V", at = @At( "STORE" ) )
    private DefaultedList< ItemStack > defaultedListModifyVariable( DefaultedList< ItemStack > defaultedList, PlayerEntity player, ItemStack stack ) {
        if( player.getWorld().getRecipeManager().getFirstMatch( RecipeType.CRAFTING, this.getInput(), player.getWorld() ).isPresent() ) {
            ItemStack itemStack;
            if( M23_RecipeHelper.repairItemRecipeMatch( this.getInput(), player.getWorld() ) ) {
                int size = defaultedList.size();
                for( int i = 0; i < size; i ++ ) {
                    itemStack = defaultedList.get( i );
                    if( !itemStack.isEmpty() ) {
                        if( !( itemStack.getItem() instanceof M23_FabricItem ) ) {
                            break;
                        }
                        defaultedList.set( i, ItemStack.EMPTY );
                    }
                }
            } else {
                int size = defaultedList.size();
                for( int i = 0; i < size; i ++ ) {
                    itemStack = this.getInput().getStack( i );
                    if( !itemStack.isEmpty() && itemStack.getItem() instanceof M23_FabricItem item ) {
                        defaultedList.set( i, item.getRecipeRemainder( itemStack, player, Hand.MAIN_HAND ) );
                    }
                }
            }
        }
        return defaultedList;
    }

}