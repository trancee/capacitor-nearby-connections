package com.getcapacitor.community.classes.options;

import androidx.annotation.Nullable;

public class CancelPayloadOptions {

    @Nullable
    private Long payloadId;

    public CancelPayloadOptions(@Nullable Long payloadId) {
        this.setPayloadId(payloadId);
    }

    @Nullable
    public Long getPayloadId() {
        return payloadId;
    }

    public void setPayloadId(@Nullable Long payloadId) {
        this.payloadId = payloadId;
    }
}
