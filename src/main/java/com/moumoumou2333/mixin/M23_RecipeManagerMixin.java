package com.moumoumou2333.mixin;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.moumoumou2333.mixin_interface.M23_RecipeManagerMixinInterface;
import com.moumoumou2333.recipe.M23_Recipe;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@SuppressWarnings( { "unchecked", "rawtypes" } )
@Mixin( RecipeManager.class )
public abstract class M23_RecipeManagerMixin implements M23_RecipeManagerMixinInterface {

    @Shadow
    private Map< RecipeType< ? >, Map< Identifier, Recipe< ? > > > recipes;

    @Override
    public < C extends Inventory, T extends Recipe< C > > Map< Identifier, T > m23_getAllOfType( RecipeType< T > type ) {
        return ( Map )this.recipes.getOrDefault( type, Collections.emptyMap() );
    }

    @Override
    public < C extends Inventory, T extends M23_Recipe< C > > Optional< T > m23_getFirstMatch( RecipeType< T > type, BlockState state, C inventory, World world ) {
        return this.m23_getAllOfType( type ).values().stream().filter( ( recipe ) -> { return recipe.matches( state, inventory, world ); } ).findFirst();
    }

}