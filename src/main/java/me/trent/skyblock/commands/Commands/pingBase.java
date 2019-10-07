package me.trent.skyblock.commands.Commands;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class pingBase extends CommandBase {

    public pingBase(String name, String description, String permissionRole, String usageMessage, String... aliases){
        super(name, description, permissionRole, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, String alias, String... args) {

        if (!super.execute(sender, alias, args)) return false; // no permission for this command...

        //todo; add some default 0 argument code ...

        if (sender instanceof Player) {
            Player p = (Player)sender;
            sender.sendMessage(SkyBlock.getInstance().getUtils().color("&aMy Ping: &b" + SkyBlock.getInstance().getUtils().getPing(p)));
        }else{
            sender.sendMessage(SkyBlock.getInstance().getUtils().color("&cThis command is only for Console..."));
        }



        return true;
    }
}