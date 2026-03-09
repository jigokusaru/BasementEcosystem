package com.jigokusaru.basement.api.sign;

import net.minecraft.network.chat.Component;

public class ScrollingLine {
    private String fullContent = "";
    private int horizontalOffset = 0;
    // Standard Minecraft signs fit roughly 15-16 characters
    private static final int SIGN_VISIBLE_WIDTH = 15;

    public void setContent(String content) {
        this.fullContent = content;
        this.horizontalOffset = 0;
    }

    public void scroll(int amount) {
        // Calculate the maximum possible scroll based on the string length
        int maxScroll = Math.max(0, fullContent.length() - SIGN_VISIBLE_WIDTH);
        this.horizontalOffset = Math.clamp(horizontalOffset + amount, 0, maxScroll);
    }

    public Component render() {
        // If it fits, just return the whole thing
        if (fullContent.length() <= SIGN_VISIBLE_WIDTH) {
            return Component.literal(fullContent);
        }

        // Extract the specific "window" of text to display on the sign
        String visibleText = fullContent.substring(horizontalOffset,
                Math.min(horizontalOffset + SIGN_VISIBLE_WIDTH, fullContent.length()));

        return Component.literal(visibleText);
    }
}