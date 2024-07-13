package com.mefrreex.whitelister.event;

import cn.nukkit.event.Cancellable;
import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WhitelistKickPlayerEvent extends Event implements Cancellable {

    private String kickMessage;
    private Reason kickReason;

    @Getter
    private static final HandlerList handlers = new HandlerList();

    public WhitelistKickPlayerEvent(String kickMessage, Reason kickReason) {
        this.kickMessage = kickMessage;
        this.kickReason = kickReason;
    }

    public enum Reason {
        /**
         * Kick when a player tries to join the server
         */
        JOINING,
        /**
         * Kick when a player is online when whitelist is enabled
         */
        ONLINE
    }
}
