package com.mefrreex.whitelister.command.subcommand;

import cn.nukkit.command.CommandSender;
import com.mefrreex.whitelister.Whitelister;
import com.mefrreex.whitelister.command.BaseSubCommand;
import com.mefrreex.whitelister.utils.Language;
import com.mefrreex.whitelister.utils.Utils;
import com.mefrreex.whitelister.whitelist.Whitelist;

import java.util.Map;
import java.util.stream.Collectors;

public class WhitelistPlayerListSubCommand extends BaseSubCommand {

    public WhitelistPlayerListSubCommand() {
        super("playerlist");
        this.setAliases(new String[]{"list", "players"});
    }

    @Override
    public void execute(CommandSender sender, String commandLabel, Map<Integer, String> args) {
        Whitelist whitelist = Whitelister.getInstance().getWhitelist();
        whitelist.getAllowedPlayers().thenAcceptAsync(playerNames -> {
            if (playerNames.isEmpty()) {
                sender.sendMessage(Language.get("command-playerlist-empty"));
            } else {
                sender.sendMessage(Language.get("command-playerlist-list", playerNames.stream()
                        .map(Utils::getOfflinePlayerName)
                        .collect(Collectors.joining(", "))));
            }
        }).exceptionally(error -> {
            error.printStackTrace();
            return null;
        });
    }
}
