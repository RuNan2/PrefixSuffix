package com.prefix.suffix;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * /prefixsuffix reload 명령어를 처리하는 클래스입니다.
 * 설정 파일을 다시 로드합니다.
 */
public class ReloadCommand implements CommandExecutor {
    private final Main plugin;

    public ReloadCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.configManager.reload();
            sender.sendMessage(ChatColor.GREEN + "설정이 성공적으로 다시 로드되었습니다.");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "/prefixsuffix reload");
        return true;
    }
}
