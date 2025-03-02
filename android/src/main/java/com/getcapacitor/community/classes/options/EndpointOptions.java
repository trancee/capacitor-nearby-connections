package com.getcapacitor.community.classes.options;

import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class EndpointOptions {

    @Nullable
    private String endpointID;

    public EndpointOptions(PluginCall call) {
        String endpointID = call.getString("endpointID", null);
        this.setEndpointID(endpointID);
    }

    public void setEndpointID(@Nullable String endpointID) {
        this.endpointID = endpointID;
    }

    @Nullable
    public String getEndpointID() {
        return endpointID;
    }
}
