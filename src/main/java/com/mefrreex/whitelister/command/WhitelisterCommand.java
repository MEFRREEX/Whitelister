package com.mefrreex.whitelister.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import com.mefrreex.whitelister.command.subcommand.*;
import com.mefrreex.whitelister.form.WhitelistMainForm;
import com.mefrreex.whitelister.utils.Language;

public class WhitelisterCommand extends BaseCommand {

    public WhitelisterCommand() {
        super("whitelister");
        this.registerSubCommand(new WhitelistEnableSubCommand());
        this.registerSubCommand(new WhitelistDisableSubCommand());
        this.registerSubCommand(new WhitelistToggleSubCommand());
        this.registerSubCommand(new WhitelistKickPlayersSubCommand());
        this.registerSubCommand(new WhitelistAddPlayerSubCommand());
        this.registerSubCommand(new WhitelistRemovePlayerSubCommand());
        this.registerSubCommand(new WhitelistPlayerListSubCommand());
        this.registerSubCommand(new WhitelistKickMessageSubCommand());
        this.registerSubCommand(new WhitelistKickPlayersSubCommand());
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
