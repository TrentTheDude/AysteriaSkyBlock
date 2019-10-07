package me.trent.skyblock.commands.SubCommands.is;

import me.trent.skyblock.PluginHook;
import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.island.Island;
import me.trent.skyblock.island.MemoryPlayer;
import me.trent.skyblock.island.quests.QuestUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isInspect extends SubCommand {

    public isInspect(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        Player p = (Player)sender;

        Island island = SkyBlock.getInstance().getIslandUtils().getIslandFromLocation(p.getLocation());
        if (island != null) {
            if (island.equals(SkyBlock.getInstance().getIslandUtils().getIsland(p.getUniqueId()))) {
                //check if the player has permission to check the inspect
                //inspect it now
                MemoryPlayer memoryPlayer = SkyBlock.getInstance().getUtils().getMemoryPlayer(p.getUniqueId());
                if (memoryPlayer == null) return false;

                if (!PluginHook.isEnabled("CoreProtect")) {
                    p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("island-inspect-disabled"));
                    return false;
                }

                if (memoryPlayer.isInspectMode()) {
                    memoryPlayer.setInspectMode(false);
                    p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("island-inspect-mode-off"));
                } else {
                    memoryPlayer.setInspectMode(true);
                    p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("island-inspect-mode-on"));
                }
            } else {
                p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noPermissionIsland"));
            }
        } else {
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noIslandHere"));
        }

        return true;
    }
}