package com.getcapacitor.community;

import androidx.annotation.Nullable;
import com.google.android.gms.nearby.connection.ConnectionType;
import com.google.android.gms.nearby.connection.Strategy;

public class NearbyConnectionsHelper {

    public static Integer toConnectionType(@Nullable String connectionType) {
        if (connectionType != null) {
            return switch (connectionType) {
                case "balanced" -> ConnectionType.BALANCED;
                case "disruptive" -> ConnectionType.DISRUPTIVE;
                case "nonDisruptive" -> ConnectionType.NON_DISRUPTIVE;
                default -> null;
            };
        }

        return null;
    }

    public static Strategy toStrategy(@Nullable String strategy) {
        if (strategy != null) {
            return switch (strategy) {
                case "cluster" -> Strategy.P2P_CLUSTER;
                case "star" -> Strategy.P2P_STAR;
                case "pointToPoint" -> Strategy.P2P_POINT_TO_POINT;
                default -> null;
            };
        }

        return null;
    }
}
