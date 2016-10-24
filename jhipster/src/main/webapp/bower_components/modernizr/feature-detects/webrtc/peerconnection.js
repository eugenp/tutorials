/*!
{
  "name": "RTC Peer Connection",
  "property": "peerconnection",
  "tags": ["webrtc"],
  "authors": ["Ankur Oberoi"],
  "notes": [{
    "name": "W3C Web RTC spec",
    "href": "https://www.w3.org/TR/webrtc/"
  }]
}
!*/
define(['Modernizr', 'prefixed'], function(Modernizr, prefixed) {
  Modernizr.addTest('peerconnection', !!prefixed('RTCPeerConnection', window));
});
