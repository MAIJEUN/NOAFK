package com.maijsoft.NOAFK;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerActivityListener implements Listener {
    private final HashMap<UUID, Long> lastActiveTime;

    public PlayerActivityListener(HashMap<UUID, Long> lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getFrom().distance(event.getTo()) > 0) {
            lastActiveTime.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        lastActiveTime.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
    }
}
