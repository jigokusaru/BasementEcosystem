package com.jigokusaru.basement;

import com.jigokusaru.basement.api.BasementAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BasementShop {
    public static final String MOD_ID = "basementshop";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        // This wakes up the API. If it's already awake, nothing happens.
        BasementAPI.init();

        LOGGER.info("Basement Shop Initializing...");
    }
}