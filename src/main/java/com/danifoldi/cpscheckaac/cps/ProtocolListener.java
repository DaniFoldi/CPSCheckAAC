package com.danifoldi.cpscheckaac.cps;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.danifoldi.cpscheckaac.CPSCheckAAC;
import me.konsolas.aac.api.AACAPI;

import javax.inject.Inject;

public class ProtocolListener {

    private final ProtocolManager protocolManager;
    private final CPSTester tester;
    private final CPSCheckAAC plugin;
    private final AACProvider aacProvider;

    @Inject
    public ProtocolListener(ProtocolManager protocolManager, CPSTester tester, CPSCheckAAC plugin, AACAPI aacApi) {
        this.protocolManager = protocolManager;
        this.tester = tester;
        this.plugin = plugin;
        this.aacProvider = new AACProvider(aacApi, this.tester, this.plugin);
    }

    public void start() {
        protocolManager.addPacketListener(new PacketAdapter(plugin, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {

                if (!event.getPacketType().equals(PacketType.Play.Client.USE_ENTITY)) {
                    return;
                }
                tester.clicked(event.getPlayer());
            }
        });

        plugin.getLogger().info("ProtocolLib listener hooked");
    }

    public void stop() {
        protocolManager.removePacketListeners(plugin);
        tester.cancel();
    }
}
