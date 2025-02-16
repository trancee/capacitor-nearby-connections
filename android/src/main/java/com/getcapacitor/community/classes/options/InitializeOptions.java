package com.getcapacitor.community.classes.options;

import static com.getcapacitor.community.NearbyConnectionsHelper.toStrategy;

import android.util.Base64;
import androidx.annotation.Nullable;
import com.google.android.gms.nearby.connection.Strategy;

public class InitializeOptions {

    @Nullable
    private String endpointName;

    @Nullable
    private String serviceId;

    @Nullable
    private Strategy strategy;

    @Nullable
    private Boolean lowPower;

    @Nullable
    private Boolean autoConnect;

    @Nullable
    private byte[] payload;

    public InitializeOptions(
        @Nullable String endpointName,
        @Nullable String serviceId,
        @Nullable String strategy,
        @Nullable Boolean lowPower,
        @Nullable Boolean autoConnect,
        @Nullable byte[] payload
    ) {
        this.setEndpointName(endpointName);

        this.setServiceId(serviceId);

        this.setStrategy(strategy);

        this.setLowPower(lowPower);

        this.setAutoConnect(autoConnect);
        this.setPayload(payload);
    }

    @Nullable
    public String getEndpointName() {
        return endpointName;
    }

    @Nullable
    public String getServiceId() {
        return serviceId;
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

    public void setEndpointName(@Nullable String endpointName) {
        this.endpointName = endpointName;
    }

    public void setServiceId(@Nullable String serviceId) {
        this.serviceId = serviceId;
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
}
