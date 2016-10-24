/*!
{
  "name": "Low Battery Level",
  "property": "lowbattery",
  "tags": ["hardware", "mobile"],
  "builderAliases": ["battery_level"],
  "authors": ["Paul Sayre"],
  "notes": [{
    "name": "MDN Docs",
    "href": "https://developer.mozilla.org/en-US/docs/Web/API/Navigator/battery"
  }]
}
!*/
/* DOC
Enable a developer to remove CPU intensive CSS/JS when battery is low
*/
define(['Modernizr', 'prefixed'], function(Modernizr, prefixed) {
  Modernizr.addTest('lowbattery', function() {
    var minLevel = 0.20;
    var battery = prefixed('battery', navigator);
    return !!(battery && !battery.charging && battery.level <= minLevel);
  });
});
