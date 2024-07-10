import { PropertyValues, TemplateResult } from 'lit';
import { BasePropertyEditor } from './base-property-editor';
export declare class RangePropertyEditor extends BasePropertyEditor {
    static get styles(): import("lit").CSSResultGroup[];
    private selectedPresetIndex;
    private presets;
    protected update(changedProperties: PropertyValues): void;
    protected renderEditor(): TemplateResult;
    private handleSliderInput;
    private handleSliderChange;
    private handleValueChange;
    protected dispatchChange(value: string): void;
    protected updateValueFromTheme(): void;
    private updateSliderValue;
}
