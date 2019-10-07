package me.trent.skyblock.commands.SubCommands.is;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.island.quests.QuestUI;
import me.trent.skyblock.island.upgrades.UpgradesUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isUpgrades extends SubCommand {

    public isUpgrades(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        Player p = (Player)sender;

        if (SkyBlock.getInstance().getIslandUtils().getIsland(p.getUniqueId()) != null) {
            UpgradesUI.openUpgradesUI(p);
        } else {
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noIsland"));
        }

        return true;
    }
}