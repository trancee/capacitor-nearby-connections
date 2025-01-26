import { WebPlugin } from '@capacitor/core';

import type { NearbyConnectionsPlugin } from './definitions';

export class NearbyConnectionsWeb extends WebPlugin implements NearbyConnectionsPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
