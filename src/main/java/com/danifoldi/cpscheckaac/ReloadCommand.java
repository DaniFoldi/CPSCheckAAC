package com.danifoldi.cpscheckaac;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class ReloadCommand implements CommandExecutor {

    private final CPSCheckAAC plugin;

    @Inject
    public ReloadCommand(CPSCheckAAC plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equals("cpscheckaac") || !args[0].equals("reload")) {
            return false;
        }
        if (!sender.hasPermission("cpscheckaac.reload") && !sender.isOp()) {
            sender.sendMessage("You don't have permission to execute that command.");
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage("Usage: /cpscheckaac reload");
            return false;
        }
        plugin.loader.unload();
        plugin.loader.load();
        sender.sendMessage("CPSCheckAAC reloaded successfully");
        return true;
    }
}
