import { WebSocketConnection } from '../websocket-connection';
import { ComponentReference } from '../component-util';
export declare enum Commands {
    response = "themeEditorResponse",
    loadComponentMetadata = "themeEditorComponentMetadata",
    setLocalClassName = "themeEditorLocalClassName",
    setCssRules = "themeEditorRules",
    loadRules = "themeEditorLoadRules",
    history = "themeEditorHistory",
    openCss = "themeEditorOpenCss",
    markAsUsed = "themeEditorMarkAsUsed"
}
export declare enum ResponseCode {
    ok = "ok",
    error = "error"
}
export interface BaseResponse {
    requestId: string;
    code: ResponseCode;
}
export interface LoadComponentMetadataResponse extends BaseResponse {
    accessible?: boolean;
    className?: string;
    suggestedClassName?: string;
}
export interface LoadPreviewResponse extends BaseResponse {
    css: string;
}
export interface ServerCssRule {
    selector: string;
    properties: {
        [key: string]: string;
    };
}
export interface LoadRulesResponse extends BaseResponse {
    rules: ServerCssRule[];
}
export declare class ThemeEditorApi {
    private wrappedConnection;
    private pendingRequests;
    private requestCounter;
    private globalUiId;
    constructor(wrappedConnection: WebSocketConnection);
    private sendRequest;
    private handleResponse;
    loadComponentMetadata(componentRef: ComponentReference): Promise<LoadComponentMetadataResponse>;
    setLocalClassName(componentRef: ComponentReference, className: string): Promise<BaseResponse>;
    setCssRules(rules: ServerCssRule[]): Promise<BaseResponse>;
    loadRules(selectors: string[]): Promise<LoadRulesResponse>;
    markAsUsed(): Promise<BaseResponse>;
    undo(requestId: string): Promise<BaseResponse>;
    redo(requestId: string): Promise<BaseResponse>;
    openCss(selector: string): Promise<BaseResponse>;
    private getGlobalUiId;
}
