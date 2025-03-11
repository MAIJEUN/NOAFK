/*
 * Copyright (C) 2025 MAIJSOFT Dev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>
 */

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
