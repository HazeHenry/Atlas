package me.hazehenry.atlas;

import lombok.Getter;
import me.hazehenry.atlas.commands.AtlasCommand;
import me.hazehenry.atlas.listeners.ActionListener;
import me.hazehenry.atlas.menus.listeners.AtlasMenuListener;
import me.hazehenry.atlas.menus.listeners.QuestMenuListener;
import me.hazehenry.atlas.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.Set;

public final class Atlas extends JavaPlugin {

    @Getter
    public static Atlas instance;

    private final Map<String, CommandExecutor> commandList = Map.of(
            "atlas", new AtlasCommand()
    );

    private final Set<Listener> listenerList = Set.of(
            new ActionListener(),
            new AtlasMenuListener(),
            new QuestMenuListener()
    );

    @Override
    public void onEnable() {
        instance = this;
        Messages.load(getConfig().getString("lang"));

        saveDefaultConfig();

        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {

    }

    private void registerListeners() {
        for (Listener l : listenerList) {
            Bukkit.getPluginManager().registerEvents(l, this);
        }
    }



    private void registerCommands() {
        for (Map.Entry<String, CommandExecutor> map : commandList.entrySet()) {
            PluginCommand command = getCommand(map.getKey());
            command.setExecutor(map.getValue());
        }
    }
}
