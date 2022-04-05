(function(jQuery) {
  jQuery(document).ready(
      function() {

        //dropdown
        jQuery('ul.dropdown-menu [data-toggle=dropdown]').on(
            'click',
            function(event) {
              event.preventDefault();
              event.stopPropagation();
              jQuery(this).parent().siblings()
                  .removeClass('open');
              jQuery(this).parent().toggleClass('open');
            });

        //tooltip
        jQuery('[data-toggle="tooltip"]').tooltip();

      });
})(jQuery);

/**
 * Checks if an object in JavaScript is undefined, null or empty string
 *
 * @param obj
 * @returns {Boolean}
 */
function isEmpty(obj) {
  if (jQuery.isPlainObject(obj)) {
    return jQuery.isEmptyObject(obj);
  } else if (jQuery.isArray(obj)) {
    return 0 === obj.length;
  } else if (typeof obj === "string") {
    return (isNull(obj) || 0 === obj.length);
  }
  return isNull(obj);
}

/**
 * Checks if an object in JavaScript is undefined or null
 *
 * @param obj
 * @returns {Boolean}
 */
function isNull(obj) {
  if (typeof obj === "undefined" || obj == null) {
    return true;
  }
  return false;
}

/**
 * Checks if an object in JavaScript is defined and not null
 *
 * @param obj
 * @returns {Boolean}
 */
function isNotNull(obj) {
  return !isNull(obj);
}

/**
 * Checks if an object in JavaScript is undefined, null or empty string
 *
 * @param obj
 * @returns {Boolean}
 */
function isNotEmpty(obj) {
  return !isEmpty(obj);
}
