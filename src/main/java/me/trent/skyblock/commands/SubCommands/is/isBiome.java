package me.trent.skyblock.commands.SubCommands.is;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.guis.Biomes;
import me.trent.skyblock.island.Island;
import me.trent.skyblock.island.permissions.Perm;
import me.trent.skyblock.island.permissions.Role;
import me.trent.skyblock.island.quests.QuestUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isBiome extends SubCommand {

    public isBiome(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        Player p = (Player)sender;

        if (SkyBlock.getInstance().getIslandUtils().inIsland(p.getUniqueId())) {
            Island island = SkyBlock.getInstance().getIslandUtils().getIsland(p.getUniqueId());
            Role role = SkyBlock.getInstance().getIslandUtils().getRole(p.getUniqueId(), island);
            if (island.hasPerm(role, Perm.MANAGE_PERMISSIONS)) {
                Biomes.openBiome(p);
            } else {
                p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noPermissionIsland"));
            }
        } else {
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("notInIsland"));
        }

        return true;
    }
}