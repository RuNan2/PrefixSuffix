package com.prefix.suffix;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrefixCommand implements CommandExecutor {
    private final Main plugin;

    public PrefixCommand(Main plugin) {
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
            if (!player.hasPermission("prefixsuffix.setprefix")) {
                player.sendMessage(ChatColor.RED + "You do not have permission.");
                return true;
            }
            String prefix = ChatColor.translateAlternateColorCodes('&', args[0]);
            plugin.configManager.set("Players." + player.getName() + ".prefix", prefix);
            plugin.configManager.save();
            player.sendMessage(ChatColor.GREEN + "Your prefix has been set to: " + prefix);

        } else if (args.length == 2) {
            if (!player.hasPermission("prefixsuffix.setprefix.others")) {
                player.sendMessage(ChatColor.RED + "You do not have permission to change others' prefixes.");
                return true;
            }
            Player target = plugin.getServer().getPlayerExact(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "That player is not online.");
                return true;
            }
            String prefix = ChatColor.translateAlternateColorCodes('&', args[1]);
            plugin.configManager.set("Players." + target.getName() + ".prefix", prefix);
            plugin.configManager.save();
            player.sendMessage(ChatColor.GREEN + target.getName() + "'s prefix has been set to: " + prefix);
            target.sendMessage(ChatColor.GREEN + "Your prefix has been updated to: " + prefix);

        } else {
            player.sendMessage(ChatColor.RED + "Usage: /setprefix <prefix> or /setprefix <player> <prefix>");
        }
        return true;
    }
}