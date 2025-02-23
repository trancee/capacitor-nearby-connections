package com.getcapacitor.community.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate.Status;

public record PayloadTransferUpdate(@NonNull Long payloadId, int status, @Nullable Long bytesTransferred, @Nullable Long totalBytes) {
    public String getStatus() {
        return switch (status) {
            case Status.SUCCESS -> "success";
            case Status.FAILURE -> "failure";
            case Status.IN_PROGRESS -> "inProgress";
            case Status.CANCELED -> "canceled";
            default -> "unknown";
        };
    }
}
