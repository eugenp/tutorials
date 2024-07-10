import { TemplateResult } from 'lit';
import { BasePropertyEditor, TextInputChangeEvent } from './base-property-editor';
export declare class TextPropertyEditor extends BasePropertyEditor {
    handleInputChange(e: TextInputChangeEvent): void;
    protected renderEditor(): TemplateResult;
}
