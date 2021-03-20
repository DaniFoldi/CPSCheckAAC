package com.danifoldi.cpscheckaac.cps;

import com.danifoldi.cpscheckaac.CPSCheckAAC;
import me.konsolas.aac.api.AACAPI;
import me.konsolas.aac.api.AACCustomFeature;
import me.konsolas.aac.api.AACCustomFeatureProvider;
import org.bukkit.OfflinePlayer;

import java.util.Collection;
import java.util.Collections;

public class AACProvider implements AACCustomFeatureProvider {

    private final AACAPI aacApi;
    private final CPSTester tester;
    private final CPSCheckAAC plugin;

    public AACProvider(AACAPI aacApi, CPSTester tester, CPSCheckAAC plugin) {
        this.aacApi = aacApi;
        this.tester = tester;
        this.plugin = plugin;
        aacApi.registerCustomFeatureProvider(this);
        plugin.getLogger().info("AAC5 Hooked");
    }

    @Override
    public Collection<AACCustomFeature> getFeaturesAsync(OfflinePlayer offlinePlayer) {
        return Collections.singleton(tester.getAsFeature(offlinePlayer));
    }
}
