import { ComponentTheme } from './model';
import { ComponentMetadata } from './metadata/model';
import { ComponentReference } from '../component-util';
export declare function detectTheme(metadata: ComponentMetadata): Promise<ComponentTheme>;
export declare function detectElementDisplayName(component: ComponentReference): string | null;
