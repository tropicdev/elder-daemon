package com.github.tropicdev.elderdaemon.mixin;

import java.util.Collection;

import com.github.tropicdev.elderdaemon.Daemon;
import com.github.tropicdev.elderdaemon.config.Config;
import net.minecraft.text.Text;
import com.mojang.authlib.GameProfile;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.command.BanCommand;
import com.github.tropicdev.elderdaemon.connections.SocketClient;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BanCommand.class)
public abstract class BanCommandMixin {

    @Inject(method = "ban", at = @At("TAIL"))
    private static void ban(ServerCommandSource source, Collection<GameProfile> targets, @Nullable Text reason, CallbackInfoReturnable<Integer> cir) {

        SocketClient socketClient = SocketClient.getInstance(source.getServer());

        for (GameProfile gameProfile : targets) {

            socketClient.emitBanEvent(gameProfile, reason);

        }
    }
}