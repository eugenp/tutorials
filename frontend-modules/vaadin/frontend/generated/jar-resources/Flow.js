import { ConnectionIndicator, ConnectionState } from '@vaadin/common-frontend';
class FlowUiInitializationError extends Error {
}
// flow uses body for keeping references
const flowRoot = window.document.body;
const $wnd = window;
/**
 * Client API for flow UI operations.
 */
export class Flow {
    constructor(config) {
        this.response = undefined;
        this.pathname = '';
        // flag used to inform Testbench whether a server route is in progress
        this.isActive = false;
        this.baseRegex = /^\//;
        this.navigation = '';
        flowRoot.$ = flowRoot.$ || [];
        this.config = config || {};
        // TB checks for the existence of window.Vaadin.Flow in order
        // to consider that TB needs to wait for `initFlow()`.
        $wnd.Vaadin = $wnd.Vaadin || {};
        $wnd.Vaadin.Flow = $wnd.Vaadin.Flow || {};
        $wnd.Vaadin.Flow.clients = {
            TypeScript: {
                isActive: () => this.isActive
            }
        };
        // Regular expression used to remove the app-context
        const elm = document.head.querySelector('base');
        this.baseRegex = new RegExp(`^${
        // IE11 does not support document.baseURI
        (document.baseURI || (elm && elm.href) || '/').replace(/^https?:\/\/[^/]+/i, '')}`);
        this.appShellTitle = document.title;
        // Put a vaadin-connection-indicator in the dom
        this.addConnectionIndicator();
    }
    /**
     * Return a `route` object for vaadin-router in an one-element array.
     *
     * The `FlowRoute` object `path` property handles any route,
     * and the `action` returns the flow container without updating the content,
     * delaying the actual Flow server call to the `onBeforeEnter` phase.
     *
     * This is a specific API for its use with `vaadin-router`.
     */
    get serverSideRoutes() {
        return [
            {
                path: '(.*)',
                action: this.action
            }
        ];
    }
    loadingStarted() {
        // Make Testbench know that server request is in progress
        this.isActive = true;
        $wnd.Vaadin.connectionState.loadingStarted();
    }
    loadingFinished() {
        // Make Testbench know that server request has finished
        this.isActive = false;
        $wnd.Vaadin.connectionState.loadingFinished();
        if ($wnd.Vaadin.listener) {
            // Listeners registered, do not register again.
            return;
        }
        $wnd.Vaadin.listener = {};
        // Listen for click on router-links -> 'link' navigation trigger
        // and on <a> nodes -> 'client' navigation trigger.
        // Use capture phase to detect prevented / stopped events.
        document.addEventListener('click', (_e) => {
            if (_e.target) {
                // eslint-disable-next-line @typescript-eslint/ban-ts-comment
                // @ts-ignore
                if (_e.target.hasAttribute('router-link')) {
                    this.navigation = 'link';
                    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
                    // @ts-ignore
                }
                else if (_e.composedPath().some((node) => node.nodeName === 'A')) {
                    this.navigation = 'client';
                }
            }
        }, {
            capture: true
        });
    }
    get action() {
        // Return a function which is bound to the flow instance, thus we can use
        // the syntax `...serverSideRoutes` in vaadin-router.
        return async (params) => {
            // Store last action pathname so as we can check it in events
            this.pathname = params.pathname;
            if ($wnd.Vaadin.connectionState.online) {
                try {
                    await this.flowInit();
                }
                catch (error) {
                    if (error instanceof FlowUiInitializationError) {
                        // error initializing Flow: assume connection lost
                        $wnd.Vaadin.connectionState.state = ConnectionState.CONNECTION_LOST;
                        return this.offlineStubAction();
                    }
                    else {
                        throw error;
                    }
                }
            }
            else {
                // insert an offline stub
                return this.offlineStubAction();
            }
            // When an action happens, navigation will be resolved `onBeforeEnter`
            this.container.onBeforeEnter = (ctx, cmd) => this.flowNavigate(ctx, cmd);
            // For covering the 'server -> client' use case
            this.container.onBeforeLeave = (ctx, cmd) => this.flowLeave(ctx, cmd);
            return this.container;
        };
    }
    // Send a remote call to `JavaScriptBootstrapUI` to check
    // whether navigation has to be cancelled.
    async flowLeave(ctx, cmd) {
        // server -> server, viewing offline stub, or browser is offline
        const { connectionState } = $wnd.Vaadin;
        if (this.pathname === ctx.pathname || !this.isFlowClientLoaded() || connectionState.offline) {
            return Promise.resolve({});
        }
        // 'server -> client'
        return new Promise((resolve) => {
            this.loadingStarted();
            // The callback to run from server side to cancel navigation
            this.container.serverConnected = (cancel) => {
                resolve(cmd && cancel ? cmd.prevent() : {});
                this.loadingFinished();
            };
            // Call server side to check whether we can leave the view
            flowRoot.$server.leaveNavigation(this.getFlowRoutePath(ctx), this.getFlowRouteQuery(ctx));
        });
    }
    // Send the remote call to `JavaScriptBootstrapUI` to render the flow
    // route specified by the context
    async flowNavigate(ctx, cmd) {
        if (this.response) {
            return new Promise((resolve) => {
                this.loadingStarted();
                // The callback to run from server side once the view is ready
                this.container.serverConnected = (cancel, redirectContext) => {
                    if (cmd && cancel) {
                        resolve(cmd.prevent());
                    }
                    else if (cmd && cmd.redirect && redirectContext) {
                        resolve(cmd.redirect(redirectContext.pathname));
                    }
                    else {
                        this.container.style.display = '';
                        resolve(this.container);
                    }
                    this.loadingFinished();
                };
                this.container.serverPaused = () => {
                    this.loadingFinished();
                };
                // Call server side to navigate to the given route
                flowRoot.$server.connectClient(this.getFlowRoutePath(ctx), this.getFlowRouteQuery(ctx), this.appShellTitle, history.state, this.navigation);
                // Default to history navigation trigger.
                // Link and client cases are handled by click listener in loadingFinished().
                this.navigation = 'history';
            });
        }
        else {
            // No server response => offline or erroneous connection
            return Promise.resolve(this.container);
        }
    }
    getFlowRoutePath(context) {
        return decodeURIComponent(context.pathname).replace(this.baseRegex, '');
    }
    getFlowRouteQuery(context) {
        return (context.search && context.search.substring(1)) || '';
    }
    // import flow client modules and initialize UI in server side.
    async flowInit() {
        // Do not start flow twice
        if (!this.isFlowClientLoaded()) {
            // show flow progress indicator
            this.loadingStarted();
            // Initialize server side UI
            this.response = await this.flowInitUi();
            const { pushScript, appConfig } = this.response;
            if (typeof pushScript === 'string') {
                await this.loadScript(pushScript);
            }
            const { appId } = appConfig;
            // Load bootstrap script with server side parameters
            const bootstrapMod = await import('./FlowBootstrap');
            await bootstrapMod.init(this.response);
            // Load custom modules defined by user
            if (typeof this.config.imports === 'function') {
                this.injectAppIdScript(appId);
                await this.config.imports();
            }
            // we use a custom tag for the flow app container
            const tag = `flow-container-${appId.toLowerCase()}`;
            const serverCreatedContainer = document.querySelector(tag);
            if (serverCreatedContainer) {
                this.container = serverCreatedContainer;
            }
            else {
                this.container = document.createElement(tag);
                this.container.id = appId;
            }
            flowRoot.$[appId] = this.container;
            // Load flow-client module
            const clientMod = await import('./FlowClient');
            await this.flowInitClient(clientMod);
            // hide flow progress indicator
            this.loadingFinished();
        }
        // It might be that components created from server expect that their content has been rendered.
        // Appending eagerly the container we avoid these kind of errors.
        // Note that the client router will move this container to the outlet if the navigation succeed
        if (this.container && !this.container.isConnected) {
            this.container.style.display = 'none';
            document.body.appendChild(this.container);
        }
        return this.response;
    }
    async loadScript(url) {
        return new Promise((resolve, reject) => {
            const script = document.createElement('script');
            script.onload = () => resolve();
            script.onerror = reject;
            script.src = url;
            document.body.appendChild(script);
        });
    }
    injectAppIdScript(appId) {
        const appIdWithoutHashCode = appId.substring(0, appId.lastIndexOf('-'));
        const scriptAppId = document.createElement('script');
        scriptAppId.type = 'module';
        scriptAppId.setAttribute('data-app-id', appIdWithoutHashCode);
        document.body.append(scriptAppId);
    }
    // After the flow-client javascript module has been loaded, this initializes flow UI
    // in the browser.
    async flowInitClient(clientMod) {
        clientMod.init();
        // client init is async, we need to loop until initialized
        return new Promise((resolve) => {
            const intervalId = setInterval(() => {
                // client `isActive() == true` while initializing or processing
                const initializing = Object.keys($wnd.Vaadin.Flow.clients)
                    .filter((key) => key !== 'TypeScript')
                    .reduce((prev, id) => prev || $wnd.Vaadin.Flow.clients[id].isActive(), false);
                if (!initializing) {
                    clearInterval(intervalId);
                    resolve();
                }
            }, 5);
        });
    }
    // Returns the `appConfig` object
    async flowInitUi() {
        // appConfig was sent in the index.html request
        const initial = $wnd.Vaadin && $wnd.Vaadin.TypeScript && $wnd.Vaadin.TypeScript.initial;
        if (initial) {
            $wnd.Vaadin.TypeScript.initial = undefined;
            return Promise.resolve(initial);
        }
        // send a request to the `JavaScriptBootstrapHandler`
        return new Promise((resolve, reject) => {
            const xhr = new XMLHttpRequest();
            const httpRequest = xhr;
            const requestPath = `?v-r=init&location=${encodeURIComponent(this.getFlowRoutePath(location))}&query=${encodeURIComponent(this.getFlowRouteQuery(location))}`;
            httpRequest.open('GET', requestPath);
            httpRequest.onerror = () => reject(new FlowUiInitializationError(`Invalid server response when initializing Flow UI.
        ${httpRequest.status}
        ${httpRequest.responseText}`));
            httpRequest.onload = () => {
                const contentType = httpRequest.getResponseHeader('content-type');
                if (contentType && contentType.indexOf('application/json') !== -1) {
                    resolve(JSON.parse(httpRequest.responseText));
                }
                else {
                    httpRequest.onerror();
                }
            };
            httpRequest.send();
        });
    }
    // Create shared connection state store and connection indicator
    addConnectionIndicator() {
        // add connection indicator to DOM
        ConnectionIndicator.create();
        // Listen to browser online/offline events and update the loading indicator accordingly.
        // Note: if flow-client is loaded, it instead handles the state transitions.
        $wnd.addEventListener('online', () => {
            if (!this.isFlowClientLoaded()) {
                // Send an HTTP HEAD request for sw.js to verify server reachability.
                // We do not expect sw.js to be cached, so the request goes to the
                // server rather than being served from local cache.
                // Require network-level failure to revert the state to CONNECTION_LOST
                // (HTTP error code is ok since it still verifies server's presence).
                $wnd.Vaadin.connectionState.state = ConnectionState.RECONNECTING;
                const http = new XMLHttpRequest();
                http.open('HEAD', 'sw.js');
                http.onload = () => {
                    $wnd.Vaadin.connectionState.state = ConnectionState.CONNECTED;
                };
                http.onerror = () => {
                    $wnd.Vaadin.connectionState.state = ConnectionState.CONNECTION_LOST;
                };
                // Postpone request to reduce potential net::ERR_INTERNET_DISCONNECTED
                // errors that sometimes occurs even if browser says it is online
                setTimeout(() => http.send(), 50);
            }
        });
        $wnd.addEventListener('offline', () => {
            if (!this.isFlowClientLoaded()) {
                $wnd.Vaadin.connectionState.state = ConnectionState.CONNECTION_LOST;
            }
        });
    }
    async offlineStubAction() {
        const offlineStub = document.createElement('iframe');
        const offlineStubPath = './offline-stub.html';
        offlineStub.setAttribute('src', offlineStubPath);
        offlineStub.setAttribute('style', 'width: 100%; height: 100%; border: 0');
        this.response = undefined;
        let onlineListener;
        const removeOfflineStubAndOnlineListener = () => {
            if (onlineListener !== undefined) {
                $wnd.Vaadin.connectionState.removeStateChangeListener(onlineListener);
                onlineListener = undefined;
            }
        };
        offlineStub.onBeforeEnter = (ctx, _cmds, router) => {
            onlineListener = () => {
                if ($wnd.Vaadin.connectionState.online) {
                    removeOfflineStubAndOnlineListener();
                    router.render(ctx, false);
                }
            };
            $wnd.Vaadin.connectionState.addStateChangeListener(onlineListener);
        };
        offlineStub.onBeforeLeave = (_ctx, _cmds, _router) => {
            removeOfflineStubAndOnlineListener();
        };
        return offlineStub;
    }
    isFlowClientLoaded() {
        return this.response !== undefined;
    }
}
//# sourceMappingURL=Flow.js.map