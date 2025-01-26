/// <reference types="@capacitor/cli" />

import type { PermissionState, PluginListenerHandle } from '@capacitor/core';

export interface PermissionStatus {
  /**
   * `NEARBY_WIFI_DEVICES` Required to be able to advertise and connect to nearby devices via Wi-Fi.
   *
   * @since 1.0.0
   */
  wifiNearby: PermissionState;
  /**
   * `ACCESS_WIFI_STATE` Allows applications to access information about Wi-Fi networks.
   * `CHANGE_WIFI_STATE` Allows applications to change Wi-Fi connectivity state.
   *
   * @since 1.0.0
   */
  wifiState: PermissionState;
  /**
   * `BLUETOOTH_ADVERTISE` Required to be able to advertise to nearby Bluetooth devices.
   * `BLUETOOTH_CONNECT` Required to be able to connect to paired Bluetooth devices.
   * `BLUETOOTH_SCAN` Required to be able to discover and pair nearby Bluetooth devices.
   *
   * @since 1.0.0
   */
  bluetoothNearby: PermissionState;
  /**
   * `BLUETOOTH` Allows applications to connect to paired bluetooth devices.
   * `BLUETOOTH_ADMIN` Allows applications to discover and pair bluetooth devices.
   *
   * @since 1.0.0
   */
  bluetoothLegacy: PermissionState;
  /**
   * `ACCESS_FINE_LOCATION` Allows an app to access precise location.
   *
   * @since 1.0.0
   */
  location: PermissionState;
  /**
   * `ACCESS_COARSE_LOCATION` Allows an app to access approximate location.
   *
   * @since 1.0.0
   */
  locationCoarse: PermissionState;
}

export type NearbyConnectionPermissionType =
  | 'wifiNearby'
  | 'wifiState'
  | 'bluetoothNearby'
  | 'bluetoothLegacy'
  | 'location'
  | 'locationCoarse';

export interface NearbyConnectionsPermissions {
  permissions: NearbyConnectionPermissionType[];
}

declare module '@capacitor/cli' {
  export interface PluginsConfig {
    /**
     * These configuration values are available:
     */
    NearbyConnections?: {
      /**
       * A human readable name for this endpoint, to appear on the remote device.
       *
       * @since 1.0.0
       * @example "My App"
       */
      name?: string;

      /**
       * An identifier to advertise your app to other endpoints.
       *
       * The `serviceId` value must uniquely identify your app.
       * As a best practice, use the package name of your app (for example, `com.example.myapp`).
       *
       * @since 1.0.0
       * @example "com.example.myapp"
       */
      serviceId?: string;

      /**
       * Sets the `Strategy` to be used when discovering or advertising to Nearby devices.
       *
       * @since 1.0.0
       * @example Strategy.STAR
       */
      strategy?: Strategy;

      /**
       * Sets whether low power should be used.
       *
       * @since 1.0.0
       * @example false
       */
      lowPower?: boolean;
    };
  }
}

export interface Endpoint {
  /**
   * The ID of the remote endpoint that was discovered.
   *
   * @since 1.0.0
   */
  id: string;

  /**
   * A human readable name for this endpoint, to appear on the remote device.
   *
   * @since 1.0.0
   */
  name?: string;

  /**
   * Identifing information about this endpoint (eg. name, device type), to appear on the remote device.
   *
   * @since 1.0.0
   */
  endpointInfo?: Base64;
}

export type EndpointFoundCallback = (_: Endpoint) => void;
export type EndpointLostCallback = (_: Endpoint) => void;

export interface Status {
  isPublishing: boolean;
  isSubscribing: boolean;

  ids: string[];
}

/**
 * The `Strategy` to be used when discovering or advertising to Nearby devices.
 *
 * The `Strategy` defines
 *  1. the connectivity requirements for the device, and
 *  2. the topology constraints of the connection.
 *
 * @since 1.0.0
 * @link https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/Strategy
 */
export enum Strategy {
  /**
   * Peer-to-peer strategy that supports an M-to-N, or cluster-shaped, connection topology.
   *
   * @since 1.0.0
   * @link https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/Strategy#P2P_CLUSTER
   */
  CLUSTER = 'cluster',
  /**
   * Peer-to-peer strategy that supports a 1-to-N, or star-shaped, connection topology.
   *
   * @since 1.0.0
   * @link https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/Strategy#P2P_STAR
   */
  STAR = 'star',
  /**
   * Peer-to-peer strategy that supports a 1-to-1 connection topology.
   *
   * @since 1.0.0
   * @link https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/Strategy#P2P_POINT_TO_POINT
   */
  POINT_TO_POINT = 'point-to-point',
}

export interface InitializeOptions {
  /**
   * A human readable name for this endpoint, to appear on the remote device.
   *
   * @since 1.0.0
   * @example "My App"
   */
  name?: string;

  /**
   * An identifier to advertise your app to other endpoints.
   *
   * The `serviceId` value must uniquely identify your app.
   * As a best practice, use the package name of your app (for example, `com.example.myapp`).
   *
   * @since 1.0.0
   * @example "com.example.myapp"
   */
  serviceId?: string;

