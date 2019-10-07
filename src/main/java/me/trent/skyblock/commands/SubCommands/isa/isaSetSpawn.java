package me.trent.skyblock.commands.SubCommands.isa;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isaSetSpawn extends SubCommand {

    public isaSetSpawn(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        Player p = (Player)sender;

        SkyBlock.getInstance().getConfig().set("settings.island-spawn", SkyBlock.getInstance().getUtils().serializeLocation(p.getLocation()));
        SkyBlock.getInstance().saveConfig();
        sender.sendMessage(SkyBlock.getInstance().getUtils().getMessage("setSpawn"));

        return true;
    }
}