import { LitElement, PropertyValues, TemplateResult } from 'lit';
import { ComponentReference } from './component-util.js';
import './shim.js';
import { Shim } from './shim.js';
export interface PickerOptions {
    infoTemplate: TemplateResult;
    pickCallback: (component: ComponentReference) => void;
}
export type PickerProvider = () => ComponentPicker;
/**
 * When active, shows a component picker that allows the user to select an element with a server side counterpart.
 */
export declare class ComponentPicker extends LitElement {
    active: boolean;
    options?: PickerOptions;
    components: ComponentReference[];
    selected: number;
    highlighted?: HTMLElement;
    overlayElement: HTMLElement;
    shim: Shim;
    static styles: import("lit").CSSResult[];
    constructor();
    connectedCallback(): void;
    disconnectedCallback(): void;
    render(): TemplateResult<1> | null;
    open(options: PickerOptions): void;
    close(): void;
    update(changedProperties: PropertyValues): void;
    mouseMoveEvent(e: MouseEvent): void;
    shimKeydown(e: CustomEvent): void;
    shimMove(e: CustomEvent): void;
    shimClick(_e: CustomEvent): void;
    pickSelectedComponent(): void;
    highlight(componentRef: ComponentReference | undefined): void;
}
