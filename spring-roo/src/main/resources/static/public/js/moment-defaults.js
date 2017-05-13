(function(jQuery) {

	moment().format();

	/**
	 * Convert Java's SimpleDateFormat to momentJS formatDate. Takes a Java
	 * pattern
	 * (http://docs.oracle.com/javase/6/docs/api/java/text/SimpleDateFormat.html)
	 * and turns it into the expected momentJS formatDate
	 * (http://momentjs.com/docs/#/parsing/string-format/).
	 *
	 * @param pattern
	 *            SimpleDateFormat pattern
	 * @return moment pattern (if 'pattern' is ommited return defautl
	 *         pattern)
	 */
	moment.javaToMomentDateFormat = function (pattern) {
		if (pattern) {
			// Year
			if (pattern.search(/y{3,}/g) >= 0) {
				pattern = pattern.replace(/y{3,}/g, "YYYY"); // yyyy to
				// yy
			} else if (pattern.search(/y{2}/g) >= 0) { // yy to YY
				pattern = pattern.replace(/y{2}/g, "YY");
			}

			// Day
			if (pattern.search(/d{2,}/g) >= 0) { // dd to DD
				pattern = pattern.replace(/d{2,}/g, "DD");
			} else if (pattern.search(/d{1}/g) >= 0) { // d to D
				pattern = pattern.replace(/d{1}/g, "D");
			} else if (pattern.search(/D{1,}/g) >= 0) { // D,DD, DDD to DDD
				pattern = pattern.replace(/D{1,}/g, "DDD");
			}

			// Day in week
			if (pattern.search(/E{4,}/g) >= 0) { // EEEE to dddd
				pattern = pattern.replace(/E{4,}/g, "dddd");
			} else if (pattern.search(/E{2,3}/g) >= 0) { // EEE to ddd
				pattern = pattern.replace(/E{2,3}/g, "ddd");
			}

			// Day in week (number)
			if (pattern.search(/F{1}/g) >= 0) { // F to e
				pattern = pattern.replace(/F{1}/g, "e");
			}

			// week of the year
			if (pattern.search(/w{1,}/g) >= 0) { // ww to WW
				pattern = pattern.replace(/w{1,}/g, "WW");
			}
		} else {
			return "YYYY/MM/DD HH:mm";
		}

		return pattern;
	}

	/**
	 * Informs if date format (momentJS) includes date information
	 *
	 * @param format
	 *            string
	 * @returns true if !format or format contains ('YQDMdw')
	 */
	moment.isDateFormatDate = function (format) {
		if (!format) {
			return true;
		}
		return format.search(/[YQDMdw]/) > -1;
	}

	/**
	 * Informs if date format (ISO 8601) includes time information
	 *
	 * @param format
	 *            string
	 * @returns true if !format or format contains ('HmAasSZ')
	 */
	moment.isDateFormatTime = function (format) {
		if (!format) {
			return true;
		}
		return format.search(/[HhmAasSZ]/) > -1;
		;
	}

})(jQuery);
