/// <reference types="@capacitor-trancee/nearby-connections" />

import { CapacitorConfig } from '@capacitor/cli';
import { ConnectionType, Strategy } from '@capacitor-trancee/nearby-connections';

const config: CapacitorConfig = {
  appId: "com.example.myapp",
  appName: "My App",
  webDir: "dist",
  plugins: {
    NearbyConnections: {
      endpointName: "My App",

      serviceId: "com.example.myapp",
      strategy: Strategy.CLUSTER,
      // lowPower: true,

      // connectionType: ConnectionType.NON_DISRUPTIVE,

      // autoConnect: true,
      // payload: btoa(`{ name: "PHiL", age: 50, gender: "MALE", photo: "LEHV6nWB2yk8pyo0adR*.7kCMdnj" }`),
    },
  },
};

export default config;
