package me.trent.skyblock.island.scoreboards;

import me.clip.placeholderapi.PlaceholderAPI;
import me.trent.skyblock.PluginHook;
import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.Storage;
import me.trent.skyblock.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import me.trent.skyblock.island.MemoryPlayer;

public class IslandBoard {

    public boolean PAPI = PluginHook.isEnabled("PlaceholderAPI");

    public void createScoreBoard(MemoryPlayer memoryPlayer) {
        CScoreboard scoreboard = new CScoreboard("a", "b", "c", memoryPlayer);
        FileConfiguration f = SkyBlock.getInstance().getFileManager().getScoreboard().getConfig();

        String title = f.getString("scoreboard-title");
        String none = SkyBlock.getInstance().getFileManager().getScoreboard().fetchString("placeholders.none");
        Player p = memoryPlayer.getPlayer();

        for (String s : f.getStringList("scoreboard-rows")) {
            if (s.length() > 32) {
               // s = s.substring(0, Math.min(s.length(), 32));
            }
            CScoreboard.Row row = scoreboard.addRow(s);
        }

        //change the row messages before the initial sending to the player

        scoreboard.setTitle(SkyBlock.getInstance().getUtils().color(title));
        scoreboard.finish();

        memoryPlayer.setScoreboard(scoreboard);
        memoryPlayer.getScoreboard().display(memoryPlayer.getPlayer());
    }

    public void updateBoardTimer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                PAPI = PluginHook.isEnabled("PlaceholderAPI");
                boolean enabled = SkyBlock.getInstance().getFileManager().getScoreboard().fetchBoolean("scoreboard-enabled");

                for (MemoryPlayer memoryPlayer : Storage.memoryPlayerList){
                    if (enabled){
                        if (memoryPlayer.getPlayer() != null && Storage.scoreboard_worlds.contains(memoryPlayer.getPlayer().getWorld().getName()) && !Storage.scoreboard_toggled.contains(memoryPlayer.getPlayer().getUniqueId())){
                            updateBoard(memoryPlayer);
                        }else{
                            if (memoryPlayer.getScoreboard() != null){
                                memoryPlayer.getScoreboard().remove(memoryPlayer.getPlayer());
                                memoryPlayer.setScoreboard(null);
                            }
                        }
                    }else{
                        if (memoryPlayer.getScoreboard() != null){
                            memoryPlayer.getScoreboard().remove(memoryPlayer.getPlayer());
                            memoryPlayer.setScoreboard(null);
                        }
                    }
                }
            }

        }.runTaskTimer(SkyBlock.getInstance(), 0, SkyBlock.getInstance().getFileManager().getScoreboard().fetchInt("scoreboard-update-time"));
    }

    public void updateBoard(MemoryPlayer memoryPlayer) {
        if (memoryPlayer != null){
            if (memoryPlayer.getScoreboard() == null){
                createScoreBoard(memoryPlayer);
            }
            memoryPlayer.getScoreboard().update();
        }
    }

    public String convertPlaceholders(MemoryPlayer memoryPlayer, String oldMessage){
        Player p = memoryPlayer.getPlayer();
        String none = SkyBlock.getInstance().getFileManager().getScoreboard().fetchString("placeholders.none");

       // oldMessage = SkyBlock.getInstance().getUtils().color(oldMessage);

        if (SkyBlock.getInstance().getIslandBoard().PAPI) {
            oldMessage = PlaceholderAPI.setPlaceholders(p, oldMessage);
        }

        oldMessage = oldMessage.replace("%player%", p.getName());

        oldMessage = oldMessage.replace("%vote_current%", SkyBlock.getInstance().getVoteListenerAPI().getCurrentVotes()+"");
        oldMessage = oldMessage.replace("%vote_max%", SkyBlock.getInstance().getVoteListenerAPI().getMaxVotes()+"");

        double bal = SkyBlock.getInstance().getUtils().getBalance(p.getUniqueId());
        oldMessage = oldMessage.replace("%money%", Utils.numberFormat.formatDbl(bal));
        if (memoryPlayer.getIsland() != null) {
            oldMessage = oldMessage.replace("%is-name%", memoryPlayer.getIsland().getName());
            oldMessage = oldMessage.replace("%is-owner%", memoryPlayer.getIsland().getOwnersName());
            oldMessage = oldMessage.replace("%is-top%", memoryPlayer.getIsland().getTopPlace() + "");
            oldMessage = oldMessage.replace("%is-worth_total%", Utils.numberFormat.formatDbl(memoryPlayer.getIsland().getWorth()));
            oldMessage = oldMessage.replace("%is-worth_bank%", Utils.numberFormat.formatDbl(memoryPlayer.getIsland().getBankBalance()));
            oldMessage = oldMessage.replace("%is-worth_blocks%", Utils.numberFormat.formatDbl(memoryPlayer.getIsland().getBlockWorth()));
            oldMessage = oldMessage.replace("%is-worth_spawners%", Utils.numberFormat.formatDbl(memoryPlayer.getIsland().getSpawnerWorth()));
        } else {
            oldMessage = oldMessage.replace("%is-name%", none);
            oldMessage = oldMessage.replace("%is-owner%", none);
            oldMessage = oldMessage.replace("%is-top%", none);
            oldMessage = oldMessage.replace("%is-worth_total%", none);
            oldMessage = oldMessage.replace("%is-worth_bank%", none);
            oldMessage = oldMessage.replace("%is-worth_blocks%", none);
            oldMessage = oldMessage.replace("%is-worth_spawners%", none);
        }
      //  oldMessage = SkyBlock.getInstance().getUtils().color(oldMessage);

        if (oldMessage.length() > 32) {
            oldMessage = oldMessage.substring(0, Math.min(oldMessage.length(), 32));
        }
        return oldMessage;
    }
}