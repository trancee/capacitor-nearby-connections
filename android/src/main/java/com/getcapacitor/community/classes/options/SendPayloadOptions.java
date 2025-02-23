package com.getcapacitor.community.classes.options;

import androidx.annotation.Nullable;
import java.util.List;

public class SendPayloadOptions {

    // @Nullable
    // private String endpointId;
    @Nullable
    private List<String> endpointIds;

    @Nullable
    private byte[] payload;

    public SendPayloadOptions(@Nullable List<String> endpointIds, @Nullable byte[] payload) {
        // this.setEndpointId(endpointId);
        this.setEndpointIds(endpointIds);

        this.setPayload(payload);
    }

    // @Nullable
    // public String getEndpointId() {
    //     return endpointId;
    // }

    @Nullable
    public List<String> getEndpointIds() {
        return endpointIds;
    }

    @Nullable
    public byte[] getPayload() {
        return payload;
    }

    // public void setEndpointId(@Nullable String endpointId) {
    //     this.endpointId = endpointId;
    // }

    public void setEndpointIds(@Nullable List<String> endpointIds) {
        this.endpointIds = endpointIds;
    }

    public void setPayload(@Nullable byte[] payload) {
        this.payload = payload;
    }
}
