package me.trent.skyblock.commands.SubCommands.is;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.island.quests.QuestUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isHelp extends SubCommand {

    public isHelp(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        Player p = (Player)sender;

        for (String l : SkyBlock.getInstance().getUtils().color(SkyBlock.getInstance().getFileManager().getMessages().fetchStringList("messages.is-help"))) {
            p.sendMessage(l);
        }

        return true;
    }
}