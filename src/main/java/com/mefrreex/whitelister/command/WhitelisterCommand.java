package com.mefrreex.whitelister.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import com.mefrreex.whitelister.command.subcommand.WhitelistAddPlayerSubCommand;
import com.mefrreex.whitelister.command.subcommand.WhitelistDisableSubCommand;
import com.mefrreex.whitelister.command.subcommand.WhitelistEnableSubCommand;
import com.mefrreex.whitelister.command.subcommand.WhitelistRemovePlayerSubCommand;
import com.mefrreex.whitelister.form.WhitelistMainForm;
import com.mefrreex.whitelister.utils.Language;

public class WhitelisterCommand extends BaseCommand {

    public WhitelisterCommand() {
        super("whitelister");
        this.registerSubCommand(new WhitelistEnableSubCommand());
        this.registerSubCommand(new WhitelistDisableSubCommand());
        this.registerSubCommand(new WhitelistAddPlayerSubCommand());
        this.registerSubCommand(new WhitelistRemovePlayerSubCommand());
    }

    @Override
    public void executeDefault(CommandSender sender, String commandLabel) {
        if (sender instanceof Player player) {
            WhitelistMainForm.sendTo(player);
        } else {
            sender.sendMessage(Language.get("command-in-game"));
        }
    }
}
