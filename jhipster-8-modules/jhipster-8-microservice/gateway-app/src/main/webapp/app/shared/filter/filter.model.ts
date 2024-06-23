import { ParamMap } from '@angular/router';
import { Subject } from 'rxjs';

export interface IFilterOptions {
  readonly filterChanges: Subject<FilterOption[]>;
  get filterOptions(): IFilterOption[];
  hasAnyFilterSet(): boolean;
  clear(): boolean;
  initializeFromParams(params: ParamMap): boolean;
  addFilter(name: string, ...values: string[]): boolean;
  removeFilter(name: string, value: string): boolean;
}

export interface IFilterOption {
  name: string;
  values: string[];
  nameAsQueryParam(): string;
}

export class FilterOption implements IFilterOption {
  constructor(
    public name: string,
    public values: string[] = [],
  ) {
    this.values = [...new Set(values)];
  }

  nameAsQueryParam(): string {
    return 'filter[' + this.name + ']';
  }

  isSet(): boolean {
    return this.values.length > 0;
  }

  addValue(...values: string[]): boolean {
    const missingValues = values.filter(value => value && !this.values.includes(value));
    if (missingValues.length > 0) {
      this.values.push(...missingValues);
      return true;
    }
    return false;
  }

  removeValue(value: string): boolean {
    const indexOf = this.values.indexOf(value);
    if (indexOf === -1) {
      return false;
    }

    this.values.splice(indexOf, 1);
    return true;
  }

  clone(): FilterOption {
    return new FilterOption(this.name, this.values.concat());
  }

  equals(other: IFilterOption): boolean {
    return (
      this.name === other.name &&
      this.values.length === other.values.length &&
      this.values.every(thisValue => other.values.includes(thisValue)) &&
      other.values.every(otherValue => this.values.includes(otherValue))
    );
  }
}

export class FilterOptions implements IFilterOptions {
  readonly filterChanges: Subject<FilterOption[]> = new Subject();
  private _filterOptions: FilterOption[];

  constructor(filterOptions: FilterOption[] = []) {
    this._filterOptions = filterOptions;
  }

  get filterOptions(): FilterOption[] {
    return this._filterOptions.filter(option => option.isSet());
  }

  hasAnyFilterSet(): boolean {
    return this._filterOptions.some(e => e.isSet());
  }

  clear(): boolean {
    const hasFields = this.hasAnyFilterSet();
    this._filterOptions = [];
    if (hasFields) {
      this.changed();
    }
    return hasFields;
  }

  initializeFromParams(params: ParamMap): boolean {
    const oldFilters: FilterOptions = this.clone();

    this._filterOptions = [];

    const filterRegex = /filter\[(.+)\]/;
    params.keys
      .filter(paramKey => filterRegex.test(paramKey))
      .forEach(matchingParam => {
        const matches = filterRegex.exec(matchingParam);
        if (matches && matches.length > 1) {
          this.getFilterOptionByName(matches[1], true).addValue(...params.getAll(matchingParam));
        }
      });

    if (oldFilters.equals(this)) {
      return false;
    }
    return true;
  }

  addFilter(name: string, ...values: string[]): boolean {
    if (this.getFilterOptionByName(name, true).addValue(...values)) {
      this.changed();
      return true;
    }
    return false;
  }

  removeFilter(name: string, value: string): boolean {
    if (this.getFilterOptionByName(name)?.removeValue(value)) {
      this.changed();
      return true;
    }
    return false;
  }

  protected changed(): void {
    this.filterChanges.next(this.filterOptions.map(option => option.clone()));
  }

  protected equals(other: FilterOptions): boolean {
    const thisFilters = this.filterOptions;
    const otherFilters = other.filterOptions;
    if (thisFilters.length !== otherFilters.length) {
      return false;
    }
    return thisFilters.every(option => other.getFilterOptionByName(option.name)?.equals(option));
  }

  protected clone(): FilterOptions {
    return new FilterOptions(this.filterOptions.map(option => new FilterOption(option.name, option.values.concat())));
  }

  protected getFilterOptionByName(name: string, add: true): FilterOption;
  protected getFilterOptionByName(name: string, add: false): FilterOption | null;
  protected getFilterOptionByName(name: string): FilterOption | null;
  protected getFilterOptionByName(name: string, add = false): FilterOption | null {
    const addOption = (option: FilterOption): FilterOption => {
      this._filterOptions.push(option);
      return option;
    };

    return this._filterOptions.find(thisOption => thisOption.name === name) ?? (add ? addOption(new FilterOption(name)) : null);
  }
}
