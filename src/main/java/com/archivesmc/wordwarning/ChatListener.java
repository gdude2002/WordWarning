package com.archivesmc.wordwarning;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

import java.util.HashSet;
import java.util.UUID;


public class ChatListener implements Listener {

    // Instance of the plugin
    WordWarning plugin;

    public ChatListener(WordWarning plugin) {
        // Store the plugin instance
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        // Check to see if the player has a bypass
        if (event.getPlayer().hasPermission("wordwarning.bypass")) {
            return;
        }

        // Check the message for a matched term
        String match = this.plugin.checkMessage(event.getMessage(), event.getPlayer().getUniqueId());

        if (match != null) { // If we have a match..
            // Cancel the chat event
            event.setCancelled(true);

            if (! this.plugin.usageMap.containsKey(match)) {
                // More checking to make sure we have the term already
                this.plugin.usageMap.put(match, new HashSet<UUID>());
            }

            // Store the fact that the user's seen the warning
            // We do this now in case the code below it breaks
            this.plugin.usageMap.get(match).add(event.getPlayer().getUniqueId());

            // Get the warning message
            String message = (String) this.plugin.config.getTerms().get(match);

            // Replace {PLAYER} in the message with the player's display name
            message = message.replace("{PLAYER}", event.getPlayer().getDisplayName());

            // Parse colour codes in the message
            message = translateAlternateColorCodes('&', message);

            // Output that the user's been warned, along with the message they sent
            this.plugin.getLogger().info(String.format("Warning user for term: %s", match));
            this.plugin.getLogger().info(String.format(
                    "<%s> %s",  event.getPlayer().getDisplayName(), event.getMessage()
            ));

            if (! this.plugin.config.getPreMessage().isEmpty()) {
                // If the pre-message isn't empty, then send it to the player
                event.getPlayer().sendMessage(this.plugin.config.getPreMessage());
            }

            // Send this term's warning message
            event.getPlayer().sendMessage(message);

            if (! this.plugin.config.getPostMessage().isEmpty()) {
                // If the post-message isn't empty, then send it to the player
                event.getPlayer().sendMessage(this.plugin.config.getPostMessage());
            }
        }
    }
}