  /**
   * Sets the `Strategy` to be used when discovering or advertising to Nearby devices.
   *
   * @since 1.0.0
   * @example: Strategy.STAR
   */
  strategy?: Strategy;

  /**
   * Sets whether low power should be used.
   *
   * @since 1.0.0
   * @default false
   * @example false
   */
  lowPower?: boolean;
}

export type Base64 = string;

export interface PublishOptions {
  /**
   * Sets whether the client should disrupt the current connection to optimize the transfer or not.
   *
   * @since 1.0.0
   * @default ConnectionType.BALANCED
   * @example: ConnectionType.BALANCED
   */
  connectionType?: ConnectionType;

  /**
   * A human readable name for this endpoint, to appear on the remote device.
   *
   * @since 1.0.0
   */
  name?: string;

  /**
   * Identifing information about this endpoint (eg. name, device type), to appear on the remote device.
   *
   * @since 1.0.0
   */
  endpointInfo?: Base64;
}

/**
 * The connection type which Nearby Connection used to establish a connection.
 *
 * @since 1.0.0
 * @link https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/ConnectionType
 */
export enum ConnectionType {
  /**
   * Nearby Connections will change the device's Wi-Fi or Bluetooth status only if necessary.
   *
   * @since 1.0.0
   */
  BALANCED = 0,
  /**
   * Nearby Connections will change the device's Wi-Fi or Bluetooth status to enhance throughput.
   *
   * @since 1.0.0
   */
  DISRUPTIVE = 1,
  /**
   * Nearby Connections should not change the device's Wi-Fi or Bluetooth status.
   *
   * @since 1.0.0
   */
  NON_DISRUPTIVE = 2,
}

export enum BluetoothState {
  /**
   * The manager’s state is unknown.
   *
   * @since 1.0.0
   */
  UNKNOWN = 'unknown',
  /**
   * A state that indicates the connection with the system service was momentarily lost.
   *
   * @since 1.0.0
   */
  RESETTING = 'resetting',
  /**
   * A state that indicates this device doesn’t support the Bluetooth low energy central or client role.
   *
   * @since 1.0.0
   */
  UNSUPPORTED = 'unsupported',
  /**
   * A state that indicates the application isn’t authorized to use the Bluetooth low energy role.
   *
   * @since 1.0.0
   */
  UNAUTHORIZED = 'unauthorized',
  /**
   * A state that indicates Bluetooth is currently powered off.
   *
   * @since 1.0.0
   */
  POWERED_OFF = 'poweredOff',
  /**
   * A state that indicates Bluetooth is currently powered on and available to use.
   *
   * @since 1.0.0
   */
  POWERED_ON = 'poweredOn',
}

export interface NearbyConnectionsPlugin {
  /**
   * Initializes Nearby Connections for advertising and discovering of endpoints.
   *
   * @since 1.0.0
   */
  initialize(options?: InitializeOptions): Promise<void>;
  /**
   * Stops and resets advertising and discovering of endpoints.
   *
   * @since 1.0.0
   */
  reset(): Promise<void>;

  /**
   * Starts advertising an endpoint for a local app.
   *
   * @since 1.0.0
   */
  publish(options?: PublishOptions): Promise<void>;
  /**
   * Stops advertising a local endpoint.
   *
   * @since 1.0.0
   */
  unpublish(): Promise<void>;

  /**
   * Starts discovery for remote endpoints with the specified service ID.
   *
   * @since 1.0.0
   */
  subscribe(): Promise<void>;
  /**
   * Stops discovery for remote endpoints.
   *
   * @since 1.0.0
   */
  unsubscribe(): Promise<void>;

  /**
   * Returns advertising and discovering status, and discovered endpoints.
   *
   * @since 1.0.0
   */
  status(): Promise<Status>;

  /**
   * Check for the appropriate permissions to use Nearby.
   *
   * @since 1.0.0
   */
  checkPermissions(): Promise<PermissionStatus>;
  /**
   * Request the appropriate permissions to use Nearby.
   *
   * @since 1.0.0
   */
  requestPermissions(permissions?: NearbyConnectionsPermissions): Promise<PermissionStatus>;

  /**
   * Called when permission is granted or revoked for this app to use Nearby.
   *
   * @since 1.0.0
   */
  addListener(
    eventName: 'onPermissionChanged',
    listenerFunc: (granted: boolean) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when state of Bluetooth has changed.
   *
   * @since 1.0.0
   */
  addListener(
    eventName: 'onBluetoothStateChanged',
    listenerFunc: (state: BluetoothState) => void,
  ): Promise<PluginListenerHandle>;

  /**
   * Called when a remote endpoint is discovered.
   *
   * @since 1.0.0
   */
  addListener(eventName: 'onEndpointFound', listenerFunc: EndpointFoundCallback): Promise<PluginListenerHandle>;
  /**
   * Called when a remote endpoint is no longer discoverable.
   *
   * @since 1.0.0
   */
  addListener(eventName: 'onEndpointLost', listenerFunc: EndpointLostCallback): Promise<PluginListenerHandle>;
}
