package com.getcapacitor.community.classes.options;

import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import com.getcapacitor.community.NearbyConnectionsConfig;
import java.util.Objects;

public abstract class EndpointOptions {

    @Nullable
    private String endpointID;

    @Nullable
    private String endpointName;

    public EndpointOptions(PluginCall call, @Nullable NearbyConnectionsConfig config) {
        String endpointID = call.getString("endpointID");
        this.setEndpointID(endpointID);

        String endpointName = call.getString("endpointName", (config == null) ? null : config.getEndpointName());
        this.setEndpointName(endpointName);
    }

    public EndpointOptions(PluginCall call) {
        this(call, null);
    }

    public void setEndpointID(@Nullable String endpointID) {
        this.endpointID = endpointID;
    }

    public void setEndpointName(@Nullable String endpointName) {
        this.endpointName = endpointName;
    }

    @Nullable
    public String getEndpointID() {
        return endpointID;
    }

    @Nullable
    public String getEndpointName() {
        return endpointName;
    }
}
