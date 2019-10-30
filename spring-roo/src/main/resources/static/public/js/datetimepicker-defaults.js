(function(jQuery) {
	jQuery(document).ready(function() {

		/*
		 * jQuery Utilities ==================================================
		 */
		/**
		 * Select the most switchable time format for time selectod related to
		 * requiered format
		 *
		 * @param format
		 * @returns time format
		 */
		function getSelectorTimeFormat(format) {
			//
			if (format.search(/h{1,2}/) > -1 && format.search(/[aA]/) > -1) {
				if (format.search(/[A]/) > -1) {
					return "hh:mm A";
				} else {
					return "hh:mm a";
				}
			}
			return "HH:mm";
		}

		// Use the same locale than MomentJs
		// (set it before setDateFormater as setLocale override formatter)
		jQuery.datetimepicker.setLocale(moment.locale());

		// Define parse/format date using moment library
		jQuery.datetimepicker.setDateFormatter({
			parseDate : function(date, format) {
				var d = moment(date, format);
				return d.isValid() ? d.toDate() : false;
			},

			formatDate : function(date, format) {
				return moment(date).format(format);
			}
		});

		jQuery(".datetimepicker").each(function(index) {
			var $input = jQuery(this);
			var options = {
				step : 5
			};

			var pattern = $input.attr("data-dateformat");
			var value = $input.attr("data-timestep");

			if (value) {
				try {
					options.step = parseInt(value);
				} catch (e) {
					timeStep = 5;
				}
			}

			// FormatDate YYYY/MM/DD
			value = $input.attr("data-startdate");
			if (value) {
				options.startDate = value;
			}
			value = $input.attr("data-mindate");
			if (value) {
				options.minDate = value;
			}
			value = $input.attr("data-maxdate");
			if (value) {
				options.maxDate = value;
			}

			// FormatTime : "HH:mm"
			value = $input.attr("data-mintime");
			if (value) {
				options.minTime = value;
			}
			value = $input.attr("data-maxtime");
			if (value) {
				options.maxTime = value;
			}
			value = $input.attr("data-allowtimes");
			if (value) {
				options.allowTimes = value.split(",");
			}

			if (isNotEmpty(pattern)) {
				var momentPattern = moment.javaToMomentDateFormat(pattern);
				jQuery.extend(options, {
					format : momentPattern,
					datepicker : moment.isDateFormatDate(momentPattern),
					timepicker : moment.isDateFormatTime(momentPattern),
					formatDate : "YYYY/MM/DD",
					formatTime : getSelectorTimeFormat(momentPattern)
				});
			} else {
				var emptyMomentPattern = moment.javaToMomentDateFormat();
				jQuery.extend(options, {
					format : emptyMomentPattern,
					formatDate : "YYYY/MM/DD",
					formatTime : "HH:mm"
				});
			}
			$input.datetimepicker(options);
		});
	});
})(jQuery);