package me.trent.skyblock.commands.SubCommands.is.oneArgs;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.island.Island;
import me.trent.skyblock.island.permissions.Perm;
import me.trent.skyblock.island.permissions.Role;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isSetBiome extends SubCommand {

    public isSetBiome(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        Player p = (Player)sender;
        String arg2 = args.get(1);

        if (SkyBlock.getInstance().getIslandUtils().inIsland(p.getUniqueId())) {

            Island island = SkyBlock.getInstance().getIslandUtils().getIsland(p.getUniqueId());
            if (island == null) {
                p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("notInIsland"));
                return false;
            }

            Role role = SkyBlock.getInstance().getIslandUtils().getRole(p.getUniqueId(), island);
            if (island.hasPerm(role, Perm.MANAGE_PERMISSIONS)) {
                if (Biome.valueOf(arg2) != null && !Biome.valueOf(arg2).name().equalsIgnoreCase("")) {
                    //is valid
                    SkyBlock.getInstance().getIslandUtils().getIsland(p.getUniqueId()).setBiome(Biome.valueOf(arg2));
                    p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("setBiome"));
                } else {
                    p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("invalidBiome"));
                }
            } else {
                p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noPermissionIsland"));
            }
        } else {
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("notInIsland"));
        }


        return true;
    }
}