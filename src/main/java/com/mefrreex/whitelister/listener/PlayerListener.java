package com.mefrreex.whitelister.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerLoginEvent;
import com.mefrreex.whitelister.Whitelister;
import com.mefrreex.whitelister.whitelist.Whitelist;
import com.mefrreex.whitelister.whitelist.WhitelistManager;

public class PlayerListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("whitelister.bypass")) {
            return;
        }

        WhitelistManager whitelistManager = Whitelister.getInstance().getWhitelistManager();
        Whitelist whitelist = whitelistManager.getWhitelist();

        if (whitelist.isEnable() && !whitelist.isPlayerAllowed(player.getName())) {
            whitelistManager.kickPlayer(player);
            event.setCancelled();
        }
    }
}