package com.archivesmc.wordwarning;

/**
 * Plugin designed for semi-censoring.
 *
 * You define certain terms. Then, when a player sends a message containing one of the terms you've defined,
 * they're sent a message. If they still want to use that term, then they can choose to simply repeat the
 * message.
 */

import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import static org.bukkit.ChatColor.translateAlternateColorCodes;

import java.util.*;

public final class WordWarning extends JavaPlugin {

    // Storage of the terms and messages specified in the configuration
    public Map<String, Object> terms;

    // Storage of who's seen what warnings
    public Map<String, HashSet<UUID>> usageMap;

    // Configured message sent before and after warnings
    public String preMessage;
    public String postMessage;

    // Listener for chat events
    public ChatListener listener;

    // Vault permissions handler
    public Permission permissions;

    @Override
    public void onEnable() {
        // Save default config if it doesn't exist, and reload it in case the plugin's been reloaded
        this.saveDefaultConfig();
        this.reloadConfig();

        // Load up our terms and reset the map of who's seen the warnings
        this.terms = this.getConfig().getConfigurationSection("terms").getValues(false);
        this.usageMap = new HashMap<>();

        // Load up the pre/post-warning messages
        this.preMessage = translateAlternateColorCodes('&', this.getConfig().getString("pre_message"));
        this.postMessage = translateAlternateColorCodes('&', this.getConfig().getString("post_message"));

        // Load up permissions
        if (! this.setupPermissions()) {
            this.getLogger().warning("Unable to load Vault. Please make sure it's installed and enabled.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Create a new chat listener and register it
        listener = new ChatListener(this);
        this.getServer().getPluginManager().registerEvents(listener, this);

        this.getLogger().info(String.format("Loaded %s terms.", terms.size()));
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        this.permissions = rsp.getProvider();
        return this.permissions != null;
    }

    public String checkMessage(String message, UUID user) {
        for (String key : terms.keySet()) { // For each term..
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
