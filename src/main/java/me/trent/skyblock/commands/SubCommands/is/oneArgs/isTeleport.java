package me.trent.skyblock.commands.SubCommands.is.oneArgs;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.island.Island;
import me.trent.skyblock.island.rules.Rule;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isTeleport extends SubCommand {

    public isTeleport(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        Player p = (Player)sender;
        String arg2 = args.get(1);

        Island island = SkyBlock.getInstance().getIslandUtils().getIslandFromName(arg2);
        if (island == null) {
            Player target = Bukkit.getPlayerExact(arg2);
            if (target != null) {
                if (SkyBlock.getInstance().getIslandUtils().getIsland(target.getUniqueId()) != null) {
                    island = SkyBlock.getInstance().getIslandUtils().getIsland(target.getUniqueId());
                }
            }
        }
        if (island == null) {
            //island-not-exist
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("island-not-exist"));
            return false;
        } else {
            //check the island's rule
            if (island.hasRule(Rule.ISLAND_LOCK)) {
                //island is locked
                p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("island-locked"));
                return false;
            } else {
                //teleport them
                p.teleport(island.getHome());
                p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("island-teleport").replace("%island%", island.getName()));
            }
        }



        return true;
    }
}