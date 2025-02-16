package com.getcapacitor.community.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.community.classes.Endpoint;

public class EndpointEvent {

    @NonNull
    Endpoint endpoint;

    public EndpointEvent(@NonNull Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("endpointId", endpoint.endpointId());
        if (endpoint.endpointName() != null) result.put("endpointName", endpoint.endpointName());
        return result;
    }
}
