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
      endpointName?: string;

      /**
       * An identifier to advertise your app to other endpoints.
       *
       * The `serviceId` value must uniquely identify your app.
       * As a best practice, use the package name of your app (for example, `com.example.myapp`).
       *
       * @since 1.0.0
       * @example "com.example.myapp"
       */
      serviceId?: ServiceID;

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
       * @example true
       */
      lowPower?: boolean;

      /**
       * Sets whether the client should disrupt the current connection to optimize the transfer or not.
       *
       * @since 1.0.0
       * @example ConnectionType.BALANCED
       */
      connectionType?: ConnectionType;

      /**
       * Automatically accept the connection on both sides.
       *
       * @since 1.0.0
       * @example true
       */
      autoConnect?: boolean;

      /**
       * What payload to send when automatically connecting to each other.
       *
       * @since 1.0.0
       * @example "Hello, World!"
       */
      payload?: string;
    };
  }
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
   * Starts advertising the local endpoint.
   *
   * @since 1.0.0
   */
  startAdvertising(options?: StartAdvertisingOptions): Promise<void>;
  /**
   * Stops advertising the local endpoint.
   *
   * @since 1.0.0
   */
  stopAdvertising(): Promise<void>;

  /**
   * Starts discovery for remote endpoints.
   *
   * @since 1.0.0
   */
  startDiscovery(options?: StartDiscoveryOptions): Promise<void>;
  /**
   * Stops discovery for remote endpoints.
   *
   * @since 1.0.0
   */
  stopDiscovery(): Promise<void>;

  /**
   * Sends a request to connect to a remote endpoint.
   *
   * @since 1.0.0
   */
  requestConnection(options: RequestConnectionOptions): Promise<void>;
  /**
   * Accepts a connection to a remote endpoint.
   *
   * @since 1.0.0
   */
  acceptConnection(options: AcceptConnectionOptions): Promise<void>;
  /**
   * Rejects a connection to a remote endpoint.
   *
   * @since 1.0.0
   */
  rejectConnection(options: RejectConnectionOptions): Promise<void>;
  /**
   * Disconnects from a remote endpoint.
   * `Payload`s can no longer be sent to or received from the endpoint after this method is called.
   */
  disconnectFromEndpoint(options: DisconnectFromEndpointOptions): Promise<void>;

  /**
   * Sends a `Payload` to a remote endpoint.
   *
   * @since 1.0.0
   */
  sendPayload(options: SendPayloadOptions): Promise<void>;
  /**
   * Cancels a `Payload` currently in-flight to or from remote endpoint(s).
   *
   * @since 1.0.0
   */
  cancelPayload(options: CancelPayloadOptions): Promise<void>;

  /**
   * Returns advertising and discovering status, and discovered endpoints.
   *
   * @since 1.0.0
   */
  status(): Promise<StatusResult>;

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

  /**
   * A basic encrypted channel has been created between you and the endpoint.
   * Both sides are now asked if they wish to accept or reject the connection before any data can be sent over this channel.
   *
   * @since 1.0.0
   */
  addListener(eventName: 'onEndpointInitiated', listenerFunc: EndpointInitiatedCallback): Promise<PluginListenerHandle>;
  /**
   * Called after both sides have accepted the connection.
   * Both sides may now send Payloads to each other.
   *
   * @since 1.0.0
   */
  addListener(eventName: 'onEndpointConnected', listenerFunc: EndpointConnectedCallback): Promise<PluginListenerHandle>;
  /**
   * Called when either side rejected the connection.
   * Payloads can not be exchaged.
   */
  addListener(eventName: 'onEndpointRejected', listenerFunc: EndpointRejectedCallback): Promise<PluginListenerHandle>;
  addListener(eventName: 'onEndpointFailed', listenerFunc: EndpointFailedCallback): Promise<PluginListenerHandle>;
  /**
   * Called when a remote endpoint is disconnected or has become unreachable.
   *
   * @since 1.0.0
   */
  addListener(
    eventName: 'onEndpointDisconnected',
    listenerFunc: EndpointDisconnectedCallback,
  ): Promise<PluginListenerHandle>;

  /**
   * Called when a connection is established or if the connection quality improves to a higher connection bandwidth.
   *
   * @since 1.0.0
   */
  addListener(
    eventName: 'onEndpointBandwidthChanged',
    listenerFunc: EndpointBandwidthChangedCallback,
  ): Promise<PluginListenerHandle>;

  /**
   * Called when a `Payload` is received from a remote endpoint.
   *
   * @since 1.0.0
   */
  addListener(eventName: 'onPayloadReceived', listenerFunc: PayloadReceivedCallback): Promise<PluginListenerHandle>;
  /**
   * Called with progress information about an active `Payload` transfer, either incoming or outgoing.
   *
   * @since 1.0.0
   */
  addListener(
    eventName: 'onPayloadTransferUpdate',
    listenerFunc: PayloadTransferUpdateCallback,
  ): Promise<PluginListenerHandle>;
}

