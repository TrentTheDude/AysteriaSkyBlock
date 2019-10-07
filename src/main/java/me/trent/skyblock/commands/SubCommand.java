package me.trent.skyblock.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public abstract class SubCommand {

    private CommandBase commandBase;
    private String name;
    private List<String> aliases;
    private String permission;
    private String description;
    private String usageMessage;

   protected SubCommand(){}

    public void setup(CommandBase commandBase, String name, String description, String permission, String usageMessage, String... aliases){
       this.setCommandBase(commandBase);
        this.setName(name);
        this.setDescription(description);
        this.setUsageMessage(usageMessage);
        this.setAliases(aliases);

        if (permission.equalsIgnoreCase("")){
            //inherit from the baseCMD
            this.setPermission(getCommandBase().getPermissionT());
        }else{
            this.setPermission(permission);
        }

        getCommandBase().addSubCommand(this);

        //we set the command base, now to modify inside of it.

    }

    public boolean execute(CommandSender sender, List<String> args){

       //we want to check the logic of PERMISSIONS, and ARGUMENTS before handling our code to our sub-class

        if (sender instanceof Player){
            //not console, check for player
            if (!sender.isOp()){
                //not op, check perm
                return sender.hasPermission(getPermission());
            }
        }

       return true;
    }

    public CommandBase getCommandBase() {
        return commandBase;
    }

    public void setCommandBase(CommandBase commandBase) {
        this.commandBase = commandBase;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAliases(String... aliases) {
        this.aliases = Arrays.asList(aliases);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setUsageMessage(String usageMessage) {
        this.usageMessage = usageMessage;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getDescription() {
        return description;
    }

    public String getUsageMessage() {
        return usageMessage;
    }
}