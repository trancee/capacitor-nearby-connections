package com.getcapacitor.community.classes.options;

import static com.getcapacitor.community.NearbyConnectionsHelper.toStrategy;

import android.util.Base64;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import com.getcapacitor.community.NearbyConnectionsConfig;
import com.google.android.gms.nearby.connection.Strategy;

public class InitializeOptions {

    @Nullable
    private String endpointName;

    @Nullable
    private String serviceID;

    @Nullable
    private Strategy strategy;

    @Nullable
    private Boolean lowPower;

    @Nullable
    private Boolean autoConnect;

    @Nullable
    private byte[] payload;

    public InitializeOptions(PluginCall call, NearbyConnectionsConfig config) {
        String endpointName = call.getString("endpointName");
        if (endpointName != null) {
            config.setEndpointName(endpointName);
        }
        this.setEndpointName(endpointName);

        String serviceID = call.getString("serviceID");
        if (serviceID != null) {
            config.setServiceID(serviceID);
        }
        this.setServiceID(serviceID);

        String strategy = call.getString("strategy");
        if (strategy != null) {
            config.setStrategy(strategy);
        }
        this.setStrategy(strategy);

        Boolean lowPower = call.getBoolean("lowPower");
        if (lowPower != null) {
            config.setLowPower(lowPower);
        }
        this.setLowPower(lowPower);

        Boolean autoConnect = call.getBoolean("autoConnect");
        if (autoConnect != null) {
            config.setAutoConnect(autoConnect);
        }
        this.setAutoConnect(autoConnect);

        String payload = call.getString("payload");
        if (payload != null) {
            config.setPayload(payload);
        }
        this.setPayload(payload);
    }

    public void setEndpointName(@Nullable String endpointName) {
        this.endpointName = endpointName;
    }

    public void setServiceID(@Nullable String serviceID) {
        this.serviceID = serviceID;
    }

    public void setStrategy(@Nullable String strategy) {
        this.strategy = toStrategy(strategy);
    }

    public void setLowPower(@Nullable Boolean lowPower) {
        this.lowPower = lowPower;
    }

    public void setAutoConnect(@Nullable Boolean autoConnect) {
        this.autoConnect = autoConnect;
    }

    public void setPayload(@Nullable byte[] payload) {
        this.payload = payload;
    }

    public void setPayload(@Nullable String payload) {
        this.payload = (payload == null) ? null : Base64.decode(payload, Base64.NO_WRAP);
    }

    @Nullable
    public String getEndpointName() {
        return endpointName;
    }

    @Nullable
    public String getServiceID() {
        return serviceID;
    }

    @Nullable
    public Strategy getStrategy() {
        return strategy;
    }

    @Nullable
    public Boolean getLowPower() {
        return lowPower;
    }

    @Nullable
    public Boolean getAutoConnect() {
        return autoConnect;
    }

    @Nullable
    public byte[] getPayload() {
        return payload;
    }
}
