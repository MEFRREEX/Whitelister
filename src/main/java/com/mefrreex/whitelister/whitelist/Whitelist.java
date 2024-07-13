package com.mefrreex.whitelister.whitelist;

import lombok.Data;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Data
public class Whitelist {

    private boolean enable;
    private boolean kickOnlinePlayers;
    private String kickMessage = "The server is whitelisted";

    public transient final WhitelistPlayerManager playerManager;

    public CompletableFuture<Set<String>> getAllowedPlayers() {
        return this.getPlayerManager().getAllowedPlayers();
    }

    public CompletableFuture<Boolean> isPlayerAllowed(String playerName) {
        return this.getPlayerManager().isPlayerAllowed(playerName);
    }

    public CompletableFuture<Void> addAllowedPlayer(String playerName) {
        return this.getPlayerManager().addAllowedPlayer(playerName.toLowerCase());
    }

    public CompletableFuture<Void> removeAllowedPlayer(String playerName) {
        return this.getPlayerManager().removeAllowedPlayer(playerName.toLowerCase());
    }
}
