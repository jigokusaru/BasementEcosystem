package com.jigokusaru.basement.api.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SignScrollPayload(BlockPos pos, int line, int amount) implements CustomPacketPayload {
    public static final Type<SignScrollPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("basement", "sign_scroll"));

    public static final StreamCodec<FriendlyByteBuf, SignScrollPayload> CODEC = StreamCodec.of(
            (buf, payload) -> {
                buf.writeBlockPos(payload.pos());
                buf.writeInt(payload.line());
                buf.writeInt(payload.amount());
            },
            buf -> new SignScrollPayload(buf.readBlockPos(), buf.readInt(), buf.readInt())
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}