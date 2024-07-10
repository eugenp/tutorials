import { ThemeEditorApi } from './api';
type HistoryCustomizerFn = () => void;
export interface HistoryEntry {
    requestId: string;
    execute?: HistoryCustomizerFn;
    rollback?: HistoryCustomizerFn;
}
export interface ThemeEditorHistoryActions {
    allowUndo: boolean;
    allowRedo: boolean;
}
export declare class ThemeEditorHistory {
    private api;
    constructor(api: ThemeEditorApi);
    get allowUndo(): boolean;
    get allowRedo(): boolean;
    get allowedActions(): ThemeEditorHistoryActions;
    push(requestId: string, execute?: HistoryCustomizerFn, rollback?: HistoryCustomizerFn): ThemeEditorHistoryActions;
    undo(): Promise<ThemeEditorHistoryActions>;
    redo(): Promise<ThemeEditorHistoryActions>;
    static clear(): void;
}
export {};
