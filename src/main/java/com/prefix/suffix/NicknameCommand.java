package com.prefix.suffix;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NicknameCommand implements CommandExecutor {
    private final Main plugin;

    public NicknameCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 1) {
            if (!player.hasPermission("prefixsuffix.setnickname")) {
                player.sendMessage(ChatColor.RED + "You do not have permission.");
                return true;
            }
            String nickname = ChatColor.translateAlternateColorCodes('&', args[0]);
            plugin.configManager.set("Players." + player.getName() + ".nickname", nickname);
            plugin.configManager.save();
            player.sendMessage(ChatColor.GREEN + "Your nickname has been set to: " + nickname);

        } else if (args.length == 2) {
            if (!player.hasPermission("prefixsuffix.setnickname.others")) {
                player.sendMessage(ChatColor.RED + "You do not have permission to change others' nicknames.");
                return true;
            }
            Player target = plugin.getServer().getPlayerExact(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "That player is not online.");
                return true;
            }
            String nickname = ChatColor.translateAlternateColorCodes('&', args[1]);
            plugin.configManager.set("Players." + target.getName() + ".nickname", nickname);
            plugin.configManager.save();
            player.sendMessage(ChatColor.GREEN + target.getName() + "'s nickname has been set to: " + nickname);
            target.sendMessage(ChatColor.GREEN + "Your nickname has been updated to: " + nickname);

        } else {
            player.sendMessage(ChatColor.RED + "Usage: /setnickname <nickname> or /setnickname <player> <nickname>");
        }
        return true;
    }
}