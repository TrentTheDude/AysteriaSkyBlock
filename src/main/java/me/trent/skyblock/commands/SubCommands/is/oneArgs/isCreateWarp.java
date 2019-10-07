package me.trent.skyblock.commands.SubCommands.is.oneArgs;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.island.Island;
import me.trent.skyblock.island.permissions.Perm;
import me.trent.skyblock.island.permissions.Role;
import me.trent.skyblock.island.upgrades.Upgrade;
import me.trent.skyblock.island.warps.IslandWarp;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isCreateWarp extends SubCommand {

    public isCreateWarp(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
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

        if (SkyBlock.getInstance().getIslandUtils().getIslandFromLocation(p.getLocation()).equals(island)) {

            Role role = SkyBlock.getInstance().getIslandUtils().getRole(p.getUniqueId(), island);
            if (island.hasPerm(role, Perm.SET_WARP)) {
                if (SkyBlock.getInstance().getIslandUtils().getIslandWarp(island, arg2) == null) {
                    //there isn't a warp with this name... allow
                    //check their upgrade value for island warps before allowing them to create a new one
                    int currentWarps = island.getIslandWarps().size() + 1;
                    if (Upgrade.Upgrades.getTierValue(Upgrade.WARP_AMOUNT, island.getUpgradeTier(Upgrade.WARP_AMOUNT), null) >= currentWarps) {
                        //allow them to create it
                        IslandWarp islandWarp = new IslandWarp(island, arg2, p.getLocation());
                        island.addIslandWarp(islandWarp);
                        p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("island-warp-created").replace("%name%", arg2));
                    } else {
                        p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("island-warp-max"));
                    }
                } else {
                    p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("island-warp-exists"));
                }
            } else {
                p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noPermissionIsland"));
            }
        } else {
            //noPermissionIsland
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noPermissionIsland"));
        }


        return true;
    }
}