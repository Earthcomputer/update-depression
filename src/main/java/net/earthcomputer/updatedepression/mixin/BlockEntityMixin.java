package net.earthcomputer.updatedepression.mixin;

import net.earthcomputer.updatedepression.UpdateDepressionConfig;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
public class BlockEntityMixin {
    @Inject(method = "validateBlockState", at = @At("HEAD"), cancellable = true)
    private void allowInvalidBlockEntities(BlockState blockState, CallbackInfo ci) {
        if (UpdateDepressionConfig.shouldAllowInvalidBlockEntities) {
            ci.cancel();
        }
    }
}
