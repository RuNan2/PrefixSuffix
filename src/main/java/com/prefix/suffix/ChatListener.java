package com.prefix.suffix;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * 플레이어의 접속과 채팅 이벤트를 처리하는 리스너 클래스입니다.
 * 접속 시 config 설정이 없으면 기본값을 저장하고, 채팅 시 포맷을 적용합니다.
 */
public class ChatListener implements Listener {
    private final Main plugin;

    public ChatListener(Main plugin) {
        this.plugin = plugin;
    }

    /**
     * 플레이어가 접속할 때 prefix, suffix, nickname 키가 없으면 기본값으로 초기화합니다.
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String path = "Players." + player.getName();
        if (!plugin.configManager.contains(path + ".prefix")) {
            plugin.configManager.set(path + ".prefix", "");
        }
        if (!plugin.configManager.contains(path + ".suffix")) {
            plugin.configManager.set(path + ".suffix", "");
        }
        if (!plugin.configManager.contains(path + ".nickname")) {
            plugin.configManager.set(path + ".nickname", player.getName());
        }
        plugin.configManager.save();
    }

    /**
     * 플레이어가 채팅을 보낼 때, prefix/nickname/suffix를 적용하여 채팅 메시지를 포맷합니다.
     */
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        String prefix = plugin.configManager.get("Players." + name + ".prefix", "");
        String suffix = plugin.configManager.get("Players." + name + ".suffix", "");
        String nickname = plugin.configManager.get("Players." + name + ".nickname", name);

        String format = ChatColor.translateAlternateColorCodes('&',
                prefix + " " + nickname + " " + suffix + ChatColor.RESET + ": " + "%2$s"
        );
        event.setFormat(format);
    }
}
