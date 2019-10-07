package me.trent.skyblock.commands.SubCommands.is;

import me.trent.skyblock.API.IslandTeleportEvent;
import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.island.quests.QuestUI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isSpawn extends SubCommand {

    public isSpawn(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        Player p = (Player)sender;

        if (SkyBlock.getInstance().getIslandUtils().getIslandSpawn() != null) {

            IslandTeleportEvent teleportEvent = new IslandTeleportEvent(SkyBlock.getInstance().getIslandUtils().getIsland(p.getUniqueId()), p.getUniqueId(), p.getLocation(), SkyBlock.getInstance().getIslandUtils().getIslandSpawn());
            Bukkit.getPluginManager().callEvent(teleportEvent);

            if (!teleportEvent.isCancelled()) {
                //  p.teleport(SkyBlock.getInstance().getIslandUtils().getIslandSpawn());
                p.teleport(teleportEvent.getTo());
                p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("teleportSpawn"));
            }
        } else {
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noSpawn"));
        }

        return true;
    }
}