package com.archivesmc.wordwarning;

/**
 * Plugin designed for semi-censoring.
 *
 * You define certain terms. Then, when a player sends a message containing one of the terms you've defined,
 * they're sent a message. If they still want to use that term, then they can choose to simply repeat the
 * message.
 */

import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

import java.util.*;

public final class WordWarning extends JavaPlugin {

    public Map<String, Object> terms;

    public Map<String, HashSet<UUID>> usageMap;

    public String preMessage;
    public String postMessage;

    public ChatListener listener;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.reloadConfig();

        this.terms = this.getConfig().getConfigurationSection("terms").getValues(false);
        this.usageMap = new HashMap<>();

        this.preMessage = translateAlternateColorCodes('&', this.getConfig().getString("pre_message"));
        this.postMessage = translateAlternateColorCodes('&', this.getConfig().getString("post_message"));

        listener = new ChatListener(this);
        this.getServer().getPluginManager().registerEvents(listener, this);

        this.getLogger().info(String.format("Loaded %s terms.", terms.size()));
    }

    public String checkMessage(String message, UUID user) {
        for (String key : terms.keySet()) {
            if (stringContains(message, key)) {
                if (!usageMap.containsKey(key)) {
                    usageMap.put(key, new HashSet<UUID>());
                }

                if (!usageMap.get(key).contains(user)) {
                    return key;
                }
            }
        }

        return null;
    }

    public boolean stringContains(String haystack, String needle) {
        return haystack.toLowerCase().contains(needle.toLowerCase());
    }
}
