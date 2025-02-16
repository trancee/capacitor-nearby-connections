package com.getcapacitor.community.classes;

import android.annotation.SuppressLint;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.nearby.connection.Payload.Type;

public record Payload(@NonNull Long payloadId, int payloadType, @Nullable byte[] data) {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Payload other) {
            return payloadId.equals(other.payloadId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return payloadId.hashCode();
    }

    public String getPayloadType() {
        return switch (payloadType) {
            case Type.BYTES -> "bytes";
            case Type.FILE -> "file";
            case Type.STREAM -> "stream";
            default -> "unknown";
        };
    }

    public String getPayload() {
        return Base64.encodeToString(data, Base64.NO_WRAP);
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {
        return String.format("Payload{payloadId=%d,type=%d,data=%s}", payloadId, payloadType, getPayload());
    }
}
