package me.trent.skyblock.commands.Commands;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.guis.Islands;
import me.trent.skyblock.guis.Panel;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class isBase extends CommandBase {

    public isBase(String name, String description, String permission, String usageMessage, String... aliases) {
        super(name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, String alias, String... args) {

        if (!super.execute(sender, alias, args)) {
            // something didn't work right from either CommandBase, or SubCommand classes
            //check the permission now...
            if (!this.testPermission(sender, getPermissionT())) {
                sender.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noPermission"));
            }
            return false;
        }

        Player p = (Player) sender;

        if (SkyBlock.getInstance().getIslandUtils().getIsland(p.getUniqueId()) != null) {
            Panel.openPanel(p);
        } else {
            Islands.openIslands(p);
        }

        return true;
    }

}