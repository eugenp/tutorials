// map from unicode eastern arabic number characters to arabic numbers
const EASTERN_ARABIC_DIGIT_MAP = {
  '\\u0660': '0',
  '\\u0661': '1',
  '\\u0662': '2',
  '\\u0663': '3',
  '\\u0664': '4',
  '\\u0665': '5',
  '\\u0666': '6',
  '\\u0667': '7',
  '\\u0668': '8',
  '\\u0669': '9'
};

/**
 * Escapes the given string so it can be safely used in a regexp.
 *
 * @param {string} string
 * @return {string}
 */
function escapeRegExp(string) {
  return string.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

/**
 * Parses eastern arabic number characters to arabic numbers (0-9)
 *
 * @param {string} digits
 * @return {string}
 */
function parseEasternArabicDigits(digits) {
  return digits.replace(/[\u0660-\u0669]/g, function (char) {
    const unicode = '\\u0' + char.charCodeAt(0).toString(16);
    return EASTERN_ARABIC_DIGIT_MAP[unicode];
  });
}

/**
 * @param {string} locale
 * @param {Date} testTime
 * @return {string | null}
 */
function getAmOrPmString(locale, testTime) {
  const testTimeString = testTime.toLocaleTimeString(locale);

  // AM/PM string is anything from one letter in eastern arabic to standard two letters,
  // to having space in between, dots ...
  // cannot disqualify whitespace since some locales use a. m. / p. m.
  // TODO when more scripts support is added (than Arabic), need to exclude those numbers too
  const amOrPmRegExp = /[^\d\u0660-\u0669]/;

  const matches =
    // In most locales, the time ends with AM/PM:
    testTimeString.match(new RegExp(`${amOrPmRegExp.source}+$`, 'g')) ||
    // In some locales, the time starts with AM/PM e.g in Chinese:
    testTimeString.match(new RegExp(`^${amOrPmRegExp.source}+`, 'g'));

  return matches && matches[0].trim();
}

/**
 * @param {string} locale
 * @return {string | null}
 */
export function getSeparator(locale) {
  let timeString = TEST_PM_TIME.toLocaleTimeString(locale);

  // Since the next regex picks first non-number-whitespace,
  // need to discard possible PM from beginning (eg. chinese locale)
  const pmString = getPmString(locale);
  if (pmString && timeString.startsWith(pmString)) {
    timeString = timeString.replace(pmString, '');
  }

  const matches = timeString.match(/[^\u0660-\u0669\s\d]/);
  return matches && matches[0];
}

/**
 * Searches for either an AM or PM token in the given time string
 * depending on what is provided in `amOrPmString`.
 *
 * The search is case and space insensitive.
 *
 * @example
 * `searchAmOrPmToken('1 P M', 'PM')` => `'P M'`
 *
 * @example
 * `searchAmOrPmToken('1 a.m.', 'A. M.')` => `a.m.`
 *
 * @param {string} timeString
 * @param {string} amOrPmString
 * @return {string | null}
 */
export function searchAmOrPmToken(timeString, amOrPmString) {
  if (!amOrPmString) return null;

  // Create a regexp string for searching for AM/PM without space-sensitivity.
  const tokenRegExpString = amOrPmString.split(/\s*/).map(escapeRegExp).join('\\s*');

  // Create a regexp without case-sensitivity.
  const tokenRegExp = new RegExp(tokenRegExpString, 'i');

  // Match the regexp against the time string.
  const tokenMatches = timeString.match(tokenRegExp);
  if (tokenMatches) {
    return tokenMatches[0];
  }
}

export const TEST_PM_TIME = new Date('August 19, 1975 23:15:30');

export const TEST_AM_TIME = new Date('August 19, 1975 05:15:30');

/**
 * @param {string} locale
 * @return {string}
 */
export function getPmString(locale) {
  return getAmOrPmString(locale, TEST_PM_TIME);
}

/**
 * @param {string} locale
 * @return {string}
 */
export function getAmString(locale) {
  return getAmOrPmString(locale, TEST_AM_TIME);
}

/**
 * @param {string} digits
 * @return {number}
 */
export function parseDigitsIntoInteger(digits) {
  return parseInt(parseEasternArabicDigits(digits));
}

/**
 * @param {string} milliseconds
 * @return {number}
 */
export function parseMillisecondsIntoInteger(milliseconds) {
  milliseconds = parseEasternArabicDigits(milliseconds);
  // digits are either .1 .01 or .001 so need to "shift"
  if (milliseconds.length === 1) {
    milliseconds += '00';
  } else if (milliseconds.length === 2) {
    milliseconds += '0';
  }
  return parseInt(milliseconds);
}

/**
 * @param {string} timeString
 * @param {number} milliseconds
 * @param {string} amString
 * @param {string} pmString
 * @return {string}
 */
export function formatMilliseconds(timeString, milliseconds, amString, pmString) {
  // might need to inject milliseconds between seconds and AM/PM
  let cleanedTimeString = timeString;
  if (timeString.endsWith(amString)) {
    cleanedTimeString = timeString.replace(' ' + amString, '');
  } else if (timeString.endsWith(pmString)) {
    cleanedTimeString = timeString.replace(' ' + pmString, '');
  }
  if (milliseconds) {
    let millisecondsString = milliseconds < 10 ? '0' : '';
    millisecondsString += milliseconds < 100 ? '0' : '';
    millisecondsString += milliseconds;
    cleanedTimeString += '.' + millisecondsString;
  } else {
    cleanedTimeString += '.000';
  }
  if (timeString.endsWith(amString)) {
    cleanedTimeString = cleanedTimeString + ' ' + amString;
  } else if (timeString.endsWith(pmString)) {
    cleanedTimeString = cleanedTimeString + ' ' + pmString;
  }
  return cleanedTimeString;
}
