import dateFnsFormat from 'date-fns/format';
import dateFnsParse from 'date-fns/parse';
import dateFnsIsValid from 'date-fns/isValid';
import { extractDateParts, parseDate as _parseDate } from '@vaadin/date-picker/src/vaadin-date-picker-helper.js';

(function () {
  const tryCatchWrapper = function (callback) {
    return window.Vaadin.Flow.tryCatchWrapper(callback, 'Vaadin Date Picker');
  };

  window.Vaadin.Flow.datepickerConnector = {
    initLazy: (datepicker) =>
      tryCatchWrapper(function (datepicker) {
        // Check whether the connector was already initialized for the datepicker
        if (datepicker.$connector) {
          return;
        }

        datepicker.$connector = {};

        const createLocaleBasedDateFormat = function (locale) {
          try {
            // Check whether the locale is supported or not
            new Date().toLocaleDateString(locale);
          } catch (e) {
            console.warn('The locale is not supported, using default format setting (ISO 8601).');
            return 'yyyy-MM-dd';
          }

          // format test date and convert to date-fns pattern
          const testDate = new Date(Date.UTC(1234, 4, 6));
          let pattern = testDate.toLocaleDateString(locale, { timeZone: 'UTC' });
          pattern = pattern
            // escape date-fns pattern letters by enclosing them in single quotes
            .replace(/([a-zA-Z]+)/g, "'$1'")
            // insert date placeholder
            .replace('06', 'dd')
            .replace('6', 'd')
            // insert month placeholder
            .replace('05', 'MM')
            .replace('5', 'M')
            // insert year placeholder
            .replace('1234', 'yyyy');
          const isValidPattern = pattern.includes('d') && pattern.includes('M') && pattern.includes('y');
          if (!isValidPattern) {
            console.warn('The locale is not supported, using default format setting (ISO 8601).');
            return 'yyyy-MM-dd';
          }

          return pattern;
        };

        const createFormatterAndParser = tryCatchWrapper(function (formats) {
          if (!formats || formats.length === 0) {
            throw new Error('Array of custom date formats is null or empty');
          }

          function getShortYearFormat(format) {
            if (format.includes('yyyy') && !format.includes('yyyyy')) {
              return format.replace('yyyy', 'yy');
            }
            if (format.includes('YYYY') && !format.includes('YYYYY')) {
              return format.replace('YYYY', 'YY');
            }
            return undefined;
          }

          function isShortYearFormat(format) {
            if (format.includes('y')) {
              return !format.includes('yyy');
            }
            if (format.includes('Y')) {
              return !format.includes('YYY');
            }
            return false;
          }

          function correctFullYear(date) {
            // The last parsed date check handles the case where a four-digit year is parsed, then formatted
            // as a two-digit year, and then parsed again. In this case we want to keep the century of the
            // originally parsed year, instead of using the century of the reference date.

            // Do not apply any correction if the previous parse attempt was failed.
            if (datepicker.$connector._lastParseStatus === 'error') {
              return;
            }

            // Update century if the last parsed date is the same except the century.
            if (datepicker.$connector._lastParseStatus === 'successful') {
              if (datepicker.$connector._lastParsedDate.day === date.getDate() &&
                datepicker.$connector._lastParsedDate.month === date.getMonth() &&
                datepicker.$connector._lastParsedDate.year % 100 === date.getFullYear() % 100) {
                date.setFullYear(datepicker.$connector._lastParsedDate.year);
              }
              return;
            }

            // Update century if this is the first parse after overlay open.
            const currentValue = _parseDate(datepicker.value);
            if (dateFnsIsValid(currentValue) &&
              currentValue.getDate() === date.getDate() &&
              currentValue.getMonth() === date.getMonth() &&
              currentValue.getFullYear() % 100 === date.getFullYear() % 100) {
              date.setFullYear(currentValue.getFullYear());
            }
          }

          function formatDate(dateParts) {
            const format = formats[0];
            const date = _parseDate(`${dateParts.year}-${dateParts.month + 1}-${dateParts.day}`);

            return dateFnsFormat(date, format);
          }

          function doParseDate(dateString, format, referenceDate) {
            const date = dateFnsParse(dateString, format, referenceDate);
            if (dateFnsIsValid(date)) {
              if (isShortYearFormat(format)) {
                correctFullYear(date);
              }
              return {
                day: date.getDate(),
                month: date.getMonth(),
                year: date.getFullYear()
              };
            }
          }

          function parseDate(dateString) {
            const referenceDate = _getReferenceDate();
            for (let format of formats) {
              const isShortFormat = isShortYearFormat(format);
              // We first try to match the date with the shorter version.
              if (!isShortFormat) {
                const parsedDate = doParseDate(dateString, getShortYearFormat(format), referenceDate);
                if (parsedDate) {
                  datepicker.$connector._lastParseStatus = 'successful';
                  datepicker.$connector._lastParsedDate = parsedDate;
                  return parsedDate;
                }
              }
              const parsedDate = doParseDate(dateString, format, referenceDate);
              if (parsedDate) {
                datepicker.$connector._lastParseStatus = 'successful';
                datepicker.$connector._lastParsedDate = parsedDate;
                return parsedDate;
              }
            }
            datepicker.$connector._lastParseStatus = 'error';
            return false;
          }

          return {
            formatDate: formatDate,
            parseDate: parseDate
          };
        });

        function _getReferenceDate() {
          const { referenceDate } = datepicker.i18n;
          return referenceDate ? new Date(referenceDate.year, referenceDate.month, referenceDate.day) : new Date();
        }

        datepicker.$connector.updateI18n = tryCatchWrapper(function (locale, i18n) {
          // Either use custom formats specified in I18N, or create format from locale
          const hasCustomFormats = i18n && i18n.dateFormats && i18n.dateFormats.length > 0;
          if (i18n && i18n.referenceDate) {
            i18n.referenceDate = extractDateParts(new Date(i18n.referenceDate));
          }
          const usedFormats = hasCustomFormats ? i18n.dateFormats : [createLocaleBasedDateFormat(locale)];
          const formatterAndParser = createFormatterAndParser(usedFormats);

          // Merge current web component I18N settings with new I18N settings and the formatting and parsing functions
          datepicker.i18n = Object.assign({}, datepicker.i18n, i18n, formatterAndParser);
        });

        datepicker.addEventListener('opened-changed', () => datepicker.$connector._lastParseStatus = undefined);
      })(datepicker)
  };
})();
