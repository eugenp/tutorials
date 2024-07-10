import { LitElement, PropertyValues } from 'lit';
import { ComponentMetadata } from '../metadata/model';
import { ThemeScope } from '../model';
export declare class ScopeChangeEvent extends CustomEvent<{
    value: ThemeScope;
}> {
    constructor(value: ThemeScope);
}
export declare class ScopeSelector extends LitElement {
    static get styles(): import("lit").CSSResult;
    value: ThemeScope;
    metadata?: ComponentMetadata;
    private select?;
    protected update(changedProperties: PropertyValues): void;
    render(): import("lit").TemplateResult<1>;
    private selectRenderer;
    private handleValueChange;
}
