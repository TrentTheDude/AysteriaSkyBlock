package me.trent.skyblock.island.scoreboards;

import me.trent.skyblock.SkyBlock;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import me.trent.skyblock.island.MemoryPlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CScoreboard {

  private final String name, criterion;

  private MemoryPlayer memoryPlayer;

    private Scoreboard bukkitScoreboard;
    private final Objective obj;
     String title;
     private Row[] rows = new Row[0];
    private List<Row> rowCache = new ArrayList<>();
    private boolean finished = false;

    public CScoreboard(String name, String criterion, String title, MemoryPlayer memoryPlayer){
        this.name = name;
        this.criterion = criterion;
        this.title = title;
        this.memoryPlayer = memoryPlayer;

        this.bukkitScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.obj = this.bukkitScoreboard.registerNewObjective(name, criterion);

        this.obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.obj.setDisplayName(title);
    }

    public void update(){
        if (getMemoryPlayer().getPlayer() == null){
            return;
        }

        Player p = getMemoryPlayer().getPlayer();

        if (SkyBlock.getInstance().getFileManager().getScoreboard().fetchBoolean("scoreboard-enabled")) {
            if (p == null) return;
            CScoreboard scoreboard = memoryPlayer.getScoreboard();
            if (scoreboard == null) {
                SkyBlock.getInstance().getIslandBoard().createScoreBoard(memoryPlayer);
                scoreboard = memoryPlayer.getScoreboard();
            }
            for (CScoreboard.Row row : scoreboard.getRows()) {
                String oldMessage = row.getOriginalMessage();

                oldMessage = SkyBlock.getInstance().getIslandBoard().convertPlaceholders(getMemoryPlayer(), oldMessage);

                row.setMessage(oldMessage);
            }
        }else{
            if (memoryPlayer.getScoreboard() != null){
                memoryPlayer.getScoreboard().remove(memoryPlayer.getPlayer());
            }
        }
    }

    public MemoryPlayer getMemoryPlayer() {
        return memoryPlayer;
    }

    public void setMemoryPlayer(MemoryPlayer memoryPlayer) {
        this.memoryPlayer = memoryPlayer;
    }

    public void setTitle(String title){
        this.title = title;

        this.obj.setDisplayName(title);
    }

    public void remove(Player player){
        //delete the scoreboard instance
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard()); // remove
        this.bukkitScoreboard = null;
    }

    public void display(Player player){
        player.setScoreboard(this.bukkitScoreboard);
    }

    public String getName() {
        return name;
    }

    public boolean isFinished() {
        return finished;
    }

    public List<Row> getRowCache() {
        return rowCache;
    }

    public String getCriterion() {
        return criterion;
    }

    public Scoreboard getBukkitScoreboard() {
        return bukkitScoreboard;
    }

    public Objective getObj() {
        return obj;
    }

    public Row[] getRows() {
        return rows;
    }

    public String getTitle() {
        return title;
    }

    public @Nullable Row addRow(String message){
        if(this.finished){
            new NullPointerException("Can not add rows if scoreboard is already finished").printStackTrace();
            return null;
        }

        try{
            final Row row = new Row(this, message, rows.length);

            this.rowCache.add(row);

            return row;
        }catch(Exception e){
            return null;
        }
    }

    public void finish(){
        if(this.finished){
            new NullPointerException("Can not finish if scoreboard is already finished").printStackTrace();
            return;
        }

        this.finished = true;

        for(int i=rowCache.size()-1; i>=0; i--){
            final Row row = rowCache.get(i);

            final Team team = this.bukkitScoreboard.registerNewTeam(name + "." + criterion + "." + (i+1));
            team.addEntry(ChatColor.values()[i] + "");
            this.obj.getScore(ChatColor.values()[i] + "").setScore(rowCache.size()-i);

            row.team = team;
            row.setMessage(row.message);
        }

        this.rows = rowCache.toArray(new Row[rowCache.size()]);
    }

    public static class Row {

        private final CScoreboard scoreboard;
        private Team team;
        private final int rowInScoreboard;
        private String message;
        private String originalMessage;

        public Row(CScoreboard sb, String message, int row){
            this.scoreboard = sb;
            this.rowInScoreboard = row;
            this.message = message;
            this.originalMessage = message;
        }

        public String getOriginalMessage() {
            return originalMessage;
        }

        public void setMessage(String message){
            this.message = message;

            if(scoreboard.finished){
                message = SkyBlock.getInstance().getIslandBoard().convertPlaceholders(scoreboard.getMemoryPlayer(), message);

               final String[] parts = splitStringWithChatcolorInHalf(message);

                this.team.setPrefix(SkyBlock.getInstance().getUtils().color(parts[0]));
                this.team.setSuffix(SkyBlock.getInstance().getUtils().color(parts[1]));
            }
        }


        public String getMessage() {
            return message;
        }

        private static String[] splitStringWithChatcolorInHalf(String str){

            final int mid = str.length() / 2; //get the middle of the String

            String part1 = str.substring(0, mid);
            String part2 = str.substring(mid);

            if (part1.endsWith("&")){
                part1 = SkyBlock.getInstance().getUtils().removeLastChar(part1);
                part2 = "&"+part2;
            }

            String[] parts = {part1, part2};

            return parts;

        }

    }
}
