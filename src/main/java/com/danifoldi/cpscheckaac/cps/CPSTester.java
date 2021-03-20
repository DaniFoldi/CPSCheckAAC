package com.danifoldi.cpscheckaac.cps;

import com.danifoldi.cpscheckaac.CPSCheckAAC;
import com.danifoldi.cpscheckaac.config.Configuration;
import me.konsolas.aac.api.AACCustomFeature;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CPSTester {

    private final CPSCheckAAC plugin;
    private final Configuration config;
    private final BukkitTask task;

    private final ConcurrentHashMap<UUID, ConcurrentLinkedQueue<Instant>> clicks = new ConcurrentHashMap<>();

    public CPSTester(CPSCheckAAC plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;

        task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            for (UUID p: clicks.keySet()) {
                ConcurrentLinkedQueue<Instant> clicksByPlayer = clicks.get(p);
                if (clicksByPlayer == null) {
                    continue;
                }
                while (!clicksByPlayer.isEmpty() && clicksByPlayer.peek().isBefore(Instant.now().minusSeconds(config.getClearTime()))) {
                    clicksByPlayer.poll();
                }

                if (Bukkit.getPlayer(p) != null && clicksByPlayer.isEmpty()) {
                    clicks.remove(p);
                }
            }
        }, 20, 20);
    }

    public void clicked(Player player) {
        UUID uuid = player.getUniqueId();
        clicks.putIfAbsent(uuid, new ConcurrentLinkedQueue<>());
        clicks.get(uuid).add(Instant.now());
    }

    public void cancel() {
        task.cancel();
    }

    public AACCustomFeature getAsFeature(OfflinePlayer player) {
        UUID uuid = player.getUniqueId();
        ConcurrentLinkedQueue<Instant> clicksByPlayer = clicks.get(uuid);
        if (!clicks.containsKey(uuid) || clicksByPlayer.isEmpty()) {
            Map<String, String> tooltip = Map.of(config.getTotalClicks(), "0", config.getMaxClicks(), "0");
            return new AACCustomFeature("cpsTest", config.getInfo().replace("{cps}", "0.00"), 0, tooltip);
        }

        int totalClicks = clicksByPlayer.size();
        List<Instant> clickCopy = new ArrayList<>(clicksByPlayer);

        int start = 0;
        int end = 0;
        int maxClicks = 0;
        int delta = 0;

        while (start < clickCopy.size()) {
            while (end < clickCopy.size() && clickCopy.get(end).isBefore(clickCopy.get(start).plusSeconds(1L))) {
                end++;
            }
            end = Math.min(end, clickCopy.size() - 1);

            if (end - start > maxClicks) {
                maxClicks = end - start;
                delta = (int)Duration.between(clickCopy.get(start), clickCopy.get(end)).toMillis();
            }
            start++;
        }

        double cps = (double)maxClicks / ((double)delta) * 1000d;
        double score = cps >= config.getMinCPS() ? config.getScore() + config.getScorePerCPS() * (cps - config.getMinCPS()): 0;

        if (config.getDebug()) {
            plugin.getLogger().info("CPS TEST of " + player.getName() + " cps " + cps + " score " + score + " totalclicks " + totalClicks + " maxclicks " + maxClicks);
        }

        String cpsValue = String.valueOf((double)Math.round(cps * 100d) / 100d);
        Map<String, String> tooltip = Map.of(config.getTotalClicks(), String.valueOf(totalClicks), config.getMaxClicks(), String.valueOf(maxClicks));
        return new AACCustomFeature("cpsTest", config.getInfo().replace("{cps}", cpsValue), score, tooltip);
    }
}
