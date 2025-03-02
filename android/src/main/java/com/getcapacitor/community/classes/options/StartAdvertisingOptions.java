package com.getcapacitor.community.classes.options;

import static com.getcapacitor.community.NearbyConnectionsHelper.toConnectionType;

import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import com.getcapacitor.community.NearbyConnectionsConfig;

public class StartAdvertisingOptions {

    @Nullable
    private String endpointName;

    @Nullable
    private Integer connectionType;

    public StartAdvertisingOptions(PluginCall call, NearbyConnectionsConfig config) {
        String endpointName = call.getString("endpointName", config.getEndpointName());
        this.setEndpointName(endpointName);

        String connectionType = call.getString("connectionType");
        this.setConnectionType(connectionType);
    }

    public void setEndpointName(@Nullable String endpointName) {
        this.endpointName = endpointName;
    }

    public void setConnectionType(@Nullable String connectionType) {
        this.connectionType = toConnectionType(connectionType);
    }

    @Nullable
    public String getEndpointName() {
        return endpointName;
    }

    @Nullable
    public Integer getConnectionType() {
        return connectionType;
    }
}
