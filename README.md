# @capacitor-trancee/nearby-connections

Uses Nearby Connections peer-to-peer networking API that allows apps to easily discover, connect to, and exchange data with nearby devices in real-time, regardless of network connectivity

## Install

```bash
npm install @capacitor-trancee/nearby-connections
npx cap sync
```

## iOS

Nearby Connections needs access to both Bluetooth and the local network to advertise, discover, make connections, and transfer data. Provide a usage description for each resource needed, in your app’s `Info.plist`. If you don’t, attempts to access the resource will fail, and might even cause your app to crash.

Required usage description keys:

- `NSBluetoothAlwaysUsageDescription`
- `NSLocalNetworkUsageDescription`

In addition to usage description keys, an `NSBonjourServices` key with a list of the service types that will be browsed by the app, will also need to be added. The only service type that must be added can be generated by taking the first 12 bytes of the `SHA-256` hash of your app's service ID.

Read about [Configuring `Info.plist`](https://capacitorjs.com/docs/ios/configuration#configuring-infoplist) in the [iOS Guide](https://capacitorjs.com/docs/ios) for more information on setting iOS permissions in Xcode

## Android

Before using Nearby Connections, your app must request the appropriate permissions. Add the following permissions to your `AndroidManifest.xml`:

```xml
    <!-- https://developers.google.com/nearby/connections/android/get-started#request_permissions -->

    <!-- Required for Nearby Connections -->
    <uses-permission 
      android:name="android.permission.ACCESS_WIFI_STATE" 
      android:maxSdkVersion="31" />
    <uses-permission 
      android:name="android.permission.CHANGE_WIFI_STATE" 
      android:maxSdkVersion="31" />

    <uses-permission 
      android:name="android.permission.BLUETOOTH" 
      android:maxSdkVersion="30" />
    <uses-permission 
      android:name="android.permission.BLUETOOTH_ADMIN" 
      android:maxSdkVersion="30" />

    <uses-permission 
      android:name="android.permission.ACCESS_COARSE_LOCATION" 
      android:maxSdkVersion="28" />
    <uses-permission 
      android:name="android.permission.ACCESS_FINE_LOCATION" 
      android:minSdkVersion="29" 
      android:maxSdkVersion="32" />

    <uses-permission 
      android:name="android.permission.BLUETOOTH_ADVERTISE"
      android:minSdkVersion="31" />
    <uses-permission 
      android:name="android.permission.BLUETOOTH_CONNECT" 
      android:minSdkVersion="31" />
    <uses-permission 
      android:name="android.permission.BLUETOOTH_SCAN" 
      android:usesPermissionFlags="neverForLocation" 
      android:minSdkVersion="31" 
      tools:targetApi="s" />

    <uses-permission 
      android:name="android.permission.NEARBY_WIFI_DEVICES" 
      android:usesPermissionFlags="neverForLocation" 
      android:minSdkVersion="32" 
      tools:targetApi="s" />

    <!-- Optional: only required for FILE payloads -->
    <uses-permission 
      android:name="android.permission.READ_EXTERNAL_STORAGE" 
      tools:ignore="ScopedStorage" />

    <uses-feature
      android:name="android.hardware.bluetooth_le"
      android:required="false" />
```

If the user does not grant all required permissions, the Nearby Connections API will refuse to allow your app to start advertising or discovering.

Read about [Setting Permissions](https://capacitorjs.com/docs/android/configuration#setting-permissions) in the [Android Guide](https://capacitorjs.com/docs/android) for more information on setting Android permissions.

### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `playServicesNearbyVersion` version of `com.google.android.gms:play-services-nearby` (default: `19.3.0`)

- `playServicesLocationVersion` version of `com.google.android.gms:play-services-location` (default: `21.3.0`)

- `protoliteWellKnownTypesVersion` version of `com.google.firebase:protolite-well-known-types` (default: `18.0.0`)

## Example

```typescript
```

## Configuration

<docgen-config>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

These configuration values are available:

| Prop            | Type                                          | Description                                                                                                                                                                                             | Since |
| --------------- | --------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`name`**      | <code>string</code>                           | A human readable name for this endpoint, to appear on the remote device.                                                                                                                                | 1.0.0 |
| **`serviceId`** | <code>string</code>                           | An identifier to advertise your app to other endpoints. The `serviceId` value must uniquely identify your app. As a best practice, use the package name of your app (for example, `com.example.myapp`). | 1.0.0 |
| **`strategy`**  | <code><a href="#strategy">Strategy</a></code> | Sets the <a href="#strategy">`Strategy`</a> to be used when discovering or advertising to Nearby devices.                                                                                               | 1.0.0 |
| **`lowPower`**  | <code>boolean</code>                          | Sets whether low power should be used.                                                                                                                                                                  | 1.0.0 |

### Examples

In `capacitor.config.json`:

```json
{
  "plugins": {
    "NearbyConnections": {
      "name": "My App",
      "serviceId": "com.example.myapp",
      "strategy": Strategy.STAR,
      "lowPower": false
    }
  }
}
```

In `capacitor.config.ts`:

```ts
/// <reference types="@capacitor-trancee/nearby-connections" />

import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    NearbyConnections: {
      name: "My App",
      serviceId: "com.example.myapp",
      strategy: Strategy.STAR,
      lowPower: false,
    },
  },
};

export default config;
```

</docgen-config>

## API

<docgen-index>

* [`initialize(...)`](#initialize)
* [`reset()`](#reset)
* [`publish(...)`](#publish)
* [`unpublish()`](#unpublish)
* [`subscribe()`](#subscribe)
* [`unsubscribe()`](#unsubscribe)
* [`status()`](#status)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions(...)`](#requestpermissions)
* [`addListener('onPermissionChanged', ...)`](#addlisteneronpermissionchanged-)
* [`addListener('onBluetoothStateChanged', ...)`](#addlisteneronbluetoothstatechanged-)
* [`addListener('onEndpointFound', ...)`](#addlisteneronendpointfound-)
* [`addListener('onEndpointLost', ...)`](#addlisteneronendpointlost-)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### initialize(...)

```typescript
initialize(options?: InitializeOptions | undefined) => Promise<void>
```

Initializes Nearby Connections for advertising and discovering of endpoints.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#initializeoptions">InitializeOptions</a></code> |

**Since:** 1.0.0

--------------------


### reset()

```typescript
reset() => Promise<void>
```

Stops and resets advertising and discovering of endpoints.

**Since:** 1.0.0

--------------------


### publish(...)

```typescript
publish(options?: PublishOptions | undefined) => Promise<void>
```

Starts advertising an endpoint for a local app.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#publishoptions">PublishOptions</a></code> |

**Since:** 1.0.0

--------------------


### unpublish()

```typescript
unpublish() => Promise<void>
```

Stops advertising a local endpoint.

**Since:** 1.0.0

--------------------


### subscribe()

```typescript
subscribe() => Promise<void>
```

Starts discovery for remote endpoints with the specified service ID.

**Since:** 1.0.0

--------------------


### unsubscribe()

```typescript
unsubscribe() => Promise<void>
```

Stops discovery for remote endpoints.

**Since:** 1.0.0

--------------------


### status()

```typescript
status() => Promise<Status>
```

Returns advertising and discovering status, and discovered endpoints.

**Returns:** <code>Promise&lt;<a href="#status">Status</a>&gt;</code>

**Since:** 1.0.0

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check for the appropriate permissions to use Nearby.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 1.0.0

--------------------


### requestPermissions(...)

```typescript
requestPermissions(permissions?: NearbyConnectionsPermissions | undefined) => Promise<PermissionStatus>
```

Request the appropriate permissions to use Nearby.

| Param             | Type                                                                                  |
| ----------------- | ------------------------------------------------------------------------------------- |
| **`permissions`** | <code><a href="#nearbyconnectionspermissions">NearbyConnectionsPermissions</a></code> |

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 1.0.0

--------------------


### addListener('onPermissionChanged', ...)

```typescript
addListener(eventName: 'onPermissionChanged', listenerFunc: (granted: boolean) => void) => Promise<PluginListenerHandle>
```

Called when permission is granted or revoked for this app to use Nearby.

| Param              | Type                                       |
| ------------------ | ------------------------------------------ |
| **`eventName`**    | <code>'onPermissionChanged'</code>         |
| **`listenerFunc`** | <code>(granted: boolean) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 1.0.0

--------------------


### addListener('onBluetoothStateChanged', ...)

```typescript
addListener(eventName: 'onBluetoothStateChanged', listenerFunc: (state: BluetoothState) => void) => Promise<PluginListenerHandle>
```

Called when state of Bluetooth has changed.

| Param              | Type                                                                          |
| ------------------ | ----------------------------------------------------------------------------- |
| **`eventName`**    | <code>'onBluetoothStateChanged'</code>                                        |
| **`listenerFunc`** | <code>(state: <a href="#bluetoothstate">BluetoothState</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 1.0.0

--------------------


### addListener('onEndpointFound', ...)

```typescript
addListener(eventName: 'onEndpointFound', listenerFunc: EndpointFoundCallback) => Promise<PluginListenerHandle>
```

Called when a remote endpoint is discovered.

| Param              | Type                                                                    |
| ------------------ | ----------------------------------------------------------------------- |
| **`eventName`**    | <code>'onEndpointFound'</code>                                          |
| **`listenerFunc`** | <code><a href="#endpointfoundcallback">EndpointFoundCallback</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 1.0.0

--------------------


### addListener('onEndpointLost', ...)

```typescript
addListener(eventName: 'onEndpointLost', listenerFunc: EndpointLostCallback) => Promise<PluginListenerHandle>
```

Called when a remote endpoint is no longer discoverable.

| Param              | Type                                                                  |
| ------------------ | --------------------------------------------------------------------- |
| **`eventName`**    | <code>'onEndpointLost'</code>                                         |
| **`listenerFunc`** | <code><a href="#endpointlostcallback">EndpointLostCallback</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 1.0.0

--------------------


### Interfaces


#### InitializeOptions

| Prop            | Type                                          | Description                                                                                                                                                                                             | Default            | Since |
| --------------- | --------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`name`**      | <code>string</code>                           | A human readable name for this endpoint, to appear on the remote device.                                                                                                                                |                    | 1.0.0 |
| **`serviceId`** | <code>string</code>                           | An identifier to advertise your app to other endpoints. The `serviceId` value must uniquely identify your app. As a best practice, use the package name of your app (for example, `com.example.myapp`). |                    | 1.0.0 |
| **`strategy`**  | <code><a href="#strategy">Strategy</a></code> | Sets the <a href="#strategy">`Strategy`</a> to be used when discovering or advertising to Nearby devices.                                                                                               |                    | 1.0.0 |
| **`lowPower`**  | <code>boolean</code>                          | Sets whether low power should be used.                                                                                                                                                                  | <code>false</code> | 1.0.0 |


#### PublishOptions

| Prop                 | Type                                                      | Description                                                                                         | Default                              | Since |
| -------------------- | --------------------------------------------------------- | --------------------------------------------------------------------------------------------------- | ------------------------------------ | ----- |
| **`connectionType`** | <code><a href="#connectiontype">ConnectionType</a></code> | Sets whether the client should disrupt the current connection to optimize the transfer or not.      | <code>ConnectionType.BALANCED</code> | 1.0.0 |
| **`name`**           | <code>string</code>                                       | A human readable name for this endpoint, to appear on the remote device.                            |                                      | 1.0.0 |
| **`endpointInfo`**   | <code><a href="#base64">Base64</a></code>                 | Identifing information about this endpoint (eg. name, device type), to appear on the remote device. |                                      | 1.0.0 |


#### Status

| Prop                | Type                  |
| ------------------- | --------------------- |
| **`isPublishing`**  | <code>boolean</code>  |
| **`isSubscribing`** | <code>boolean</code>  |
| **`ids`**           | <code>string[]</code> |


#### PermissionStatus

| Prop                  | Type                                                        | Description                                                                                                                                                                                                                                             | Since |
| --------------------- | ----------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`wifiNearby`**      | <code><a href="#permissionstate">PermissionState</a></code> | `NEARBY_WIFI_DEVICES` Required to be able to advertise and connect to nearby devices via Wi-Fi.                                                                                                                                                         | 1.0.0 |
| **`wifiState`**       | <code><a href="#permissionstate">PermissionState</a></code> | `ACCESS_WIFI_STATE` Allows applications to access information about Wi-Fi networks. `CHANGE_WIFI_STATE` Allows applications to change Wi-Fi connectivity state.                                                                                         | 1.0.0 |
| **`bluetoothNearby`** | <code><a href="#permissionstate">PermissionState</a></code> | `BLUETOOTH_ADVERTISE` Required to be able to advertise to nearby Bluetooth devices. `BLUETOOTH_CONNECT` Required to be able to connect to paired Bluetooth devices. `BLUETOOTH_SCAN` Required to be able to discover and pair nearby Bluetooth devices. | 1.0.0 |
| **`bluetoothLegacy`** | <code><a href="#permissionstate">PermissionState</a></code> | `BLUETOOTH` Allows applications to connect to paired bluetooth devices. `BLUETOOTH_ADMIN` Allows applications to discover and pair bluetooth devices.                                                                                                   | 1.0.0 |
| **`location`**        | <code><a href="#permissionstate">PermissionState</a></code> | `ACCESS_FINE_LOCATION` Allows an app to access precise location.                                                                                                                                                                                        | 1.0.0 |
| **`locationCoarse`**  | <code><a href="#permissionstate">PermissionState</a></code> | `ACCESS_COARSE_LOCATION` Allows an app to access approximate location.                                                                                                                                                                                  | 1.0.0 |


#### NearbyConnectionsPermissions

| Prop              | Type                                          |
| ----------------- | --------------------------------------------- |
| **`permissions`** | <code>NearbyConnectionPermissionType[]</code> |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### Endpoint

| Prop               | Type                                      | Description                                                                                         | Since |
| ------------------ | ----------------------------------------- | --------------------------------------------------------------------------------------------------- | ----- |
| **`id`**           | <code>string</code>                       | The ID of the remote endpoint that was discovered.                                                  | 1.0.0 |
| **`name`**         | <code>string</code>                       | A human readable name for this endpoint, to appear on the remote device.                            | 1.0.0 |
| **`endpointInfo`** | <code><a href="#base64">Base64</a></code> | Identifing information about this endpoint (eg. name, device type), to appear on the remote device. | 1.0.0 |


### Type Aliases


#### Base64

<code>string</code>


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### NearbyConnectionPermissionType

<code>'wifiNearby' | 'wifiState' | 'bluetoothNearby' | 'bluetoothLegacy' | 'location' | 'locationCoarse'</code>


#### EndpointFoundCallback

<code>(_: <a href="#endpoint">Endpoint</a>): void</code>


#### EndpointLostCallback

<code>(_: <a href="#endpoint">Endpoint</a>): void</code>


### Enums


#### Strategy

| Members              | Value                         | Description                                                                            | Since |
| -------------------- | ----------------------------- | -------------------------------------------------------------------------------------- | ----- |
| **`CLUSTER`**        | <code>'cluster'</code>        | Peer-to-peer strategy that supports an M-to-N, or cluster-shaped, connection topology. | 1.0.0 |
| **`STAR`**           | <code>'star'</code>           | Peer-to-peer strategy that supports a 1-to-N, or star-shaped, connection topology.     | 1.0.0 |
| **`POINT_TO_POINT`** | <code>'point-to-point'</code> | Peer-to-peer strategy that supports a 1-to-1 connection topology.                      | 1.0.0 |


#### ConnectionType

| Members              | Value          | Description                                                                                  | Since |
| -------------------- | -------------- | -------------------------------------------------------------------------------------------- | ----- |
| **`BALANCED`**       | <code>0</code> | Nearby Connections will change the device's Wi-Fi or Bluetooth status only if necessary.     | 1.0.0 |
| **`DISRUPTIVE`**     | <code>1</code> | Nearby Connections will change the device's Wi-Fi or Bluetooth status to enhance throughput. | 1.0.0 |
| **`NON_DISRUPTIVE`** | <code>2</code> | Nearby Connections should not change the device's Wi-Fi or Bluetooth status.                 | 1.0.0 |


#### BluetoothState

| Members            | Value                       | Description                                                                                         | Since |
| ------------------ | --------------------------- | --------------------------------------------------------------------------------------------------- | ----- |
| **`UNKNOWN`**      | <code>'unknown'</code>      | The manager’s state is unknown.                                                                     | 1.0.0 |
| **`RESETTING`**    | <code>'resetting'</code>    | A state that indicates the connection with the system service was momentarily lost.                 | 1.0.0 |
| **`UNSUPPORTED`**  | <code>'unsupported'</code>  | A state that indicates this device doesn’t support the Bluetooth low energy central or client role. | 1.0.0 |
| **`UNAUTHORIZED`** | <code>'unauthorized'</code> | A state that indicates the application isn’t authorized to use the Bluetooth low energy role.       | 1.0.0 |
| **`POWERED_OFF`**  | <code>'poweredOff'</code>   | A state that indicates Bluetooth is currently powered off.                                          | 1.0.0 |
| **`POWERED_ON`**   | <code>'poweredOn'</code>    | A state that indicates Bluetooth is currently powered on and available to use.                      | 1.0.0 |

</docgen-api>
