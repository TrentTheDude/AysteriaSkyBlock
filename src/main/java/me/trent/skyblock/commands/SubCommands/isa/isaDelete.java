package me.trent.skyblock.commands.SubCommands.isa;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.island.Island;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isaDelete extends SubCommand {

    public isaDelete(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        // isa delete <playerName>

        //1 argument...

        if (args.size() >= 1){
            Player target = Bukkit.getPlayerExact(args.get(0));

            if (target != null && target.isOnline()){
                if (SkyBlock.getInstance().getIslandUtils().getIsland(target.getUniqueId()) != null) {
                    Island island = SkyBlock.getInstance().getIslandUtils().getIsland(target.getUniqueId());
                    island.delete();
                    sender.sendMessage(SkyBlock.getInstance().getUtils().getMessage("forceDeleteDeleter"));
                    target.sendMessage(SkyBlock.getInstance().getUtils().getMessage("forceDelete"));
                }else{
                    sender.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noIslandArg"));
                }
            }else{
                sender.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noPlayer"));
            }
        }else{
            sender.sendMessage(getUsageMessage());
        }

        return true;
    }
}