package me.hazehenry.atlas.commands;

import me.hazehenry.atlas.menus.AtlasMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AtlasCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cOnly players can execute this command.");
            return true;
        }

        if (args.length < 1) {
            // main book

            return true;
        }

        if (!p.hasPermission("atlas.admin")) {
            p.sendMessage("§cYou don't have permission.");
            return true;
        }

        new AtlasMenu(p).open();
        return true;
    }
}
