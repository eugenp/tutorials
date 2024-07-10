/**
 * Resolves HTMLElement that should be considered instead of directly picked element.
 *
 * Used with overlays that have different HTMLElements visible than present in node tree.
 *
 * Resolvers cannot be added to component metadata as component metadata is dynamically imported after being picked.
 *
 * Using Polymer __dataHost property to get base Vaadin component.
 *
 * TODO: Refactor required after moving to Lit components
 */
declare class ComponentResolver {
    resolveElement(element: HTMLElement): HTMLElement;
}
declare class ComponentHighlightResolver {
    resolveElement(element: HTMLElement): HTMLElement;
}
export declare const componentResolver: ComponentResolver;
export declare const componentHighlightResolver: ComponentHighlightResolver;
export {};
