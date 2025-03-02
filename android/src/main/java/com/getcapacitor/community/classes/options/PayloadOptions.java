package com.getcapacitor.community.classes.options;

import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class PayloadOptions {

    @Nullable
    private Long payloadID;

    public PayloadOptions(PluginCall call) {
        Long payloadID = call.getLong("payloadID", null);
        this.setPayloadID(payloadID);
    }

    public void setPayloadID(@Nullable Long payloadID) {
        this.payloadID = payloadID;
    }

    @Nullable
    public Long getPayloadID() {
        return payloadID;
    }
}
