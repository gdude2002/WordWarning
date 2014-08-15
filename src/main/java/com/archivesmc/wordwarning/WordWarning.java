package com.archivesmc.wordwarning;

/**
 * Plugin designed for semi-censoring.
 *
 * You define certain terms. Then, when a player sends a message containing one of the terms you've defined,
 * they're sent a message. If they still want to use that term, then they can choose to simply repeat the
 * message.
 */

import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class WordWarning extends JavaPlugin {

    // Configuration handler
    public ConfigHandler config;

    // Storage of who's seen what warnings
    public Map<String, HashSet<UUID>> usageMap;

    // Listener for chat events
    public ChatListener listener;

    @Override
    public void onEnable() {
        // Load up the config
        this.config = new ConfigHandler(this);
        this.usageMap = new HashMap<>();

        // Create a new chat listener and register it
        listener = new ChatListener(this);
        this.getServer().getPluginManager().registerEvents(listener, this);

        this.getLogger().info(String.format("Loaded %s terms.", this.config.getTerms().size()));
    }

    public void reload() {
        this.config.reload();
    }

    public String checkMessage(String message, UUID user) {
        for (String key : this.config.getTerms().keySet()) { // For each term..
            if (stringContains(message, key)) { // If the term is in the message..
                if (!usageMap.containsKey(key)) { // If nobody's used the term before
                    usageMap.put(key, new HashSet<UUID>()); // Store it so we know who's used it
                }

                if (!usageMap.get(key).contains(user)) { // If the user hasn't used the term before..
                    return key; // Return it
                }
            }
        }

        return null;
    }

    public boolean stringContains(String haystack, String needle) {
        // Quick 'n dirty case-insensitive string contains method
        return haystack.toLowerCase().contains(needle.toLowerCase());
    }
}