/**
 * This specifies the maximum allowed size of `Payload`s sent.
 *
 * @since 1.0.0
 * @link https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/ConnectionsClient#MAX_BYTES_DATA_SIZE
 */
export const MAX_BYTES_DATA_SIZE = 1047552;

/**
 * Used to represent a service identifier.
 *
 * @since 1.0.0
 */
export type ServiceID = string;

/**
 * Used to represent an enpoint.
 *
 * @since 1.0.0
 */
export type EndpointID = string;

/**
 * Used to represent a payload.
 *
 * @since 1.0.0
 */
export type PayloadID = number;

/**
 * Indicates the current status of the connection between a local and remote endpoint.
 *
 * @since 1.0.0
 */
export enum ConnectionState {
  /**
   * A connection to the remote endpoint is currently being established.
   *
   * @since 1.0.0
   */
  CONNECTING = 'connecting',
  /**
   * The local endpoint is currently connected to the remote endpoint.
   *
   * @since 1.0.0
   */
  CONNECTED = 'connected',
  /**
   * The remote endpoint is no longer connected.
   *
   * @since 1.0.0
   */
  DISCONNECTED = 'disconnected',
  /**
   * The local or remote endpoint has rejected the connection request.
   *
   * @since 1.0.0
   */
  REJECTED = 'rejected',
}

export interface Endpoint {
  /**
   * The ID of the remote endpoint that was discovered.
   *
   * @since 1.0.0
   */
  endpointId: EndpointID;

  /**
   * A human readable name for this endpoint, to appear on the remote device.
   *
   * @since 1.0.0
   */
  endpointName?: string;
}

/**
 * Bandwidth quality.
 *
 * Only available on Android.
 *
 * @since 1.0.0
 * @link https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/BandwidthInfo.Quality
 */
export enum Quality {
  UNKNOWN = 'unknown',

  /**
   * The connection quality is poor (5KBps) and is not suitable for sending files.
   * It's recommended you wait until the connection quality improves.
   *
   * @since 1.0.0
   */
  LOW = 'low',

  /**
   * The connection quality is ok (60~200KBps) and is suitable for sending small files.
   * For large files, it's recommended you wait until the connection quality improves.
   *
   * @since 1.0.0
   */
  MEDIUM = 'medium',

  /**
   * The connection quality is good or great (6MBps~60MBps) and files can readily be sent.
   * The connection quality cannot improve further but may still be impacted by environment or hardware limitations.
   *
   * @since 1.0.0
   */
  HIGH = 'high',
}

export enum Status {
  /**
   * The operation was successful.
   */
  OK = 'OK',
  /**
   * The operation failed, without any more information.
   */
  ERROR = 'ERROR',

  NETWORK_NOT_CONNECTED = 'NETWORK_NOT_CONNECTED', // 8000

  /**
   * The app is already advertising; call `stopAdvertising()` before trying to advertise again.
   */
  ALREADY_ADVERTISING = 'ALREADY_ADVERTISING', // 8001
  /**
   * The app is already discovering the specified application ID; call `stopDiscovery()` before trying to advertise again.
   */
  ALREADY_DISCOVERING = 'ALREADY_DISCOVERING', // 8002
  /**
   * The app is already connected to the specified endpoint.
   * Multiple connections to a remote endpoint cannot be maintained simultaneously.
   */
  ALREADY_CONNECTED_TO_ENDPOINT = 'ALREADY_CONNECTED_TO_ENDPOINT', // 8003
  /**
   * The remote endpoint rejected the connection request.
   */
  CONNECTION_REJECTED = 'CONNECTION_REJECTED', // 8004
  /**
   * The remote endpoint is not connected; messages cannot be sent to it.
   */
  NOT_CONNECTED_TO_ENDPOINT = 'NOT_CONNECTED_TO_ENDPOINT', // 8005
  /**
   * There was an error trying to use the phone's Bluetooth/WiFi/NFC capabilities.
   */
  RADIO_ERROR = 'RADIO_ERROR', // 8007
  /**
   * The app already has active operations (advertising, discovering, or connected to other devices) with another `Strategy`.
   * Stop these operations on the current `Strategy` before trying to advertise or discover with a new `Strategy`.
   */
  ALREADY_HAVE_ACTIVE_STRATEGY = 'ALREADY_HAVE_ACTIVE_STRATEGY', // 8008
  /**
   * The app called an API method out of order (i.e. another method is expected to be called first).
   */
  OUT_OF_ORDER_API_CALL = 'OUT_OF_ORDER_API_CALL', // 8009

