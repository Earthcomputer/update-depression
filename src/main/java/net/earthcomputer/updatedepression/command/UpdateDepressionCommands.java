package net.earthcomputer.updatedepression.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;

public class UpdateDepressionCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
        SetSuppressedShulkerBoxCommand.register(dispatcher, context);
        SetNoiseSuppressorCommand.register(dispatcher, context);
    }
}
