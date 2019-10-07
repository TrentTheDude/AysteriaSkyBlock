package me.trent.skyblock.commands.SubCommands.is.oneArgs;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.island.Island;
import me.trent.skyblock.island.permissions.Role;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isKick extends SubCommand {

    public isKick(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        Player p = (Player)sender;
        String arg2 = args.get(1);

        if (Bukkit.getPlayer(arg2) != null && Bukkit.getPlayer(arg2).isOnline()) {
            Player target = Bukkit.getPlayerExact(arg2);
            //check for owner
            if (SkyBlock.getInstance().getIslandUtils().inIsland(p.getUniqueId())) {
                //check for owner or officer
                if (!SkyBlock.getInstance().getIslandUtils().isMember(p.getUniqueId(), SkyBlock.getInstance().getIslandUtils().getIsland(p.getUniqueId()))) {
                    //in an island, and is not member, must be owner or officer, let them run the commands
                    Island island = SkyBlock.getInstance().getIslandUtils().getIsland(p.getUniqueId());
                    if (island != null) {
                        Role role = SkyBlock.getInstance().getIslandUtils().getRole(p.getUniqueId(), island);
                        if (role == null) return false;


                        island.kick(p.getUniqueId(), target.getUniqueId());
                    }
                } else {
                    p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noPermissionIsland"));
                }
            } else {
                p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("notInIsland"));
            }
        } else {
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noPlayer"));
        }


        return true;
    }
}