  UNSUPPORTED_PAYLOAD_TYPE_FOR_STRATEGY = 'UNSUPPORTED_PAYLOAD_TYPE_FOR_STRATEGY', // 8010

  /**
   * An attempt to interact with a remote endpoint failed because it's unknown to us -- it's either an endpoint that was never discovered, or an endpoint that never connected to us (both of which are indicative of bad input from the client app).
   */
  ENDPOINT_UNKNOWN = 'ENDPOINT_UNKNOWN', // 8011
  /**
   * An attempt to read from/write to a connected remote endpoint failed.
   */
  ENDPOINT_IO_ERROR = 'ENDPOINT_IO_ERROR', // 8012
  /**
   * An attempt to read/write data for a `Payload` of type `FILE` or `STREAM` failed.
   */
  PAYLOAD_IO_ERROR = 'PAYLOAD_IO_ERROR', // 8013

  PAYLOAD_UNKNOWN = 'PAYLOAD_UNKNOWN', // 8014

  ALREADY_LISTENING = 'ALREADY_LISTENING', // 8015

  AUTH_ERROR = 'AUTH_ERROR', // 8016

  /**
   * This error indicates that Nearby Connections is already in use by some app, and thus is currently unavailable to the caller.
   */
  ALREADY_IN_USE = 'ALREADY_IN_USE', // 8050
}

/**
 * Information about a connection's bandwidth.
 *
 * @since 1.0.0
 */
export interface BandwidthInfo {
  /**
   * The connection's current quality.
   *
   * @since 1.0.0
   */
  readonly quality: Quality;
}

/**
 * Information about a connection that is being initiated.
 *
 * @since 1.0.0
 * @link https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/ConnectionInfo
 */
export interface ConnectionInfo {
  /**
   * A 4 digit authentication token that has been given to both devices.
   *
   * @since 1.0.0
   */
  readonly authenticationToken: string;
  /**
   * An authentication status for Authentication handshaking result after uKey2 verification.
   *
   * @since 1.0.0
   */
  readonly authenticationStatus: number;
  /**
   * `True` if the connection request was initiated from a remote device.
   * `False` if this device was the one to try and initiate the connection.
   *
   * @since 1.0.0
   */
  readonly isIncomingConnection: boolean;
}

// Endpoint Discovery

/**
 * Called when a remote endpoint is discovered.
 */
export type EndpointFoundCallback = (_: Endpoint) => void;
/**
 * Called when a remote endpoint is no longer discoverable.
 */
export type EndpointLostCallback = (_: Endpoint) => void;

// Connection Lifecycle

/**
 * A basic encrypted channel has been created between you and the endpoint.
 * Both sides are now asked if they wish to accept or reject the connection before any data can be sent over this channel.
 */
export type EndpointInitiatedCallback = (_: Endpoint & ConnectionInfo) => void;
/**
 * Called after both sides have accepted the connection.
 * Both sides may now send Payloads to each other.
 */
export type EndpointConnectedCallback = (_: Endpoint) => void;
/**
 * Called when either side rejected the connection.
 * Payloads can not be exchaged.
 */
export type EndpointRejectedCallback = (_: Endpoint) => void;
export type EndpointFailedCallback = (_: Endpoint & Status) => void;
/**
 * Called when a remote endpoint is disconnected or has become unreachable.
 * At this point service (re-)discovery may start again.
 */
export type EndpointDisconnectedCallback = (_: Endpoint) => void;

/**
 * Called when a connection is established or if the connection quality improves to a higher connection bandwidth.
 */
export type EndpointBandwidthChangedCallback = (_: Endpoint & BandwidthInfo) => void;

// Payload

/**
 * Called when a payload is received from a remote endpoint. Depending on the type of the payload,
 * all of the data may or may not have been received at the time of this call.
 */
