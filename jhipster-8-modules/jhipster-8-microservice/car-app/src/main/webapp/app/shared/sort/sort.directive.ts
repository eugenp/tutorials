import { Directive, EventEmitter, Input, Output } from '@angular/core';
import { SortOrder, SortState, SortStateSignal } from './sort-state';

export interface SortChangeDirective<T> {
  sortChange: EventEmitter<SortState>;

  sort(field: T): void;
}

@Directive({
  standalone: true,
  selector: '[jhiSort]',
})
export class SortDirective implements SortChangeDirective<string> {
  @Input() sortState!: SortStateSignal;

  @Output() sortChange = new EventEmitter<SortState>();

  sort(field: string): void {
    const { predicate, order } = this.sortState();
    const toggle = (): SortOrder => (order === 'asc' ? 'desc' : 'asc');
    this.sortChange.emit({ predicate: field, order: field !== predicate ? 'asc' : toggle() });
  }
}
