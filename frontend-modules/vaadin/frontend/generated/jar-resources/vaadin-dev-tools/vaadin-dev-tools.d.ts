import 'construct-style-sheets-polyfill';
import { LitElement, PropertyValueMap, TemplateResult } from 'lit';
import { Product } from './License';
import { ConnectionStatus } from './connection';
import './theme-editor/editor';
import { ThemeEditorState } from './theme-editor/model';
import './vaadin-dev-tools-info';
import './vaadin-dev-tools-log';
/**
 * Plugin API for the dev tools window.
 */
export interface DevToolsInterface {
    send(command: string, data: any): void;
    addTab(id: string, tag: string): void;
}
export interface MessageHandler {
    handleMessage(message: ServerMessage): boolean;
}
export interface ServerMessage {
    /**
     * The command
     */
    command: string;
    /**
     * the data for the command
     */
    data: any;
}
/**
 * To create and register a plugin, use e.g.
 * @example
 * export class MyTab extends LitElement implements MessageHandler {
 *   render() {
 *     return html`<div>Here I am</div>`;
 *   }
 * }
 * customElements.define('my-tab', MyTab);
 *
 * const plugin: DevToolsPlugin = {
 *   init: function (devToolsInterface: DevToolsInterface): void {
 *     devToolsInterface.addTab('Tab title', 'my-tab')
 *   }
 * };
 *
 * (window as any).Vaadin.devToolsPlugins.push(plugin);
 */
export interface DevToolsPlugin {
    /**
     * Called once to initialize the plugin.
     *
     * @param devToolsInterface provides methods to interact with the dev tools
     */
    init(devToolsInterface: DevToolsInterface): void;
}
interface Feature {
    id: string;
    title: string;
    moreInfoLink: string;
    requiresServerRestart: boolean;
    enabled: boolean;
}
export declare enum MessageType {
    LOG = "log",
    INFORMATION = "information",
    WARNING = "warning",
    ERROR = "error"
}
interface Message {
    id: number;
    type: MessageType;
    message: string;
    details?: string;
    link?: string;
    persistentId?: string;
    dontShowAgain: boolean;
    dontShowAgainMessage?: string;
    deleted: boolean;
}
type DevToolsConf = {
    enable: boolean;
    url: string;
    backend?: string;
    liveReloadPort: number;
    token?: string;
};
export declare class VaadinDevTools extends LitElement {
    static MAX_LOG_ROWS: number;
    unhandledMessages: ServerMessage[];
    conf: DevToolsConf;
    static get styles(): import("lit").CSSResult[];
    static DISMISSED_NOTIFICATIONS_IN_LOCAL_STORAGE: string;
    static ACTIVE_KEY_IN_SESSION_STORAGE: string;
    static TRIGGERED_KEY_IN_SESSION_STORAGE: string;
    static TRIGGERED_COUNT_KEY_IN_SESSION_STORAGE: string;
    static AUTO_DEMOTE_NOTIFICATION_DELAY: number;
    static HOTSWAP_AGENT: string;
    static JREBEL: string;
    static SPRING_BOOT_DEVTOOLS: string;
    static BACKEND_DISPLAY_NAME: Record<string, string>;
    static get isActive(): boolean;
    static notificationDismissed(persistentId: string): boolean;
    expanded: boolean;
    messages: Message[];
    splashMessage?: string;
    notifications: Message[];
    frontendStatus: ConnectionStatus;
    javaStatus: ConnectionStatus;
    private tabs;
    private activeTab;
    private features;
    unreadErrors: boolean;
    private root;
    private componentPicker;
    componentPickActive: boolean;
    themeEditorState: ThemeEditorState;
    private javaConnection?;
    private frontendConnection?;
    private nextMessageId;
    private disableEventListener?;
    private transitionDuration;
    elementTelemetry(): void;
    openWebSocketConnection(): void;
    tabHandleMessage(tabElement: HTMLElement, message: ServerMessage): boolean;
    handleFrontendMessage(message: ServerMessage): void;
    getDedicatedWebSocketUrl(): string | undefined;
    getSpringBootWebSocketUrl(location: any): string;
    constructor();
    connectedCallback(): void;
    initPlugin(plugin: DevToolsPlugin): Promise<void>;
    format(o: any): string;
    catchErrors(): void;
    disconnectedCallback(): void;
    toggleExpanded(): void;
    showSplashMessage(msg: string | undefined): void;
    demoteSplashMessage(): void;
    checkLicense(productInfo: Product): void;
    log(type: MessageType, message: string, details?: string, link?: string, dontShowAgainMessage?: string): void;
    showNotification(type: MessageType, message: string, details?: string, link?: string, persistentId?: string, dontShowAgainMessage?: string): void;
    dismissNotification(id: number): void;
    findNotificationIndex(id: number): number;
    toggleDontShowAgain(id: number): void;
    setActive(yes: boolean): void;
    getStatusColor(status: ConnectionStatus | undefined): "none" | "var(--dev-tools-green-color)" | "var(--dev-tools-grey-color)" | "var(--dev-tools-yellow-hsl)" | "var(--dev-tools-red-color)";
    renderMessage(messageObject: Message): TemplateResult<1>;
    render(): TemplateResult<1>;
    protected updated(_changedProperties: PropertyValueMap<any> | Map<PropertyKey, unknown>): void;
    renderCode(): TemplateResult<1>;
    private renderFeatures;
    setJavaLiveReloadActive(active: boolean): void;
    renderThemeEditor(): TemplateResult<1>;
    toggleFeatureFlag(e: Event, feature: Feature): void;
}
export {};
