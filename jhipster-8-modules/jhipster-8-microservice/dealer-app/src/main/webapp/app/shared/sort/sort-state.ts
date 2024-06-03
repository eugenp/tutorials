import { signal, Signal, WritableSignal } from '@angular/core';

export type SortOrder = 'asc' | 'desc';

export type SortState = { predicate?: string; order?: SortOrder };

export type SortStateSignal = Signal<SortState>;

export const sortStateSignal = (state: SortState): WritableSignal<SortState> =>
  signal<SortState>(state, {
    equal: (a, b) => a.predicate === b.predicate && a.order === b.order,
  });
