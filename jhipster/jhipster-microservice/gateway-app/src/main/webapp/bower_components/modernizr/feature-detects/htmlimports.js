/*!
{
  "name": "HTML Imports",
  "notes": [
    {
      "name": "W3C HTML Imports Specification",
      "href": "https://w3c.github.io/webcomponents/spec/imports/"
    },
    {
      "name": "HTML Imports - #include for the web",
      "href": "http://www.html5rocks.com/en/tutorials/webcomponents/imports/"
    }
  ],
  "polyfills": ["polymer-htmlimports"],
  "property": "htmlimports",
  "tags": ["html", "import"]
}
!*/
/* DOC
Detects support for HTML import, a feature that is used for loading in Web Components.
 */

define(['addTest', 'createElement'], function(addTest, createElement) {
  addTest('htmlimports', 'import' in createElement('link'));
});
