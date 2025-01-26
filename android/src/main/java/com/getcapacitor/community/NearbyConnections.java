package com.getcapacitor.community;

import static com.google.android.gms.nearby.Nearby.getConnectionsClient;

import androidx.annotation.NonNull;
import com.getcapacitor.community.interfaces.EmptyCallback;
import com.google.android.gms.nearby.connection.ConnectionsClient;

public class NearbyConnections {

    private static final String MISSING_NAME = "missing name";
    private static final String MISSING_SERVICE_ID = "missing service identifier";
    private static final String MISSING_STRATEGY = "missing strategy";
    private static final String MISSING_LOW_POWER = "missing low power";

    @NonNull
    private final NearbyConnectionsPlugin plugin;

    @NonNull
    private final NearbyConnectionsConfig config;

    @NonNull
    private final ConnectionsClient connectionsClient;

    public NearbyConnections(@NonNull NearbyConnectionsConfig config, @NonNull NearbyConnectionsPlugin plugin) {
        this.config = config;
        this.plugin = plugin;

        connectionsClient = getConnectionsClient(plugin.getActivity());
    }

    public void initialize(@NonNull EmptyCallback callback) {
        if (config.getName() == null) {
            Exception exception = new Exception(MISSING_NAME);
            callback.error(exception);
            return;
        }

        if (config.getServiceId() == null) {
            Exception exception = new Exception(MISSING_SERVICE_ID);
            callback.error(exception);
            return;
        }

        if (config.getStrategy() == null) {
            Exception exception = new Exception(MISSING_STRATEGY);
            callback.error(exception);
            return;
        }

        if (config.getLowPower() == null) {
            Exception exception = new Exception(MISSING_LOW_POWER);
            callback.error(exception);
            return;
        }

        callback.success();
    }
}
