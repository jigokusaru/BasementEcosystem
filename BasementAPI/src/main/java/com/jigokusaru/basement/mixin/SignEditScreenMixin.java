package com.jigokusaru.basement.mixin;

import com.jigokusaru.basement.api.sign.SignManager;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(SignEditScreen.class)
public class SignEditScreenMixin {

    // Minecraft uses a StringWidget or EditBox internally for the sign text.
    // We target the method that sets the maximum length.
    @ModifyArgs(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/EditBox;setMaxLength(I)V"))
    private void basement$increaseMaxLength(Args args) {
        // Change the vanilla limit (usually 15) to something huge
        args.set(0, 1000);
    }
}