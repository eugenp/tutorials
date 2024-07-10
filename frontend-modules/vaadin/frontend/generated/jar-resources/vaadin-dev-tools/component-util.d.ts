export type ComponentReference = {
    nodeId: number;
    uiId: number;
    element?: HTMLElement;
    highlightElement?: HTMLElement;
};
export declare function getComponents(element: HTMLElement): ComponentReference[];
export declare function getComponent(element: HTMLElement): ComponentReference;
export declare function deepContains(container: HTMLElement, node: Node): boolean;