export type PayloadReceivedCallback = (_: Endpoint & Payload) => void;
/**
 * Called with progress information about an active payload transfer, either incoming or outgoing.
 */
export type PayloadTransferUpdateCallback = (_: Endpoint & PayloadTransferUpdate) => void;

/**
 * A Payload sent between devices.
 *
 * @since 1.0.0
 * @link https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/Payload
 */
export interface Payload {
  /**
   * A unique identifier for this payload.
   *
   * @since 1.0.0
   */
  readonly payloadId: PayloadID;

  /**
   * The type of this payload.
   *
   * @since 1.0.0
   * @example PayloadType.BYTES
   */
  readonly payloadType: PayloadType;

  /**
   * Payload data.
   *
   * @since 1.0.0
   * @example "Hello, World!"
   */
  readonly payload: string;
}

/**
 * The type of this payload.
 *
 * @since 1.0.0
 * @link https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/Payload.Type
 */
export enum PayloadType {
  UNKNOWN = 'unknown',

  /**
   * A Payload consisting of a single byte array.
   *
   * @since 1.0.0
   */
  BYTES = 'bytes',
  /**
   * A Payload representing a file on the device.
   *
   * @since 1.0.0
   */
  FILE = 'file',
  /**
   * A Payload representing a real-time stream of data; e.g. generated data for which the total size is not known ahead of time.
   *
   * @since 1.0.0
   */
  STREAM = 'stream',
}

/**
 * The status of the payload transfer at the time of this update.
 *
 * @since 1.0.0
 * @link https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/PayloadTransferUpdate.Status
 */
export enum PayloadTransferUpdateStatus {
  /**
   * The remote endpoint has successfully received the full transfer.
   *
   * @since 1.0.0
   */
  SUCCESS = 'success',
  /**
   * The remote endpoint failed to receive the transfer.
   *
   * @since 1.0.0
   */
  FAILURE = 'failure',
  /**
   * The the transfer is currently in progress with an associated progress value.
   *
   * @since 1.0.0
   */
  IN_PROGRESS = 'inProgress',
  /**
   * Either the local or remote endpoint has canceled the transfer.
   *
   * @since 1.0.0
   */
  CANCELED = 'canceled',
}

/**
 * Describes the status for an active `Payload` transfer, either incoming or outgoing.
 *
 * @since 1.0.0
 * @link https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/PayloadTransferUpdate
 */
export interface PayloadTransferUpdate {
  /**
   * The payload identifier.
   *
   * @since 1.0.0
   */
  readonly payloadId: PayloadID;

  /**
   * The status of the payload.
   *
   * @since 1.0.0
   */
  readonly status: PayloadTransferUpdateStatus;

  /**
   * The number of bytes transferred so far.
   *
   * @since 1.0.0
   */
  readonly bytesTransferred: number;

  /**
   * The total number of bytes in the payload.
   *
   * @since 1.0.0
   */
  readonly totalBytes: number;
}

export interface StatusResult {
  isAdvertising: boolean;
  isDiscovering: boolean;
}

/**
 * The `Strategy` to be used when discovering or advertising to Nearby devices.
 *
 * The `Strategy` defines
 *  1. the connectivity requirements for the device, and
 *  2. the topology constraints of the connection.
 *
 * Indicates the connection strategy and restrictions for advertisement or discovery.
 *
 * - Note: Only a subset of mediums are supported by certain strategies as depicted in the table
 *   below. Generally, more restrictive strategies have better medium support.
 *
 * |              | BT Classic | BLE | LAN | Aware | NFC | USB | Hotspot | Direct | 5GHz WiFi |
 * |--------------|:----------:|:---:|:---:|:-----:|:---:|:---:|:-------:|:------:|:---------:|
 * | cluster      | ✓          | ✓   | ✓   | ✓     | ✓   | ✓   | ×       | ×      | ×         |
 * | star         | ✓          | ✓   | ✓   | ✓     | ✓   | ✓   | ✓       | ✓      | ×         |
 * | pointToPoint | ✓          | ✓   | ✓   | ✓     | ✓   | ✓   | ✓       | ✓      | ✓         |
 *
 * - Important: In order to discover another endpoint, the remote endpoint must be advertising
 *   using the same strategy.
 *
 * @since 1.0.0
 * @link https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/Strategy
 */
