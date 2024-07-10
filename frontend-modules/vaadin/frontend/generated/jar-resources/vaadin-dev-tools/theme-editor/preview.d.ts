declare class ThemePreview {
    private _stylesheet?;
    private _localClassNameMap;
    get stylesheet(): CSSStyleSheet;
    add(css: string): void;
    clear(): void;
    previewLocalClassName(element?: HTMLElement, className?: string): void;
    private ensureStylesheet;
}
export declare const themePreview: ThemePreview;
export {};
