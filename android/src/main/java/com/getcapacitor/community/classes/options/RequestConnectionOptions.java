package com.getcapacitor.community.classes.options;

import static com.getcapacitor.community.NearbyConnectionsHelper.toConnectionType;

import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import com.getcapacitor.community.NearbyConnectionsConfig;

public class RequestConnectionOptions extends EndpointOptions {

    @Nullable
    private String endpointName;

    @Nullable
    private Integer connectionType;

    @Nullable
    private Boolean lowPower;

    public RequestConnectionOptions(PluginCall call, NearbyConnectionsConfig config) {
        super(call);
        String endpointName = call.getString("endpointName", config.getEndpointName());
        this.setEndpointName(endpointName);

        String connectionType = call.getString("connectionType", null);
        this.setConnectionType(connectionType);

        Boolean lowPower = call.getBoolean("lowPower", null);
        this.setLowPower(lowPower);
    }

    public void setEndpointName(@Nullable String endpointName) {
        this.endpointName = endpointName;
    }

    public void setConnectionType(@Nullable String connectionType) {
        this.connectionType = toConnectionType(connectionType);
    }

    public void setLowPower(@Nullable Boolean lowPower) {
        this.lowPower = lowPower;
    }

    @Nullable
    public String getEndpointName() {
        return endpointName;
    }

    @Nullable
    public Integer getConnectionType() {
        return connectionType;
    }

    @Nullable
    public Boolean getLowPower() {
        return lowPower;
    }
}
