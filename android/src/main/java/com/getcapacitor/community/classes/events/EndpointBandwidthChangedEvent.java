package com.getcapacitor.community.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.community.classes.Bandwidth;
import com.getcapacitor.community.classes.Endpoint;

public class EndpointBandwidthChangedEvent extends EndpointEvent {

    @NonNull
    Bandwidth bandwidth;

    public EndpointBandwidthChangedEvent(@NonNull Endpoint endpoint, @NonNull Bandwidth bandwidth) {
        super(endpoint);
        this.bandwidth = bandwidth;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = super.toJSObject();
        result.put("quality", bandwidth.getQuality());
        return result;
    }
}
