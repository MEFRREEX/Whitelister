package com.mefrreex.whitelister.command.subcommand;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import com.mefrreex.whitelister.Whitelister;
import com.mefrreex.whitelister.command.BaseSubCommand;
import com.mefrreex.whitelister.utils.Language;
import com.mefrreex.whitelister.whitelist.Whitelist;

import java.util.Map;

public class WhitelistRemovePlayerSubCommand extends BaseSubCommand {

    public WhitelistRemovePlayerSubCommand() {
        super("removeplayer");
        this.setAliases(new String[]{"remove"});
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[]{
                CommandParameter.newType("player", CommandParamType.TARGET)
        };
    }

    @Override
    public void execute(CommandSender sender, String commandLabel, Map<Integer, String> args) {
        Whitelist whitelist = Whitelister.getInstance().getWhitelist();

        String playerName = args.get(0);
        if (playerName == null) {
            sender.sendMessage(Language.get("command-removeplayer-missing-nickname"));
            return;
        }

        whitelist.isPlayerAllowed(playerName).thenAcceptAsync(allowed -> {
            if (!allowed) {
                sender.sendMessage(Language.get("command-removeplayer-not-found", playerName));
            } else {
                whitelist.removeAllowedPlayer(playerName).whenCompleteAsync((v, error) -> {
                    if (error != null) {
                        error.printStackTrace();
                    } else {
                        sender.sendMessage(Language.get("command-removeplayer-removed", playerName));
                    }
                });
            }
        }).exceptionally(error -> {
            error.printStackTrace();
            return null;
        });
    }
}
