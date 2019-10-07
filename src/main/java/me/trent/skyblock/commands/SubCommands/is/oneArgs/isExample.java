package me.trent.skyblock.commands.SubCommands.is.oneArgs;

import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isExample extends SubCommand {

    public isExample(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        Player p = (Player)sender;
        String arg2 = args.get(1);




        return true;
    }
}