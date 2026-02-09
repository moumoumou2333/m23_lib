package com.moumoumou2333.inventory;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface M23_Inventory extends Inventory {

    DefaultedList< ItemStack > getItems();

    @Override
    default int size() {
        return this.getItems().size();
    }

    @Override
    default boolean isEmpty() {
        for ( int i = 0; i < size(); ++ i ) {
            ItemStack stack = this.getStack( i );
            if ( !stack.isEmpty() ) {
                return false;
            }
        }
        return true;
    }

    default < T extends Block > boolean isEmpty( T block ) {
        return this.isEmpty();
    }

    @Override
    default ItemStack getStack( int slot ) {
        return this.getItems().get( slot );
    }

    @Override
    default ItemStack removeStack( int slot, int count ) {
        ItemStack result = Inventories.splitStack( this.getItems(), slot, count );
        if ( !result.isEmpty() ) {
            this.markDirty();
        }
        return result;
    }

    @Override
    default ItemStack removeStack( int slot ) {
        return Inventories.removeStack( this.getItems(), slot );
    }

    @Override
    default void setStack( int slot, ItemStack stack ) {
        this.getItems().set( slot, stack );
        if ( stack.getCount() > getMaxCountPerStack() ) {
            stack.setCount( getMaxCountPerStack() );
        }
        this.markDirty();
    }

    @Override
    default void clear() {
        this.getItems().clear();
    }

    @Override
    default boolean canPlayerUse( PlayerEntity player ) {
        return true;
    }

    default boolean isFull() {
        for ( int i = 0; i < size(); ++ i ) {
            ItemStack stack = this.getStack( i );
            if ( stack.isEmpty() ) {
                return false;
            }
        }
        return true;
    }

    default void setInventory( DefaultedList< ItemStack > inventory ) {
        for( int i = 0; i < inventory.size(); i ++ ) {
            this.getItems().set( i, inventory.get( i ) );
        }
    }

}