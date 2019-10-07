package me.trent.skyblock.commands;

import me.trent.skyblock.commands.Commands.isBase;
import me.trent.skyblock.commands.Commands.isaBase;
import me.trent.skyblock.commands.Commands.pingBase;
import me.trent.skyblock.commands.SubCommands.is.*;
import me.trent.skyblock.commands.SubCommands.isa.isaDelete;
import me.trent.skyblock.commands.SubCommands.isa.isaHelp;
import me.trent.skyblock.commands.SubCommands.isa.isaReload;
import me.trent.skyblock.commands.SubCommands.isa.isaSetSpawn;
import me.trent.skyblock.commands.SubCommands.pingSUB;

public class CommandImplementation {

    public void registerCustomCommands(){

        CommandBase pingBASE = new pingBase("test", "test command", "", "Do /test <player>", "t", "tt");

        SubCommand pingSUB = new pingSUB(pingBASE, "ping", "test command, sub command is 'ping'", "", "&cTry /ping <player>", "p");



        //##############################################\\
        //    ISA COMMANDS \\
        //##############################################\\

        CommandBase isaBase = new isaBase("isa", "Island Admin Command", "skyblock.admin", "/isa", "isadmin");

        SubCommand isaHelp = new isaHelp(isaBase, "help", "Displays help for ISA-related commands", "", "/isa help", "h");
        SubCommand isaReload = new isaReload(isaBase, "reload", "Reloads the Data Files...", "", "/isa reload", "r");
        SubCommand isaSetSpawn = new isaSetSpawn(isaBase, "setspawn", "Sets the Server's Spawn...", "", "/isa setspawn", "ss");
        SubCommand isaDelete = new isaDelete(isaBase, "delete", "Delete a player's Island...", "", "/isa delete <playerName>", "d");

        //##############################################\\
        //    IS COMMANDS \\
        //##############################################\\

        CommandBase isBase = new isBase("is", "Main Island Command", "", "/is");

        SubCommand isBalance = new isBalance(isBase, "balance", "Check Island's Balance", "", "/is balance", "bal", "money", "m");
        SubCommand isBank = new isBank(isBase, "bank", "Opne Island's Bank Storage", "", "/is bank", "b", "chest");
        SubCommand isBiome = new isBiome(isBase, "biome", "Change Island's Biome", "", "/is biome", "");
        SubCommand isCreate = new isCreate(isBase, "create", "Create an Island", "", "/is create", "c", "new");
        SubCommand isDelete = new isDelete(isBase, "delete", "Delete your Island", "", "/is delete", "remove");
        SubCommand isHelp = new isHelp(isBase, "help", "Skyblock help command", "", "/is help", "h");
        SubCommand isHome = new isHome(isBase, "home", "Teleport to Island's home location", "", "/is home", "go");
        SubCommand isInspect = new isInspect(isBase, "inspect", "Inspect land on your Island", "", "/is inspect (toggles on/off)", "i");
        SubCommand isLeave = new isLeave(isBase, "leave", "Leave your current Island", "", "/is leave", "quit");
        SubCommand isPerms = new isPerms(isBase, "perms", "Open your Island's perms GUI", "", "/is perms", "p");
        SubCommand isQuests = new isQuests(isBase, "quests", "Open Island Quests", "", "/is quests", "q", "quest");
        SubCommand isRules = new isRules(isBase, "rules", "Open your Island's rules GUI", "", "/is rules", "r", "rule");
        SubCommand isScoreboard = new isScoreboard(isBase, "scoreboard", "Turns the Island Scoreboard on/off", "", "/is sb (turns on/off)", "sb");
        SubCommand isSetHome = new isSethome(isBase, "sethome", "Sets island's home location", "", "/is sethome", "");
        SubCommand isSpawn = new isSpawn(isBase, "spawn", "Teleport to the Server's Skyblock spawn", "", "/is spawn", "");
        SubCommand isTop = new isTop(isBase, "top", "View the Top Islands", "", "/is top", "t");
        SubCommand isUpgrades = new isBank(isBase, "upgrades", "View your Island's upgrades menu", "", "/is upgrades", "upgrade");
        SubCommand isVersion = new isVersion(isBase, "version", "View the Skyblock's core version.", "", "/is version", "ver");
        SubCommand isWarps = new isWarps(isBase, "warps", "View the Island's Warps menu", "", "/is warps", "");
        SubCommand isWorth = new isWorth(isBase, "worth", "View the Island's worth.", "", "/is worth", "");


    }

}