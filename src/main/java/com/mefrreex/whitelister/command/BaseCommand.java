package com.mefrreex.whitelister.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandEnum;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.ConfigSection;
import com.mefrreex.whitelister.Whitelister;
import com.mefrreex.whitelister.utils.Utils;

import java.util.*;

public abstract class BaseCommand extends Command {

    private final Map<String, BaseSubCommand> subCommands = new LinkedHashMap<>();

    public BaseCommand(String name) {
        this(name, Whitelister.getInstance().getConfig().getSection("commands." + name));
    }

    public BaseCommand(String name, ConfigSection command) {
        super(command.getString("name"));
        this.setDescription(command.getString("description"));
        this.setAliases(command.getStringList("aliases").toArray(new String[]{}));
        this.setPermission("whitelister.command." + name);
        this.getCommandParameters().clear();
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }

        if (args.length > 0) {
            BaseSubCommand subCommand = subCommands.get(args[0]);

            if (subCommand == null) {
                return false;
            }

            subCommand.execute(sender, args[0], Utils.convertArrayToMap(Arrays.copyOfRange(args, 1, args.length)));
            return true;
        }

        this.executeDefault(sender, commandLabel);
        return true;
    }

    public void executeDefault(CommandSender sender, String commandLabel) {

    }

    public void registerSubCommand(BaseSubCommand subCommand) {
        this.registerSubCommand(subCommand.getName(), subCommand);

        for (String alias : subCommand.getAliases()) {
            this.registerSubCommand(alias, subCommand);
        }
    }

    private void registerSubCommand(String name, BaseSubCommand subCommand) {
        this.subCommands.put(name, subCommand);

        List<CommandParameter> parameters = new LinkedList<>();
        parameters.add(CommandParameter.newEnum(name, new CommandEnum(name, name)));

        parameters.addAll(Arrays.asList(subCommand.getParameters()));

        this.addCommandParameters(name, parameters.toArray(new CommandParameter[0]));
    }
}