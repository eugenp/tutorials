import { LitElement } from 'lit';
import { VaadinDevTools } from './vaadin-dev-tools.js';
export declare class VaadinDevToolsLog extends LitElement {
    _devTools: VaadinDevTools;
    protected createRenderRoot(): Element | ShadowRoot;
    activate(): void;
    render(): import("lit").TemplateResult<1>;
}
