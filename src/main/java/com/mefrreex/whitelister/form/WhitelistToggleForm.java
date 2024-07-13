package com.mefrreex.whitelister.form;

import cn.nukkit.Player;
import com.formconstructor.form.ModalForm;
import com.mefrreex.whitelister.Whitelister;
import com.mefrreex.whitelister.event.WhitelistKickPlayerEvent;
import com.mefrreex.whitelister.utils.Language;
import com.mefrreex.whitelister.whitelist.Whitelist;
import com.mefrreex.whitelister.whitelist.WhitelistManager;

public class WhitelistToggleForm {

    public static void sendTo(Player player, Whitelist whitelist) {
        WhitelistManager whitelistManager = Whitelister.getInstance().getWhitelistManager();
        new ModalForm(Language.get("form-toggle-title"))

                .setContent(Language.get(whitelist.isEnable() ?
                        "form-toggle-content-disable" :
                        "form-toggle-content-enable"))

                .setPositiveButton(Language.get(whitelist.isEnable() ?
                        "form-toggle-button-disable" :
                        "form-toggle-button-enable"))

                .setNegativeButton(Language.get("form-toggle-button-back"))
                .setHandler((pl, result) -> {
                    if (result) {
                        whitelist.setEnable(!whitelist.isEnable());
                        if (whitelist.isKickOnlinePlayers()) {
                            whitelistManager.kickNotAllowedPlayers(WhitelistKickPlayerEvent.Reason.ONLINE);
                        }

                        player.sendMessage(Language.get(whitelist.isEnable() ?
                                "form-toggle-message-enable" :
                                "form-toggle-message-disable"));
                    } else {
                        WhitelistMainForm.sendTo(player);
                    }
                })
                .send(player);
    }
}
