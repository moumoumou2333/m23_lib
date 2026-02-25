package com.moumoumou2333.mixin_interface;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;

public interface M23_EnchantmentMixinInterface {

    default Text m23_getName( int level, String color, String color_isCursed ) {
        return MutableText.of( TextContent.EMPTY );
    }

}