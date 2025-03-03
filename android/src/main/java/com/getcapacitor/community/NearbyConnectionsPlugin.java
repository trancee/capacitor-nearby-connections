package com.getcapacitor.community;

import android.Manifest;
import android.os.Build;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import com.getcapacitor.community.classes.Bandwidth;
import com.getcapacitor.community.classes.Connection;
import com.getcapacitor.community.classes.Endpoint;
import com.getcapacitor.community.classes.Payload;
import com.getcapacitor.community.classes.PayloadTransferUpdate;
import com.getcapacitor.community.classes.events.EndpointBandwidthChangedEvent;
import com.getcapacitor.community.classes.events.EndpointConnectedEvent;
import com.getcapacitor.community.classes.events.EndpointDisconnectedEvent;
import com.getcapacitor.community.classes.events.EndpointFailedEvent;
import com.getcapacitor.community.classes.events.EndpointFoundEvent;
import com.getcapacitor.community.classes.events.EndpointInitiatedEvent;
import com.getcapacitor.community.classes.events.EndpointLostEvent;
import com.getcapacitor.community.classes.events.EndpointRejectedEvent;
import com.getcapacitor.community.classes.events.PayloadReceivedEvent;
import com.getcapacitor.community.classes.events.PayloadTransferUpdateEvent;
import com.getcapacitor.community.classes.options.AcceptConnectionOptions;
import com.getcapacitor.community.classes.options.CancelPayloadOptions;
import com.getcapacitor.community.classes.options.DisconnectOptions;
import com.getcapacitor.community.classes.options.InitializeOptions;
import com.getcapacitor.community.classes.options.RejectConnectionOptions;
import com.getcapacitor.community.classes.options.RequestConnectionOptions;
import com.getcapacitor.community.classes.options.SendPayloadOptions;
import com.getcapacitor.community.classes.options.StartAdvertisingOptions;
import com.getcapacitor.community.classes.options.StartDiscoveryOptions;
import com.getcapacitor.community.interfaces.Callback;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;

@CapacitorPlugin(
    name = "NearbyConnections",
    permissions = {
        @Permission(
            strings = {
                // Required to be able to advertise and connect to nearby devices via Wi-Fi.
                Manifest.permission.NEARBY_WIFI_DEVICES
            },
            alias = "wifiNearby"
        ),
        @Permission(
            strings = {
                // Allows applications to access information about Wi-Fi networks.
                Manifest.permission.ACCESS_WIFI_STATE,
                // Allows applications to change Wi-Fi connectivity state.
                Manifest.permission.CHANGE_WIFI_STATE
            },
            alias = "wifiState"
        ),
        @Permission(
            strings = {
                // Required to be able to connect to paired Bluetooth devices.
                Manifest.permission.BLUETOOTH_CONNECT,
                // Required to be able to advertise to nearby Bluetooth devices.
                Manifest.permission.BLUETOOTH_ADVERTISE,
                // Required to be able to discover and pair nearby Bluetooth devices.
                Manifest.permission.BLUETOOTH_SCAN
            },
            alias = "bluetoothNearby"
        ),
        @Permission(
            strings = {
                // Allows applications to connect to paired bluetooth devices.
                Manifest.permission.BLUETOOTH,
                // Allows applications to discover and pair bluetooth devices.
                Manifest.permission.BLUETOOTH_ADMIN
            },
            alias = "bluetoothLegacy"
        ),
        @Permission(
            strings = {
                // Allows an app to access approximate location.
                Manifest.permission.ACCESS_COARSE_LOCATION,
                // Allows an app to access precise location.
                Manifest.permission.ACCESS_FINE_LOCATION
            },
            alias = "location"
        ),
        @Permission(
            strings = {
                // Allows an app to access approximate location.
                Manifest.permission.ACCESS_COARSE_LOCATION
            },
            alias = "locationCoarse"
        )
    }
)
public class NearbyConnectionsPlugin extends Plugin {

    static final String ENDPOINT_FOUND_EVENT = "onEndpointFound";
    static final String ENDPOINT_LOST_EVENT = "onEndpointLost";
    static final String ENDPOINT_INITIATED_EVENT = "onEndpointInitiated";
    static final String ENDPOINT_CONNECTED_EVENT = "onEndpointConnected";
    static final String ENDPOINT_REJECTED_EVENT = "onEndpointRejected";
    static final String ENDPOINT_FAILED_EVENT = "onEndpointFailed";
    static final String ENDPOINT_DISCONNECTED_EVENT = "onEndpointDisconnected";
    static final String ENDPOINT_BANDWIDTH_CHANGED_EVENT = "onEndpointBandwidthChanged";
    static final String PAYLOAD_RECEIVED_EVENT = "onPayloadReceived";
    static final String PAYLOAD_TRANSFER_UPDATE_EVENT = "onPayloadTransferUpdate";

