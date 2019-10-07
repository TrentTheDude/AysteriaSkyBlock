package me.trent.skyblock.commands.SubCommands.is.oneArgs;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.island.Island;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isDeposit extends SubCommand {

    public isDeposit(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
        this.setup(commandBase, name, description, permission, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, List<String> args) {

        if (!super.execute(sender, args)) return false; // no permission for this command...

        Player p = (Player)sender;
        String arg2 = args.get(1);

        Island island = SkyBlock.getInstance().getIslandUtils().getIsland(p.getUniqueId());
        if (island == null) {
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("notInIsland"));
            return false;
        }
        double money = Double.parseDouble(arg2);
        if (SkyBlock.getInstance().getUtils().getBalance(p.getUniqueId()) >= money) {
            //remove the money
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("deposit-yes").replace("%money%", SkyBlock.getInstance().getUtils().formatNumber(money + "")));
            SkyBlock.getInstance().getUtils().takeMoney(p.getUniqueId(), money);
            island.setBankBalance((island.getBankBalance() + money));
        } else {
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("deposit-no"));
        }


        return true;
    }
}