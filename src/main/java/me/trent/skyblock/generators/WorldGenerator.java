package me.trent.skyblock.generators;

import me.trent.skyblock.SkyBlock;
import me.trent.worldedit6.WorldEdit6;
import me.trent.worldedit7.WorldEdit7;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import org.bukkit.*;
import me.trent.skyblock.worldedit.WorldEditPersistence;

import java.io.File;

public class WorldGenerator {

    public void generateWorld(String worldName) { // simple generator for all versions
        World world = WorldCreator.name(worldName).type(WorldType.FLAT).environment(World.Environment.NORMAL).generator(new VoidWorld()).createWorld();
    }

    public void pasteSchem(Location location, String fileName) {
        File file = new File(SkyBlock.getInstance().getDataFolder() + "/Schematics/" + fileName);

        if (WorldEditPersistence.isOldWorldEdit()) {
            //System.out.println("Current TPS: "+ MinecraftServer.getServer().recentTps[0]);
            for (double tps : MinecraftServer.getServer().recentTps){
                System.out.println("Old TPS: " + tps);
            }
            WorldEdit6.paste(file, location); // 1.8 - 1.12.2
            for (double tps : MinecraftServer.getServer().recentTps){
                System.out.println("New TPS: " + tps);
            }
        }

        if (WorldEditPersistence.isNewWorldEdit()) {
            WorldEdit7.paste(file, location); // 1.13 - 1.14.4
        }
    }
}