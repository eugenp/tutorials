import { LitElement } from 'lit';
export declare class InfoTab extends LitElement {
    private _devTools;
    private serverInfo;
    protected createRenderRoot(): Element | ShadowRoot;
    render(): import("lit").TemplateResult<1>;
    handleMessage(message: any): boolean;
    copyInfoToClipboard(): void;
}
