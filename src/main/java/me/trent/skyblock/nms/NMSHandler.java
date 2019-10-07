package me.trent.skyblock.nms;

import me.trent.skyblock.island.Island;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public abstract class NMSHandler {

    public abstract void calculate(Chunk chunk, Island island);

    public abstract void removeBlockSuperFast(int X, int Y, int Z, boolean applyPhysics);

    public abstract void sendBorder(Player p, double x, double z, double radius);

    public abstract void sendTitle(Player p, String text, int in, int stay, int out, String type);

    public abstract void generate(String name);

    public abstract String getVersion();

}