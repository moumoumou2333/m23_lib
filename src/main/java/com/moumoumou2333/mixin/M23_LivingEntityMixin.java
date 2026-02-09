package com.moumoumou2333.mixin;

import net.minecraft.entity.LivingEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import com.moumoumou2333.mixin_interface.M23_LivingEntityMixinInterface;

@Mixin( LivingEntity.class )
public abstract class M23_LivingEntityMixin implements M23_LivingEntityMixinInterface {

	@Shadow
    protected float lastDamageTaken;

    @Unique
	@Override
	public float m23_getLastDamageTaken() {
        return this.lastDamageTaken;
    }

}