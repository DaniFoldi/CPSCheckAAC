package com.danifoldi.cpscheckaac.inject;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.danifoldi.cpscheckaac.CPSCheckAAC;
import com.danifoldi.cpscheckaac.config.Configuration;
import com.danifoldi.cpscheckaac.cps.CPSTester;
import dagger.Module;
import dagger.Provides;
import me.konsolas.aac.api.AACAPI;
import org.bukkit.Bukkit;

import javax.inject.Singleton;

@Module
public class ProvidingModule {

    @Provides
    @Singleton
    public Configuration provideConfiguration() {
        return new Configuration();
    }

    @Provides
    @Singleton
    public ProtocolManager provideProtocolManager() {
        return ProtocolLibrary.getProtocolManager();
    }

    @Provides
    @Singleton
    public AACAPI provideAACApi() {
        return Bukkit.getServicesManager().load(AACAPI.class);
    }

    @Provides
    @Singleton
    public CPSTester provideCPSTester(CPSCheckAAC plugin, Configuration config) {
        return new CPSTester(plugin, config);
    }
}