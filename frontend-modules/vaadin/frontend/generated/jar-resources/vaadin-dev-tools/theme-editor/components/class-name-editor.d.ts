import { LitElement, PropertyValues } from 'lit';
import './editors/base-property-editor';
export declare class ClassNameChangeEvent extends CustomEvent<{
    value: string;
}> {
    constructor(value: string);
}
export declare class ClassNameEditor extends LitElement {
    static get styles(): import("lit").CSSResult[];
    className: string;
    private editedClassName;
    private invalid;
    protected update(changedProperties: PropertyValues): void;
    render(): import("lit").TemplateResult<1>;
    private handleInputChange;
}
