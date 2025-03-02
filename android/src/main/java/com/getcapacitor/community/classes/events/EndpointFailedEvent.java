package com.getcapacitor.community.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.community.classes.Endpoint;

public class EndpointFailedEvent extends EndpointEvent {

    @NonNull
    String status;

    public EndpointFailedEvent(@NonNull Endpoint endpoint, @NonNull String status) {
        super(endpoint);
        this.status = status;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = super.toJSObject();

        result.put("status", status);

        return result;
    }
}
