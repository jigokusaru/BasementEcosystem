package com.jigokusaru.basement.api.sign;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import java.util.HashMap;
import java.util.Map;

public class SignManager {
    private static final Map<BlockPos, ScrollingLine[]> trackedSigns = new HashMap<>();

    /**
     * Internal helper to ensure a sign has scrolling data the moment we touch it.
     */
    private static ScrollingLine[] getOrCreateLines(BlockPos pos) {
        return trackedSigns.computeIfAbsent(pos, k -> new ScrollingLine[]{
                new ScrollingLine(), new ScrollingLine(),
                new ScrollingLine(), new ScrollingLine()
        });
    }

    public static void init() {
        InteractionEvent.RIGHT_CLICK_BLOCK.register((player, hand, pos, face) -> {
            if (player.level().getBlockEntity(pos) instanceof SignBlockEntity) {
                // Now works on ANY sign. Crouch + Right Click = Scroll Line 0.
                if (player.isCrouching()) {
                    doScroll(player.level(), pos, 0, 1);
                    return EventResult.interruptTrue();
                }
            }
            return EventResult.pass();
        });
    }

    public static void setLine(BlockPos pos, int lineIndex, String text) {
        ScrollingLine[] lines = getOrCreateLines(pos);
        if (lineIndex >= 0 && lineIndex < 4) {
            lines[lineIndex].setContent(text);
        }
    }

    public static void doScroll(Level level, BlockPos pos, int lineIndex, int amount) {
        ScrollingLine[] lines = getOrCreateLines(pos);
        if (lineIndex >= 0 && lineIndex < 4) {
            lines[lineIndex].scroll(amount);

            if (level.getBlockEntity(pos) instanceof SignBlockEntity sign) {
                sign.updateText(text -> {
                    for (int i = 0; i < 4; i++) {
                        text = text.setMessage(i, lines[i].render());
                    }
                    return text;
                }, true);

                level.sendBlockUpdated(pos, level.getBlockState(pos), level.getBlockState(pos), 3);
            }
        }
    }
}