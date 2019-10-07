package me.trent.skyblock.commands.SubCommands.is;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.Storage;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isScoreboard extends SubCommand {

    public isScoreboard(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        Player p = (Player) sender;

        if (Storage.scoreboard_toggled.contains(p.getUniqueId())) {
            Storage.scoreboard_toggled.remove(p.getUniqueId());
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("scoreboard-on"));
        } else {
            Storage.scoreboard_toggled.add(p.getUniqueId());
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("scoreboard-off"));
        }

        return true;
    }
}