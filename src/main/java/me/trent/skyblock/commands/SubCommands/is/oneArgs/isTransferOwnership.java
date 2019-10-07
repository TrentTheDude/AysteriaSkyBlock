package me.trent.skyblock.commands.SubCommands.is.oneArgs;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.island.Island;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isTransferOwnership extends SubCommand {

    public isTransferOwnership(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        Player p = (Player)sender;
        String arg2 = args.get(1);

        Player targetPlayer = Bukkit.getPlayerExact(arg2);
        if (targetPlayer != null && targetPlayer.isOnline()) {
            //check for their island
            Island island = SkyBlock.getInstance().getIslandUtils().getIsland(p.getUniqueId());
            if (island != null) {
                if (island.getOwnerUUID().equals(p.getUniqueId())) {
                    //if they are owner, let them transfer island ownership to the specified person
                    if (SkyBlock.getInstance().getIslandUtils().getIsland(targetPlayer.getUniqueId()) == island) {
                        //in the same island, change it now
                        island.setOwnerUUID(targetPlayer.getUniqueId());
                        p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("promotedOwner").replace("%player%", targetPlayer.getName()));
                        targetPlayer.sendMessage(SkyBlock.getInstance().getUtils().getMessage("promotedOwnerMe"));
                        island.addCoOwner(p.getUniqueId());

                        island.removeCoOwner(targetPlayer.getUniqueId());
                        island.removeOfficer(targetPlayer.getUniqueId());
                        island.removeMember(targetPlayer.getUniqueId());
                    } else {
                        p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("demotedNoPlayer"));
                    }
                } else {
                    p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noPermissionIsland"));
                }
            }
        }


        return true;
    }
}