package com.getcapacitor.community.classes.options;

import static com.getcapacitor.community.NearbyConnectionsHelper.toConnectionType;

import androidx.annotation.Nullable;

public class StartDiscoveryOptions {

    @Nullable
    private Boolean lowPower;

    public StartDiscoveryOptions(@Nullable Boolean lowPower) {
        this.setLowPower(lowPower);
    }

    @Nullable
    public Boolean getLowPower() {
        return lowPower;
    }

    public void setLowPower(@Nullable Boolean lowPower) {
        this.lowPower = lowPower;
    }
}
