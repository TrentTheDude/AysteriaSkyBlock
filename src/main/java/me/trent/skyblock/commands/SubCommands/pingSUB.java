package me.trent.skyblock.commands.SubCommands;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class pingSUB extends SubCommand {


    public pingSUB(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        //todo; add some code...



            //look for player from first arg
            Player target = Bukkit.getPlayerExact(args.get(0));
            if (target != null && target.isOnline()){
                sender.sendMessage(SkyBlock.getInstance().getUtils().color("&a"+target.getName()+"'s Ping: &b"+SkyBlock.getInstance().getUtils().getPing(target)));
            }else{
                //no player
                sender.sendMessage(SkyBlock.getInstance().getUtils().color("&cCould not find player...")); }

        return false;
    }


}