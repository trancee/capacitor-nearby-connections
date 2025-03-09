import Capacitor

public class NearbyConnectionsConfig {
    private var endpointName: String?

    private var serviceID: String?
    private var strategy: String?

    private var autoConnect: Bool?
    private var payload: String?

    init(config: PluginConfig) {
        self.endpointName = config.getString("endpointName")

        self.serviceID = config.getString("serviceID")
        self.strategy = config.getString("strategy")

        if config.getString("autoConnect") != nil {
            self.autoConnect = config.getBoolean("autoConnect", false)
        }
        self.payload = config.getString("payload")
    }

    func setEndpointName(_ endpointName: String?) {
        self.endpointName = endpointName
    }

    func setServiceID(_ serviceID: String?) {
        self.serviceID = serviceID
    }
    func setStrategy(_ strategy: String?) {
        self.strategy = strategy
    }

    func setAutoConnect(_ autoConnect: Bool?) {
        self.autoConnect = autoConnect
    }
    func setPayload(_ payload: String?) {
        self.payload = payload
    }

    func getEndpointName() -> String? {
        return self.endpointName
    }

    func getServiceID() -> String? {
        return self.serviceID
    }
    func getStrategy() -> String? {
        return self.strategy
    }

    func getAutoConnect() -> Bool? {
        return self.autoConnect
    }
    func getPayload() -> String? {
        return self.payload
    }
}
