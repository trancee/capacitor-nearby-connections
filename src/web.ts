import { WebPlugin } from '@capacitor/core';

import type {
  InitializeOptions,
  PublishOptions,
  Status,
  PermissionStatus,
  NearbyConnectionsPlugin,
} from './definitions';

export class NearbyConnectionsWeb extends WebPlugin implements NearbyConnectionsPlugin {
  async initialize(options?: InitializeOptions): Promise<void> {
    console.info('initialize', options);
    throw this.unimplemented('Method not implemented.');
  }
  async reset(): Promise<void> {
    console.info('reset');
    throw this.unimplemented('Method not implemented.');
  }

  async publish(options?: PublishOptions): Promise<void> {
    console.info('publish', options);
    throw this.unimplemented('Method not implemented.');
  }
  async unpublish(): Promise<void> {
    console.info('unpublish');
    throw this.unimplemented('Method not implemented.');
  }

  async subscribe(): Promise<void> {
    console.info('subscribe');
    throw this.unimplemented('Method not implemented.');
  }
  async unsubscribe(): Promise<void> {
    console.info('unsubscribe');
    throw this.unimplemented('Method not implemented.');
  }

  async status(): Promise<Status> {
    console.info('status');
    throw this.unimplemented('Method not implemented.');
  }

  async checkPermissions(): Promise<PermissionStatus> {
    console.info('checkPermissions');
    throw this.unimplemented('Method not implemented.');
  }

  async requestPermissions(): Promise<PermissionStatus> {
    console.info('requestPermissions');
    throw this.unimplemented('Method not implemented.');
  }
}
