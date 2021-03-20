package com.danifoldi.cpscheckaac.config;

import com.electronwill.nightconfig.core.conversion.Path;

public class Configuration implements ObjectMappable {

    @Path("cpsCheck.minCPS")
    private double minCPS;
    @Path("cpsCheck.score")
    private double score;
    @Path("cpsCheck.scorePerCPS")
    private double scorePerCPS;
    @Path("cpsCheck.clearTime")
    private int clearTime;
    @Path("cpsCheck.info")
    private String info;
    @Path("cpsCheck.debug")
    private boolean debug;
    @Path("cpsCheck.totalClicks")
    private String totalClicks;
    @Path("cpsCheck.maxClicks")
    private String maxClicks;

    public double getMinCPS() {
        return minCPS;
    }

    public double getScore() {
        return score;
    }

    public double getScorePerCPS() {
        return scorePerCPS;
    }

    public int getClearTime() {
        return clearTime;
    }

    public String getInfo() {
        return info;
    }

    public boolean getDebug() {
        return debug;
    }

    public String getTotalClicks() {
        return totalClicks;
    }

    public String getMaxClicks() {
        return maxClicks;
    }
}
