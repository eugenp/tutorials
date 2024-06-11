import dayjs from 'dayjs/esm';
import customParseFormat from 'dayjs/esm/plugin/customParseFormat';
import duration from 'dayjs/esm/plugin/duration';
import relativeTime from 'dayjs/esm/plugin/relativeTime';

// jhipster-needle-i18n-language-dayjs-imports - JHipster will import languages from dayjs here

// DAYJS CONFIGURATION
dayjs.extend(customParseFormat);
dayjs.extend(duration);
dayjs.extend(relativeTime);
