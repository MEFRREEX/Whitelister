package com.mefrreex.whitelister.command.subcommand;

import cn.nukkit.command.CommandSender;
import com.mefrreex.whitelister.Whitelister;
import com.mefrreex.whitelister.command.BaseSubCommand;
import com.mefrreex.whitelister.event.WhitelistKickPlayerEvent;
import com.mefrreex.whitelister.utils.Language;
import com.mefrreex.whitelister.whitelist.Whitelist;
import com.mefrreex.whitelister.whitelist.WhitelistManager;

import java.util.Map;

public class WhitelistEnableSubCommand extends BaseSubCommand {

    public WhitelistEnableSubCommand() {
        super("enable");
        this.setAliases(new String[]{"on"});
    }

    @Override
    public void execute(CommandSender sender, String commandLabel, Map<Integer, String> args) {
        WhitelistManager whitelistManager = Whitelister.getInstance().getWhitelistManager();
        Whitelist whitelist = whitelistManager.getWhitelist();

        if (whitelist.isEnable()) {
            sender.sendMessage(Language.get("command-enable-already-enabled"));
            return;
        }

        whitelist.setEnable(true);
        sender.sendMessage(Language.get("command-enable-enabled"));

        if (whitelist.isKickOnlinePlayers()) {
            whitelistManager.kickNotAllowedPlayers(WhitelistKickPlayerEvent.Reason.ONLINE);
        }
    }
}
