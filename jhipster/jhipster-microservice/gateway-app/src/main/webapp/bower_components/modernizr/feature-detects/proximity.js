/*!
{
  "authors": ["Cătălin Mariș"],
  "caniuse": "proximity",
  "name": "Proximity API",
  "notes": [{
    "name": "MDN documentation",
    "href": "https://developer.mozilla.org/en-US/docs/Web/API/Proximity_Events"
  },{
    "name": "W3C specification",
    "href": "https://www.w3.org/TR/proximity/"
  }],
  "property": "proximity",
  "tags": ["events", "proximity"]
}
!*/
/* DOC
Detects support for an API that allows users to get proximity related information from the device's proximity sensor.
*/
define(['Modernizr', 'addTest'], function(Modernizr, addTest) {

  Modernizr.addAsyncTest(function() {

    var timeout;
    var timeoutTime = 300;

    function advertiseSupport() {

      // Clean up after ourselves
      clearTimeout(timeout);
      window.removeEventListener('deviceproximity', advertiseSupport);

      // Advertise support as the browser supports
      // the API and the device has a proximity sensor
      addTest('proximity', true);

    }

    // Check if the browser has support for the API
    if ('ondeviceproximity' in window && 'onuserproximity' in window) {

      // Check if the device has a proximity sensor
      // ( devices without such a sensor support the events but
      //   will never fire them resulting in a false positive )
      window.addEventListener('deviceproximity', advertiseSupport);

      // If the event doesn't fire in a reasonable amount of time,
      // it means that the device doesn't have a proximity sensor,
      // thus, we can advertise the "lack" of support
      timeout = setTimeout(function() {
        window.removeEventListener('deviceproximity', advertiseSupport);
        addTest('proximity', false);
      }, timeoutTime);

    } else {
      addTest('proximity', false);
    }

  });

});
