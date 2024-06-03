import { SortService } from './sort.service';

describe('sort state', () => {
  const service = new SortService();

  describe('parseSortParam', () => {
    it('should accept undefined value', () => {
      const sortState = service.parseSortParam(undefined);
      expect(sortState).toEqual({});
    });
    it('should accept empty string', () => {
      const sortState = service.parseSortParam('');
      expect(sortState).toEqual({});
    });
    it('should accept predicate only string', () => {
      const sortState = service.parseSortParam('predicate');
      expect(sortState).toEqual({ predicate: 'predicate' });
    });
    it('should accept predicate and ASC string', () => {
      const sortState = service.parseSortParam('predicate,asc');
      expect(sortState).toEqual({ predicate: 'predicate', order: 'asc' });
    });
    it('should accept predicate and DESC string', () => {
      const sortState = service.parseSortParam('predicate,desc');
      expect(sortState).toEqual({ predicate: 'predicate', order: 'desc' });
    });
  });
  describe('buildSortParam', () => {
    it('should accept empty object', () => {
      const sortParam = service.buildSortParam({});
      expect(sortParam).toEqual([]);
    });
    it('should accept object with predicate', () => {
      const sortParam = service.buildSortParam({ predicate: 'column' });
      expect(sortParam).toEqual([]);
    });
    it('should accept object with predicate and asc value', () => {
      const sortParam = service.buildSortParam({ predicate: 'column', order: 'asc' });
      expect(sortParam).toEqual(['column,asc']);
    });
    it('should accept object with predicate and desc value', () => {
      const sortParam = service.buildSortParam({ predicate: 'column', order: 'desc' });
      expect(sortParam).toEqual(['column,desc']);
    });
  });
});
