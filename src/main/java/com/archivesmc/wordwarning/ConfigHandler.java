package com.archivesmc.wordwarning;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.Map;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class ConfigHandler {

    private WordWarning plugin;
    private FileConfiguration config;

    public ConfigHandler(WordWarning plugin) {
        this.plugin = plugin;
        File fh = new File(this.plugin.getDataFolder() + "/config.yml");

        if (!fh.isFile()) {
            // Only recreate if it's gone
            this.plugin.saveDefaultConfig();
        }

        this.config = this.plugin.getConfig();
    }

    public void update() {
        String version = this.getVersion();

        switch (version) {
            case "":
                // No version in the config
                this.config.set("version", this.plugin.getDescription().getVersion());
                this.reload();
        }
    }

    public FileConfiguration get() {
        return this.config;
    }

    public void reload() {
        this.plugin.reloadConfig();
    }

    public Map<String, Object> getTerms() {
        ConfigurationSection section = this.config.getConfigurationSection("terms");
        if (section == null) {
            return null;
        }

        return section.getValues(false);
    }

    public String getPreMessage() {
        return translateAlternateColorCodes('&', this.config.getString("pre_message", null));
    }

    public String getPostMessage() {
        return translateAlternateColorCodes('&', this.config.getString("post_message", null));
    }

    public String getVersion() {
        return this.config.getString("version", "");
    }
}
