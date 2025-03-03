import { NearbyConnections } from '@capacitor-trancee/nearby-connections';

window.testInitialize = async () => {
    let options = {}

    const endpointName = document.getElementById("initialize-endpointName").value;
    const serviceID = document.getElementById("initialize-serviceID").value;
    const strategy = document.getElementById("initialize-strategy").value;
    const connectionType = document.getElementById("initialize-connectionType").value;
    const lowPower = document.getElementById("initialize-lowPower").value;
    const autoConnect = document.getElementById("initialize-autoConnect").value;
    const payload = document.getElementById("initialize-payload").value;

    if (endpointName !== undefined && endpointName.length > 0) {
        options.endpointName = endpointName
    }
    if (serviceID !== undefined && serviceID.length > 0) {
        options.serviceID = serviceID
    }
    if (strategy !== undefined && strategy.length > 0) {
        options.strategy = strategy
    }
    if (connectionType !== undefined && connectionType.length > 0) {
        options.connectionType = connectionType
    }
    if (lowPower !== undefined && lowPower.length > 0) {
        options.lowPower = lowPower === "true"
    }
    if (autoConnect !== undefined && autoConnect.length > 0) {
        options.autoConnect = autoConnect === "true"
    }
    if (payload !== undefined && payload.length > 0) {
        options.payload = btoa(payload)
    }

    const result = await window.execute("initialize", options)
}

window.testReset = async () => {
    let options = {}

    const result = await window.execute("reset", options)

    document.getElementById("status").value = ""
    document.getElementById("events").value = ""
}

window.testStartAdvertising = async () => {
    let options = {}

    const endpointName = document.getElementById("startAdvertising-endpointName").value;
    const connectionType = document.getElementById("startAdvertising-connectionType").value;
    const lowPower = document.getElementById("startAdvertising-lowPower").value;

    if (endpointName !== undefined && endpointName.length > 0) {
        options.endpointName = endpointName
    }
    if (connectionType !== undefined && connectionType.length > 0) {
        options.connectionType = connectionType
    }
    if (lowPower !== undefined && lowPower.length > 0) {
        options.lowPower = lowPower === "true"
    }

    const result = await window.execute("startAdvertising", options)
}

window.testStopAdvertising = async () => {
    let options = {}

    const result = await window.execute("stopAdvertising", options)
}

window.testStartDiscovery = async () => {
    let options = {}

    const lowPower = document.getElementById("startDiscovery-lowPower").value;

    if (lowPower !== undefined && lowPower.length > 0) {
        options.lowPower = lowPower === "true"
    }

    const result = await window.execute("startDiscovery", options)
}

window.testStopDiscovery = async () => {
    let options = {};

    const result = await window.execute("stopDiscovery", options);
}

window.testStatus = async () => {
    let options = {};

    const result = await window.execute("status", options);
}

window.testCheckPermissions = async () => {
    let options = {};

    const result = await window.execute("checkPermissions", options);
}

window.testRequestPermissions = async () => {
    let options = {};

    const aliases = document.getElementById("aliases").selectedOptions;

    if (aliases !== undefined && aliases.length > 0) {
        options.permissions = Array.from(aliases).map(option => option.value)
    }

    const result = await window.execute("requestPermissions", options);
}

window.execute = async (method, options) => {
    try {
        options = Object.keys(options).length > 0 ? options : undefined

        document.getElementById("status").value += `⚪ ${method}(${JSON.stringify(options) || ""})` + "\n"

        const result = await NearbyConnections[method](options)

        document.getElementById("status").value += `⚫ ${method}(${JSON.stringify(result) || ""})` + "\n"

        return result
    } catch (error) {
        document.getElementById("status").value += `⛔ ${error}` + "\n";
    }
}

NearbyConnections.addListener('onEndpointFound', (endpoint) => {
    console.log('onEndpointFound', endpoint);

    document.getElementById("events").value += `⚡ onEndpointFound(${JSON.stringify(endpoint) || ""})` + "\n";
});

NearbyConnections.addListener('onEndpointLost', (endpoint) => {
    console.log('onEndpointLost', endpoint);

    document.getElementById("events").value += `⚡ onEndpointLost(${JSON.stringify(endpoint) || ""})` + "\n";
});

NearbyConnections.addListener('onEndpointInitiated', (endpoint) => {
    console.log('onEndpointInitiated', endpoint);

    document.getElementById("events").value += `⚡ onEndpointInitiated(${JSON.stringify(endpoint) || ""})` + "\n";
});

NearbyConnections.addListener('onEndpointConnected', (endpoint) => {
    console.log('onEndpointConnected', endpoint);

    document.getElementById("events").value += `⚡ onEndpointConnected(${JSON.stringify(endpoint) || ""})` + "\n";
});

NearbyConnections.addListener('onEndpointRejected', (endpoint) => {
    console.log('onEndpointRejected', endpoint);

    document.getElementById("events").value += `⚡ onEndpointRejected(${JSON.stringify(endpoint) || ""})` + "\n";
});

NearbyConnections.addListener('onEndpointFailed', (endpoint) => {
    console.log('onEndpointFailed', endpoint);

    document.getElementById("events").value += `⚡ onEndpointFailed(${JSON.stringify(endpoint) || ""})` + "\n";
});

NearbyConnections.addListener('onEndpointDisconnected', (endpoint) => {
    console.log('onEndpointDisconnected', endpoint);

    document.getElementById("events").value += `⚡ onEndpointDisconnected(${JSON.stringify(endpoint) || ""})` + "\n";
});

NearbyConnections.addListener('onEndpointBandwidthChanged', (endpoint) => {
    console.log('onEndpointBandwidthChanged', endpoint);

    document.getElementById("events").value += `⚡ onEndpointBandwidthChanged(${JSON.stringify(endpoint) || ""})` + "\n";
});

NearbyConnections.addListener('onPayloadReceived', (endpoint) => {
    console.log('onPayloadReceived', endpoint);

    document.getElementById("events").value += `⚡ onPayloadReceived(${JSON.stringify(endpoint) || ""})` + "\n";
});

NearbyConnections.addListener('onPayloadTransferUpdate', (endpoint) => {
    console.log('onPayloadTransferUpdate', endpoint);

    document.getElementById("events").value += `⚡ onPayloadTransferUpdate(${JSON.stringify(endpoint) || ""})` + "\n";
});

document.getElementById("status").onchange = () => {
    document.getElementById("status").scrollTop = document.getElementById("status").scrollHeight;
}
document.getElementById("events").onchange = () => {
    document.getElementById("events").scrollTop = document.getElementById("events").scrollHeight;
}
