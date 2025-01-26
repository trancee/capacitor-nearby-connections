package com.getcapacitor.community;

import androidx.annotation.Nullable;
import com.google.android.gms.nearby.connection.Strategy;

public class NearbyConnectionsConfig {

    @Nullable
    private String name;

    @Nullable
    private String serviceId;

    @Nullable
    private Strategy strategy;

    @Nullable
    private Boolean lowPower;

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getServiceId() {
        return serviceId;
    }

    @Nullable
    public Strategy getStrategy() {
        return strategy;
    }

    @Nullable
    public Boolean getLowPower() {
        return lowPower;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public void setServiceId(@Nullable String serviceId) {
        this.serviceId = serviceId;
    }

    public void setStrategy(@Nullable String strategy) {
        if (strategy != null) {
            switch (strategy) {
                case "cluster":
                    this.strategy = Strategy.P2P_CLUSTER;
                    break;
                case "star":
                    this.strategy = Strategy.P2P_STAR;
                    break;
                case "point-to-point":
                    this.strategy = Strategy.P2P_POINT_TO_POINT;
                    break;
                default:
                    this.strategy = null;
            }
        }
    }

    public void setLowPower(@Nullable Boolean lowPower) {
        this.lowPower = lowPower;
    }
}
