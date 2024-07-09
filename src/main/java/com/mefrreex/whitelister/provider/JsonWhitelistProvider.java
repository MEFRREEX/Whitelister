package com.mefrreex.whitelister.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mefrreex.whitelister.Whitelister;
import com.mefrreex.whitelister.whitelist.Whitelist;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;

public class JsonWhitelistProvider implements WhitelistProvider {

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public File getWhitelistFile() {
        return new File(Whitelister.getInstance().getDataFolder(), "whitelist.json");
    }

    @Override
    public CompletableFuture<Void> saveWhitelist(Whitelist whitelist) {
        return CompletableFuture.runAsync(() -> {
            try {
                Files.writeString(this.getWhitelistFile().toPath(), gson.toJson(whitelist));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Whitelist> loadWhitelist() {
        return CompletableFuture.supplyAsync(() -> {
            if (!this.getWhitelistFile().exists()) {
                return new Whitelist();
            }
            try {
                return gson.fromJson(new FileReader(this.getWhitelistFile()), Whitelist.class);
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
