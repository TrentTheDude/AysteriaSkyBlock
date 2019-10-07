package me.trent.skyblock.commands.SubCommands.is.oneArgs;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.island.Island;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isJoin extends SubCommand {

    public isJoin(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        Player p = (Player)sender;
        String arg2 = args.get(1);

        Player target = Bukkit.getPlayerExact(arg2);
        if (target != null) {
            if (SkyBlock.getInstance().getIslandUtils().inIsland(target.getUniqueId())) {
                //in an island, check the invites
                Island island = SkyBlock.getInstance().getIslandUtils().getIsland(target.getUniqueId());
                if (island.isInvited(p.getUniqueId())) {
                    // we're invited to the island, now try to join it...
                    int memberCap = island.getMemberLimit();
                    int currentMembers = island.getAllPlayers().size();

                    if (Math.addExact(currentMembers, 1) >= memberCap) {
                        //can't let them join
                        p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("islandLimit"));
                        return false;
                    }

                    //if the cap isn't reached, we want them to join the island now...

                    if (!SkyBlock.getInstance().getIslandUtils().inIsland(p.getUniqueId())) {
                        //we're not in an island, try to join...
                        //we're not in an island right now, continue on...
                        //so we're not in an island, and we're invited, and the cap isn't reached, so we can probably join the island now
                        if (island.join(p.getUniqueId())) {
                            //we have joined the specified island... now send the message

                            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("joined"));

                            //send the join message to everyone in the island that's online
                            String everyoneMessage = SkyBlock.getInstance().getUtils().getMessage("joinedEveryone").replace("%player%", p.getName());
                            for (Player t : island.getAllPlayersOnline()) {
                                if (!t.getUniqueId().equals(p.getUniqueId())) {
                                    //that's the sender so we can send them the message now
                                    t.sendMessage(everyoneMessage);
                                }
                            }

                        } else {
                            //we did not join the island, but we really don't know what happened, so just send an error happened message...
                            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("error"));
                        }

                    } else {
                        //we're already in an island
                        p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("alreadyIsland"));
                    }
                } else {
                    //you are not invited to the island
                    p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("notInvited"));
                }
            } else {
                //this player isnt part of an island
                p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("inviterNotInIsland"));
            }
        } else {
            //cant find this player
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noPlayer"));
        }


        return true;
    }
}