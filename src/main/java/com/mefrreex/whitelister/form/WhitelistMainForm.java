package com.mefrreex.whitelister.form;

import cn.nukkit.Player;
import com.formconstructor.form.SimpleForm;
import com.formconstructor.form.element.simple.Button;
import com.mefrreex.whitelister.Whitelister;
import com.mefrreex.whitelister.utils.Language;
import com.mefrreex.whitelister.whitelist.Whitelist;

public class WhitelistMainForm {

    public static void sendTo(Player player) {
        SimpleForm form = new SimpleForm(Language.get("form-main-title"));
        form.addContent(Language.get("form-main-content"));

        Whitelist whitelist = Whitelister.getInstance().getWhitelist();

        form.addButton(new Button()
                .setName(whitelist.isEnable() ?
                        Language.get("form-main-button-toggle-disable") :
                        Language.get("form-main-button-toggle-enable"))
                .onClick((pl, b) -> {
                    WhitelistToggleForm.sendTo(player, whitelist);
                }));

        form.addButton(Language.get("form-main-button-players"), (pl, b) -> {
            WhitelistPlayersForm.sendTo(player, whitelist);
        });

        form.addButton(Language.get("form-main-button-settings"), (pl, b) -> {
            WhitelistSettingsForm.sendTo(player, whitelist);
        });

        form.send(player);
    }
}
