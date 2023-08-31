package net.earthcomputer.updatedepression.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.earthcomputer.updatedepression.UpdateDepression;
import net.earthcomputer.updatedepression.UpdateDepressionConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.PlayerList;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Shadow @Final private static Logger LOGGER;
    @Shadow private PlayerList playerList;


    @WrapOperation(method = "tickChildren", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;tick(Ljava/util/function/BooleanSupplier;)V"))
    private void addTryCatch(ServerLevel level, BooleanSupplier shouldKeepTicking, Operation<Void> original) {
        if (UpdateDepressionConfig.updateSuppressionCrashFix) {
            try {
                original.call(level, shouldKeepTicking);
            } catch (Throwable e) {
                LOGGER.error("Caused a crash in server tick", e);
                playerList.broadcastSystemMessage(UpdateDepression.translatableWithFallback("updatedepression.crashFix").withStyle(style -> style.withItalic(true).withColor(ChatFormatting.GRAY)), false);
            }
        } else {
            original.call(level, shouldKeepTicking);
        }
    }
}
