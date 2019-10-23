define(['classes'], function(classes) {
  /**
   * slice exposes Array.prototype.slice to convert array-like objects to actual
   * arrays in a compact format without recreating new arrays over and over again
   * (which leads to decreased performance)
   *
   * @access private
   */

  var slice = classes.slice;
  return slice;
});
