package me.trent.skyblock.commands.SubCommands.is;

import me.trent.skyblock.API.IslandTeleportEvent;
import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.island.Island;
import me.trent.skyblock.island.quests.QuestUI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isHome extends SubCommand {

    public isHome(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        Player p = (Player)sender;

        if (SkyBlock.getInstance().getIslandUtils().inIsland(p.getUniqueId())) {
            Island island = SkyBlock.getInstance().getIslandUtils().getIsland(p.getUniqueId());

            IslandTeleportEvent teleportEvent = new IslandTeleportEvent(island, p.getUniqueId(), p.getLocation(), island.getHome());
            Bukkit.getPluginManager().callEvent(teleportEvent);

            if (!teleportEvent.isCancelled()) {
                p.teleport(teleportEvent.getTo());
                p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("teleportHome"));
            }
        } else {
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("notInIsland"));
        }

        return true;
    }
}