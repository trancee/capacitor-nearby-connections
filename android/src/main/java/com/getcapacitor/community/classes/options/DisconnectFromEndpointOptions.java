package com.getcapacitor.community.classes.options;

import androidx.annotation.Nullable;

public class DisconnectFromEndpointOptions {

    @Nullable
    private String endpointId;

    public DisconnectFromEndpointOptions(@Nullable String endpointId) {
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
