/*!
{
  "name": "XHR responseType='arraybuffer'",
  "property": "xhrresponsetypearraybuffer",
  "tags": ["network"],
  "notes": [{
    "name": "XMLHttpRequest Living Standard",
    "href": "https://xhr.spec.whatwg.org/#the-responsetype-attribute"
  }]
}
!*/
/* DOC
Tests for XMLHttpRequest xhr.responseType='arraybuffer'.
*/
define(['Modernizr', 'testXhrType'], function(Modernizr, testXhrType) {
  Modernizr.addTest('xhrresponsetypearraybuffer', testXhrType('arraybuffer'));
});
