package com.danifoldi.cpscheckaac;

import com.danifoldi.cpscheckaac.inject.DaggerPluginComponent;
import com.danifoldi.cpscheckaac.inject.PluginComponent;
import org.bukkit.plugin.java.JavaPlugin;

public class CPSCheckAAC extends JavaPlugin {
    protected PluginLoader loader;

    @Override
    public void onEnable() {
        final PluginComponent component = DaggerPluginComponent.builder()
                .bindPlugin(this)
                .bindDatafolder(getDataFolder().toPath())
                .build();
        this.loader = component.loader();
        this.loader.load();
    }

    @Override
    public void onDisable() {
        this.loader.unload();
    }
}