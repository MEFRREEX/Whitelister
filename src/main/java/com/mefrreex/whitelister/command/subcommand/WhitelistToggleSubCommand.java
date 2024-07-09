package com.mefrreex.whitelister.command.subcommand;

import cn.nukkit.command.CommandSender;
import com.mefrreex.whitelister.Whitelister;
import com.mefrreex.whitelister.command.BaseSubCommand;
import com.mefrreex.whitelister.utils.Language;
import com.mefrreex.whitelister.whitelist.Whitelist;

import java.util.Map;

public class WhitelistToggleSubCommand extends BaseSubCommand {

    public WhitelistToggleSubCommand() {
        super("toggle");
    }

    @Override
    public void execute(CommandSender sender, String commandLabel, Map<Integer, String> args) {
        Whitelist whitelist = Whitelister.getInstance().getWhitelist();
        whitelist.setEnable(!whitelist.isEnable());

        sender.sendMessage(Language.get(whitelist.isEnable() ?
                "command-enable-enabled" :
                "command-disable-disabled"));
    }
}
