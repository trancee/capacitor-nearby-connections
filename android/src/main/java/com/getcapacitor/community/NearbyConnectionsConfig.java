package com.getcapacitor.community;

import static com.getcapacitor.community.NearbyConnectionsHelper.toConnectionType;
import static com.getcapacitor.community.NearbyConnectionsHelper.toStrategy;

import android.util.Base64;
import androidx.annotation.Nullable;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.Strategy;

public class NearbyConnectionsConfig {

    @Nullable
    private String endpointName;

    @Nullable
    private String serviceId;

    @Nullable
    private Strategy strategy;

    @Nullable
    private Boolean lowPower;

    @Nullable
    private Integer connectionType;

    @Nullable
    private Boolean autoConnect;

    @Nullable
    private Payload payload;

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
    public Integer getConnectionType() {
        return connectionType;
    }

    @Nullable
    public Boolean getAutoConnect() {
        return autoConnect;
    }

    @Nullable
    public Payload getPayload() {
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

    public void setConnectionType(@Nullable String connectionType) {
        this.connectionType = toConnectionType(connectionType);
    }

    public void setAutoConnect(@Nullable Boolean autoConnect) {
        this.autoConnect = autoConnect;
    }

    public void setPayload(@Nullable String payload) {
        if (payload == null) this.payload = null;
        else this.payload = Payload.fromBytes(Base64.decode(payload, Base64.NO_WRAP));
    }
}
