import { Connection } from './connection.js';
export declare class LiveReloadConnection extends Connection {
    webSocket?: WebSocket;
    constructor(url: string);
    onReload(): void;
    handleMessage(msg: any): void;
    handleError(msg: any): void;
}
