package com.mefrreex.whitelister.command.subcommand;

import cn.nukkit.command.CommandSender;
import com.mefrreex.whitelister.Whitelister;
import com.mefrreex.whitelister.command.BaseSubCommand;
import com.mefrreex.whitelister.utils.Language;
import com.mefrreex.whitelister.whitelist.Whitelist;

import java.util.Map;

public class WhitelistDisableSubCommand extends BaseSubCommand {

    public WhitelistDisableSubCommand() {
        super("disable");
        this.setAliases(new String[]{"off"});
    }

    @Override
    public void execute(CommandSender sender, String commandLabel, Map<Integer, String> args) {
        Whitelist whitelist = Whitelister.getInstance().getWhitelist();

        if (!whitelist.isEnable()) {
            sender.sendMessage(Language.get("command-disable-already-disabled"));
            return;
        }

        whitelist.setEnable(false);
        sender.sendMessage(Language.get("command-disable-disabled"));
    }
}
