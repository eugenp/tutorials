import { ComponentReference } from '../../component-util';
import { ComponentMetadata } from '../metadata/model';
export declare class ComponentOverlayManager {
    currentActiveComponent: ComponentReference | null;
    currentActiveComponentMetaData: ComponentMetadata | null;
    componentPicked: (component: ComponentReference, metaData: ComponentMetadata) => Promise<void>;
    showOverlay: () => void;
    hideOverlay: () => void;
    reset: () => void;
}
export declare const componentOverlayManager: ComponentOverlayManager;
export declare const defaultShowOverlay: (component: ComponentReference) => void;
export declare const defaultHideOverlay: (component: ComponentReference) => void;
export declare const showOverlayMixin: (element: any, overlayMixinElement: any, overlay: any) => void;
/**
 * Hide overlay of given element
 * @param element
 * @param overlayMixinElement
 * @param overlay
 */
export declare const hideOverlayMixin: (element: any, overlayMixinElement: any, overlay: any) => void;
/**
 * Creates a click event that checks the clicked target. If clicked target is dev-tools or the element itself overlay becomes visible otherwise becomes hidden.
 * @param element
 * @param overlayMixinElement
 */
export declare const createDocumentClickEvent: (element: HTMLElement, overlayMixinElement: any) => (e: MouseEvent) => void;
