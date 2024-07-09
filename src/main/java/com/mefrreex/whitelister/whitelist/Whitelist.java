package com.mefrreex.whitelister.whitelist;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Whitelist {

    private boolean enable;
    private boolean kickOnlinePlayers;
    private String kickMessage;
    private final Set<String> allowedPlayers = new HashSet<>();

    public boolean isPlayerAllowed(String playerName) {
        return allowedPlayers.contains(playerName.toLowerCase());
    }

    public void addAllowedPlayer(String playerName) {
        allowedPlayers.add(playerName.toLowerCase());
    }

    public void removeAllowedPlayer(String playerName) {
        allowedPlayers.remove(playerName.toLowerCase());
    }
}
