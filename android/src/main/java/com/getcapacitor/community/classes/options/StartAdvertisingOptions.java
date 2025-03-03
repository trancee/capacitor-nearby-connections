package com.getcapacitor.community.classes.options;

import static com.getcapacitor.community.NearbyConnectionsHelper.toConnectionType;

import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import com.getcapacitor.community.NearbyConnectionsConfig;

public class StartAdvertisingOptions extends ConnectionOptions {

    public StartAdvertisingOptions(PluginCall call, NearbyConnectionsConfig config) {
        super(call, config);
    }
}
