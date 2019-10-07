package me.trent.skyblock.API;

import me.trent.skyblock.island.IslandUtils;
import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.Utils;
import me.trent.skyblock.filemanager.FileManager;
import me.trent.skyblock.generators.WorldGenerator;

public class SavageSkyBlockAPI {

    private SkyBlock getSkyBlockPlusInstance() {
        return SkyBlock.getInstance();
    }

    public IslandUtils getIslandUtils(){
        return getSkyBlockPlusInstance().getIslandUtils();
    }

    public WorldGenerator getWorldGenerator(){
        return getSkyBlockPlusInstance().getWorldGenerator();
    }

    public Utils getUtils(){
        return getSkyBlockPlusInstance().getUtils();
    }

    public FileManager getFileManager(){
        return getSkyBlockPlusInstance().getFileManager();
    }
}