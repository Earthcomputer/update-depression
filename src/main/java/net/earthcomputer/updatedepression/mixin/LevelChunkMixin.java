package net.earthcomputer.updatedepression.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.earthcomputer.updatedepression.UpdateDepressionConfig;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelChunk.class)
public class LevelChunkMixin {
    @ModifyExpressionValue(method = "setBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntity;isValidBlockState(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private boolean allowInvalidBlockEntities(boolean original) {
        if (UpdateDepressionConfig.shouldAllowInvalidBlockEntities) {
            return true;
        }
        return original;
    }
}
