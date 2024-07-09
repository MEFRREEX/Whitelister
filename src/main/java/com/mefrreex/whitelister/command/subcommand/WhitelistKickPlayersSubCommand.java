package com.mefrreex.whitelister.command.subcommand;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandEnum;
import cn.nukkit.command.data.CommandParameter;
import com.mefrreex.whitelister.Whitelister;
import com.mefrreex.whitelister.command.BaseSubCommand;
import com.mefrreex.whitelister.utils.Language;
import com.mefrreex.whitelister.whitelist.Whitelist;

import java.util.Map;

public class WhitelistKickPlayersSubCommand extends BaseSubCommand {

    public WhitelistKickPlayersSubCommand() {
        super("setkickplayers");
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[]{
                CommandParameter.newEnum("message", CommandEnum.ENUM_BOOLEAN)
        };
    }

    @Override
    public void execute(CommandSender sender, String commandLabel, Map<Integer, String> args) {
        Whitelist whitelist = Whitelister.getInstance().getWhitelist();

        Boolean kickOnlinePlayers = switch (args.getOrDefault(0, "").toLowerCase()) {
            case "true" -> true;
            case "false" -> false;
            default -> null;
        };

        if (kickOnlinePlayers == null) {
            sender.sendMessage(Language.get("command-kickplayers-wrong-value"));
            return;
        }

        whitelist.setKickOnlinePlayers(kickOnlinePlayers);
        sender.sendMessage(Language.get(kickOnlinePlayers ?
                "command-kickplayers-enabled" :
                "command-kickplayers-disabled"));
    }
}
