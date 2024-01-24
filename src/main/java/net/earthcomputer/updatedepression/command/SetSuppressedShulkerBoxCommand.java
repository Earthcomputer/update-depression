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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.commands.Commands.*;
import static net.minecraft.commands.arguments.blocks.BlockStateArgument.*;
import static net.minecraft.commands.arguments.coordinates.BlockPosArgument.*;

public class SetSuppressedShulkerBoxCommand {
    private static final SimpleCommandExceptionType NOT_SHULKER_BOX_EXCEPTION = new SimpleCommandExceptionType(UpdateDepression.translatableWithFallback("commands.setsuppressedshulkerbox.notShulkerBox"));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
        dispatcher.register(literal("setsuppressedshulkerbox")
            .requires(source -> source.hasPermission(2))
            .then(argument("pos", blockPos())
                .executes(ctx -> setSuppressedShulkerBox(ctx.getSource(), getLoadedBlockPos(ctx, "pos"), Blocks.SHULKER_BOX.defaultBlockState()))
                .then(argument("shulkerBox", block(context))
                    .executes(ctx -> setSuppressedShulkerBox(ctx.getSource(), getBlockPos(ctx, "pos"), getBlock(ctx, "shulkerBox").getState())))));
    }

    private static int setSuppressedShulkerBox(CommandSourceStack source, BlockPos pos, BlockState shulkerBox) throws CommandSyntaxException {
        if (!(shulkerBox.getBlock() instanceof ShulkerBoxBlock)) {
            throw NOT_SHULKER_BOX_EXCEPTION.create();
        }

        ServerLevel level = source.getLevel();
        level.setBlock(pos, shulkerBox, Block.UPDATE_CLIENTS);
        level.setBlockEntity(((EntityBlock) Blocks.LECTERN).newBlockEntity(pos, shulkerBox));

        source.sendSuccess(UpdateDepression.translatableWithFallback("commands.setsuppressedshulkerbox.success"), true);

        return Command.SINGLE_SUCCESS;
    }
}
