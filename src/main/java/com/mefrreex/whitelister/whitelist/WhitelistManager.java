package com.mefrreex.whitelister.whitelist;

import cn.nukkit.Player;
import cn.nukkit.Server;
import com.mefrreex.whitelister.event.WhitelistKickPlayerEvent;
import com.mefrreex.whitelister.provider.WhitelistProvider;
import lombok.Getter;

import java.util.concurrent.ExecutionException;

public class WhitelistManager {

    @Getter
    private Whitelist whitelist;

    private boolean initialized = false;

    private final WhitelistProvider whitelistProvider;

    public WhitelistManager(WhitelistProvider whitelistProvider) {
        this.whitelistProvider = whitelistProvider;
    }

    public void kickPlayer(Player player, WhitelistKickPlayerEvent.Reason reason) {
        WhitelistKickPlayerEvent event = new WhitelistKickPlayerEvent(whitelist.getKickMessage(), reason);
        Server.getInstance().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        player.kick(event.getKickMessage(), false);
    }

    public void kickNotAllowedPlayers(WhitelistKickPlayerEvent.Reason reason) {
        for (Player player : Server.getInstance().getOnlinePlayers().values()) {
            if (!whitelist.isPlayerAllowed(player.getName())) {
                this.kickPlayer(player, reason);
            }
        }
    }

    public void loadWhitelist() {
        try {
            this.whitelist = whitelistProvider.loadWhitelist().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        this.initialized = true;
    }

    public void saveWhitelist() {
        if (initialized) {
            try {
                this.whitelistProvider.saveWhitelist(whitelist).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
