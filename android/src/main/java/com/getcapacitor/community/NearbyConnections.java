package com.getcapacitor.community;

import static com.google.android.gms.nearby.Nearby.getConnectionsClient;

import androidx.annotation.NonNull;
import com.getcapacitor.community.classes.Connection;
import com.getcapacitor.community.classes.Endpoint;
import com.getcapacitor.community.classes.options.AcceptConnectionOptions;
import com.getcapacitor.community.classes.options.CancelPayloadOptions;
import com.getcapacitor.community.classes.options.DisconnectFromEndpointOptions;
import com.getcapacitor.community.classes.options.RejectConnectionOptions;
import com.getcapacitor.community.classes.options.RequestConnectionOptions;
import com.getcapacitor.community.classes.options.SendPayloadOptions;
import com.getcapacitor.community.classes.options.StartAdvertisingOptions;
import com.getcapacitor.community.classes.options.StartDiscoveryOptions;
import com.getcapacitor.community.interfaces.EmptyCallback;
import com.getcapacitor.community.interfaces.VoidCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.BandwidthInfo;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionOptions;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.Strategy;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * True if we are asking a discovered device to connect to us. While we ask, we cannot ask another
     * device.
     */
    private boolean isConnecting = false;

    /**
     * True if we are discovering.
     */
    private boolean isDiscovering = false;

    /**
     * True if we are advertising.
     */
    private boolean isAdvertising = false;

    /**
     * The devices we've discovered near us.
     */
    private final Map<String, Endpoint> endpoints = new HashMap<>();

    /**
     * The devices we have pending connections to. They will stay pending until we call {@link
     * #acceptConnection(Endpoint)} or {@link #rejectConnection(Endpoint)}.
     */
    private final Map<String, Endpoint> requests = new HashMap<>();

    /**
     * The devices we are currently connected to. For advertisers, this may be large. For discoverers,
     * there will only be one entry in this map.
     */
    private final Map<String, Endpoint> connections = new HashMap<>();

    public NearbyConnections(@NonNull NearbyConnectionsConfig config, @NonNull NearbyConnectionsPlugin plugin) {
        this.config = config;
        this.plugin = plugin;

        connectionsClient = getConnectionsClient(plugin.getActivity());
    }

    public void initialize(@NonNull EmptyCallback callback) {
        if (config.getEndpointName() == null) {
            Exception exception = new Exception(MISSING_ENDPOINT_NAME);
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

    public void reset(@NonNull EmptyCallback callback) {
        stop();

        callback.success();
    }

    /**
     * Advertising
     */

    public void startAdvertising(@NonNull StartAdvertisingOptions options, @NonNull EmptyCallback callback) {
        Integer connectionType = options.getConnectionType();
        if (connectionType == null) {
            connectionType = config.getConnectionType();
        }

        String name = options.getEndpointName();
        if (name == null || name.isEmpty() || name.isBlank()) {
            name = config.getEndpointName();
            if (name == null || name.isEmpty() || name.isBlank()) {
                Exception exception = new Exception(MISSING_ENDPOINT_NAME);
                callback.error(exception);
                return;
            }
        }

        String serviceId = config.getServiceId();
        if (serviceId == null) {
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
            .startAdvertising(name, serviceId, connectionLifecycleCallback, advertisingOptions.build())
            .addOnSuccessListener(unusedResult -> {
                // Log.v(getLogTag(), "Now advertising endpoint " + name);
                onAdvertisingStarted();

                callback.success();
            })
            .addOnFailureListener(exception -> {
                isAdvertising = false;

                // Log.w(getLogTag(), "startAdvertising failed.", exception);
                onAdvertisingFailed();

                callback.error(exception);
            });
    }

    public void stopAdvertising(@NonNull EmptyCallback callback) {
        stopAdvertising();

        callback.success();
    }

    /**
     * Discovery
     */

    public void startDiscovery(@NonNull StartDiscoveryOptions options, @NonNull EmptyCallback callback) {
        Boolean lowPower = options.getLowPower();
        if (lowPower != null) config.setLowPower(lowPower);

        String serviceId = config.getServiceId();
        if (serviceId == null) {
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
            .startDiscovery(serviceId, endpointDiscoveryCallback, discoveryOptions.build())
            .addOnSuccessListener(unusedResult -> {
                // Log.v(getLogTag(), "Now starting discovery");
                onDiscoveryStarted();

                callback.success();
            })
            .addOnFailureListener(exception -> {
                isDiscovering = false;

                // Log.w(getLogTag(), "startDiscovering failed.", exception);
                onDiscoveryFailed();

                callback.error(exception);
            });

        endpoints.clear();
    }

    public void stopDiscovery(@NonNull EmptyCallback callback) {
        stopDiscovering();

        callback.success();
    }

    /**
     * Connection
     */

    public void requestConnection(@NonNull RequestConnectionOptions options, @NonNull VoidCallback callback) {
        String endpointId = options.getEndpointId();
        if (endpointId == null) {
            Exception exception = new Exception(MISSING_ENDPOINT_ID);
            callback.error(exception);
            return;
        }

        String name = options.getEndpointName();
        if (name == null || name.isEmpty() || name.isBlank()) {
            name = config.getEndpointName();
            if (name == null || name.isEmpty() || name.isBlank()) {
                Exception exception = new Exception(MISSING_ENDPOINT_NAME);
                callback.error(exception);
                return;
            }
        }

        Integer connectionType = options.getConnectionType();
        Boolean lowPower = options.getLowPower();

        ConnectionOptions.Builder connectionOptions = new ConnectionOptions.Builder();
        if (connectionType != null) connectionOptions.setConnectionType(connectionType);
        if (lowPower != null) connectionOptions.setLowPower(lowPower);

        connectionsClient
            .requestConnection(name, endpointId, connectionLifecycleCallback, connectionOptions.build())
            .addOnSuccessListener(callback::success)
            .addOnFailureListener(callback::error);
    }

    public void acceptConnection(@NonNull AcceptConnectionOptions options, @NonNull VoidCallback callback) {
        String endpointId = options.getEndpointId();
        if (endpointId == null) {
            Exception exception = new Exception(MISSING_ENDPOINT_ID);
            callback.error(exception);
            return;
        }

        connectionsClient
            .acceptConnection(endpointId, payloadCallback)
            .addOnSuccessListener(callback::success)
            .addOnFailureListener(callback::error);
    }

    public void rejectConnection(@NonNull RejectConnectionOptions options, @NonNull VoidCallback callback) {
        String endpointId = options.getEndpointId();
        if (endpointId == null) {
            Exception exception = new Exception(MISSING_ENDPOINT_ID);
            callback.error(exception);
            return;
        }

        connectionsClient.rejectConnection(endpointId).addOnSuccessListener(callback::success).addOnFailureListener(callback::error);
    }

    public void disconnectFromEndpoint(@NonNull DisconnectFromEndpointOptions options, @NonNull VoidCallback callback) {
        String endpointId = options.getEndpointId();
        if (endpointId == null) {
            Exception exception = new Exception(MISSING_ENDPOINT_ID);
            callback.error(exception);
            return;
        }

        connectionsClient.disconnectFromEndpoint(endpointId);
    }

    /**
     * Payload
     */

    public void sendPayload(@NonNull SendPayloadOptions options, @NonNull VoidCallback callback) {
        String endpointId = options.getEndpointId();
        if (endpointId == null) {
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
            .sendPayload(endpointId, Payload.fromBytes(payload))
            .addOnSuccessListener(callback::success)
            .addOnFailureListener(callback::error);
    }

    public void cancelPayload(@NonNull CancelPayloadOptions options, @NonNull VoidCallback callback) {
        Long payloadId = options.getPayloadId();
        if (payloadId == null) {
            Exception exception = new Exception(MISSING_PAYLOAD_ID);
            callback.error(exception);
            return;
        }

        connectionsClient.cancelPayload(payloadId).addOnSuccessListener(callback::success).addOnFailureListener(callback::error);
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
    protected void stopDiscovering() {
        isDiscovering = false;

        connectionsClient.stopDiscovery();
    }

    private void stop() {
        connectionsClient.stopAllEndpoints();

        isAdvertising = false;
        isDiscovering = false;
        isConnecting = false;

        endpoints.clear();
        requests.clear();
        connections.clear();
    }

    /**
     * Callback for discovering endpoints.
     */
    private final EndpointDiscoveryCallback endpointDiscoveryCallback = new EndpointDiscoveryCallback() {
        @Override
        public void onEndpointFound(@NonNull String endpointId, @NonNull DiscoveredEndpointInfo info) {
            String serviceId = config.getServiceId();

            if (serviceId != null && serviceId.equals(info.getServiceId())) {
                Endpoint endpoint = new Endpoint(endpointId, info.getEndpointName());
                endpoints.put(endpointId, endpoint);

                if (Boolean.TRUE.equals(config.getAutoConnect())) {
                    String endpointName = config.getEndpointName();
                    if (!(endpointName == null || endpointName.isEmpty() || endpointName.isBlank())) {
                        connectionsClient
                            .requestConnection(endpointName, endpointId, connectionLifecycleCallback)
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
        public void onEndpointLost(@NonNull String endpointId) {
            Endpoint endpoint = new Endpoint(endpointId, null);

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
        public void onBandwidthChanged(@NonNull String endpointId, @NonNull BandwidthInfo bandwidthInfo) {}

        /**
         * A basic encrypted channel has been created between you and the endpoint.
         * Both sides are now asked if they wish to accept or reject the connection before any data can be sent over this channel.
         *
         * @param endpointId The identifier for the remote endpoint.
         * @param connectionInfo Other relevant information about the connection.
         */
        @Override
        public void onConnectionInitiated(@NonNull String endpointId, @NonNull ConnectionInfo connectionInfo) {
            // Log.d(
            //         getLogTag(),
            //         String.format("onConnectionInitiated(endpointId=%s, endpointName=%s)", endpointId, connectionInfo.getEndpointName())
            // );

            Endpoint endpoint = new Endpoint(endpointId, connectionInfo.getEndpointName());
            requests.put(endpointId, endpoint);

            Connection connection = new Connection(connectionInfo.getAuthenticationDigits(), connectionInfo.isIncomingConnection());

            plugin.onEndpointInitiated(endpoint, connection);

            if (Boolean.TRUE.equals(config.getAutoConnect())) {
                connectionsClient.acceptConnection(endpointId, payloadCallback);
            }
        }

        /**
         * Called after both sides have either accepted or rejected the connection.
         *
         * @param endpointId The identifier for the remote endpoint.
         * @param resolution The final result after tallying both devices' accept/reject responses.
         */
        @Override
        public void onConnectionResult(@NonNull String endpointId, @NonNull ConnectionResolution resolution) {
            // Log.d(getLogTag(), String.format("onConnectionResult(endpointId=%s, resolution=%s)", endpointId, resolution));

            // We're no longer connecting
            isConnecting = false;

            Status status = resolution.getStatus();

            if (status.isSuccess()) {
                var payload = config.getPayload();
                if (payload != null) {
                    connectionsClient.sendPayload(endpointId, payload);
                }

                Endpoint endpoint = requests.remove(endpointId);
                if (endpoint != null) connectedToEndpoint(endpoint);
            } else {
                // Log.w(getLogTag(), String.format("Connection failed. Received status %s.", ConnectionsStatusCodes.getStatusCodeString(status.getStatusCode())));
                // status.getStatusMessage();

                Endpoint endpoint = requests.remove(endpointId);
                if (endpoint != null) onConnectionFailed(endpoint);
            }
        }

        /**
         * Called when a remote endpoint is disconnected or has become unreachable.
         *
         * @param endpointId The identifier for the remote endpoint that disconnected.
         */
        @Override
        public void onDisconnected(@NonNull String endpointId) {
            // if (!connections.containsKey(endpointId)) {
            // Log.w(getLogTag(), "Unexpected disconnection from endpoint " + endpointId);
            // return;
            // }

            Endpoint endpoint = connections.get(endpointId);
            if (endpoint != null) disconnectedFromEndpoint(endpoint);
        }
    };

    private void connectedToEndpoint(@NonNull Endpoint endpoint) {
        // Log.d(getLogTag(), String.format("connectedToEndpoint(endpoint=%s)", endpoint));

        connections.put(endpoint.endpointId(), endpoint);

        plugin.onEndpointConnected(endpoint);
    }

    private void disconnectedFromEndpoint(@NonNull Endpoint endpoint) {
        // Log.d(getLogTag(), String.format("disconnectedFromEndpoint(endpoint=%s)", endpoint));

        connections.remove(endpoint.endpointId());

        plugin.onEndpointDisconnected(endpoint);
    }

    /**
     * Callbacks for payloads (bytes of data) sent from another device to us.
     */
    private final PayloadCallback payloadCallback = new PayloadCallback() {
        @Override
        public void onPayloadReceived(@NonNull String endpointId, @NonNull Payload payload) {
            Endpoint endpoint = connections.get(endpointId);

            plugin.onPayloadReceived(
                endpoint,
                new com.getcapacitor.community.classes.Payload(payload.getId(), payload.getType(), payload.asBytes())
            );
        }

        @Override
        public void onPayloadTransferUpdate(@NonNull String endpointId, @NonNull PayloadTransferUpdate update) {}
    };

    /**
     * Events
     */

    /**
     * Called when advertising successfully starts. Override this method to act on the event.
     */
    protected void onAdvertisingStarted() {}

    /**
     * Called when advertising fails to start. Override this method to act on the event.
     */
    protected void onAdvertisingFailed() {}

    /**
     * Called when discovery successfully starts. Override this method to act on the event.
     */
    protected void onDiscoveryStarted() {}

    /**
     * Called when discovery fails to start. Override this method to act on the event.
     */
    protected void onDiscoveryFailed() {}

    /**
     * Called when a pending connection with a remote endpoint is created. Use {@link ConnectionInfo}
     * for metadata about the connection (like incoming vs outgoing, or the authentication token). If
     * we want to continue with the connection, call {@link #acceptConnection(Endpoint)}. Otherwise,
     * call {@link #rejectConnection(Endpoint)}.
     */
    protected void onConnectionInitiated(@NonNull Endpoint endpoint, @NonNull ConnectionInfo connectionInfo) {}

    /**
     * Called when a connection with this endpoint has failed. Override this method to act on the
     * event.
     */
    protected void onConnectionFailed(@NonNull Endpoint endpoint) {}
}
