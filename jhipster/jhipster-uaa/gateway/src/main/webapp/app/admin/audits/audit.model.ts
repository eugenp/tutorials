import { AuditData } from './audit-data.model';

export class Audit {
    constructor(public data: AuditData, public principal: string, public timestamp: string, public type: string) {}
}
