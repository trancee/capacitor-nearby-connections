package com.getcapacitor.community.classes.options;

import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class StartDiscoveryOptions {

    @Nullable
    private Boolean lowPower;

    public StartDiscoveryOptions(PluginCall call) {
        Boolean lowPower = call.getBoolean("lowPower", null);
        this.setLowPower(lowPower);
    }

    public void setLowPower(@Nullable Boolean lowPower) {
        this.lowPower = lowPower;
    }

    @Nullable
    public Boolean getLowPower() {
        return lowPower;
    }
}
