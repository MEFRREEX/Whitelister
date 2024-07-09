package com.mefrreex.whitelister.command.subcommand;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import com.mefrreex.whitelister.Whitelister;
import com.mefrreex.whitelister.command.BaseSubCommand;
import com.mefrreex.whitelister.utils.Language;
import com.mefrreex.whitelister.whitelist.Whitelist;

import java.util.Map;

public class WhitelistKickMessageSubCommand extends BaseSubCommand {

    public WhitelistKickMessageSubCommand() {
        super("setkickmessage");
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[]{
                CommandParameter.newType("message", CommandParamType.MESSAGE)
        };
    }

    @Override
    public void execute(CommandSender sender, String commandLabel, Map<Integer, String> args) {
        Whitelist whitelist = Whitelister.getInstance().getWhitelist();

        String kickMessage = String.join(" ", args.values());
        if (kickMessage.isBlank()) {
            sender.sendMessage(Language.get("command-kickmessage-kick-message-blank"));
            return;
        }

        whitelist.setKickMessage(kickMessage);
        sender.sendMessage(Language.get("command-kickmessage-set"));
    }
}
