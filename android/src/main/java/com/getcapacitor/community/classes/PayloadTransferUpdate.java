package com.getcapacitor.community.classes;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate.Status;

public record PayloadTransferUpdate(@NonNull Long payloadId, int status, @Nullable Long bytesTransferred, @Nullable Long totalBytes) {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PayloadTransferUpdate other) {
            return payloadId.equals(other.payloadId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return payloadId.hashCode();
    }

    public String getStatus() {
        return switch (status) {
            case Status.SUCCESS -> "success";
            case Status.FAILURE -> "failure";
            case Status.IN_PROGRESS -> "inProgress";
            case Status.CANCELED -> "canceled";
            default -> "unknown";
        };
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {
        return String.format(
            "PayloadTransferUpdate{payloadId=%d,status=%s,bytesTransferred=%d,totalBytes=%d}",
            payloadId,
            getStatus(),
            bytesTransferred,
            totalBytes
        );
    }
}
