package com.getcapacitor.community.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Represents a device we can talk to.
 */
public record Endpoint(@NonNull String endpointId, @Nullable String endpointName) {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Endpoint other) {
            return endpointId.equals(other.endpointId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return endpointId.hashCode();
    }

    @NonNull
    @Override
    public String toString() {
        String format = String.format("Endpoint{endpointId=%s}", endpointId);

        if (endpointName != null) {
            format += String.format(", endpointName=%s", endpointName);
        }

        return format;
    }
}
