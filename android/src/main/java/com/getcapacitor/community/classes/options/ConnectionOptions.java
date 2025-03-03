package com.getcapacitor.community.classes.options;

import static com.getcapacitor.community.NearbyConnectionsHelper.toConnectionType;
import static com.google.android.gms.nearby.connection.ConnectionOptions.convertConnectionTypeToString;

import androidx.annotation.Nullable;

import com.getcapacitor.PluginCall;
import com.getcapacitor.community.NearbyConnectionsConfig;

public abstract class ConnectionOptions extends EndpointOptions {

    @Nullable
    private Integer connectionType;

    @Nullable
    private Boolean lowPower;

    public ConnectionOptions(PluginCall call, @Nullable NearbyConnectionsConfig config) {
        super(call, config);

        String connectionType = call.getString("connectionType", (config == null) ? null : getConnectionType(config.getConnectionType()));
        this.setConnectionType(connectionType);

        Boolean lowPower = call.getBoolean("lowPower", (config == null) ? null : config.getLowPower());
        this.setLowPower(lowPower);
    }

    public ConnectionOptions(PluginCall call) {
        this(call, null);
    }

    public void setConnectionType(@Nullable String connectionType) {
        this.connectionType = toConnectionType(connectionType);
    }

    public void setLowPower(@Nullable Boolean lowPower) {
        this.lowPower = lowPower;
    }

    @Nullable
    public Integer getConnectionType() {
        return connectionType;
    }

    @Nullable
    public Boolean getLowPower() {
        return lowPower;
    }

    private String getConnectionType(Integer connectionType) {
        return convertConnectionTypeToString(connectionType);
    }
}
