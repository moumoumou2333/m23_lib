package com.moumoumou2333.mixin;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

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

    @Unique
    @Override
    public < C extends Inventory, T extends Recipe< C > > Map< Identifier, T > m23_getAllOfType( RecipeType< T > type ) {
        return ( Map )this.recipes.getOrDefault( type, Collections.emptyMap() );
    }

    @Unique
    @Override
    public < C extends Inventory, T extends Recipe< C > > List< T > m23_getAllMatches_unsorted( RecipeType< T > type, C inventory, World world ) {
        return ( List )this.m23_getAllOfType( type ).values().stream().filter( ( recipe ) -> { return recipe.matches( inventory, world ); } ).collect( Collectors.toList() );
    }

    @Unique
    @Override
    public < C extends Inventory, T extends M23_Recipe< C > > Optional< T > m23_getFirstMatch( RecipeType< T > type, BlockState state, C inventory, World world ) {
        return this.m23_getAllOfType( type ).values().stream().filter( ( recipe ) -> { return recipe.matches( state, inventory, world ); } ).findFirst();
    }

    @Unique
    @Override
    public < C extends Inventory, T extends M23_Recipe< C > > List< T > m23_getAllMatches( RecipeType< T > type, BlockState state, C inventory, World world ) {
        return ( List )this.m23_getAllOfType( type ).values().stream().filter( ( recipe ) -> { return recipe.matches( state, inventory, world ); } ).sorted( Comparator.comparing( ( recipe ) -> { return recipe.getOutput( world.getRegistryManager() ).getTranslationKey(); } ) ).collect( Collectors.toList() );
    }

    @Unique
    @Override
    public < C extends Inventory, T extends M23_Recipe< C > > List< T > m23_getAllMatches_unsorted( RecipeType< T > type, BlockState state, C inventory, World world ) {
        return ( List )this.m23_getAllOfType( type ).values().stream().filter( ( recipe ) -> { return recipe.matches( state, inventory, world ); } ).collect( Collectors.toList() );
    }

}