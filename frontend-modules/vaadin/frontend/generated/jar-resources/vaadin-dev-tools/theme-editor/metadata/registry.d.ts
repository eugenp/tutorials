import { ComponentReference } from '../../component-util';
import { ComponentMetadata } from './model';
type ModuleLoader = (modulePath: string) => Promise<any>;
export declare class MetadataRegistry {
    private loader;
    private metadata;
    constructor(loader?: ModuleLoader);
    getMetadata(component: ComponentReference): Promise<ComponentMetadata | null>;
}
export declare const metadataRegistry: MetadataRegistry;
export {};
