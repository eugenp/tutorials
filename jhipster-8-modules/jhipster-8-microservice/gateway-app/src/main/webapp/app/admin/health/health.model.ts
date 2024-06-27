export type HealthStatus = 'UP' | 'DOWN' | 'UNKNOWN' | 'OUT_OF_SERVICE';

export type HealthKey =
  | 'discoveryComposite'
  | 'refreshScope'
  | 'clientConfigServer'
  | 'hystrix'
  | 'diskSpace'
  | 'mail'
  | 'ping'
  | 'livenessState'
  | 'readinessState'
  | 'r2dbc';

export interface Health {
  status: HealthStatus;
  components: {
    [key in HealthKey]?: HealthDetails;
  };
}

export interface HealthDetails {
  status: HealthStatus;
  details?: { [key: string]: unknown };
}
