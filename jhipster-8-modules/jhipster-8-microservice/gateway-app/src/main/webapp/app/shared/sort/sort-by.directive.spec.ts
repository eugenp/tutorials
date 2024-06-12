import { Component, DebugElement, inject } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { FaIconComponent, FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fas, faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';

import { SortByDirective } from './sort-by.directive';
import { SortDirective } from './sort.directive';
import { sortStateSignal } from './sort-state';

@Component({
  standalone: true,
  imports: [SortDirective, SortByDirective, FaIconComponent],
  template: `
    <table>
      <thead>
        <tr jhiSort [sortState]="sortState" (sortChange)="transition($event)">
          <th jhiSortBy="name">
            ID
            <fa-icon [icon]="'sort'"></fa-icon>
          </th>
        </tr>
      </thead>
    </table>
  `,
})
class TestSortByDirectiveComponent {
  sortState = sortStateSignal({ predicate: 'name' });
  sortAllowed = true;
  transition = jest.fn();

  private library = inject(FaIconLibrary);

  constructor() {
    this.library.addIconPacks(fas);
    this.library.addIcons(faSort, faSortDown, faSortUp);
  }
}

describe('Directive: SortByDirective', () => {
  let component: TestSortByDirectiveComponent;
  let fixture: ComponentFixture<TestSortByDirectiveComponent>;
  let tableHead: DebugElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TestSortByDirectiveComponent],
    }).compileComponents();
    fixture = TestBed.createComponent(TestSortByDirectiveComponent);
    component = fixture.componentInstance;
    tableHead = fixture.debugElement.query(By.directive(SortByDirective));
  });

  it('should have a neutral state for predicate column and undefined order value', () => {
    // GIVEN
    component.sortState.set({ predicate: 'name' });
    const sortByDirective = tableHead.injector.get(SortByDirective);

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(sortByDirective.jhiSortBy).toEqual('name');
    expect(sortByDirective.iconComponent?.icon).toEqual(faSort.iconName);
  });

  it('should have an asc state for predicate column and true asc value', () => {
    // GIVEN
    component.sortState.set({ predicate: 'name', order: 'asc' });
    const sortByDirective = tableHead.injector.get(SortByDirective);

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(sortByDirective.jhiSortBy).toEqual('name');
    expect(sortByDirective.iconComponent?.icon).toEqual(faSortUp.iconName);
  });

  it('should have a desc state for predicate column and desc value', () => {
    // GIVEN
    component.sortState.set({ predicate: 'name', order: 'desc' });
    const sortByDirective = tableHead.injector.get(SortByDirective);

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(sortByDirective.jhiSortBy).toEqual('name');
    expect(sortByDirective.iconComponent?.icon).toEqual(faSortDown.iconName);
  });

  it('should have a neutral state for non-predicate column', () => {
    // GIVEN
    component.sortState.set({ predicate: 'non-existing-column', order: 'asc' });
    const sortByDirective = tableHead.injector.get(SortByDirective);

    // WHEN
    fixture.detectChanges();

    // THEN
    expect(sortByDirective.jhiSortBy).toEqual('name');
    expect(sortByDirective.iconComponent?.icon).toEqual(faSort.iconName);
  });

  it('multiple clicks at same component, should call SortDirective sort', () => {
    // GIVEN
    const sortDirective = tableHead.injector.get(SortDirective);
    sortDirective.sort = jest.fn();

    // WHEN
    fixture.detectChanges();
    tableHead.triggerEventHandler('click', null);
    tableHead.triggerEventHandler('click', null);

    // THEN
    expect(sortDirective.sort).toHaveBeenCalledTimes(2);
    expect(sortDirective.sort).toHaveBeenNthCalledWith(1, 'name');
    expect(sortDirective.sort).toHaveBeenNthCalledWith(2, 'name');
  });
});
