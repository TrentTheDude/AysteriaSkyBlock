package me.trent.skyblock.commands.SubCommands.is.oneArgs;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.island.Island;
import me.trent.skyblock.island.permissions.Perm;
import me.trent.skyblock.island.permissions.Role;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isRename extends SubCommand {

    public isRename(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        Player p = (Player)sender;
        String arg2 = args.get(1);

        if (arg2 != null && !arg2.equalsIgnoreCase("")) {
            //check if the island's name isn't already taken...
            if (!SkyBlock.getInstance().getIslandUtils().isIslandName(arg2)) {
                Island island = SkyBlock.getInstance().getIslandUtils().getIsland(p.getUniqueId());
                if (island != null) {
                    Role role = SkyBlock.getInstance().getIslandUtils().getRole(p.getUniqueId(), island);
                    if (island.hasPerm(role, Perm.MANAGE_PERMISSIONS)) {
                        island.setName(arg2);
                        p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("island-name-set").replace("%name%", arg2));
                    } else {
                        p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noPermissionIsland"));
                    }
                } else {
                    //not in an island
                    p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noIsland"));
                }
            } else {
                //taken
                p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("island-name-taken"));
            }
        }


        return true;
    }
}