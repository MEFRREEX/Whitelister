package com.mefrreex.whitelister.form;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import com.formconstructor.form.SimpleForm;
import com.formconstructor.form.element.simple.Button;
import com.mefrreex.whitelister.utils.Language;
import com.mefrreex.whitelister.utils.Utils;
import com.mefrreex.whitelister.whitelist.Whitelist;

public class WhitelistPlayersForm {

    public static void sendTo(Player player, Whitelist whitelist) {
        SimpleForm form = new SimpleForm(Language.get("form-players-title"));
        form.addContent(Language.get("form-players-content"));

        form.addButton(Language.get("form-players-button-add"), (pl, b) -> {
            WhitelistAddPlayerForm.sendTo(player, whitelist);
        });

        whitelist.getAllowedPlayers().whenCompleteAsync((playerNames, error) -> {
            if (error != null) {
                error.printStackTrace();
            } else {
                for (String playerName : playerNames) {
                    String offlinePlayerName = Utils.getOfflinePlayerName(playerName);

                    form.addButton(new Button()
                            .setName(Language.get("form-players-button-format", playerName, offlinePlayerName))
                            .onClick((pl, b) -> {
                                WhitelistRemovePlayerForm.sendTo(player, whitelist, playerName);
                            }));
                }
            }

            form.send(player);
        });
    }
}
