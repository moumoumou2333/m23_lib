package com.moumoumou2333.mixin_interface;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;

public interface M23_ExperienceDroppingBlockMixinInterface {

    default IntProvider m23_getExperienceDropped() {
        return UniformIntProvider.create( 0, 0 );
    }

    default int m23_getExperienceDropped( Random random ) {
        return 0;
    }

    default int m23_getExperienceDropped( ServerWorld world ) {
        return 0;
    }

}