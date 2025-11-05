import { inject, Injectable } from '@angular/core';
import { NgbPaginationConfig } from '@ng-bootstrap/ng-bootstrap';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';

@Injectable({ providedIn: 'root' })
export class PaginationConfig {
  private config = inject(NgbPaginationConfig);
  constructor() {
    this.config.boundaryLinks = true;
    this.config.maxSize = 5;
    this.config.pageSize = ITEMS_PER_PAGE;
    this.config.size = 'sm';
  }
}
