package com.mefrreex.whitelister;

import cn.nukkit.plugin.PluginBase;
import com.mefrreex.whitelister.command.WhitelisterCommand;
import com.mefrreex.whitelister.listener.PlayerListener;
import com.mefrreex.whitelister.utils.Language;
import com.mefrreex.whitelister.whitelist.Whitelist;
import com.mefrreex.whitelister.whitelist.WhitelistManager;
import lombok.Getter;

@Getter
public class Whitelister extends PluginBase {

    @Getter
    private static Whitelister instance;

    private WhitelistManager whitelistManager;

    @Override
    public void onLoad() {
        Whitelister.instance = this;
        this.saveDefaultConfig();
        this.saveResource("whitelist.json");
    }

    @Override
    public void onEnable() {
        Language.init(this);
        this.whitelistManager = new WhitelistManager();
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