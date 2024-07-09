package com.mefrreex.whitelister.whitelist;

import cn.nukkit.Player;
import cn.nukkit.Server;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mefrreex.whitelister.Whitelister;
import lombok.Getter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class WhitelistManager {

    @Getter
    private Whitelist whitelist;

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private boolean initialized = false;

    public void kickPlayer(Player player) {
        player.kick(whitelist.getKickMessage(), false);
    }

    public void kickNotAllowedPlayers() {
        for (Player player : Server.getInstance().getOnlinePlayers().values()) {
            if (!whitelist.isPlayerAllowed(player.getName())) {
                this.kickPlayer(player);
            }
        }
    }

    public File getWhitelistFile() {
        return new File(Whitelister.getInstance().getDataFolder(), "whitelist.json");
    }

    public void loadWhitelist() {
        try {
            this.whitelist = gson.fromJson(new FileReader(this.getWhitelistFile()), Whitelist.class);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        this.initialized = true;
    }

    public void saveWhitelist() {
        if (!initialized) return;

        try {
            Files.writeString(this.getWhitelistFile().toPath(), gson.toJson(whitelist));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
