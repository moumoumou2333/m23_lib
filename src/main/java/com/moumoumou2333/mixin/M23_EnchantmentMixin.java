package com.moumoumou2333.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.moumoumou2333.mixin_interface.M23_EnchantmentMixinInterface;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

@Mixin( Enchantment.class )
public abstract class M23_EnchantmentMixin implements M23_EnchantmentMixinInterface {

    @Shadow
    public abstract int getMaxLevel();

    @Shadow
    public abstract String getTranslationKey();

    @Shadow
    public abstract Text getName( int level );

    @Shadow
    public abstract boolean isCursed();

    @Override
    public Text m23_getName( int level, String color, String color_isCursed ) {
        Formatting c = Formatting.byName( color );
        Formatting c_i = Formatting.byName( color_isCursed );
        if( c == null || c_i == null ) {
            return this.getName( level );
        }
        MutableText mutableText = Text.translatable( this.getTranslationKey() ).formatted( this.isCursed() ? c_i : c );
        if( level != 1 || this.getMaxLevel() != 1 ) {
            mutableText.append( ScreenTexts.SPACE ).append( Text.translatable( "enchantment.level." + level ) );
        }
        return mutableText;
    }

}