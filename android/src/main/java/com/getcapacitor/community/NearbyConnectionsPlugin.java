package com.getcapacitor.community;

import android.Manifest;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.community.interfaces.EmptyCallback;

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

    static final String UNKNOWN_STRATEGY = "unknown strategy";

    static final String UNKNOWN_ERROR = "unknown error has occurred";

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
        String name = call.getString("name", null);
        if (name != null) {
            config.setName(name);
        }

        String serviceId = call.getString("serviceId", null);
        if (serviceId != null) {
            config.setServiceId(serviceId);
        }

        String strategy = call.getString("strategy", null);
        if (strategy != null) {
            config.setStrategy(strategy);
        }

        Boolean lowPower = call.getBoolean("lowPower", null);
        if (lowPower != null) {
            config.setLowPower(lowPower);
        }

        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.initialize(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private NearbyConnectionsConfig getNearbyConnectionsConfig() {
        NearbyConnectionsConfig config = new NearbyConnectionsConfig();

        String name = getConfig().getString("name", null);
        config.setName(name);

        String serviceId = getConfig().getString("serviceId", null);
        config.setServiceId(serviceId);

        String strategy = getConfig().getString("strategy", null);
        config.setStrategy(strategy);

        Boolean lowPower = getConfig().getBoolean("lowPower", false);
        config.setLowPower(lowPower);

        return config;
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable JSObject result) {
        if (result == null) {
            resolveCall(call);
        } else {
            call.resolve(result);
        }
    }

    private void resolveCall(@NonNull PluginCall call) {
        call.resolve();
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = UNKNOWN_ERROR;
        }
        Log.e(getLogTag(), message, exception);
        call.reject(message, exception);
    }
}
