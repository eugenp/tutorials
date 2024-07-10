import {
  TEST_PM_TIME,
  formatMilliseconds,
  parseMillisecondsIntoInteger,
  parseDigitsIntoInteger,
  getAmString,
  getPmString,
  getSeparator,
  searchAmOrPmToken
} from './helpers.js';

(function () {
  const tryCatchWrapper = function (callback) {
    return window.Vaadin.Flow.tryCatchWrapper(callback, 'Vaadin Time Picker');
  };

  // Execute callback when predicate returns true.
  // Try again later if predicate returns false.
  function when(predicate, callback, timeout = 0) {
    if (predicate()) {
      callback();
    } else {
      setTimeout(() => when(predicate, callback, 200), timeout);
    }
  }

  window.Vaadin.Flow.timepickerConnector = {
    initLazy: (timepicker) =>
      tryCatchWrapper(function (timepicker) {
        // Check whether the connector was already initialized for the timepicker
        if (timepicker.$connector) {
          return;
        }

        timepicker.$connector = {};

        timepicker.$connector.setLocale = tryCatchWrapper(function (locale) {
          // capture previous value if any
          let previousValueObject;
          if (timepicker.value && timepicker.value !== '') {
            previousValueObject = timepicker.i18n.parseTime(timepicker.value);
          }

          try {
            // Check whether the locale is supported by the browser or not
            TEST_PM_TIME.toLocaleTimeString(locale);
          } catch (e) {
            locale = 'en-US';
            // FIXME should do a callback for server to throw an exception ?
            throw new Error(
              'vaadin-time-picker: The locale ' +
                locale +
                ' is not supported, falling back to default locale setting(en-US).'
            );
          }

          // 1. 24 or 12 hour clock, if latter then what are the am/pm strings ?
          const pmString = getPmString(locale);
          const amString = getAmString(locale);

          // 2. What is the separator ?
          const separator = getSeparator(locale);

          const includeSeconds = function () {
            return timepicker.step && timepicker.step < 60;
          };

          const includeMilliSeconds = function () {
            return timepicker.step && timepicker.step < 1;
          };

          let cachedTimeString;
          let cachedTimeObject;

          timepicker.i18n = {
            formatTime: tryCatchWrapper(function (timeObject) {
              if (!timeObject) return;

              const timeToBeFormatted = new Date();
              timeToBeFormatted.setHours(timeObject.hours);
              timeToBeFormatted.setMinutes(timeObject.minutes);
              timeToBeFormatted.setSeconds(timeObject.seconds !== undefined ? timeObject.seconds : 0);

              // the web component expects the correct granularity used for the time string,
              // thus need to format the time object in correct granularity by passing the format options
              let localeTimeString = timeToBeFormatted.toLocaleTimeString(locale, {
                hour: 'numeric',
                minute: 'numeric',
                second: includeSeconds() ? 'numeric' : undefined
              });

              // milliseconds not part of the time format API
              if (includeMilliSeconds()) {
                localeTimeString = formatMilliseconds(localeTimeString, timeObject.milliseconds, amString, pmString);
              }

              return localeTimeString;
            }),

            parseTime: tryCatchWrapper(function (timeString) {
              if (timeString && timeString === cachedTimeString && cachedTimeObject) {
                return cachedTimeObject;
              }

              if (!timeString) {
                // when nothing is returned, the component shows the invalid state for the input
                return;
              }

              const amToken = searchAmOrPmToken(timeString, amString);
              const pmToken = searchAmOrPmToken(timeString, pmString);

              const numbersOnlyTimeString = timeString
                .replace(amToken || '', '')
                .replace(pmToken || '', '')
                .trim();

              // A regexp that allows to find the numbers with optional separator and continuing searching after it.
              const numbersRegExp = new RegExp('([\\d\\u0660-\\u0669]){1,2}(?:' + separator + ')?', 'g');

              let hours = numbersRegExp.exec(numbersOnlyTimeString);
              if (hours) {
                hours = parseDigitsIntoInteger(hours[0].replace(separator, ''));
                // handle 12 am -> 0
                // do not do anything if am & pm are not used or if those are the same,
                // as with locale bg-BG there is always ч. at the end of the time
                if (amToken !== pmToken) {
                  if (hours === 12 && amToken) {
                    hours = 0;
                  }
                  if (hours !== 12 && pmToken) {
                    hours += 12;
                  }
                }
                const minutes = numbersRegExp.exec(numbersOnlyTimeString);
                const seconds = minutes && numbersRegExp.exec(numbersOnlyTimeString);
                // detecting milliseconds from input, expects am/pm removed from end, eg. .0 or .00 or .000
                const millisecondRegExp = /[[\.][\d\u0660-\u0669]{1,3}$/;
                // reset to end or things can explode
                let milliseconds = seconds && includeMilliSeconds() && millisecondRegExp.exec(numbersOnlyTimeString);
                // handle case where last numbers are seconds and . is the separator (invalid regexp match)
                if (milliseconds && milliseconds['index'] <= seconds['index']) {
                  milliseconds = undefined;
                }
                // hours is a number at this point, others are either arrays or null
                // the string in [0] from the arrays includes the separator too
                cachedTimeObject = hours !== undefined && {
                  hours: hours,
                  minutes: minutes ? parseDigitsIntoInteger(minutes[0].replace(separator, '')) : 0,
                  seconds: seconds ? parseDigitsIntoInteger(seconds[0].replace(separator, '')) : 0,
                  milliseconds:
                    minutes && seconds && milliseconds
                      ? parseMillisecondsIntoInteger(milliseconds[0].replace('.', ''))
                      : 0
                };
                cachedTimeString = timeString;
                return cachedTimeObject;
              }
            })
          };

          if (previousValueObject) {
            when(
              () => timepicker.$,
              () => {
                const newValue = timepicker.i18n.formatTime(previousValueObject);
                // FIXME works but uses private API, needs fixes in web component
                if (timepicker.inputElement.value !== newValue) {
                  timepicker.inputElement.value = newValue;
                  timepicker.$.comboBox.value = newValue;
                }
              }
            );
          }
        });
      })(timepicker)
  };
})();
