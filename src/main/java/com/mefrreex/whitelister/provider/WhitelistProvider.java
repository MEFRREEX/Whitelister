package com.mefrreex.whitelister.provider;

import com.mefrreex.whitelister.whitelist.Whitelist;

import java.util.concurrent.CompletableFuture;

public interface WhitelistProvider {

    CompletableFuture<Void> saveWhitelist(Whitelist whitelist);

    CompletableFuture<Whitelist> loadWhitelist();
}
