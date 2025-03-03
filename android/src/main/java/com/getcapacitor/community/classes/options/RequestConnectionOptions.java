package com.getcapacitor.community.classes.options;

import static com.getcapacitor.community.NearbyConnectionsHelper.toConnectionType;

import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import com.getcapacitor.community.NearbyConnectionsConfig;

public class RequestConnectionOptions extends ConnectionOptions {

    public RequestConnectionOptions(PluginCall call, NearbyConnectionsConfig config) {
        super(call, config);
    }
}
