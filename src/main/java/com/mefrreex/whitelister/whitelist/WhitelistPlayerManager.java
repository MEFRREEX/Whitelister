package com.mefrreex.whitelister.whitelist;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface WhitelistPlayerManager {

    CompletableFuture<Set<String>> getAllowedPlayers();

    CompletableFuture<Boolean> isPlayerAllowed(String playerName);

    CompletableFuture<Void> addAllowedPlayer(String playerName);

    CompletableFuture<Void> removeAllowedPlayer(String playerName);
}
