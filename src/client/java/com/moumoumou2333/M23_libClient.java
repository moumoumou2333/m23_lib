package com.moumoumou2333;

import com.moumoumou2333.network.M23_ClientMessage;

import net.fabricmc.api.ClientModInitializer;

public class M23_libClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		M23_ClientMessage.registerS2CPackets();
	}

}