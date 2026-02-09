package com.moumoumou2333.network;

import com.moumoumou2333.network.packet.ItemStackSyncS2CPacket;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class M23_ClientMessage {

    public static void registerC2SPackets() {
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver( M23_Message.ITEM_SYNC, ItemStackSyncS2CPacket::receive );
    }

}