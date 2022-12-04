package com.mitchej123.hodgepodge.mixins.late.worldedit;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.worldedit.command.UtilityCommands;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.util.command.binding.Text;
import net.minecraft.command.CommandException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = UtilityCommands.class, remap = false)
public abstract class MixinUtilityCommands {

    /**
     * @reason I do not like this command, it can crash your server!
     */
    @Overwrite(remap = false)
    @Command(aliases = { "/calc", "/calculate", "/butcher", "/eval", "/evaluate", "/solve" }, usage = "<expression>", desc = "Evaluate a mathematical expression")
    public void calc(Actor actor, @Text String input) throws CommandException {
        actor.printError("This command was disabled!");
    }
}
