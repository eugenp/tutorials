import { filterNaN, isPresent } from './operators';

describe('Operators Test', () => {
  describe('isPresent', () => {
    it('should remove null and undefined values', () => {
      expect([1, null, undefined].filter(isPresent)).toEqual([1]);
    });
  });

  describe('filterNaN', () => {
    it('should return 0 for NaN', () => {
      expect(filterNaN(NaN)).toBe(0);
    });
    it('should return number for a number', () => {
      expect(filterNaN(12345)).toBe(12345);
    });
  });
});
