package com.getcapacitor.community;

import static com.google.android.gms.nearby.Nearby.getConnectionsClient;

import androidx.annotation.NonNull;
import com.getcapacitor.community.classes.Bandwidth;
import com.getcapacitor.community.classes.Connection;
import com.getcapacitor.community.classes.Endpoint;
import com.getcapacitor.community.classes.options.AcceptConnectionOptions;
import com.getcapacitor.community.classes.options.CancelPayloadOptions;
import com.getcapacitor.community.classes.options.DisconnectOptions;
import com.getcapacitor.community.classes.options.InitializeOptions;
import com.getcapacitor.community.classes.options.RejectConnectionOptions;
import com.getcapacitor.community.classes.options.RequestConnectionOptions;
import com.getcapacitor.community.classes.options.SendPayloadOptions;
import com.getcapacitor.community.classes.options.StartAdvertisingOptions;
import com.getcapacitor.community.classes.options.StartDiscoveryOptions;
import com.getcapacitor.community.classes.results.StatusResult;
import com.getcapacitor.community.interfaces.Callback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.BandwidthInfo;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionOptions;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.Strategy;
import java.util.List;

public class NearbyConnections {

    private static final String MISSING_ENDPOINT_ID = "missing endpoint identifier";
    private static final String MISSING_ENDPOINT_NAME = "missing endpoint name";
    private static final String MISSING_SERVICE_ID = "missing service identifier";
    private static final String MISSING_STRATEGY = "missing strategy";
    private static final String MISSING_LOW_POWER = "missing low power";

    private static final String MISSING_PAYLOAD_ID = "missing payload identifier";
    private static final String MISSING_PAYLOAD = "missing payload";

    @NonNull
    private final NearbyConnectionsPlugin plugin;

    @NonNull
    private final NearbyConnectionsConfig config;

    @NonNull
    private final ConnectionsClient connectionsClient;

    private boolean isDiscovering = false;
    private boolean isAdvertising = false;

    public NearbyConnections(@NonNull NearbyConnectionsConfig config, @NonNull NearbyConnectionsPlugin plugin) {
        this.config = config;
        this.plugin = plugin;

        connectionsClient = getConnectionsClient(plugin.getActivity());
    }

