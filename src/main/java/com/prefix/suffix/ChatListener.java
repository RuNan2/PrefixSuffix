package com.prefix.suffix;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
        // ──────────────
        String prefix   = plugin.configManager.get(path + ".prefix", "");
        String suffix   = plugin.configManager.get(path + ".suffix", "");
        String nickname = plugin.configManager.get(path + ".nickname", player.getName());

        String joinFormat = plugin.getConfig().getString("join-format",
                "&e{nickname} 님이 서버에 접속했습니다!");

        String joinMessage = ChatColor.translateAlternateColorCodes('&', joinFormat)
                .replace("{prefix}", ChatColor.translateAlternateColorCodes('&', prefix))
                .replace("{suffix}", ChatColor.translateAlternateColorCodes('&', suffix))
                .replace("{nickname}", ChatColor.translateAlternateColorCodes('&', nickname));

        // 최종 입장 메시지 설정
        event.setJoinMessage(joinMessage);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String path     = "Players." + player.getName();

        // prefix, suffix, nickname 읽기 (getString이 구현되어 있다고 가정)
        String prefix   = plugin.configManager.get(path + ".prefix", "");
        String suffix   = plugin.configManager.get(path + ".suffix", "");
        String nickname = plugin.configManager.get(path + ".nickname", player.getName());

        // config.yml에서 quit-format 가져오기
        String quitFormat = plugin.getConfig().getString(
                "quit-format",
                "&e{nickname} 님이 서버를 떠났습니다!"
        );

        // 색상코드 번역 + 플레이스홀더 치환
        String quitMessage = ChatColor.translateAlternateColorCodes('&', quitFormat)
                .replace("{prefix}", ChatColor.translateAlternateColorCodes('&', prefix))
                .replace("{suffix}", ChatColor.translateAlternateColorCodes('&', suffix))
                .replace("{nickname}", ChatColor.translateAlternateColorCodes('&', nickname));

        event.setQuitMessage(quitMessage);
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

        // config.yml에서 chat-format 불러오기
        String format = plugin.getConfig().getString("chat-format",
                "&f{prefix}&r {nickname} &7{suffix}&r: %2$s");

        // 플레이어별 데이터 반영
        format = format.replace("{prefix}", ChatColor.translateAlternateColorCodes('&', prefix))
                .replace("{suffix}", ChatColor.translateAlternateColorCodes('&', suffix))
                .replace("{nickname}", ChatColor.translateAlternateColorCodes('&', nickname))
                .replace("{message}", "%2$s");

        // 최종 채팅 포맷 설정
        event.setFormat(ChatColor.translateAlternateColorCodes('&', format));
    }
}
