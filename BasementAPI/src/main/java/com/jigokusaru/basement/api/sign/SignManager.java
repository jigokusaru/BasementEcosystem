package com.jigokusaru.basement.api.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import java.util.HashMap;
import java.util.Map;

public class SignManager {
    // Stores the 4 scrolling lines for every registered sign
    private static final Map<BlockPos, ScrollingLine[]> trackedSigns = new HashMap<>();

    public static void register(BlockPos pos) {
        trackedSigns.put(pos, new ScrollingLine[]{
                new ScrollingLine(), new ScrollingLine(),
                new ScrollingLine(), new ScrollingLine()
        });
    }

    public static void setLine(BlockPos pos, int lineIndex, String text) {
        ScrollingLine[] lines = trackedSigns.get(pos);
        if (lines != null && lineIndex >= 0 && lineIndex < 4) {
            lines[lineIndex].setContent(text);
        }
    }

    public static void doScroll(Level level, BlockPos pos, int lineIndex, int amount) {
        ScrollingLine[] lines = trackedSigns.get(pos);
        if (lines != null && lineIndex >= 0 && lineIndex < 4) {
            lines[lineIndex].scroll(amount);

            // Apply the virtual line "render" to the physical sign block
            if (level.getBlockEntity(pos) instanceof SignBlockEntity sign) {
                sign.updateText(text -> {
                    for (int i = 0; i < 4; i++) {
                        text = text.setMessage(i, lines[i].render());
                    }
                    return text;
                }, true); // true updates the front of the sign

                // Force a sync so players see the text change immediately
                level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 3);
            }
        }
    }
}