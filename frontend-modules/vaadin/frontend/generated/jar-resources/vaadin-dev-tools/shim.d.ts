import { LitElement } from 'lit';
/**
 * Blocks interaction with all elements on the page. Forwards mouse and key events as custom shim-* events.
 */
export declare class Shim extends LitElement {
    highlighted?: HTMLElement;
    static shadowRootOptions: {
        delegatesFocus: boolean;
        mode: ShadowRootMode;
        slotAssignment?: SlotAssignmentMode | undefined;
        customElements?: CustomElementRegistry | undefined;
        registry?: CustomElementRegistry | undefined;
    };
    static styles: import("lit").CSSResult[];
    render(): import("lit").TemplateResult<1>;
    onClick(e: MouseEvent): void;
    onMouseMove(e: MouseEvent): void;
    onKeyDown(e: KeyboardEvent): void;
    getTargetElement(e: MouseEvent): HTMLElement;
}
