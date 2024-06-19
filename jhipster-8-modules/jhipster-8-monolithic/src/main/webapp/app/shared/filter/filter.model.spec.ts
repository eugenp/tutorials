import { convertToParamMap, ParamMap, Params } from '@angular/router';
import { FilterOptions, FilterOption } from './filter.model';

describe('FilterModel Tests', () => {
  describe('FilterOption', () => {
    let filterOption: FilterOption;

    beforeEach(() => {
      filterOption = new FilterOption('foo', ['bar', 'bar2']);
    });

    it('nameAsQueryParam returns query key', () => {
      expect(filterOption.nameAsQueryParam()).toEqual('filter[foo]');
    });

    describe('addValue', () => {
      it('adds multiples unique values and returns true', () => {
        const ret = filterOption.addValue('bar2', 'bar3', 'bar4');
        expect(filterOption.values).toMatchObject(['bar', 'bar2', 'bar3', 'bar4']);
        expect(ret).toBe(true);
      });
      it("doesn't adds duplicated values and return false", () => {
        const ret = filterOption.addValue('bar', 'bar2');
        expect(filterOption.values).toMatchObject(['bar', 'bar2']);
        expect(ret).toBe(false);
      });
    });

    describe('removeValue', () => {
      it('removes the exiting value and return true', () => {
        const ret = filterOption.removeValue('bar');
        expect(filterOption.values).toMatchObject(['bar2']);
        expect(ret).toBe(true);
      });
      it("doesn't removes the value and return false", () => {
        const ret = filterOption.removeValue('foo');
        expect(filterOption.values).toMatchObject(['bar', 'bar2']);
        expect(ret).toBe(false);
      });
    });

    describe('equals', () => {
      it('returns true to matching options', () => {
        const otherFilterOption = new FilterOption(filterOption.name, filterOption.values.concat());
        expect(filterOption.equals(otherFilterOption)).toBe(true);
        expect(otherFilterOption.equals(filterOption)).toBe(true);
      });
      it('returns false to different name', () => {
        const otherFilterOption = new FilterOption('bar', filterOption.values.concat());
        expect(filterOption.equals(otherFilterOption)).toBe(false);
        expect(otherFilterOption.equals(filterOption)).toBe(false);
      });
      it('returns false to different values', () => {
        const otherFilterOption = new FilterOption('bar', []);
        expect(filterOption.equals(otherFilterOption)).toBe(false);
        expect(otherFilterOption.equals(filterOption)).toBe(false);
      });
    });
  });

  describe('FilterOptions', () => {
    describe('hasAnyFilterSet', () => {
      it('with empty options returns false', () => {
        const filters = new FilterOptions();
        expect(filters.hasAnyFilterSet()).toBe(false);
      });
      it('with options and empty values returns false', () => {
        const filters = new FilterOptions([new FilterOption('foo'), new FilterOption('bar')]);
        expect(filters.hasAnyFilterSet()).toBe(false);
      });
      it('with option and value returns true', () => {
        const filters = new FilterOptions([new FilterOption('foo', ['bar'])]);
        expect(filters.hasAnyFilterSet()).toBe(true);
      });
    });

    describe('clear', () => {
      it("removes empty filters and doesn't emit next element", () => {
        const filters = new FilterOptions([new FilterOption('foo'), new FilterOption('bar')]);
        jest.spyOn(filters.filterChanges, 'next');

        filters.clear();

        expect(filters.filterChanges.next).not.toBeCalled();
        expect(filters.filterOptions).toMatchObject([]);
      });
      it('removes empty filters and emits next element', () => {
        const filters = new FilterOptions([new FilterOption('foo', ['existingFoo1']), new FilterOption('bar')]);
        jest.spyOn(filters.filterChanges, 'next');

        filters.clear();

        expect(filters.filterChanges.next).toHaveBeenCalledTimes(1);
        expect(filters.filterOptions).toMatchObject([]);
      });
    });

    describe('addFilter', () => {
      it('adds a non existing FilterOption, returns true and emit next element', () => {
        const filters = new FilterOptions([new FilterOption('foo', ['existingFoo1', 'existingFoo2']), new FilterOption('bar')]);
        jest.spyOn(filters.filterChanges, 'next');

        const result = filters.addFilter('addedFilter', 'addedValue');

        expect(result).toBe(true);
        expect(filters.filterChanges.next).toHaveBeenCalledTimes(1);
        expect(filters.filterOptions).toMatchObject([
          { name: 'foo', values: ['existingFoo1', 'existingFoo2'] },
          { name: 'addedFilter', values: ['addedValue'] },
        ]);
      });
      it('adds a non existing value to FilterOption, returns true and emit next element', () => {
        const filters = new FilterOptions([new FilterOption('foo', ['existingFoo1', 'existingFoo2']), new FilterOption('bar')]);
        jest.spyOn(filters.filterChanges, 'next');

        const result = filters.addFilter('foo', 'addedValue1', 'addedValue2');

        expect(result).toBe(true);
        expect(filters.filterChanges.next).toHaveBeenCalledTimes(1);
        expect(filters.filterOptions).toMatchObject([
          { name: 'foo', values: ['existingFoo1', 'existingFoo2', 'addedValue1', 'addedValue2'] },
        ]);
      });
      it("doesn't add FilterOption values already added, returns false and doesn't emit next element", () => {
        const filters = new FilterOptions([new FilterOption('foo', ['existingFoo1', 'existingFoo2']), new FilterOption('bar')]);
        jest.spyOn(filters.filterChanges, 'next');

        const result = filters.addFilter('foo', 'existingFoo1', 'existingFoo2');

        expect(result).toBe(false);
        expect(filters.filterChanges.next).not.toBeCalled();
        expect(filters.filterOptions).toMatchObject([{ name: 'foo', values: ['existingFoo1', 'existingFoo2'] }]);
      });
    });

    describe('removeFilter', () => {
      it('removes an existing FilterOptions and returns true', () => {
        const filters = new FilterOptions([new FilterOption('foo', ['existingFoo1', 'existingFoo2']), new FilterOption('bar')]);
        jest.spyOn(filters.filterChanges, 'next');

        const result = filters.removeFilter('foo', 'existingFoo1');

        expect(result).toBe(true);
        expect(filters.filterChanges.next).toHaveBeenCalledTimes(1);
        expect(filters.filterOptions).toMatchObject([{ name: 'foo', values: ['existingFoo2'] }]);
      });
      it("doesn't remove a non existing FilterOptions values returns false", () => {
        const filters = new FilterOptions([new FilterOption('foo', ['existingFoo1', 'existingFoo2']), new FilterOption('bar')]);
        jest.spyOn(filters.filterChanges, 'next');

        const result = filters.removeFilter('foo', 'nonExisting1');

        expect(result).toBe(false);
        expect(filters.filterChanges.next).not.toBeCalled();
        expect(filters.filterOptions).toMatchObject([{ name: 'foo', values: ['existingFoo1', 'existingFoo2'] }]);
      });
      it("doesn't remove a non existing FilterOptions returns false", () => {
        const filters = new FilterOptions([new FilterOption('foo', ['existingFoo1', 'existingFoo2']), new FilterOption('bar')]);
        jest.spyOn(filters.filterChanges, 'next');

        const result = filters.removeFilter('nonExisting', 'nonExisting1');

        expect(result).toBe(false);
        expect(filters.filterChanges.next).not.toBeCalled();
        expect(filters.filterOptions).toMatchObject([{ name: 'foo', values: ['existingFoo1', 'existingFoo2'] }]);
      });
    });

    describe('initializeFromParams', () => {
      const oneValidParam: Params = {
        test: 'blub',
        'filter[hello.in]': 'world',
        'filter[invalid': 'invalid',
        filter_invalid2: 'invalid',
      };

      const noValidParam: Params = {
        test: 'blub',
        'filter[invalid': 'invalid',
        filter_invalid2: 'invalid',
      };

      const paramWithTwoValues: Params = {
        'filter[hello.in]': ['world', 'world2'],
      };

      const paramWithTwoKeys: Params = {
        'filter[hello.in]': ['world', 'world2'],
        'filter[hello.notIn]': ['world3', 'world4'],
      };

      it('should parse from Params if there are any and not emit next element', () => {
        const filters: FilterOptions = new FilterOptions([new FilterOption('foo', ['bar'])]);
        jest.spyOn(filters.filterChanges, 'next');
        const paramMap: ParamMap = convertToParamMap(oneValidParam);

        filters.initializeFromParams(paramMap);

        expect(filters.filterChanges.next).not.toHaveBeenCalled();
        expect(filters.filterOptions).toMatchObject([{ name: 'hello.in', values: ['world'] }]);
      });

      it('should parse from Params and have none if there are none', () => {
        const filters: FilterOptions = new FilterOptions();
        const paramMap: ParamMap = convertToParamMap(noValidParam);
        jest.spyOn(filters.filterChanges, 'next');

        filters.initializeFromParams(paramMap);

        expect(filters.filterChanges.next).not.toHaveBeenCalled();
        expect(filters.filterOptions).toMatchObject([]);
      });

      it('should parse from Params and have a parameter with 2 values and one additional value', () => {
        const filters: FilterOptions = new FilterOptions([new FilterOption('hello.in', ['world'])]);
        jest.spyOn(filters.filterChanges, 'next');

        const paramMap: ParamMap = convertToParamMap(paramWithTwoValues);

        filters.initializeFromParams(paramMap);

        expect(filters.filterChanges.next).not.toHaveBeenCalled();
        expect(filters.filterOptions).toMatchObject([{ name: 'hello.in', values: ['world', 'world2'] }]);
      });

      it('should parse from Params and have a parameter with 2 keys', () => {
        const filters: FilterOptions = new FilterOptions();
        jest.spyOn(filters.filterChanges, 'next');

        const paramMap: ParamMap = convertToParamMap(paramWithTwoKeys);

        filters.initializeFromParams(paramMap);

        expect(filters.filterChanges.next).not.toHaveBeenCalled();
        expect(filters.filterOptions).toMatchObject([
          { name: 'hello.in', values: ['world', 'world2'] },
          { name: 'hello.notIn', values: ['world3', 'world4'] },
        ]);
      });
    });
  });
});
