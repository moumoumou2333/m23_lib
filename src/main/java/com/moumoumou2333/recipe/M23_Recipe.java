package com.moumoumou2333.recipe;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.world.World;

public interface M23_Recipe< C extends Inventory > extends Recipe< C > {

    default boolean matches( C inventory, World world ) {
        return false;
    }

    boolean matches( BlockState state, C inventory, World world );

}