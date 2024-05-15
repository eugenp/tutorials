import dayjs from 'dayjs/esm';

import FormatMediumDatePipe from './format-medium-date.pipe';

describe('FormatMediumDatePipe', () => {
  const formatMediumDatePipe = new FormatMediumDatePipe();

  it('should return an empty string when receive undefined', () => {
    expect(formatMediumDatePipe.transform(undefined)).toBe('');
  });

  it('should return an empty string when receive null', () => {
    expect(formatMediumDatePipe.transform(null)).toBe('');
  });

  it('should format date like this D MMM YYYY', () => {
    expect(formatMediumDatePipe.transform(dayjs('2020-11-16').locale('fr'))).toBe('16 Nov 2020');
  });
});
