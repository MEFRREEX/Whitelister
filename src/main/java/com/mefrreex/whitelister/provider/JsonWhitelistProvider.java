package com.mefrreex.whitelister.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mefrreex.whitelister.Whitelister;
import com.mefrreex.whitelister.whitelist.Whitelist;
import com.mefrreex.whitelister.whitelist.WhitelistPlayerManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class JsonWhitelistProvider implements WhitelistProvider {

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public File getWhitelistFile() {
        return new File(Whitelister.getInstance().getDataFolder(), "whitelist.json");
    }

    public File getPlayersFile() {
        return new File(Whitelister.getInstance().getDataFolder(), "players.json");
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
            WhitelistPlayerManager playerManager = this.createPlayerManager();
            if (!this.getWhitelistFile().exists()) {
                return new Whitelist(playerManager);
            }
            try (FileReader reader = new FileReader(this.getWhitelistFile())) {
                Whitelist whitelistData = gson.fromJson(reader, Whitelist.class);
                Whitelist whitelist = new Whitelist(playerManager);
                whitelist.setEnable(whitelistData.isEnable());
                whitelist.setKickMessage(whitelistData.getKickMessage());
                whitelist.setKickOnlinePlayers(whitelistData.isKickOnlinePlayers());
                return whitelist;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private WhitelistPlayerManager createPlayerManager() {
        return new WhitelistPlayerManager() {
            @Override
            public CompletableFuture<Set<String>> getAllowedPlayers() {
                return JsonWhitelistProvider.this.getAllowedPlayers();
            }

            @Override
            public CompletableFuture<Boolean> isPlayerAllowed(String playerName) {
                return JsonWhitelistProvider.this.isPlayerAllowed(playerName);
            }

            @Override
            public CompletableFuture<Void> addAllowedPlayer(String playerName) {
                return JsonWhitelistProvider.this.addAllowedPlayer(playerName);
            }

            @Override
            public CompletableFuture<Void> removeAllowedPlayer(String playerName) {
                return JsonWhitelistProvider.this.removeAllowedPlayer(playerName);
            }
        };
    }

    @Override
    public CompletableFuture<Set<String>> getAllowedPlayers() {
        return CompletableFuture.supplyAsync(() -> {
            if (!this.getPlayersFile().exists()) {
                return new HashSet<>();
            }
            try (FileReader reader = new FileReader(this.getPlayersFile())) {
                return gson.fromJson(reader, new TypeToken<Set<String>>(){}.getType());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> isPlayerAllowed(String playerName) {
        return this.getAllowedPlayers().thenApplyAsync(playerNames -> playerNames.contains(playerName.toLowerCase()));
    }

    @Override
    public CompletableFuture<Void> addAllowedPlayer(String playerName) {
        return this.updateAllowedPlayers(playerNames -> playerNames.add(playerName.toLowerCase()));
    }

    @Override
    public CompletableFuture<Void> removeAllowedPlayer(String playerName) {
        return this.updateAllowedPlayers(playerNames -> playerNames.remove(playerName.toLowerCase()));
    }

    private CompletableFuture<Void> updateAllowedPlayers(Consumer<Set<String>> updateFunction) {
        return getAllowedPlayers().thenAcceptAsync(playerNames -> {
            updateFunction.accept(playerNames);
            try {
                Files.writeString(this.getPlayersFile().toPath(), gson.toJson(playerNames));
            } catch (IOException e) {
                throw new RuntimeException("Failed to write allowed players", e);
            }
        });
    }
}
