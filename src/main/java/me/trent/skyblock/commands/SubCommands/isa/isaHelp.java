package me.trent.skyblock.commands.SubCommands.isa;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class isaHelp extends SubCommand {

    public isaHelp(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        for (String l : SkyBlock.getInstance().getUtils().color(SkyBlock.getInstance().getFileManager().getMessages().fetchStringList("messages.isa-help"))) {
            sender.sendMessage(l);
        }

        return true;
    }
}