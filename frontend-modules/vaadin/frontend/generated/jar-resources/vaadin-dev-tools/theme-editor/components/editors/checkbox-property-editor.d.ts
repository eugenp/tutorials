import { TemplateResult } from 'lit';
import { BasePropertyEditor } from './base-property-editor';
export declare class CheckboxPropertyEditor extends BasePropertyEditor {
    static get styles(): import("lit").CSSResultGroup[];
    handleInputChange(e: Event): void;
    protected renderEditor(): TemplateResult;
}
