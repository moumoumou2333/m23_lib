package com.moumoumou2333.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.moumoumou2333.mixin_interface.M23_ExperienceDroppingBlockMixinInterface;

import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;

@Mixin( ExperienceDroppingBlock.class )
public abstract class M23_ExperienceDroppingBlockMixin implements M23_ExperienceDroppingBlockMixinInterface {

    @Accessor( "experienceDropped" )
    abstract IntProvider get_experienceDropped();

    @Override
    public IntProvider m23_getExperienceDropped() {
        return this.get_experienceDropped();
    }

    @Override
    public int m23_getExperienceDropped( Random random ) {
        return this.m23_getExperienceDropped().get( random );
    }

    @Override
    public int m23_getExperienceDropped( ServerWorld world ) {
        return this.m23_getExperienceDropped( world.random );
    }

}