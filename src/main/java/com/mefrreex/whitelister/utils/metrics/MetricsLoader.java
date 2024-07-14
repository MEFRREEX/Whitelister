package com.mefrreex.whitelister.utils.metrics;

import com.mefrreex.whitelister.Whitelister;
import com.mefrreex.whitelister.utils.metrics.Metrics.SimplePie;

public class MetricsLoader {

    private final Whitelister main;
    private final Metrics metrics;

    private static final int PLUGIN_ID = 22641;

    public MetricsLoader() {
        this.main = Whitelister.getInstance();
        this.metrics = new Metrics(main, PLUGIN_ID);
    }

    public void addCustomMetrics() {
        metrics.addCustomChart(new SimplePie("whitelist", () -> main.getWhitelist().isEnable() ? "Enabled" : "Disabled"));
        metrics.addCustomChart(new SimplePie("nukkit_version", () -> main.getServer().getNukkitVersion()));
        metrics.addCustomChart(new SimplePie("xbox_auth", () -> main.getServer().getPropertyBoolean("xbox-auth") ? "Required" : "Not required"));
    }
}
