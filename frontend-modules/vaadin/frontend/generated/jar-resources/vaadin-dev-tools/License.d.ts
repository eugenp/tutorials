import { ServerMessage } from "./vaadin-dev-tools";
export interface Product {
    name: string;
    version: string;
}
export interface ProductAndMessage {
    message: string;
    messageHtml?: string;
    product: Product;
}
export declare const findAll: (element: Element | ShadowRoot | Document, tags: string[]) => Element[];
export declare const licenseCheckOk: (data: Product) => void;
export declare const licenseCheckFailed: (data: ProductAndMessage) => void;
export declare const licenseCheckNoKey: (data: ProductAndMessage) => void;
export declare const handleLicenseMessage: (message: ServerMessage) => boolean;
export declare const licenseInit: () => void;
