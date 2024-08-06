package net.earthcomputer.updatedepression.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.earthcomputer.updatedepression.UpdateDepression;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CalibratedSculkSensorBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.commands.Commands.*;
import static net.minecraft.commands.arguments.blocks.BlockStateArgument.*;
import static net.minecraft.commands.arguments.coordinates.BlockPosArgument.*;

public class SetNoiseSuppressorCommand {
    private static final SimpleCommandExceptionType INVALID_BLOCK_EXCEPTION = new SimpleCommandExceptionType(UpdateDepression.translatableWithFallback("commands.setnoisesuppressor.invalidBlock"));
    private static final BlockState BENIS = Blocks.BEE_NEST.defaultBlockState()
        .setValue(BeehiveBlock.HONEY_LEVEL, BeehiveBlock.MAX_HONEY_LEVELS);

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
        dispatcher.register(literal("setnoisesuppressor")
            .requires(source -> source.hasPermission(2))
            .then(argument("pos", blockPos())
                .executes(ctx -> setNoiseSuppressor(ctx.getSource(), getLoadedBlockPos(ctx, "pos"), BENIS))
                .then(argument("validBlock", block(context))
                    .executes(ctx -> setNoiseSuppressor(ctx.getSource(), getBlockPos(ctx, "pos"), getBlock(ctx, "validBlock").getState())))));
    }

    private static int setNoiseSuppressor(CommandSourceStack source, BlockPos pos, BlockState blockState) throws CommandSyntaxException {
        if (!blockState.hasBlockEntity() || !blockState.hasProperty(CalibratedSculkSensorBlock.FACING)) {
            throw INVALID_BLOCK_EXCEPTION.create();
        }

        BlockState state = tryGetStateFacingEntity(source.getEntity(), blockState);
        ServerLevel level = source.getLevel();
        level.setBlock(pos, state, Block.UPDATE_CLIENTS);
        level.setBlockEntity(((EntityBlock) Blocks.CALIBRATED_SCULK_SENSOR).newBlockEntity(pos, state));

        source.sendSuccess(() -> UpdateDepression.translatableWithFallback("commands.setnoisesuppressor.success"), true);

        return Command.SINGLE_SUCCESS;
    }

    private static BlockState tryGetStateFacingEntity(Entity entity, BlockState state) {
        if (entity == null)
            return state;

        return state.setValue(CalibratedSculkSensorBlock.FACING, entity.getDirection().getOpposite());
    }
}
