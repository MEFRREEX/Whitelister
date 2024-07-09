package com.mefrreex.whitelister.utils;

import cn.nukkit.IPlayer;
import cn.nukkit.Server;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    @SuppressWarnings("deprecation")
    public static @Nullable IPlayer getOfflinePlayer(String playerName) {
        IPlayer player = Server.getInstance().getOfflinePlayer(playerName);
        return player.getUniqueId() != null ? player : null;
    }

    public static <T> Map<Integer, T> convertArrayToMap(T[] array) {
        Map<Integer, T> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            map.put(i, array[i]);
        }
        return map;
    }
}
