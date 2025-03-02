package com.getcapacitor.community.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate.Status;

public record PayloadTransferUpdate(@NonNull Long payloadID, int status, @Nullable Long bytesTransferred, @Nullable Long totalBytes) {
    public String getStatus() {
        return switch (status) {
            case Status.SUCCESS -> "success";
            case Status.CANCELED -> "canceled";
            case Status.FAILURE -> "failure";
            case Status.IN_PROGRESS -> "inProgress";
            default -> "unknown";
        };
    }
}
