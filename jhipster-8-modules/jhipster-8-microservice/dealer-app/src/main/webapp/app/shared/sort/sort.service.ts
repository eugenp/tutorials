import { Injectable } from '@angular/core';
import { SortState } from './sort-state';

@Injectable({ providedIn: 'root' })
export class SortService {
  private collator = new Intl.Collator(undefined, {
    numeric: true,
    sensitivity: 'base',
  });

  public startSort({ predicate, order }: Required<SortState>, fallback?: Required<SortState>): (a: any, b: any) => number {
    const multiply = order === 'desc' ? -1 : 1;
    return (a: any, b: any) => {
      const compare = this.collator.compare(a[predicate], b[predicate]);
      if (compare === 0 && fallback) {
        return this.startSort(fallback)(a, b);
      }
      return compare * multiply;
    };
  }

  public parseSortParam(sortParam: string | undefined): SortState {
    if (sortParam?.includes(',')) {
      const split = sortParam.split(',');
      if (split[0]) {
        return { predicate: split[0], order: split[1] as any };
      }
    }
    return { predicate: sortParam?.length ? sortParam : undefined };
  }

  public buildSortParam({ predicate, order }: SortState, fallback?: string): string[] {
    const sortParam = predicate && order ? [`${predicate},${order}`] : [];
    if (fallback && predicate !== fallback) {
      sortParam.push(`${fallback},asc`);
    }
    return sortParam;
  }
}
