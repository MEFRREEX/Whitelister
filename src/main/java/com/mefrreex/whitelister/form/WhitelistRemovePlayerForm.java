package com.mefrreex.whitelister.form;

import cn.nukkit.Player;
import com.formconstructor.form.ModalForm;
import com.mefrreex.whitelister.utils.Language;
import com.mefrreex.whitelister.whitelist.Whitelist;

public class WhitelistRemovePlayerForm {

    public static void sendTo(Player player, Whitelist whitelist, String playerName) {
        new ModalForm(Language.get("form-remove-player-title"))
                .setContent(Language.get("form-remove-player-content"))
                .setPositiveButton(Language.get("form-remove-player-button-remove"))
                .setNegativeButton(Language.get("form-remove-player-button-back"))
                .setHandler((pl, result) -> {
                    if (result) {
                        whitelist.removeAllowedPlayer(playerName).whenCompleteAsync((v, error) -> {
                            if (error != null) {
                                error.printStackTrace();
                            } else {
                                player.sendMessage(Language.get("form-remove-player-message-removed"));
                            }
                        });
                    } else {
                        WhitelistPlayersForm.sendTo(player, whitelist);
                    }
                })
                .send(player);
    }
}
