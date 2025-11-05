import { Component, Input } from '@angular/core';

/**
 * A component that will take care of item count statistics of a pagination.
 */
@Component({
  standalone: true,
  selector: 'jhi-item-count',
  template: ` <div>Showing {{ first }} - {{ second }} of {{ total }} items.</div> `,
})
export default class ItemCountComponent {
  /**
   * @param params  Contains parameters for component:
   *                    page          Current page number
   *                    totalItems    Total number of items
   *                    itemsPerPage  Number of items per page
   */
  @Input() set params(params: { page?: number; totalItems?: number; itemsPerPage?: number }) {
    if (params.page && params.totalItems !== undefined && params.itemsPerPage) {
      this.first = (params.page - 1) * params.itemsPerPage + 1;
      this.second = params.page * params.itemsPerPage < params.totalItems ? params.page * params.itemsPerPage : params.totalItems;
    } else {
      this.first = undefined;
      this.second = undefined;
    }
    this.total = params.totalItems;
  }

  first?: number;
  second?: number;
  total?: number;
}
