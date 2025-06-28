package com.prefix.suffix;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuffixCommand implements CommandExecutor {
    private final Main plugin;

    public SuffixCommand(Main plugin) {
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
            if (!player.hasPermission("prefixsuffix.setsuffix")) {
                player.sendMessage(ChatColor.RED + "You do not have permission.");
                return true;
            }
            String suffix = ChatColor.translateAlternateColorCodes('&', args[0]);
            plugin.configManager.set("Players." + player.getName() + ".suffix", suffix);
            plugin.configManager.save();
            player.sendMessage(ChatColor.GREEN + "Your suffix has been set to: " + suffix);

        } else if (args.length == 2) {
            if (!player.hasPermission("prefixsuffix.setsuffix.others")) {
                player.sendMessage(ChatColor.RED + "You do not have permission to change others' suffixes.");
                return true;
            }
            Player target = plugin.getServer().getPlayerExact(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "That player is not online.");
                return true;
            }
            String suffix = ChatColor.translateAlternateColorCodes('&', args[1]);
            plugin.configManager.set("Players." + target.getName() + ".suffix", suffix);
            plugin.configManager.save();
            player.sendMessage(ChatColor.GREEN + target.getName() + "'s suffix has been set to: " + suffix);
            target.sendMessage(ChatColor.GREEN + "Your suffix has been updated to: " + suffix);

        } else {
            player.sendMessage(ChatColor.RED + "Usage: /setsuffix <suffix> or /setsuffix <player> <suffix>");
        }
        return true;
    }
}