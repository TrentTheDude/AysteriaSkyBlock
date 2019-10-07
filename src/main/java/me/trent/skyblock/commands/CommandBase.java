package me.trent.skyblock.commands;

import me.trent.skyblock.Storage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class CommandBase extends BukkitCommand {

    private String name;
    private List<String> aliases;
    private String permission;
    private String description;

    private String usageMessage;

    private List<SubCommand> subCommands = new ArrayList<>();


    protected CommandBase(String name, String description, String permission, String usageMessage, String... aliases) {
        super(name);
        setup(name, description, permission, usageMessage, aliases);
    }


    public void setup(String name, String description, String permission, String usageMessage, String... aliases) {
        this.setNameT(name);
        this.setDescriptionT(description);
        this.setPermissionT(permission);
        this.setUsageMessageT(usageMessage);
        this.setAliasesT(aliases);

        this.setPermission(getPermissionT());
        this.setAliases(getAliasesT());
        this.setDescription(getDescriptionT());
        this.setUsage(getUsageMessageT());
        this.setPermissionMessage("perm message......");

        Storage.commandSet.add(this);

        register();


        System.out.println("\n TrentSkyBlock - Command Injection: Injected command /"+name+"\n");
    }

    private void register() { // INJECT COMMAND INTO BUKKIT
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register(getName(), this);

            //((CraftServer) Bukkit.getServer()).getCommandMap().register(getNameT(), this); // NMS VERSION, ABOVE IS REFLECTION, very good

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(CommandSender sender, String defaultAlias, String[] defaultArgs) {
            List<String> args = new LinkedList<String>(Arrays.asList(defaultArgs));

        if (sender instanceof Player){
            //not console, check for player
            if (!sender.isOp()){
                //not op, check perm
                if (!sender.hasPermission(getPermissionT())){
                    return false;
                }
            }
        }

            if (!getSubCommands().isEmpty()) {
                //not empty, we have sub commands, try and find the sub command we're looking for based off of the first argument after the main command...
                if (!args.isEmpty()) {
                    //our arguments are not empty, that means we have at least 1 argument we can check from...
                    String subCommandName = args.get(0);
                    SubCommand subCommand = getSubCommand(subCommandName);
                    if (subCommand != null) {
                        //we found a subCommand that's name was matching the first argument that was provided, we want to re-run the
                        // sub command's code with the next argument in line, so we need to remove the first argument that was the subcmd's name...
                        args.remove(0); // remove the subCommand's name from the list...

                        subCommand.execute(sender, args);
                        return false;
                    }
                }
            }
        return true;
    }


    public boolean testPermission(CommandSender sender, String permission){
        if (sender instanceof Player){
            //not console, check for player
            if (!sender.isOp()){
                //not op, check perm
                return sender.hasPermission(permission);
            }
        }
        return true;
    }

    public boolean match(String string) {
        if (getName().equalsIgnoreCase(string)) {
            return true;
        } else {
            for (String alias : getAliases()) {
                if (alias.equalsIgnoreCase(string)) {
                    return true;
                }
            }
        }
        return false;
    }

    public SubCommand getSubCommand(String name) {
        for (SubCommand subCommand : getSubCommands()) {
            if (subCommand.getName().equalsIgnoreCase(name)) {
                return subCommand;
            } else {
                for (String alias : subCommand.getAliases()) {
                    if (alias.equalsIgnoreCase(name)) {
                        return subCommand;
                    }
                }
            }
        }
        return null;
    }


    public void addSubCommand(SubCommand subCommand) {
        if (!this.getSubCommands().contains(subCommand)) {
            this.subCommands.add(subCommand);
        }
    }

    public void removeSubCommand(SubCommand subCommand) {
        this.subCommands.remove(subCommand);
    }

    public List<SubCommand> getSubCommands() {
        return subCommands;
    }

    public String getNameT() {
        return name;
    }

    public void setNameT(String name) {
        this.name = name;
    }

    public String getPermissionT() {
        return permission;
    }

    public void setPermissionT(String permission) {
        this.permission = permission;
    }

    public List<String> getAliasesT() {
        return aliases;
    }

    public void setAliasesT(String... aliases) {
        this.aliases = Arrays.asList(aliases);
    }

    public String getDescriptionT() {
        return description;
    }

    public void setDescriptionT(String description) {
        this.description = description;
    }

    public String getUsageMessageT() {
        return usageMessage;
    }

    public void setUsageMessageT(String usageMessage) {
        this.usageMessage = usageMessage;
    }
}