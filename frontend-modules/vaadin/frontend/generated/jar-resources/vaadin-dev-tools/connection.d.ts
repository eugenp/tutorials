export declare enum ConnectionStatus {
    ACTIVE = "active",
    INACTIVE = "inactive",
    UNAVAILABLE = "unavailable",
    ERROR = "error"
}
export declare abstract class Connection {
    static HEARTBEAT_INTERVAL: number;
    status: ConnectionStatus;
    onHandshake(): void;
    onConnectionError(_: string): void;
    onStatusChange(_: ConnectionStatus): void;
    setActive(yes: boolean): void;
    setStatus(status: ConnectionStatus): void;
}
