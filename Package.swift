// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorTranceeNearbyConnections",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapacitorTranceeNearbyConnections",
            targets: ["NearbyConnectionsPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.1"),
        .package(url: "https://github.com/trancee/nearby.git", branch: "capacitor")
    ],
    targets: [
        .target(
            name: "NearbyConnectionsPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "NearbyConnections", package: "nearby")
            ],
            path: "ios/Sources/NearbyConnectionsPlugin"),
        .testTarget(
            name: "NearbyConnectionsPluginTests",
            dependencies: ["NearbyConnectionsPlugin"],
            path: "ios/Tests/NearbyConnectionsPluginTests")
    ]
)
