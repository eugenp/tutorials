/*!
{
  "name": "XHR responseType='text'",
  "property": "xhrresponsetypetext",
  "tags": ["network"],
  "notes": [{
    "name": "XMLHttpRequest Living Standard",
    "href": "https://xhr.spec.whatwg.org/#the-responsetype-attribute"
  }]
}
!*/
/* DOC
Tests for XMLHttpRequest xhr.responseType='text'.
*/
define(['Modernizr', 'testXhrType'], function(Modernizr, testXhrType) {
  Modernizr.addTest('xhrresponsetypetext', testXhrType('text'));
});
