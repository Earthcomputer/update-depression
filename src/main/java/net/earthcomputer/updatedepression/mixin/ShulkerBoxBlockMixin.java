package net.earthcomputer.updatedepression.mixin;

import net.earthcomputer.updatedepression.UpdateDepressionConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxBlockMixin {
    @Inject(method = "getAnalogOutputSignal", at = @At("HEAD"), cancellable = true)
    private void reintroduceCCESuppression(BlockState state, Level level, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (UpdateDepressionConfig.reintroduceCCESuppression) {
            cir.setReturnValue(AbstractContainerMenu.getRedstoneSignalFromContainer((Container) level.getBlockEntity(pos)));
        }
    }
}
