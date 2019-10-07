package me.trent.skyblock.commands.SubCommands.isa;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.Storage;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.island.Island;
import me.trent.skyblock.island.MemoryPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;

public class isaReload extends SubCommand {

    public isaReload(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        SkyBlock.getInstance().reloadConfig();
        SkyBlock.getInstance().getFileManager().getData().loadFile(); //todo; might have to remove this if it overrides data saves
        SkyBlock.getInstance().getFileManager().getGuis().loadFile();
        SkyBlock.getInstance().getFileManager().getQuestFile().loadFile();
        SkyBlock.getInstance().getFileManager().getMessages().loadFile();
        SkyBlock.getInstance().getFileManager().getUpgrades().loadFile();
        SkyBlock.getInstance().getFileManager().getWorth().loadFile();
        SkyBlock.getInstance().getFileManager().getScoreboard().loadFile();
        SkyBlock.getInstance().getFileManager().getPermissions().loadFile();
        SkyBlock.getInstance().getFileManager().getRules().loadFile();

        Storage.scoreboard_worlds = SkyBlock.getInstance().getFileManager().getScoreboard().fetchStringList("scoreboard-worlds");

        // quests

        SkyBlock.getInstance().getFileManager().getDailyQuestFile().loadFile();
        SkyBlock.getInstance().getFileManager().getForeverQuestFile().loadFile();
        SkyBlock.getInstance().getFileManager().getWeeklyQuestFile().loadFile();
        SkyBlock.getInstance().getFileManager().getMonthlyQuestFile().loadFile();

        //destroy all of the memoryplayer's scoreboards so we reload them
        for (MemoryPlayer memoryPlayer : Storage.memoryPlayerList){
            if (memoryPlayer.getScoreboard() != null){
                memoryPlayer.getScoreboard().remove(memoryPlayer.getPlayer());
            }
            memoryPlayer.setScoreboard(null);
        }


        sender.sendMessage(SkyBlock.getInstance().getUtils().getMessage("reload"));

        Bukkit.getScheduler().cancelTasks(SkyBlock.getInstance());

        for (Island island : Storage.islandList) {
            SkyBlock.getInstance().getIslandUtils().calculateIslandLevel(island);
        }

        SkyBlock.getInstance().startTopTimer();
        SkyBlock.getInstance().startCalculationTimer();
        SkyBlock.getInstance().startCacheTimer();
        SkyBlock.getInstance().getIslandBoard().updateBoardTimer();

        return true;
    }
}