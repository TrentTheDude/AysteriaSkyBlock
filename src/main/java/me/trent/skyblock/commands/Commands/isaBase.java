package me.trent.skyblock.commands.Commands;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class isaBase extends CommandBase {

    public isaBase(String name, String description, String permission, String usageMessage, String... aliases){
        super(name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, String alias, String... args) {

        if (!super.execute(sender, alias, args)){
            // something didn't work right from either CommandBase, or SubCommand classes
            //check the permission now...
            if (!this.testPermission(sender, getPermissionT())){
                sender.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noPermission"));
            }
            return false;
        }


        Player p = (Player)sender;

        //base command... no arguments
        for (String l : SkyBlock.getInstance().getUtils().color(SkyBlock.getInstance().getFileManager().getMessages().fetchStringList("messages.isa-help"))) {
            p.sendMessage(l);
        }

        return true;
    }
}
