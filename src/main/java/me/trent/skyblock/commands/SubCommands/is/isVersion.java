package me.trent.skyblock.commands.SubCommands.is;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class isVersion extends SubCommand {

    public isVersion(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        String[] list = {"&a&lGOG SkyBlock", " ", "&bVersion: " + SkyBlock.getInstance().getDescription().getVersion(), "&bBy: Trent™", " "};
        for (String s : list) {
            sender.sendMessage(SkyBlock.getInstance().getUtils().color(s));
        }

        return true;
    }
}