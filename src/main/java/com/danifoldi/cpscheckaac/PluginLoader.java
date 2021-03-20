package com.danifoldi.cpscheckaac;

import com.danifoldi.cpscheckaac.config.Configuration;
import com.danifoldi.cpscheckaac.cps.ProtocolListener;
import com.danifoldi.cpscheckaac.util.Common;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public final class PluginLoader {
    private final Path datafolder;
    private final Configuration config;
    private final ProtocolListener listener;
    private final CPSCheckAAC plugin;
    private final ReloadCommand reloadCommand;

    @Inject
    public PluginLoader(final @NotNull @Named("datafolder") Path datafolder,
                        final @NotNull Configuration config,
                        final @NotNull ProtocolListener listener,
                        final @NotNull CPSCheckAAC plugin,
                        final @NotNull ReloadCommand reloadCommand) {
        this.datafolder = datafolder;
        this.config = config;
        this.listener = listener;
        this.plugin = plugin;
        this.reloadCommand = reloadCommand;
    }

    protected void load() {
        try {
            this.config.populate(Common.loadConfig(Common.copyResource("config.yml", this.datafolder)));
            plugin.getLogger().info("Config loaded");
            listener.start();
            Optional.ofNullable(plugin.getCommand("cpscheckaac")).ifPresent(command -> command.setExecutor(reloadCommand));
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

    protected void unload() {
        listener.stop();
    }
}