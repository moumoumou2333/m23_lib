package com.moumoumou2333.resource;

import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;

import com.moumoumou2333.M23Lib;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public final class M23_ResourceManagerHelper {

    private static final Logger logger = M23Lib.LOGGER;

	private M23_ResourceManagerHelper() {}

    public static void load( String modId, String folderName, List< Consumer< ResourceManager > > consumers ) {
        ResourceManagerHelper.get( ResourceType.SERVER_DATA ).registerReloadListener(
			new SimpleSynchronousResourceReloadListener() {

				@Override
				public Identifier getFabricId() {
					return new Identifier( modId, folderName );
				}

				@Override
				public void reload( ResourceManager manager ) {
					consumers.forEach(
						( consumer ) -> {
							try {
								consumer.accept( manager );
							} catch ( Exception e ) {
								logger.error( "Error occurred while loading resource json {}: {}", modId, folderName, e );
							}
						}
					);
				}

			}
		);
    }

}