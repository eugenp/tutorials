/*!
{
  "name": "Event Listener",
  "property": "eventlistener",
  "authors": ["Andrew Betts (@triblondon)"],
  "notes": [{
    "name": "W3C Spec",
    "href": "https://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-Registration-interfaces"
  }],
  "polyfills": ["eventlistener"]
}
!*/
/* DOC
Detects native support for addEventListener
*/
define(['Modernizr'], function(Modernizr) {
  Modernizr.addTest('eventlistener', 'addEventListener' in window);
});
