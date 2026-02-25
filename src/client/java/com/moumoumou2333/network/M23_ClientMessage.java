package com.moumoumou2333.network;

import java.util.ArrayList;

import com.moumoumou2333.inventory.M23_Inventory;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class M23_ClientMessage {

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(
            M23_Message.ITEM_SYNC,
            ( client, handler, buf, sender ) -> {
                DefaultedList< ItemStack > list = DefaultedList.copyOf( ItemStack.EMPTY, buf.readCollection( ArrayList::new, PacketByteBuf::readItemStack ).toArray( new ItemStack[ 0 ] ) );
                BlockPos pos = buf.readBlockPos();
                if( client.world.getBlockEntity( pos ) instanceof M23_Inventory block ) {
                    block.setInventory( list );
                }
            }
        );
    }

}