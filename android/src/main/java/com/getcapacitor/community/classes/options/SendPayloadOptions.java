package com.getcapacitor.community.classes.options;

import androidx.annotation.Nullable;

public class SendPayloadOptions {

    @Nullable
    private String endpointId;

    @Nullable
    private byte[] payload;

    public SendPayloadOptions(@Nullable String endpointId, @Nullable byte[] payload) {
        this.setEndpointId(endpointId);

        this.setPayload(payload);
    }

    @Nullable
    public String getEndpointId() {
        return endpointId;
    }

    @Nullable
    public byte[] getPayload() {
        return payload;
    }

    public void setEndpointId(@Nullable String endpointId) {
        this.endpointId = endpointId;
    }

    public void setPayload(@Nullable byte[] payload) {
        this.payload = payload;
    }
}