export enum Strategy {
  /**
   * Peer-to-peer strategy that supports an M-to-N, or cluster-shaped, connection topology.
   *
   * No restrictions on the amount of incoming and outgoing connections.
   *
   * @since 1.0.0
   * @link https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/Strategy#P2P_CLUSTER
   */
  CLUSTER = 'cluster',
  /**
   * Peer-to-peer strategy that supports a 1-to-N, or star-shaped, connection topology.
   *
   * Restricts the discoverer to a single connection, while advertisers have no restrictions on amount of connections.
   *
   * @since 1.0.0
   * @link https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/Strategy#P2P_STAR
   */
  STAR = 'star',
  /**
   * Peer-to-peer strategy that supports a 1-to-1 connection topology.
   *
   * Restricts both adverisers and discoverers to a single connection.
   *
   * @since 1.0.0
   * @link https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/Strategy#P2P_POINT_TO_POINT
   */
  POINT_TO_POINT = 'pointToPoint',
}

export interface InitializeOptions {
  /**
   * A human readable name for this endpoint, to appear on the remote device.
   *
   * @since 1.0.0
   * @example "My App"
   */
  endpointName?: string;

  /**
   * An identifier to advertise your app to other endpoints.
   *
   * The `serviceId` value must uniquely identify your app.
   * As a best practice, use the package name of your app (for example, `com.example.myapp`).
   *
   * @since 1.0.0
   * @example "com.example.myapp"
   */
  serviceId?: ServiceID;

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
   * @default false
   * @example true
   */
  lowPower?: boolean;

  /**
   * Automatically accept the connection on both sides.
   *
   * @since 1.0.0
   * @example true
   */
  autoConnect?: boolean;

  /**
   * What payload to send when automatically connecting to each other.
   *
   * @since 1.0.0
   * @example "Hello, World!"
   */
  payload?: string;
}

export interface StartAdvertisingOptions {
  /**
   * A human readable name for this endpoint, to appear on the remote device.
   *
   * @since 1.0.0
   */
  endpointName?: string;

  /**
   * Sets whether the client should disrupt the current connection to optimize the transfer or not.
   *
   * @since 1.0.0
   * @default ConnectionType.BALANCED
   * @example ConnectionType.NON_DISRUPTIVE
   */
  connectionType?: ConnectionType;

  /**
   * Sets whether low power should be used.
   *
   * @since 1.0.0
   * @default false
   * @example true
   */
  lowPower?: boolean;
}

export interface StartDiscoveryOptions {
  /**
   * Sets whether low power should be used.
   *
   * @since 1.0.0
   * @default false
   * @example true
   */
  lowPower?: boolean;
}

export interface RequestConnectionOptions {
  /**
   * The identifier for the remote endpoint to which a connection request will be sent.
   *
   * @since 1.0.0
   */
  endpointId: EndpointID;

  /**
   * A human readable name for this endpoint, to appear on the remote device.
   *
   * @since 1.0.0
   */
  endpointName?: string;
}

export interface AcceptConnectionOptions {
  /**
   * The identifier for the remote endpoint.
   *
   * @since 1.0.0
   */
  endpointId: EndpointID;
}

export interface RejectConnectionOptions {
  /**
   * The identifier for the remote endpoint.
   *
   * @since 1.0.0
   */
  endpointId: EndpointID;
}

export interface SendPayloadOptions {
  /**
   * The identifier(s) for the remote endpoint(s) to which the payload should be sent.
   *
   * @since 1.0.0
   */
  endpointId: EndpointID | EndpointID[];

  /**
   * The `Payload` to be sent.
   *
   * @since 1.0.0
   * @type Base64 encoded string
   */
  payload: string;
}

export interface CancelPayloadOptions {
  /**
   * The identifier for the Payload to be canceled.
   *
   * @since 1.0.0
   */
  payloadId: PayloadID;
}

export interface DisconnectFromEndpointOptions {
  /**
   * The identifier for the remote endpoint to disconnect from.
   *
   * @since 1.0.0
   */
  endpointId: EndpointID;
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
  BALANCED = 'balanced',
  /**
   * Nearby Connections will change the device's Wi-Fi or Bluetooth status to enhance throughput.
   *
   * @since 1.0.0
   */
  DISRUPTIVE = 'disruptive',
  /**
   * Nearby Connections should not change the device's Wi-Fi or Bluetooth status.
   *
   * @since 1.0.0
   */
  NON_DISRUPTIVE = 'nonDisruptive',
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