    private NearbyConnections implementation;

    private NearbyConnectionsConfig config;

    @Override
    public void load() {
        super.load();

        config = getNearbyConnectionsConfig();
        implementation = new NearbyConnections(config, this);
    }

    /**
     * Initialize
     */

    @PluginMethod
    public void initialize(PluginCall call) {
        Callback callback = new Callback(call) {};

        try {
            InitializeOptions options = new InitializeOptions(call, config);

            implementation.initialize(options, callback);
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    /**
     * Reset
     */

    @PluginMethod
    public void reset(PluginCall call) {
        Callback callback = new Callback(call) {};

        try {
            implementation.reset(callback);
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    /**
     * Advertising
     */

    @PluginMethod
    public void startAdvertising(PluginCall call) {
        Callback callback = new Callback(call) {};

        try {
            StartAdvertisingOptions options = new StartAdvertisingOptions(call, config);

            implementation.startAdvertising(options, callback);
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    @PluginMethod
    public void stopAdvertising(PluginCall call) {
        Callback callback = new Callback(call) {};

        try {
            implementation.stopAdvertising(callback);
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    /**
     * Discovery
     */

    @PluginMethod
    public void startDiscovery(PluginCall call) {
        Callback callback = new Callback(call) {};

        try {
            StartDiscoveryOptions options = new StartDiscoveryOptions(call, config);

            implementation.startDiscovery(options, callback);
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    @PluginMethod
    public void stopDiscovery(PluginCall call) {
        Callback callback = new Callback(call) {};

        try {
            implementation.stopDiscovery(callback);
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    /**
     * Connection
     */

    @PluginMethod
    public void requestConnection(PluginCall call) {
        Callback callback = new Callback(call) {};

        try {
            RequestConnectionOptions options = new RequestConnectionOptions(call, config);

            implementation.requestConnection(options, callback);
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    @PluginMethod
    public void acceptConnection(PluginCall call) {
        Callback callback = new Callback(call) {};

        try {
            AcceptConnectionOptions options = new AcceptConnectionOptions(call);

            implementation.acceptConnection(options, callback);
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    @PluginMethod
    public void rejectConnection(PluginCall call) {
        Callback callback = new Callback(call) {};

        try {
            RejectConnectionOptions options = new RejectConnectionOptions(call);

            implementation.rejectConnection(options, callback);
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    @PluginMethod
    public void disconnect(PluginCall call) {
        Callback callback = new Callback(call) {};

        try {
            DisconnectOptions options = new DisconnectOptions(call);

            implementation.disconnect(options, callback);
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    /**
     * Payload
     */

    @PluginMethod
    public void sendPayload(PluginCall call) {
        Callback callback = new Callback(call) {};

        try {
            SendPayloadOptions options = new SendPayloadOptions(call);

            implementation.sendPayload(options, callback);
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    @PluginMethod
    public void cancelPayload(PluginCall call) {
        Callback callback = new Callback(call) {};

        try {
            CancelPayloadOptions options = new CancelPayloadOptions(call);

            implementation.cancelPayload(options, callback);
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    /**
     * Status
     */

    @PluginMethod
    public void status(PluginCall call) {
        Callback callback = new Callback(call) {};

        try {
            implementation.status(callback);
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    /**
     * Permissions
     */

    @Override
    @PluginMethod
    public void checkPermissions(PluginCall call) {
        // super.checkPermissions(call);

        Map<String, PermissionState> permissionsResult = getPermissionStates();

        if (permissionsResult.isEmpty()) {
            call.resolve();
        } else {
            List<String> aliases = getAliases();

            JSObject result = new JSObject();

            for (Map.Entry<String, PermissionState> entry : permissionsResult.entrySet()) {
                if (aliases.contains(entry.getKey())) {
                    result.put(entry.getKey(), entry.getValue());
                }
            }

            call.resolve(result);
        }
    }

    private List<String> getAliases() {
        List<String> aliases = new ArrayList<>();

        aliases.add("wifiState");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            aliases.add("wifiNearby");
            aliases.add("bluetoothNearby");
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            aliases.add("bluetoothNearby");
            aliases.add("location");
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            aliases.add("bluetoothLegacy");
            aliases.add("location");
        } else {
            aliases.add("bluetoothLegacy");
            aliases.add("locationCoarse");
        }

        return aliases;
    }

    @Override
    @PluginMethod
    public void requestPermissions(PluginCall call) {
        List<String> aliases = getAliases();

        JSArray permissions = call.getArray("permissions");
        if (permissions != null) {
            try {
                aliases = permissions.toList();
            } catch (JSONException ignored) {}
        }

        requestPermissionForAliases(aliases.toArray(new String[0]), call, "permissionsCallback");
    }

    @PermissionCallback
    private void permissionsCallback(PluginCall call) {
        this.checkPermissions(call);
    }

    /**
     * Configuration
     */

    private NearbyConnectionsConfig getNearbyConnectionsConfig() {
        NearbyConnectionsConfig config = new NearbyConnectionsConfig();

        String endpointName = getConfig().getString("endpointName", null);
        config.setEndpointName(endpointName);

        String serviceID = getConfig().getString("serviceID", null);
        config.setServiceID(serviceID);

        String strategy = getConfig().getString("strategy", null);
        config.setStrategy(strategy);

        Boolean lowPower = getConfig().getBoolean("lowPower", false);
        config.setLowPower(lowPower);

        String connectionType = getConfig().getString("connectionType", null);
        config.setConnectionType(connectionType);

        Boolean autoConnect = getConfig().getBoolean("autoConnect", false);
        config.setAutoConnect(autoConnect);

        String payload = getConfig().getString("payload", null);
        config.setPayload(payload);

        return config;
    }

    /**
     * Called when a remote endpoint is discovered.
     */
    protected void onEndpointFound(Endpoint endpoint) {
        EndpointFoundEvent event = new EndpointFoundEvent(endpoint);

        notifyListeners(ENDPOINT_FOUND_EVENT, event.toJSObject());
    }

    /**
     * Called when a remote endpoint is no longer discoverable.
     */
    protected void onEndpointLost(Endpoint endpoint) {
        EndpointLostEvent event = new EndpointLostEvent(endpoint);

        notifyListeners(ENDPOINT_LOST_EVENT, event.toJSObject());
    }

    /**
     * A basic encrypted channel has been created between you and the endpoint.
     */
    protected void onEndpointInitiated(Endpoint endpoint, Connection connection) {
        EndpointInitiatedEvent event = new EndpointInitiatedEvent(endpoint, connection);

        notifyListeners(ENDPOINT_INITIATED_EVENT, event.toJSObject());
    }

    /**
     * Called after both sides have accepted the connection.
     */
    protected void onEndpointConnected(Endpoint endpoint) {
        EndpointConnectedEvent event = new EndpointConnectedEvent(endpoint);

        notifyListeners(ENDPOINT_CONNECTED_EVENT, event.toJSObject());
    }

    /**
     * Called after one side has rejected the connection.
     */
    protected void onEndpointRejected(Endpoint endpoint) {
        EndpointRejectedEvent event = new EndpointRejectedEvent(endpoint);

        notifyListeners(ENDPOINT_REJECTED_EVENT, event.toJSObject());
    }

    /**
     * Called after the connection has failed.
     */
    protected void onEndpointFailed(Endpoint endpoint, String status) {
        EndpointFailedEvent event = new EndpointFailedEvent(endpoint, status);

        notifyListeners(ENDPOINT_FAILED_EVENT, event.toJSObject());
    }

    /**
     * Called when a remote endpoint is disconnected or has become unreachable.
     */
    protected void onEndpointDisconnected(Endpoint endpoint) {
        EndpointDisconnectedEvent event = new EndpointDisconnectedEvent(endpoint);

        notifyListeners(ENDPOINT_DISCONNECTED_EVENT, event.toJSObject());
    }

    /**
     * Called when a connection is established or if the connection quality improves to a higher connection bandwidth.
     */
    protected void onEndpointBandwidthChanged(Endpoint endpoint, Bandwidth bandwidth) {
        EndpointBandwidthChangedEvent event = new EndpointBandwidthChangedEvent(endpoint, bandwidth);

        notifyListeners(ENDPOINT_BANDWIDTH_CHANGED_EVENT, event.toJSObject());
    }

    /**
     * Called when a Payload is received from a remote endpoint.
     */
    protected void onPayloadReceived(Endpoint endpoint, Payload payload) {
        PayloadReceivedEvent event = new PayloadReceivedEvent(endpoint, payload);

        notifyListeners(PAYLOAD_RECEIVED_EVENT, event.toJSObject());
    }

    /**
     * Called with progress information about an active Payload transfer, either incoming or outgoing.
     */
    protected void onPayloadTransferUpdate(Endpoint endpoint, PayloadTransferUpdate update) {
        PayloadTransferUpdateEvent event = new PayloadTransferUpdateEvent(endpoint, update);

        notifyListeners(PAYLOAD_TRANSFER_UPDATE_EVENT, event.toJSObject());
    }
}
