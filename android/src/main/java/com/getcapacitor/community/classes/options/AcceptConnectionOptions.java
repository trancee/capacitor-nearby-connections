package com.getcapacitor.community.classes.options;

import static com.getcapacitor.community.NearbyConnectionsHelper.toConnectionType;

import android.util.Base64;
import androidx.annotation.Nullable;

public class AcceptConnectionOptions {

    @Nullable
    private String endpointId;

    public AcceptConnectionOptions(@Nullable String endpointId) {
        this.setEndpointId(endpointId);
    }

    @Nullable
    public String getEndpointId() {
        return endpointId;
    }

    public void setEndpointId(@Nullable String endpointId) {
        this.endpointId = endpointId;
    }
}
