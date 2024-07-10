export interface FlowConfig {
    imports?: () => void;
}
interface AppConfig {
    productionMode: boolean;
    appId: string;
    uidl: any;
}
interface AppInitResponse {
    appConfig: AppConfig;
    pushScript?: string;
}
interface Router {
    render: (ctx: NavigationParameters, shouldUpdateHistory: boolean) => Promise<void>;
}
interface HTMLRouterContainer extends HTMLElement {
    onBeforeEnter?: (ctx: NavigationParameters, cmd: PreventAndRedirectCommands, router: Router) => void | Promise<any>;
    onBeforeLeave?: (ctx: NavigationParameters, cmd: PreventCommands, router: Router) => void | Promise<any>;
    serverConnected?: (cancel: boolean, url?: NavigationParameters) => void;
    serverPaused?: () => void;
}
interface FlowRoute {
    action: (params: NavigationParameters) => Promise<HTMLRouterContainer>;
    path: string;
}
export interface NavigationParameters {
    pathname: string;
    search: string;
}
export interface PreventCommands {
    prevent: () => any;
}
export interface PreventAndRedirectCommands extends PreventCommands {
    redirect: (route: string) => any;
}
/**
 * Client API for flow UI operations.
 */
export declare class Flow {
    config: FlowConfig;
    response?: AppInitResponse;
    pathname: string;
    container: HTMLRouterContainer;
    private isActive;
    private baseRegex;
    private appShellTitle;
    private navigation;
    constructor(config?: FlowConfig);
    /**
     * Return a `route` object for vaadin-router in an one-element array.
     *
     * The `FlowRoute` object `path` property handles any route,
     * and the `action` returns the flow container without updating the content,
     * delaying the actual Flow server call to the `onBeforeEnter` phase.
     *
     * This is a specific API for its use with `vaadin-router`.
     */
    get serverSideRoutes(): [FlowRoute];
    loadingStarted(): void;
    loadingFinished(): void;
    private get action();
    private flowLeave;
    private flowNavigate;
    private getFlowRoutePath;
    private getFlowRouteQuery;
    private flowInit;
    private loadScript;
    private injectAppIdScript;
    private flowInitClient;
    private flowInitUi;
    private addConnectionIndicator;
    private offlineStubAction;
    private isFlowClientLoaded;
}
export {};
