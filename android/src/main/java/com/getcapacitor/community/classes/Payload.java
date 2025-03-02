package com.getcapacitor.community.classes;

import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.nearby.connection.Payload.Type;

public record Payload(@NonNull Long payloadID, int payloadType, @Nullable byte[] data) {
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
}
