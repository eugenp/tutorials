/*!
{
  "name": "Ambient Light Events",
  "property": "ambientlight",
  "notes": [{
    "name": "W3C Ambient Light Events",
    "href": "https://www.w3.org/TR/ambient-light/"
  }]
}
!*/
/* DOC
Detects support for the API that provides information about the ambient light levels, as detected by the device's light detector, in terms of lux units.
*/
define(['Modernizr', 'hasEvent'], function(Modernizr, hasEvent) {
  Modernizr.addTest('ambientlight', hasEvent('devicelight', window));
});
