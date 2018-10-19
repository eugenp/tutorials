/*!
{
  "name": "XHR responseType='document'",
  "property": "xhrresponsetypedocument",
  "tags": ["network"],
  "notes": [{
    "name": "XMLHttpRequest Living Standard",
    "href": "https://xhr.spec.whatwg.org/#the-responsetype-attribute"
  }]
}
!*/
/* DOC
Tests for XMLHttpRequest xhr.responseType='document'.
*/
define(['Modernizr', 'testXhrType'], function(Modernizr, testXhrType) {
  Modernizr.addTest('xhrresponsetypedocument', testXhrType('document'));
});
