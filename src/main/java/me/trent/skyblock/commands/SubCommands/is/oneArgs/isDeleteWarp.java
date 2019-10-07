package me.trent.skyblock.commands.SubCommands.is.oneArgs;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.island.Island;
import me.trent.skyblock.island.permissions.Perm;
import me.trent.skyblock.island.permissions.Role;
import me.trent.skyblock.island.warps.IslandWarp;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isDeleteWarp extends SubCommand {

    public isDeleteWarp(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        Player p = (Player)sender;
        String arg2 = args.get(1);

        Island island = SkyBlock.getInstance().getIslandUtils().getIsland(p.getUniqueId());
        if (island == null) {
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("notInIsland"));
            return false;
        }

        Role role = SkyBlock.getInstance().getIslandUtils().getRole(p.getUniqueId(), island);
        if (island.hasPerm(role, Perm.DELETE_WARP)) {
            if (SkyBlock.getInstance().getIslandUtils().getIslandWarp(island, arg2) != null) {
                //has a warp named this name
                //remove it now
                IslandWarp islandWarp = SkyBlock.getInstance().getIslandUtils().getIslandWarp(island, arg2);
                if (islandWarp != null) {
                    island.removeIslandWarp(islandWarp);
                    islandWarp.remove();
                    p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("island-warp-removed").replace("%name%", arg2));
                } else {
                    p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("island-warp-none"));
                }
            } else {
                p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("island-warp-none"));
            }
        } else {
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noPermissionIsland"));
        }


        return true;
    }
}