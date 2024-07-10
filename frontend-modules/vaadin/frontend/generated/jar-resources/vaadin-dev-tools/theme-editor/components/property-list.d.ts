import { LitElement } from 'lit';
import { ComponentElementMetadata, ComponentMetadata } from '../metadata/model';
import { ComponentTheme } from '../model';
import './editors/checkbox-property-editor';
import './editors/text-property-editor';
import './editors/range-property-editor';
import './editors/color-property-editor';
export declare class OpenCssEvent extends CustomEvent<{
    element: ComponentElementMetadata;
}> {
    constructor(element: ComponentElementMetadata);
}
export declare class PropertyList extends LitElement {
    static get styles(): import("lit").CSSResult;
    metadata: ComponentMetadata;
    theme: ComponentTheme;
    render(): import("lit").TemplateResult<1>;
    private renderSection;
    private handleOpenCss;
    private renderPropertyEditor;
}
