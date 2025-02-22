import { WebPlugin } from '@capacitor/core';

import type {
  InitializeOptions,
  StatusResult,
  PermissionStatus,
  NearbyConnectionsPlugin,
  RequestConnectionOptions,
  AcceptConnectionOptions,
  RejectConnectionOptions,
  SendPayloadOptions,
  CancelPayloadOptions,
  DisconnectFromEndpointOptions,
  StartAdvertisingOptions,
  StartDiscoveryOptions,
} from './definitions';

export class NearbyConnectionsWeb extends WebPlugin implements NearbyConnectionsPlugin {
  async initialize(options?: InitializeOptions): Promise<void> {
    console.info('initialize', options);
    // throw this.unimplemented('Method not implemented.');
  }
  async reset(): Promise<void> {
    console.info('reset');
    // throw this.unimplemented('Method not implemented.');
  }

  async startAdvertising(options?: StartAdvertisingOptions): Promise<void> {
    console.info('startAdvertising', options);
    // throw this.unimplemented('Method not implemented.');
  }
  async stopAdvertising(): Promise<void> {
    console.info('stopAdvertising');
    // throw this.unimplemented('Method not implemented.');
  }

  async startDiscovery(options?: StartDiscoveryOptions): Promise<void> {
    console.info('startDiscovery', options);
    // throw this.unimplemented('Method not implemented.');
  }
  async stopDiscovery(): Promise<void> {
    console.info('stopDiscovery');
    // throw this.unimplemented('Method not implemented.');
  }

  async requestConnection(options: RequestConnectionOptions): Promise<void> {
    console.info('requestConnection', options);
    // throw this.unimplemented('Method not implemented.');
  }
  async acceptConnection(options: AcceptConnectionOptions): Promise<void> {
    console.info('acceptConnection', options);
    // throw this.unimplemented('Method not implemented.');
  }
  async rejectConnection(options: RejectConnectionOptions): Promise<void> {
    console.info('rejectConnection', options);
    // throw this.unimplemented('Method not implemented.');
  }
  async disconnectFromEndpoint(options: DisconnectFromEndpointOptions): Promise<void> {
    console.info('disconnectFromEndpoint', options);
    // throw this.unimplemented('Method not implemented.');
  }

  async sendPayload(options: SendPayloadOptions): Promise<void> {
    console.info('sendPayload', options);
    // throw this.unimplemented('Method not implemented.');
  }
  async cancelPayload(options: CancelPayloadOptions): Promise<void> {
    console.info('cancelPayload', options);
    // throw this.unimplemented('Method not implemented.');
  }

  async status(): Promise<StatusResult> {
    console.info('status');
    throw this.unimplemented('Method not implemented.');
  }

  async checkPermissions(): Promise<PermissionStatus> {
    console.info('checkPermissions');
    // throw this.unimplemented('Method not implemented.');
    return {
      wifiNearby: 'prompt',
      wifiState: 'prompt',
      bluetoothNearby: 'prompt',
      bluetoothLegacy: 'prompt',
      locationCoarse: 'prompt',
      location: 'prompt',
    };
  }
  async requestPermissions(): Promise<PermissionStatus> {
    console.info('requestPermissions');
    // throw this.unimplemented('Method not implemented.');
    return {
      wifiNearby: 'granted',
      wifiState: 'granted',
      bluetoothNearby: 'granted',
      bluetoothLegacy: 'granted',
      locationCoarse: 'granted',
      location: 'granted',
    };
  }
}
