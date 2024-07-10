import { PropertyValues, TemplateResult } from 'lit';
import './color-picker';
import { BasePropertyEditor } from './base-property-editor';
export declare class ColorPropertyEditor extends BasePropertyEditor {
    static get styles(): import("lit").CSSResultGroup[];
    private presets;
    protected update(changedProperties: PropertyValues): void;
    protected renderEditor(): TemplateResult;
    private handleInputChange;
    private handleColorPickerChange;
    private handleColorPickerCommit;
    private handleColorPickerCancel;
    protected dispatchChange(value: string): void;
    protected updateValueFromTheme(): void;
}
