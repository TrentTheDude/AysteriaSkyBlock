package me.trent.skyblock.commands.SubCommands.is;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.guis.DeleteIsland;
import me.trent.skyblock.island.quests.QuestUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isDelete extends SubCommand {

    public isDelete(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        Player p = (Player)sender;

        if (SkyBlock.getInstance().getIslandUtils().inIsland(p.getUniqueId())) {
            //check if owner
            if (SkyBlock.getInstance().getIslandUtils().isOwner(p.getUniqueId(), SkyBlock.getInstance().getIslandUtils().getIsland(p.getUniqueId()))) {
                //open the delete gui
                SkyBlock.getInstance().getIslandUtils().getIsland(p.getUniqueId()).setDeleting(true);
                DeleteIsland.openDelete(p);
            } else {
                p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("notOwner"));
            }
        } else {
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("notInIsland"));
        }

        return true;
    }
}