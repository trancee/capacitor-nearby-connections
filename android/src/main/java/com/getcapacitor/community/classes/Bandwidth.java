package com.getcapacitor.community.classes;

import com.google.android.gms.nearby.connection.BandwidthInfo.Quality;

public record Bandwidth(int quality) {
    public String getQuality() {
        return switch (quality) {
            case Quality.LOW -> "low";
            case Quality.MEDIUM -> "medium";
            case Quality.HIGH -> "high";
            default -> "unknown";
        };
    }
}
