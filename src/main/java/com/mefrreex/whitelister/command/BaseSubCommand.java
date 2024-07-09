package com.mefrreex.whitelister.command;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.utils.TextFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public abstract class BaseSubCommand {

    private final String name;
    private final String description;
    private String[] aliases = new String[0];
    private String permission;

    protected BaseSubCommand(String name) {
        this(name, name);
    }

    protected BaseSubCommand(String name, String description) {
        this.name = name.toLowerCase();
        this.description = description;

    }

    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }

    public abstract void execute(CommandSender sender, String commandLabel, Map<Integer, String> args);

    public boolean testPermission(CommandSender target) {
        if (this.testPermissionSilent(target)) {
            return true;
        }

        target.sendMessage(new TranslationContainer(TextFormat.RED + "%commands.generic.unknown", this.name));
        return false;
    }

    public boolean testPermissionSilent(CommandSender target) {
        if (this.permission == null || this.permission.isEmpty()) {
            return true;
        }

        String[] permissions = this.permission.split(";");
        for (String permission : permissions) {
            if (target.hasPermission(permission)) {
                return true;
            }
        }

        return false;
    }
}
