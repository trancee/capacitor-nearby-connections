export interface NearbyConnectionsPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
