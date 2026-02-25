package com.moumoumou2333.mixin_interface;

public interface M23_LivingEntityMixinInterface {

    default float m23_getLastDamageTaken() {
        return 0;
    }

}