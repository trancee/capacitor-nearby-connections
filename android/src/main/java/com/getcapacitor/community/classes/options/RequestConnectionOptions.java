package com.getcapacitor.community.classes.options;

import static com.getcapacitor.community.NearbyConnectionsHelper.toConnectionType;

import android.util.Base64;
import androidx.annotation.Nullable;

public class RequestConnectionOptions {

    @Nullable
    private String endpointId;

    @Nullable
    private String endpointName;

    @Nullable
    private Integer connectionType;

    @Nullable
    private Boolean lowPower;

    public RequestConnectionOptions(
        @Nullable String endpointId,
        @Nullable String endpointName,
        @Nullable String connectionType,
        @Nullable Boolean lowPower
    ) {
        this.setEndpointId(endpointId);
        this.setEndpointName(endpointName);

        this.setConnectionType(connectionType);
        this.setLowPower(lowPower);
    }

    @Nullable
    public String getEndpointId() {
        return endpointId;
    }

    @Nullable
    public String getEndpointName() {
        return endpointName;
    }

    @Nullable
    public Integer getConnectionType() {
        return connectionType;
    }

    @Nullable
    public Boolean getLowPower() {
        return lowPower;
    }

    public void setEndpointId(@Nullable String endpointId) {
        this.endpointId = endpointId;
    }

    public void setEndpointName(@Nullable String endpointName) {
        this.endpointName = endpointName;
    }

    public void setConnectionType(@Nullable String connectionType) {
        this.connectionType = toConnectionType(connectionType);
    }

    public void setLowPower(@Nullable Boolean lowPower) {
        this.lowPower = lowPower;
    }
}
