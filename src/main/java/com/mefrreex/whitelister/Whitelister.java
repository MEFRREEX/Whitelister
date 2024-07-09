package com.mefrreex.whitelister;

import cn.nukkit.plugin.PluginBase;
import com.mefrreex.jooq.database.MySQLDatabase;
import com.mefrreex.jooq.database.SQLiteDatabase;
import com.mefrreex.whitelister.command.WhitelisterCommand;
import com.mefrreex.whitelister.listener.PlayerListener;
import com.mefrreex.whitelister.provider.DatabaseWhitelistProvider;
import com.mefrreex.whitelister.provider.JsonWhitelistProvider;
import com.mefrreex.whitelister.provider.WhitelistProvider;
import com.mefrreex.whitelister.utils.Language;
import com.mefrreex.whitelister.whitelist.Whitelist;
import com.mefrreex.whitelister.whitelist.WhitelistManager;
import lombok.Getter;

import java.io.File;

@Getter
public class Whitelister extends PluginBase {

    @Getter
    private static Whitelister instance;

    private WhitelistProvider whitelistProvider;
    private WhitelistManager whitelistManager;

    @Override
    public void onLoad() {
        Whitelister.instance = this;
        this.saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        Language.init(this);

        // Whitelist repository initialization
        this.whitelistProvider = switch(this.getConfig().getString("provider.type").toUpperCase()) {
            case "SQLITE" -> new DatabaseWhitelistProvider(new SQLiteDatabase(new File(this.getDataFolder(), "database.db")));
            case "MYSQL" -> {
                var section = this.getConfig().getSection("provider.mysql");
                var database = new MySQLDatabase(
                        section.getString("host"),
                        section.getString("database"),
                        section.getString("user"),
                        section.getString("password")
                );
                yield new DatabaseWhitelistProvider(database);
            }
            case "JSON" -> new JsonWhitelistProvider();
            default -> throw new RuntimeException("Provider not found");
        };

        // Whitelist manager initialization
        this.whitelistManager = new WhitelistManager(whitelistProvider);
        this.whitelistManager.loadWhitelist();

        this.getServer().getCommandMap().register("Whitelister", new WhitelisterCommand());
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {
        this.whitelistManager.saveWhitelist();
    }

    public Whitelist getWhitelist() {
        return whitelistManager.getWhitelist();
    }
}