import { ComponentElementMetadata, ComponentMetadata } from './metadata/model';
import { ServerCssRule } from './api';
import { ComponentReference } from '../component-util';
export declare enum ThemeEditorState {
    disabled = "disabled",
    enabled = "enabled",
    missing_theme = "missing_theme"
}
export declare enum ThemeScope {
    local = "local",
    global = "global"
}
export interface SelectorScope {
    themeScope: ThemeScope;
    localClassName?: string;
}
export interface ThemeContext {
    scope: ThemeScope;
    metadata?: ComponentMetadata;
    component: ComponentReference;
    accessible?: boolean;
    localClassName?: string;
    suggestedClassName?: string;
}
export interface ThemePropertyValue {
    elementSelector: string;
    propertyName: string;
    value: string;
    modified: boolean;
}
export declare class ComponentTheme {
    private _metadata;
    private _properties;
    constructor(metadata: ComponentMetadata);
    get metadata(): ComponentMetadata;
    get properties(): ThemePropertyValue[];
    getPropertyValue(elementSelector: string, propertyName: string): ThemePropertyValue;
    updatePropertyValue(elementSelector: string, propertyName: string, value: string, modified?: boolean): void;
    addPropertyValues(values: ThemePropertyValue[]): void;
    getPropertyValuesForElement(elementSelector: string): ThemePropertyValue[];
    static combine(...themes: ComponentTheme[]): ComponentTheme;
    static fromServerRules(metadata: ComponentMetadata, scope: SelectorScope, rules: ServerCssRule[]): ComponentTheme;
}
export declare function createScopedSelector(element: ComponentElementMetadata, scope: SelectorScope): string;
export declare function generateThemeRule(element: ComponentElementMetadata, scope: SelectorScope, propertyName: string, value: string): ServerCssRule;
export declare function generateThemeRuleCss(rule: ServerCssRule): string;
