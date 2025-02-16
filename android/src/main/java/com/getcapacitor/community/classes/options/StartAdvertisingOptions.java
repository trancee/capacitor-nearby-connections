package com.getcapacitor.community.classes.options;

import static com.getcapacitor.community.NearbyConnectionsHelper.toConnectionType;

import android.util.Base64;
import androidx.annotation.Nullable;

public class StartAdvertisingOptions {

    @Nullable
    private Integer connectionType;

    @Nullable
    private String endpointName;

    public StartAdvertisingOptions(@Nullable String connectionType, @Nullable String endpointName) {
        this.setConnectionType(connectionType);

        this.setEndpointName(endpointName);
    }

    @Nullable
    public Integer getConnectionType() {
        return connectionType;
    }

    @Nullable
    public String getEndpointName() {
        return endpointName;
    }

    public void setConnectionType(@Nullable String connectionType) {
        this.connectionType = toConnectionType(connectionType);
    }

    public void setEndpointName(@Nullable String endpointName) {
        this.endpointName = endpointName;
    }
}
