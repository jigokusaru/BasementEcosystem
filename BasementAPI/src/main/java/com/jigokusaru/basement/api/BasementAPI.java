package com.jigokusaru.basement.api;

import com.jigokusaru.basement.api.network.SignScrollPayload;
import com.jigokusaru.basement.api.sign.SignManager;
import dev.architectury.networking.NetworkManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasementAPI {
    public static final String MOD_ID = "basementapi";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static boolean initialized = false;

    public static void init() {
        // If another mod already started the brain, we stop here.
        if (initialized) {
            return;
        }

        initialized = true;
        LOGGER.info("Basement API: Initializing Global Sign Ecosystem...");

        // Register Networking only once
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, SignScrollPayload.TYPE, SignScrollPayload.CODEC, (payload, context) -> {
            context.queue(() -> {
                SignManager.doScroll(context.getPlayer().level(), payload.pos(), payload.line(), payload.amount());
            });
        });

        // Start the global interaction listeners
        SignManager.init();
    }
}