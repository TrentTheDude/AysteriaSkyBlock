package me.trent.skyblock.commands.SubCommands.is.oneArgs;

import me.trent.skyblock.SkyBlock;
import me.trent.skyblock.commands.CommandBase;
import me.trent.skyblock.commands.SubCommand;
import me.trent.skyblock.island.Island;
import me.trent.skyblock.island.permissions.Perm;
import me.trent.skyblock.island.permissions.Role;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class isWithdraw extends SubCommand {

    public isWithdraw(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
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

        Role role = SkyBlock.getInstance().getIslandUtils().getRole(p.getUniqueId(), island);
        if (island.hasPerm(role, Perm.BANK_WITHDRAW)) {
            double money = Double.parseDouble(arg2);
            if (island.getBankBalance() >= money) {
                //remove the money
                p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("withdraw-yes").replace("%money%", SkyBlock.getInstance().getUtils().formatNumber(money + "")));
                SkyBlock.getInstance().getUtils().addMoney(p.getUniqueId(), money);
                island.setBankBalance((island.getBankBalance() - money));
            } else {
                p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("withdraw-no"));
            }
        } else {
            p.sendMessage(SkyBlock.getInstance().getUtils().getMessage("noPermissionIsland"));
        }


        return true;
    }
}