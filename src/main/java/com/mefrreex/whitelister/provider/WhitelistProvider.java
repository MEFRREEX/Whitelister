package com.mefrreex.whitelister.provider;

import com.mefrreex.whitelister.whitelist.Whitelist;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface WhitelistProvider {

    CompletableFuture<Void> saveWhitelist(Whitelist whitelist);

    CompletableFuture<Whitelist> loadWhitelist();

    CompletableFuture<Set<String>> getAllowedPlayers();

    CompletableFuture<Boolean> isPlayerAllowed(String playerName);

    CompletableFuture<Void> addAllowedPlayer(String playerName);

    CompletableFuture<Void> removeAllowedPlayer(String playerName);
}
