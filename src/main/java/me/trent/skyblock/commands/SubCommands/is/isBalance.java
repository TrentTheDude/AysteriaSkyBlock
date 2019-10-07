package me.trent.skyblock.commands.SubCommands.is;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.island.Island;
import me.trent.skyblock.island.quests.QuestUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isBalance extends SubCommand {

    public isBalance(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        Player p = (Player)sender;

        Island island = SkyBlock.getInstance().getIslandUtils().getIsland(p.getUniqueId());
        if (island != null) {
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("bank-balance").replace("%balance%", SkyBlock.getInstance().getUtils().formatNumber(island.getBankBalance() + "")));
        } else {
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noIsland"));
        }

        return true;
    }
}