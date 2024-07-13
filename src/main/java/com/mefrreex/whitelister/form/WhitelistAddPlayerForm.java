package com.mefrreex.whitelister.form;

import cn.nukkit.Player;
import com.formconstructor.form.CustomForm;
import com.formconstructor.form.element.custom.Input;
import com.mefrreex.whitelister.utils.Language;
import com.mefrreex.whitelister.whitelist.Whitelist;

public class WhitelistAddPlayerForm {

    public static void sendTo(Player player, Whitelist whitelist) {
        CustomForm form = new CustomForm(Language.get("form-add-player-title"));

        form.addElement("name", new Input()
                .setName(Language.get("form-add-player-input-name-name"))
                .setPlaceholder(Language.get("form-add-player-input-name-placeholder")));

        form.setHandler((pl, response) -> {
            String playerName = response.getInput("name").getValue();

            whitelist.isPlayerAllowed(playerName).thenAcceptAsync(allowed -> {
                if (allowed) {
                    player.sendMessage(Language.get("form-add-player-message-already-added", playerName));
                } else {
                    whitelist.addAllowedPlayer(playerName).whenCompleteAsync((v, error) -> {
                        if (error != null) {
                            error.printStackTrace();
                        } else {
                            player.sendMessage(Language.get("form-add-player-message-added", playerName));
                        }
                    });
                }
            }).exceptionally(error -> {
                error.printStackTrace();
                return null;
            });
        });

        form.send(player);
    }
}
