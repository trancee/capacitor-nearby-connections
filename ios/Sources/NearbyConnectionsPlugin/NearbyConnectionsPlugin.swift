import Foundation
import Capacitor

import CoreBluetooth
import CoreLocation

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(NearbyConnectionsPlugin)
public class NearbyConnectionsPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "NearbyConnectionsPlugin"
    public let jsName = "NearbyConnections"

    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "initialize", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "reset", returnType: CAPPluginReturnPromise),

        CAPPluginMethod(name: "startAdvertising", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stopAdvertising", returnType: CAPPluginReturnPromise),

        CAPPluginMethod(name: "startDiscovery", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stopDiscovery", returnType: CAPPluginReturnPromise),

        CAPPluginMethod(name: "requestConnection", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "acceptConnection", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "rejectConnection", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "disconnect", returnType: CAPPluginReturnPromise),

        CAPPluginMethod(name: "sendPayload", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "cancelPayload", returnType: CAPPluginReturnPromise),

        CAPPluginMethod(name: "status", returnType: CAPPluginReturnPromise),

        CAPPluginMethod(name: "checkPermissions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "requestPermissions", returnType: CAPPluginReturnPromise)
    ]

    public let tag = "NearbyConnectionsPlugin"

    let ENDPOINT_FOUND_EVENT = "onEndpointFound"
    let ENDPOINT_LOST_EVENT = "onEndpointLost"
    let ENDPOINT_INITIATED_EVENT = "onEndpointInitiated"
    let ENDPOINT_CONNECTED_EVENT = "onEndpointConnected"
    let ENDPOINT_REJECTED_EVENT = "onEndpointRejected"
    let ENDPOINT_FAILED_EVENT = "onEndpointFailed"
    let ENDPOINT_DISCONNECTED_EVENT = "onEndpointDisconnected"
    let ENDPOINT_BANDWIDTH_CHANGED_EVENT = "onEndpointBandwidthChanged"
    let PAYLOAD_RECEIVED_EVENT = "onPayloadReceived"
    let PAYLOAD_TRANSFER_UPDATE_EVENT = "onPayloadTransferUpdate"

    private var implementation: NearbyConnectionsImpl!
    private var config: NearbyConnectionsConfig!

    override public func load() {
        super.load()

        self.config = NearbyConnectionsConfig(config: getConfig())
        self.implementation = NearbyConnectionsImpl(plugin: self, config: self.config)
    }

    /**
     * Initialize
     */

    @objc func initialize(_ call: CAPPluginCall) {
        if let endpointName = call.getString("endpointName") {
            config.setEndpointName(endpointName)
        }

        if let serviceID = call.getString("serviceID") {
            config.setServiceID(serviceID)
        }
        if let strategy = call.getString("strategy") {
            config.setStrategy(strategy)
        }

        if let autoConnect = call.getBool("autoConnect") {
            config.setAutoConnect(autoConnect)
        }
        if let payload = call.getString("payload") {
            config.setPayload(payload)
        }

        let options = InitializeOptions(call, config)

        implementation.initialize(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, nil)
            }
        })
    }

    /**
     * Reset
     */

    @objc func reset(_ call: CAPPluginCall) {
        implementation.reset(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, nil)
            }
        })
    }

    /**
     * Advertising
     */

    @objc func startAdvertising(_ call: CAPPluginCall) {
        let options = StartAdvertisingOptions(call)

        implementation.startAdvertising(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, nil)
            }
        })
    }

    @objc func stopAdvertising(_ call: CAPPluginCall) {
        implementation.stopAdvertising(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, nil)
            }
        })
    }

    /**
     * Discovery
     */

    @objc func startDiscovery(_ call: CAPPluginCall) {
        let options = StartDiscoveryOptions(call)

        implementation.startDiscovery(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, nil)
            }
        })
    }

    @objc func stopDiscovery(_ call: CAPPluginCall) {
        implementation.stopDiscovery(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, nil)
            }
        })
    }

    /**
     * Connection
     */

    @objc func requestConnection(_ call: CAPPluginCall) {
        let options = RequestConnectionOptions(call)

        implementation.requestConnection(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, nil)
            }
        })
    }

    @objc func acceptConnection(_ call: CAPPluginCall) {
        let options = AcceptConnectionOptions(call)

        implementation.acceptConnection(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, nil)
            }
        })
    }

    @objc func rejectConnection(_ call: CAPPluginCall) {
        let options = RejectConnectionOptions(call)

        implementation.rejectConnection(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, nil)
            }
        })
    }

    @objc func disconnect(_ call: CAPPluginCall) {
        let options = DisconnectOptions(call)

        implementation.disconnect(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, nil)
            }
        })
    }

    /**
     * Payload
     */

    @objc func sendPayload(_ call: CAPPluginCall) {
        let options = SendPayloadOptions(call)

        implementation.sendPayload(options, completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else if let result = result?.toJSObject() as? JSObject {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func cancelPayload(_ call: CAPPluginCall) {
        let options = CancelPayloadOptions(call)

        implementation.cancelPayload(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, nil)
            }
        })
    }

    /**
     * Status
     */

    @objc func status(_ call: CAPPluginCall) {
        implementation.status(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else if let result = result?.toJSObject() as? JSObject {
                self.resolveCall(call, result)
            }
        })
    }

    /**
     * Permissions
     */

    @objc override public func checkPermissions(_ call: CAPPluginCall) {
        let wifiState: String = "prompt"
        let bluetoothState: String
        let locationState: String

        // The current authorization status for using Bluetooth.
        switch CBCentralManager.authorization {
        case .notDetermined:
            bluetoothState = "prompt"
        case .restricted, .denied:
            bluetoothState = "denied"
        case .allowedAlways:
            bluetoothState = "granted"
        @unknown default:
            bluetoothState = "prompt"
        }

        let manager = CLLocationManager()
        // manager.locationServicesEnabled()
        let authorizationStatus: CLAuthorizationStatus

        if #available(iOS 14, *) {
            authorizationStatus = manager.authorizationStatus
        } else {
            authorizationStatus = CLLocationManager.authorizationStatus()
        }

        // The current authorization status for using location services.
        switch authorizationStatus {
        case .notDetermined:
            locationState = "prompt"
        case .restricted, .denied:
            locationState = "denied"
        case .authorizedAlways, .authorizedWhenInUse:
            locationState = "granted"
        @unknown default:
            locationState = "prompt"
        }

        call.resolve([
            "wifiState": wifiState,
            "wifiNearby": wifiState,
            "bluetoothNearby": bluetoothState,
            "bluetoothLegacy": bluetoothState,
            "location": locationState,
            "locationCoarse": locationState
        ])
    }

    var permissionCallID: String?
    var locationManager: CLLocationManager?

    @objc override public func requestPermissions(_ call: CAPPluginCall) {
        // get the permissions to check or default to all of them
        var permissions = call.getArray("permissions", String.self) ?? []
        if permissions.isEmpty {
            permissions = ["wifiState", "wifiNearby", "bluetoothNearby", "bluetoothLegacy", "location", "locationCoarse"]
        }

        let group = DispatchGroup()

        if permissions.contains("wifiState") || permissions.contains("wifiNearby") {
            //            group.enter()
            //            store.requestAccess(for: .contacts) { (_, _) in
            //                group.leave()
            //            }
        }
        if permissions.contains("bluetoothNearby") || permissions.contains("bluetoothLegacy") {
            //            group.enter()
            //            AVCaptureDevice.requestAccess(for: .video) { _ in
            //                group.leave()
            //            }
        }
        if permissions.contains("location") || permissions.contains("locationCoarse") {
            if let manager = locationManager, CLLocationManager.locationServicesEnabled() {
                let authorizationStatus: CLAuthorizationStatus

                if #available(iOS 14, *) {
                    authorizationStatus = manager.authorizationStatus
                } else {
                    authorizationStatus = CLLocationManager.authorizationStatus()
                }

                if authorizationStatus == .notDetermined {
                    group.enter()

                    bridge?.saveCall(call)

                    permissionCallID = call.callbackId
                    call.options["group"] = group

                    manager.requestWhenInUseAuthorization()
                }
            }
        }

        group.notify(queue: DispatchQueue.main) {
            self.checkPermissions(call)
        }
    }

    public func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        if let callID = permissionCallID, let call = bridge?.savedCall(withID: callID), let group = call.options["group"] {
            if let group = group as? DispatchGroup {
                group.leave()
            }

            bridge?.releaseCall(call)
        }
    }

    /**
     * Events
     */

    /**
     * Called when a remote endpoint is discovered.
     */
    func onEndpointFound(_ event: EndpointFoundEvent) {
        notifyListeners(self.ENDPOINT_FOUND_EVENT, data: event.toJSObject())
    }
    /**
     * Called when a remote endpoint is no longer discoverable.
     */
    func onEndpointLost(_ event: EndpointLostEvent) {
        notifyListeners(self.ENDPOINT_LOST_EVENT, data: event.toJSObject())
    }

    /**
     * A basic encrypted channel has been created between you and the endpoint.
     */
    func onEndpointInitiated(_ event: EndpointInitiatedEvent) {
        notifyListeners(self.ENDPOINT_INITIATED_EVENT, data: event.toJSObject())
    }

    /**
     * Called after both sides have accepted the connection.
     */
    func onEndpointConnected(_ event: EndpointConnectedEvent) {
        notifyListeners(self.ENDPOINT_CONNECTED_EVENT, data: event.toJSObject())
    }
    /**
     * Called after one side has rejected the connection.
     */
    func onEndpointRejected(_ event: EndpointRejectedEvent) {
        notifyListeners(self.ENDPOINT_REJECTED_EVENT, data: event.toJSObject())
    }
    /**
     * Called after the connection has failed.
     */
    func onEndpointFailed(_ event: EndpointFailedEvent) {
        notifyListeners(self.ENDPOINT_FAILED_EVENT, data: event.toJSObject())
    }
    /**
     * Called when a remote endpoint is disconnected or has become unreachable.
     */
    func onEndpointDisconnected(_ event: EndpointDisconnectedEvent) {
        notifyListeners(self.ENDPOINT_DISCONNECTED_EVENT, data: event.toJSObject())
    }

    /**
     * Called when a Payload is received from a remote endpoint.
     */
    func onPayloadReceived(_ event: PayloadReceivedEvent) {
        notifyListeners(self.PAYLOAD_RECEIVED_EVENT, data: event.toJSObject())
    }
    /**
     * Called with progress information about an active Payload transfer, either incoming or outgoing.
     */
    func onPayloadTransferUpdate(_ event: PayloadTransferUpdateEvent) {
        notifyListeners(self.PAYLOAD_TRANSFER_UPDATE_EVENT, data: event.toJSObject())
    }

    /**
     * Calls
     */

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", self.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: JSObject?) {
        if let result {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
