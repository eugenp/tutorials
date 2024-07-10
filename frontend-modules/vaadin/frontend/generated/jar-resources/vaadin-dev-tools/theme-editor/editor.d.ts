import { LitElement, PropertyValues, TemplateResult } from 'lit';
import { PickerProvider } from '../component-picker';
import { ThemeEditorState } from './model';
import { WebSocketConnection } from '../websocket-connection';
import './components/class-name-editor';
import './components/scope-selector';
import './components/property-list';
import '../component-picker.js';
import { OpenCssEvent } from './components/property-list';
export declare class ThemeEditor extends LitElement {
    expanded: boolean;
    themeEditorState: ThemeEditorState;
    pickerProvider: PickerProvider;
    connection: WebSocketConnection;
    private api;
    private history;
    private historyActions?;
    private context;
    /**
     * Base theme detected from existing CSS files for the selected component
     */
    private baseTheme;
    /**
     * Currently edited theme modifications for the selected component since the
     * last reload
     */
    private editedTheme;
    /**
     * The effective theme for the selected component, including base theme and
     * previously saved modifications
     */
    private effectiveTheme;
    private markedAsUsed;
    private undoRedoListener;
    static get styles(): import("lit").CSSResult;
    protected firstUpdated(): void;
    activate(): void;
    deactivate(): void;
    protected update(changedProperties: PropertyValues): void;
    disconnectedCallback(): void;
    render(): TemplateResult<1>;
    renderMissingThemeNotice(): TemplateResult<1>;
    renderPropertyList(): TemplateResult<1> | null;
    handleShowComponent(): void;
    handleOpenCss(event: OpenCssEvent): Promise<void>;
    renderPicker(): TemplateResult<1>;
    renderLocalClassNameEditor(): TemplateResult<1> | null;
    private handleClassNameChange;
    private pickComponent;
    private handleScopeChange;
    private handlePropertyChange;
    private handleUndo;
    private handleRedo;
    private ensureLocalClassName;
    private refreshComponentAndTheme;
    private refreshTheme;
    private highlightElement;
    private removeElementHighlight;
}
