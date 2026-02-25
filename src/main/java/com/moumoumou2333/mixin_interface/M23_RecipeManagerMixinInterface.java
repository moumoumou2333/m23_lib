package com.moumoumou2333.mixin_interface;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import com.moumoumou2333.recipe.M23_Recipe;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public interface M23_RecipeManagerMixinInterface {

    default < C extends Inventory, T extends Recipe< C > > Map< Identifier, T > m23_getAllOfType( RecipeType< T > type ) {
        return Collections.emptyMap();
    }

    default < C extends Inventory, T extends M23_Recipe< C > > Optional< T > m23_getFirstMatch( RecipeType< T > type, BlockState state, C inventory, World world ) {
        return Optional.empty();
    }

}