    /**
     * Initialize
     */
    public void initialize(@NonNull InitializeOptions options, @NonNull Callback callback) {
        if (config.getEndpointName() == null) {
            Exception exception = new Exception(MISSING_ENDPOINT_NAME);
            callback.error(exception);
            return;
        }

        if (config.getServiceID() == null) {
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

    /**
     * Reset
     */
    public void reset(@NonNull Callback callback) {
        stop();

        callback.success();
    }

    /**
     * Advertising
     */
    public void startAdvertising(@NonNull StartAdvertisingOptions options, @NonNull Callback callback) {
        Integer connectionType = options.getConnectionType();
        if (connectionType == null) {
            connectionType = config.getConnectionType();
        }

        String name = options.getEndpointName();
        if (name == null || name.isEmpty() || name.isBlank()) {
            Exception exception = new Exception(MISSING_ENDPOINT_NAME);
            callback.error(exception);
            return;
        }

        String serviceID = config.getServiceID();
        if (serviceID == null) {
            Exception exception = new Exception(MISSING_SERVICE_ID);
            callback.error(exception);
            return;
        }

        Strategy strategy = config.getStrategy();
        if (strategy == null) {
            Exception exception = new Exception(MISSING_STRATEGY);
            callback.error(exception);
            return;
        }

        Boolean lowPower = config.getLowPower();

        AdvertisingOptions.Builder advertisingOptions = new AdvertisingOptions.Builder();
        if (connectionType != null) advertisingOptions.setConnectionType(connectionType);
        if (lowPower != null) advertisingOptions.setLowPower(lowPower);
        advertisingOptions.setStrategy(strategy);

        connectionsClient
            .startAdvertising(name, serviceID, connectionLifecycleCallback, advertisingOptions.build())
            .addOnSuccessListener(unusedResult -> {
                isAdvertising = true;

                callback.success();
            })
            .addOnFailureListener(exception -> {
                isAdvertising = false;

                callback.error(exception);
            });
    }

    public void stopAdvertising(@NonNull Callback callback) {
        stopAdvertising();

        callback.success();
    }

    /**
     * Discovery
     */
    public void startDiscovery(@NonNull StartDiscoveryOptions options, @NonNull Callback callback) {
        Boolean lowPower = options.getLowPower();
        if (lowPower != null) config.setLowPower(lowPower);

        String serviceID = config.getServiceID();
        if (serviceID == null) {
            Exception exception = new Exception(MISSING_SERVICE_ID);
            callback.error(exception);
            return;
        }

        Strategy strategy = config.getStrategy();
        if (strategy == null) {
            Exception exception = new Exception(MISSING_STRATEGY);
            callback.error(exception);
            return;
        }

        DiscoveryOptions.Builder discoveryOptions = new DiscoveryOptions.Builder();
        if (lowPower != null) discoveryOptions.setLowPower(lowPower);
        discoveryOptions.setStrategy(strategy);

        connectionsClient
            .startDiscovery(serviceID, endpointDiscoveryCallback, discoveryOptions.build())
            .addOnSuccessListener(unusedResult -> {
                isDiscovering = true;

                callback.success();
            })
            .addOnFailureListener(exception -> {
                isDiscovering = false;

                callback.error(exception);
            });
    }

    public void stopDiscovery(@NonNull Callback callback) {
        stopDiscovery();

        callback.success();
    }

    /**
     * Connection
     */
    public void requestConnection(@NonNull RequestConnectionOptions options, @NonNull Callback callback) {
        String endpointID = options.getEndpointID();
        if (endpointID == null) {
            Exception exception = new Exception(MISSING_ENDPOINT_ID);
            callback.error(exception);
            return;
        }

        String name = options.getEndpointName();
        if (name == null || name.isEmpty() || name.isBlank()) {
            Exception exception = new Exception(MISSING_ENDPOINT_NAME);
            callback.error(exception);
            return;
        }

        Integer connectionType = options.getConnectionType();
        Boolean lowPower = options.getLowPower();

        ConnectionOptions.Builder connectionOptions = new ConnectionOptions.Builder();
        if (connectionType != null) connectionOptions.setConnectionType(connectionType);
        if (lowPower != null) connectionOptions.setLowPower(lowPower);

        connectionsClient
            .requestConnection(name, endpointID, connectionLifecycleCallback, connectionOptions.build())
            .addOnSuccessListener(callback::success)
            .addOnFailureListener(callback::error);
    }

    public void acceptConnection(@NonNull AcceptConnectionOptions options, @NonNull Callback callback) {
        String endpointID = options.getEndpointID();
        if (endpointID == null) {
            Exception exception = new Exception(MISSING_ENDPOINT_ID);
            callback.error(exception);
            return;
        }

        connectionsClient
            .acceptConnection(endpointID, payloadCallback)
            .addOnSuccessListener(callback::success)
            .addOnFailureListener(callback::error);
    }

    public void rejectConnection(@NonNull RejectConnectionOptions options, @NonNull Callback callback) {
        String endpointID = options.getEndpointID();
        if (endpointID == null) {
            Exception exception = new Exception(MISSING_ENDPOINT_ID);
            callback.error(exception);
            return;
        }

        connectionsClient.rejectConnection(endpointID).addOnSuccessListener(callback::success).addOnFailureListener(callback::error);
    }

    public void disconnect(@NonNull DisconnectOptions options, @NonNull Callback callback) {
        String endpointID = options.getEndpointID();
        if (endpointID == null) {
            Exception exception = new Exception(MISSING_ENDPOINT_ID);
            callback.error(exception);
            return;
        }

        connectionsClient.disconnectFromEndpoint(endpointID);
    }

    /**
     * Payload
     */
    public void sendPayload(@NonNull SendPayloadOptions options, @NonNull Callback callback) {
        List<String> endpointIDs = options.getEndpointIDs();
        if (endpointIDs == null || endpointIDs.isEmpty()) {
            Exception exception = new Exception(MISSING_ENDPOINT_ID);
            callback.error(exception);
            return;
        }

        byte[] payload = options.getPayload();
        if (payload == null) {
            Exception exception = new Exception(MISSING_PAYLOAD);
            callback.error(exception);
            return;
        }

        connectionsClient
            .sendPayload(endpointIDs, Payload.fromBytes(payload))
            .addOnSuccessListener(callback::success)
            .addOnFailureListener(callback::error);
    }

    public void cancelPayload(@NonNull CancelPayloadOptions options, @NonNull Callback callback) {
        Long payloadID = options.getPayloadID();
        if (payloadID == null) {
            Exception exception = new Exception(MISSING_PAYLOAD_ID);
            callback.error(exception);
            return;
        }

        connectionsClient.cancelPayload(payloadID).addOnSuccessListener(callback::success).addOnFailureListener(callback::error);
    }

    /**
     * Status
     */
    public void status(@NonNull Callback callback) {
        StatusResult result = new StatusResult(isAdvertising, isDiscovering);

        callback.success(result);
    }

    /**
     * Stops advertising.
     */
    protected void stopAdvertising() {
        isAdvertising = false;

        connectionsClient.stopAdvertising();
    }

    /**
     * Stops discovery.
     */
    protected void stopDiscovery() {
        isDiscovering = false;

        connectionsClient.stopDiscovery();
    }

    private void stop() {
        connectionsClient.stopAllEndpoints();

        isAdvertising = false;
        isDiscovering = false;
    }

    /**
     * Callback for discovering endpoints.
     */
    private final EndpointDiscoveryCallback endpointDiscoveryCallback = new EndpointDiscoveryCallback() {
        @Override
        public void onEndpointFound(@NonNull String endpointID, @NonNull DiscoveredEndpointInfo info) {
            String serviceID = config.getServiceID();

            if (serviceID != null && serviceID.equals(info.getServiceId())) {
                Endpoint endpoint = new Endpoint(endpointID, info.getEndpointName());

                if (Boolean.TRUE.equals(config.getAutoConnect())) {
                    String endpointName = config.getEndpointName();
                    if (!(endpointName == null || endpointName.isEmpty() || endpointName.isBlank())) {
                        connectionsClient
                            .requestConnection(endpointName, endpointID, connectionLifecycleCallback)
                            .addOnSuccessListener((Void unused) -> {
                                System.out.println("requestConnection::success");
                            })
                            .addOnFailureListener((Exception e) -> {
                                System.out.println("requestConnection::failure");
                            });
                    }
                }

                plugin.onEndpointFound(endpoint);
            }
        }

        @Override
        public void onEndpointLost(@NonNull String endpointID) {
            Endpoint endpoint = new Endpoint(endpointID, null);

            plugin.onEndpointLost(endpoint);
        }
    };

    /**
     * Listener for lifecycle events associated with a connection to a remote endpoint.
     */
    private final ConnectionLifecycleCallback connectionLifecycleCallback = new ConnectionLifecycleCallback() {
        /**
         * Called when a connection is established or if the connection quality improves to a higher connection bandwidth.
         */
        @Override
        public void onBandwidthChanged(@NonNull String endpointID, @NonNull BandwidthInfo bandwidthInfo) {
            Endpoint endpoint = new Endpoint(endpointID, null);
            Bandwidth bandwidth = new Bandwidth(bandwidthInfo.getQuality());

            plugin.onEndpointBandwidthChanged(endpoint, bandwidth);
        }

        /**
         * A basic encrypted channel has been created between you and the endpoint.
         * Both sides are now asked if they wish to accept or reject the connection before any data can be sent over this channel.
         *
         * @param endpointID The identifier for the remote endpoint.
         * @param connectionInfo Other relevant information about the connection.
         */
        @Override
        public void onConnectionInitiated(@NonNull String endpointID, @NonNull ConnectionInfo connectionInfo) {
            Endpoint endpoint = new Endpoint(endpointID, connectionInfo.getEndpointName());
            Connection connection = new Connection(connectionInfo.getAuthenticationDigits(), connectionInfo.isIncomingConnection());

            plugin.onEndpointInitiated(endpoint, connection);

            if (Boolean.TRUE.equals(config.getAutoConnect())) {
                connectionsClient.acceptConnection(endpointID, payloadCallback);
            }
        }

        /**
         * Called after both sides have either accepted or rejected the connection.
         *
         * @param endpointID The identifier for the remote endpoint.
         * @param resolution The final result after tallying both devices' accept/reject responses.
         */
        @Override
        public void onConnectionResult(@NonNull String endpointID, @NonNull ConnectionResolution resolution) {
            Endpoint endpoint = new Endpoint(endpointID, null);
            Status status = resolution.getStatus();

            if (status.isSuccess()) {
                var payload = config.getPayload();
                if (payload != null) {
                    connectionsClient.sendPayload(endpointID, payload);
                }

                acceptedEndpoint(endpoint);
            } else if (status.getStatusCode() == ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED) {
                rejectedEndpoint(endpoint);
            } else {
                failedEndpoint(endpoint, ConnectionsStatusCodes.getStatusCodeString(status.getStatusCode()));
            }
        }

        /**
         * Called when a remote endpoint is disconnected or has become unreachable.
         *
         * @param endpointID The identifier for the remote endpoint that disconnected.
         */
        @Override
        public void onDisconnected(@NonNull String endpointID) {
            Endpoint endpoint = new Endpoint(endpointID, null);

            disconnectedEndpoint(endpoint);
        }
    };

    private void acceptedEndpoint(@NonNull Endpoint endpoint) {
        plugin.onEndpointConnected(endpoint);
    }

    private void rejectedEndpoint(@NonNull Endpoint endpoint) {
        plugin.onEndpointRejected(endpoint);
    }

    private void failedEndpoint(@NonNull Endpoint endpoint, @NonNull String status) {
        plugin.onEndpointFailed(endpoint, status);
    }

    private void disconnectedEndpoint(@NonNull Endpoint endpoint) {
        plugin.onEndpointDisconnected(endpoint);
    }

    /**
     * Callbacks for payloads (bytes of data) sent from another device to us.
     */
    private final PayloadCallback payloadCallback = new PayloadCallback() {
        @Override
        public void onPayloadReceived(@NonNull String endpointID, @NonNull Payload payload) {
            Endpoint endpoint = new Endpoint(endpointID, null);

            plugin.onPayloadReceived(
                endpoint,
                new com.getcapacitor.community.classes.Payload(payload.getId(), payload.getType(), payload.asBytes())
            );
        }

        @Override
        public void onPayloadTransferUpdate(@NonNull String endpointID, @NonNull PayloadTransferUpdate update) {
            Endpoint endpoint = new Endpoint(endpointID, null);

            plugin.onPayloadTransferUpdate(
                endpoint,
                new com.getcapacitor.community.classes.PayloadTransferUpdate(
                    update.getPayloadId(),
                    update.getStatus(),
                    update.getBytesTransferred(),
                    update.getTotalBytes()
                )
            );
        }
    };
}
