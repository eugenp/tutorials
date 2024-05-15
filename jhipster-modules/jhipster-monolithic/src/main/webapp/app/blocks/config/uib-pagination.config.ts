import { ITEMS_PER_PAGE } from '../../shared';
import { Injectable } from '@angular/core';
import { NgbPaginationConfig} from '@ng-bootstrap/ng-bootstrap';

@Injectable()
export class PaginationConfig {
    constructor(private config: NgbPaginationConfig) {
        config.boundaryLinks = true;
        config.maxSize = 5;
        config.pageSize = ITEMS_PER_PAGE;
        config.size = 'sm';
    }
}
