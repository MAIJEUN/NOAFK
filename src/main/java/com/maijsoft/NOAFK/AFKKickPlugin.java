/*
 * Copyright (C) 2024 MAIJSOFT Dev
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

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class AFKKickPlugin extends JavaPlugin implements CommandExecutor {
    private final HashMap<UUID, Long> lastActiveTime = new HashMap<>();
    private long afkTimeout = 300000; // 기본 5분 (밀리초)

    @Override
    public void onEnable() {
        getLogger().info("AFK 킥 플러그인이 활성화되었습니다.");
        startAFKChecker();

        // 명령어 등록
        this.getCommand("NOAFK").setExecutor(this);

        // 이벤트 리스너 등록
        Bukkit.getPluginManager().registerEvents(new PlayerActivityListener(lastActiveTime), this);
    }

    private void startAFKChecker() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    UUID playerId = player.getUniqueId();
                    long currentTime = System.currentTimeMillis();

                    if (lastActiveTime.containsKey(playerId) &&
                            (currentTime - lastActiveTime.get(playerId)) > afkTimeout) {
                        player.kickPlayer("AFK 상태로 인해 킥되었습니다.");
                    }
                }
            }
        }.runTaskTimer(this, 0, 1200); // 1분마다 체크
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("NOAFK")) {
            if (sender.isOp()) { // OP 여부 확인
                if (args.length == 1) {
                    try {
                        int minutes = Integer.parseInt(args[0]);
                        afkTimeout = minutes * 60 * 1000; // 분을 밀리초로 변환
                        sender.sendMessage("AFK 시간 제한이 " + minutes + "분으로 설정되었습니다.");
                        return true;
                    } catch (NumberFormatException e) {
                        sender.sendMessage("올바른 숫자를 입력해 주세요.");
                    }
                } else {
                    sender.sendMessage("사용법: /NOAFK <분>");
                }
            } else {
                sender.sendMessage("이 명령어를 사용할 권한이 없습니다.");
            }
        }
        return false;
    }

}
