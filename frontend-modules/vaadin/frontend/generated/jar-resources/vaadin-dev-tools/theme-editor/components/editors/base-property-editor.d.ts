import { CSSResultGroup, LitElement, PropertyValues, TemplateResult } from 'lit';
import { ComponentElementMetadata, CssPropertyMetadata } from '../../metadata/model';
import { ComponentTheme, ThemePropertyValue } from '../../model';
export declare class ThemePropertyValueChangeEvent extends CustomEvent<{
    element: ComponentElementMetadata;
    property: CssPropertyMetadata;
    value: string;
}> {
    constructor(element: ComponentElementMetadata, property: CssPropertyMetadata, value: string);
}
export declare abstract class BasePropertyEditor extends LitElement {
    static get styles(): CSSResultGroup;
    elementMetadata: ComponentElementMetadata;
    propertyMetadata: CssPropertyMetadata;
    theme: ComponentTheme;
    protected propertyValue?: ThemePropertyValue;
    protected value: string;
    protected update(changedProperties: PropertyValues): void;
    render(): TemplateResult<1>;
    protected abstract renderEditor(): TemplateResult;
    protected updateValueFromTheme(): void;
    protected dispatchChange(value: string): void;
}
export declare class PropertyPresets {
    private _values;
    private _rawValues;
    get values(): string[];
    get rawValues(): {
        [key: string]: string;
    };
    constructor(propertyMetadata?: CssPropertyMetadata);
    tryMapToRawValue(presetOrValue: string): string;
    tryMapToPreset(value: string): string;
    findPreset(rawValue: string): string | undefined;
}
export declare class TextInputChangeEvent extends CustomEvent<{
    value: string;
}> {
    constructor(value: string);
}
export declare class TextInput extends LitElement {
    static get styles(): import("lit").CSSResult;
    value: string;
    showClearButton: boolean;
    protected update(changedProperties: PropertyValues): void;
    render(): TemplateResult<1>;
    private handleInputChange;
    private handleClearClick;
}
