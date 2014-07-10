package com.archivesmc.wordwarning;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.UUID;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class ChatListener implements Listener {

    WordWarning plugin;

    public ChatListener(WordWarning plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        String match = this.plugin.checkMessage(event.getMessage(), event.getPlayer().getUniqueId());

        if (match != null) {
            event.setCancelled(true);

            if (! this.plugin.usageMap.containsKey(match)) {
                this.plugin.usageMap.put(match, new ArrayList<UUID>());
            }

            this.plugin.usageMap.get(match).add(event.getPlayer().getUniqueId());

            String message = (String) this.plugin.terms.get(match);

            message = message.replace("{PLAYER}", event.getPlayer().getDisplayName());
            message = translateAlternateColorCodes('&', message);

            this.plugin.getLogger().info(String.format("Warning user for term: %s", match));
            this.plugin.getLogger().info(String.format(
                    "<%s> %s",  event.getPlayer().getDisplayName(), event.getMessage()
            ));

            if (! this.plugin.preMessage.isEmpty()) {
                event.getPlayer().sendMessage(this.plugin.preMessage);
            }

            event.getPlayer().sendMessage(message);

            if (! this.plugin.postMessage.isEmpty()) {
                event.getPlayer().sendMessage(this.plugin.postMessage);
            }
        }
    }
}
