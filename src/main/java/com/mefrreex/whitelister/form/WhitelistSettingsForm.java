package com.mefrreex.whitelister.form;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import com.formconstructor.form.CustomForm;
import com.formconstructor.form.element.custom.Input;
import com.formconstructor.form.element.custom.Toggle;
import com.mefrreex.whitelister.Whitelister;
import com.mefrreex.whitelister.event.WhitelistKickPlayerEvent;
import com.mefrreex.whitelister.utils.Language;
import com.mefrreex.whitelister.whitelist.Whitelist;
import com.mefrreex.whitelister.whitelist.WhitelistManager;

public class WhitelistSettingsForm {

    public static void sendTo(Player player, Whitelist whitelist) {
        WhitelistManager whitelistManager = Whitelister.getInstance().getWhitelistManager();

        CustomForm form = new CustomForm(Language.get("form-settings-title"));

        form.addElement("enable", new Toggle(Language.get("form-settings-toggle-enable"), whitelist.isEnable()))
            .addElement("kickOnlinePlayers", new Toggle(Language.get("form-settings-toggle-kick-online-players"), whitelist.isKickOnlinePlayers()))
            .addElement("kickMessage", new Input()
                    .setName(Language.get("form-settings-input-kick-message-name"))
                    .setPlaceholder(Language.get("form-settings-input-kick-message-placeholder"))
                    .setDefaultValue(whitelist.getKickMessage()));

        form.setHandler((pl, response) -> {
            boolean defaultEnable = whitelist.isEnable();
            boolean enable = response.getToggle("enable").getValue();
            boolean kickOnlinePlayers = response.getToggle("kickOnlinePlayers").getValue();

            String kickMessage = response.getInput("kickMessage").getValue();
            if (kickMessage.isBlank()) {
                player.sendMessage(Language.get("form-settings-message-kick-message-blank"));
                return;
            }

            whitelist.setEnable(enable);
            whitelist.setKickOnlinePlayers(kickOnlinePlayers);
            whitelist.setKickMessage(TextFormat.colorize(kickMessage));
            whitelistManager.saveWhitelist();

            player.sendMessage(Language.get("form-settings-message-settings-saved"));
            if (defaultEnable != enable) {
                player.sendMessage(Language.get(enable ?
                        "form-settings-message-whitelist-enabled" :
                        "form-settings-message-whitelist-disabled"));
            }

            if (enable && whitelist.isKickOnlinePlayers()) {
                whitelistManager.kickNotAllowedPlayers(WhitelistKickPlayerEvent.Reason.ONLINE);
            }
        });

        form.setNoneHandler(pl -> {
            player.sendMessage(Language.get("form-settings-message-settings-not-saved"));
        });

        form.send(player);
    }
}
