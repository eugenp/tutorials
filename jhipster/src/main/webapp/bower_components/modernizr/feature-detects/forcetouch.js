/*!
{
  "name": "Force Touch Events",
  "property": "forcetouch",
  "authors": ["Kraig Walker"],
  "notes": [{
    "name": "Responding to Force Touch Events from JavaScript",
    "href": "https://developer.apple.com/library/prerelease/mac/documentation/AppleApplications/Conceptual/SafariJSProgTopics/Articles/RespondingtoForceTouchEventsfromJavaScript.html"
  }]
}
!*/
/* DOC
Tests whether the browser supports the detection of Force Touch Events.
Force Touch Events allow custom behaviours and interactions to take place based on the given pressure or change in pressure from a compatible trackpad.

Force Touch events are available in OS X 10.11 and later on devices equipped with Force Touch trackpads.
*/
define(['Modernizr', 'hasEvent', 'prefixed'], function(Modernizr, hasEvent, prefixed) {
  Modernizr.addTest('forcetouch', function() {
		// github.com/Modernizr/Modernizr/issues/1613
    // Test if the browser supports the force touch event progression (see notes link)
    if (!hasEvent(prefixed('mouseforcewillbegin', window, false), window)) {
          return false;
    }

    // Test if the browser provides thresholds defining a "force touch" from a normal touch/click event
    return MouseEvent.WEBKIT_FORCE_AT_MOUSE_DOWN && MouseEvent.WEBKIT_FORCE_AT_FORCE_MOUSE_DOWN;
  });
});
