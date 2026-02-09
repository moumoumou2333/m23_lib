package com.moumoumou2333.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class M23_SyncHelper {

    public static void syncInventory( World world, DefaultedList< ItemStack > items, BlockPos pos ) {
        if( !world.isClient ) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt( items.size() );
            for( int i = 0; i < items.size(); i ++ ) {
                data.writeItemStack( items.get( i ) );
            }
            data.writeBlockPos( pos );
            for( ServerPlayerEntity serverPlayerEntity : PlayerLookup.tracking( ( ServerWorld ) world, pos ) ) {
                ServerPlayNetworking.send( serverPlayerEntity, M23_Message.ITEM_SYNC, data );
            }
        }
    }

}